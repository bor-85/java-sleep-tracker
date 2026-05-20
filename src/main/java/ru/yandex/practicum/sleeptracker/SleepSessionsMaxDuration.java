package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class SleepSessionsMaxDuration implements Function<List<SleepSession>,SleepAnalysisResult> {

    //Максимальная продолжительность сна
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long maxDuration = sleepSessions.stream()
                .mapToLong(sleepSession -> Duration.between(sleepSession.getDateBegin(), sleepSession.getDateEnd()).toMinutes())
                .max()
                .orElse(0);
        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах)", String.valueOf(maxDuration));
    }
}
