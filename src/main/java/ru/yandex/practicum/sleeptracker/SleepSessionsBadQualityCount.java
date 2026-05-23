package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class SleepSessionsBadQualityCount implements SleepTrackerFunction {
    private static final String BAD_STATUS = "BAD";

    //Количество ночей с плохим качеством сна
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        long count = sleepSessions.stream()
                .filter(sleepSession -> sleepSession.getDreamStatus().equals(BAD_STATUS))
                .count();
        return new SleepAnalysisResult("Количество сессий с плохим качеством сна", count);
    }
}
