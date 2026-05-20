package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class SleepSessionsMinDuration implements Function<List<SleepSession>,SleepAnalysisResult> {

    //Минимальная продолжительность сна
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long minDuration = sleepSessions.stream()
                .mapToLong(sleepSession -> Duration.between(sleepSession.getDateBegin(), sleepSession.getDateEnd()).toMinutes())
                .min()
                .orElse(0);
        return new SleepAnalysisResult("Минимальная продолжительность сессии (в минутах)", String.valueOf(minDuration));
    }
}
