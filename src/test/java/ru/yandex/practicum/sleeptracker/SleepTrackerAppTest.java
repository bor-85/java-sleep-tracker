package ru.yandex.practicum.sleeptracker;


import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SleepTrackerAppTest {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static SleepSession session(String begin, String end, String status) {
        return new SleepSession(
                LocalDateTime.parse(begin, FORMATTER),
                LocalDateTime.parse(end, FORMATTER),
                status
        );
    }

    //SleepSessionsAmount
    @Test
    void shouldReturnAmountForThreeSessions() {
        SleepAnalysisResult result = new SleepSessionsAmount().apply(List.of(
                session("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                session("02.10.25 23:50", "03.10.25 06:40", "NORMAL"),
                session("03.10.25 14:10", "03.10.25 15:00", "NORMAL")
        ));

        assertEquals("Количество сессий сна: 3", result.toString());
    }

    @Test
    void shouldReturnAmountForSingleSession() {
        SleepAnalysisResult result = new SleepSessionsAmount().apply(List.of(
                session("01.10.25 23:15", "02.10.25 07:30", "GOOD")
        ));

        assertEquals("Количество сессий сна: 1", result.toString());
    }

    //SleepSessionsMinDuration
    @Test
    void shouldReturnMinDurationInManySessions() {
        SleepAnalysisResult result = new SleepSessionsMinDuration().apply(List.of(
                session("01.10.25 10:00", "01.10.25 11:00", "NORMAL"), // 60
                session("01.10.25 12:00", "01.10.25 12:30", "NORMAL"), // 30
                session("01.10.25 23:00", "02.10.25 01:00", "GOOD")    // 120
        ));

        assertEquals("Минимальная продолжительность сессии (в минутах): 30", result.toString());
    }

    @Test
    void shouldReturnMinDurationOneSession() {
        SleepAnalysisResult result = new SleepSessionsMinDuration().apply(List.of(
                session("01.10.25 08:00", "01.10.25 08:15", "NORMAL") // 15
        ));

        assertEquals("Минимальная продолжительность сессии (в минутах): 15", result.toString());
    }

    //SleepSessionsMaxDuration
    @Test
    void shouldReturnMaxDurationInManySessions() {
        SleepAnalysisResult result = new SleepSessionsMaxDuration().apply(List.of(
                session("01.10.25 10:00", "01.10.25 11:00", "NORMAL"), // 60
                session("01.10.25 12:00", "01.10.25 12:30", "NORMAL"), // 30
                session("01.10.25 23:00", "02.10.25 01:30", "GOOD")    // 150
        ));

        assertEquals("Максимальная продолжительность сессии (в минутах): 150", result.toString());
    }

    @Test
    void shouldReturnAnotherMaxDurationOneSession() {
        SleepAnalysisResult result = new SleepSessionsMaxDuration().apply(List.of(
                session("01.10.25 10:00", "01.10.25 11:30", "NORMAL") // 90
        ));

        assertEquals("Максимальная продолжительность сессии (в минутах): 90", result.toString());
    }

    //SleepSessionsAvgDuration
    @Test
    void shouldReturnAverageDuration() {
        SleepAnalysisResult result = new SleepSessionsAvgDuration().apply(List.of(
                session("01.10.25 10:00", "01.10.25 10:30", "NORMAL"), // 30
                session("01.10.25 12:00", "01.10.25 13:30", "NORMAL")  // 90
        ));

        assertEquals("Средняя продолжительность сессии (в минутах): 60,00", result.toString());
    }

    @Test
    void shouldReturnAverageDurationOneSession() {
        SleepAnalysisResult result = new SleepSessionsAvgDuration().apply(List.of(
                session("01.10.25 08:00", "01.10.25 08:15", "NORMAL") // 15
        ));

        assertEquals("Средняя продолжительность сессии (в минутах): 15,00", result.toString());
    }

    //SleepSessionsBadQualityCount
    @Test
    void shouldCountTwoBadSessions() {
        SleepAnalysisResult result = new SleepSessionsBadQualityCount().apply(List.of(
                session("01.10.25 10:00", "01.10.25 11:00", "BAD"),
                session("01.10.25 12:00", "01.10.25 13:00", "GOOD"),
                session("01.10.25 23:00", "02.10.25 01:00", "BAD")
        ));

        assertEquals("Количество сессий с плохим качеством сна: 2", result.toString());
    }

    @Test
    void shouldReturnZeroWhenNoBadSessions() {
        SleepAnalysisResult result = new SleepSessionsBadQualityCount().apply(List.of(
                session("01.10.25 10:00", "01.10.25 11:00", "GOOD"),
                session("01.10.25 12:00", "01.10.25 13:00", "NORMAL")
        ));

        assertEquals("Количество сессий с плохим качеством сна: 0", result.toString());
    }

    //SleepSessionsChronotype
    @Test
    void shouldReturnOwl() {
        SleepAnalysisResult result = new SleepSessionsChronotype().apply(List.of(
                session("01.10.25 23:30", "02.10.25 09:30", "GOOD"),   // OWL
                session("02.10.25 23:40", "03.10.25 09:10", "GOOD"),   // OWL
                session("03.10.25 21:30", "04.10.25 06:30", "GOOD")   // LARK
        ));

        assertEquals("Хронотип по логу: Сова", result.toString());
    }

    @Test
    void shouldReturnPigeon() {
        SleepAnalysisResult result = new SleepSessionsChronotype().apply(List.of(
                session("01.10.25 23:30", "02.10.25 09:30", "GOOD"),   // OWL
                session("02.10.25 21:30", "03.10.25 06:30", "GOOD"),   // LARK
                session("03.10.25 22:30", "04.10.25 07:30", "GOOD")    // PIGEON
        ));

        assertEquals("Хронотип по логу: Голубь", result.toString());
    }

    //SleepSessionSleeplessNights

    private List<SleepSession> sampleLog() {
        return List.of(
                session("01.10.25 23:15", "02.10.25 07:30", "GOOD"),
                session("02.10.25 23:50", "03.10.25 06:40", "NORMAL"),
                session("03.10.25 14:10", "03.10.25 15:00", "NORMAL"),
                session("03.10.25 23:40", "04.10.25 08:00", "BAD"),
                session("05.10.25 00:10", "05.10.25 06:20", "GOOD"),
                session("05.10.25 13:30", "05.10.25 14:15", "NORMAL"),
                session("06.10.25 22:30", "07.10.25 05:50", "GOOD"),
                session("07.10.25 23:45", "08.10.25 06:30", "GOOD"),
                session("08.10.25 23:50", "09.10.25 07:10", "GOOD"),
                session("10.10.25 13:00", "10.10.25 14:30", "NORMAL"),
                session("10.10.25 23:55", "11.10.25 06:10", "GOOD"),
                session("11.10.25 23:10", "12.10.25 07:00", "BAD"),
                session("30.10.25 23:50", "31.10.25 06:30", "GOOD")
        );
    }

    @Test
    void shouldReturnTwentySleeplessNightsForSampleLog() {
        SleepAnalysisResult result = new SleepSessionSleeplessNights().apply(sampleLog());

        assertEquals("Количество ночей без сна: 20", result.toString());
    }

    @Test
    void shouldCountOneSleeplessNight() {
        SleepAnalysisResult result = new SleepSessionSleeplessNights().apply(List.of(
                session("05.10.25 00:10", "05.10.25 06:20", "GOOD")
        ));

        assertEquals("Количество ночей без сна: 1", result.toString());
    }

    @Test
    void shouldReturnZeroWhenSingleNight() {
        SleepAnalysisResult result = new SleepSessionSleeplessNights().apply(List.of(
                session("30.10.25 23:50", "31.10.25 06:30", "GOOD")
        ));

        assertEquals("Количество ночей без сна: 0", result.toString());
    }

    @Test
    void shouldReturnCorrectSleeplessNightWhenMonthChange() {
        SleepAnalysisResult result = new SleepSessionSleeplessNights().apply(List.of(
                session("30.10.25 23:50", "31.10.25 06:30", "GOOD"),
                session("01.11.25 23:50", "02.11.25 06:30", "GOOD")
        ));

        assertEquals("Количество ночей без сна: 1", result.toString());
    }
}
