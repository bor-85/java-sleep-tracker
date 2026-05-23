package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Stream;

public class SleepSessionSleeplessNights implements SleepTrackerFunction {

    //Вычисление количества бессонных ночей
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {

        long sleepNights = sleepSessions.stream()
                .filter(this::isIntersectsNight)
                .count();

        long totalNights = getTotalNights(sleepSessions);

        return new SleepAnalysisResult("Количество ночей без сна", totalNights - sleepNights);
    }

    //вычисление, что ночь попадает в период с 00:00 по 06:00
    private boolean isIntersectsNight(SleepSession session) {
        LocalDateTime nightStart = session.getDateEnd().toLocalDate().atStartOfDay(); // 00:00
        LocalDateTime nightEnd = nightStart.plusHours(6);                              // 06:00

        return session.getDateBegin().isBefore(nightEnd)
                && session.getDateEnd().isAfter(nightStart);
    }

    //вычисление общего количества ночей
    private long getTotalNights(List<SleepSession> sleepSessions) {
        //дата старта лога
        LocalDateTime firstBegin = sleepSessions.stream()
                .map(SleepSession::getDateBegin)
                .min(LocalDateTime::compareTo)
                .orElseThrow();
        //дата окончания лога
        LocalDateTime lastEnd = sleepSessions.stream()
                .map(SleepSession::getDateEnd)
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        // по условию: если первая сессия после 12 — начинаем со следующей ночи,
        // если до 12 — с предыдущей
        LocalDate startDate = firstBegin.toLocalTime().isAfter(LocalTime.NOON)
                ? firstBegin.toLocalDate().plusDays(1)
                : firstBegin.toLocalDate().minusDays(1);

        LocalDate endDate = lastEnd.toLocalDate();

        // Period используем для границы периода
        Period period = Period.between(startDate, endDate.plusDays(1));
        LocalDate endExclusive = startDate.plus(period);

        return Stream.iterate(startDate, date -> date.plusDays(1))
                .takeWhile(date -> date.isBefore(endExclusive))
                .count();
    }


}
