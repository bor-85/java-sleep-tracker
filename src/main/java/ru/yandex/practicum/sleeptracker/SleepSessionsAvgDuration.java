package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class SleepSessionsAvgDuration implements SleepTrackerFunction {

    //Средняя продолжительность сна
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        double avgDuration = sleepSessions.stream()
                .mapToLong(sleepSession -> Duration.between(sleepSession.getDateBegin(), sleepSession.getDateEnd()).toMinutes())
                .average()
                .orElse(0);
        String result = String.format(Locale.US, "%.2f", avgDuration);
        return new SleepAnalysisResult("Средняя продолжительность сессии (в минутах)", result);
    }
}
