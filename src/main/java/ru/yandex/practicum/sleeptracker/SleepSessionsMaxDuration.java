package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class SleepSessionsMaxDuration implements SleepTrackerFunction {

    //Максимальная продолжительность сна
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long maxDuration = sleepSessions.stream()
                .mapToLong(sleepSession -> Duration.between(sleepSession.getDateBegin(), sleepSession.getDateEnd()).toMinutes())
                .max()
                .orElse(0);
        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах)", maxDuration);
    }
}
