package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepSessionsAmount implements Function<List<SleepSession>,SleepAnalysisResult> {

    //количество сессий сна, зафиксированных в логе
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        int amountSessions = sleepSessions.size();
        return new SleepAnalysisResult("Количество сессий сна", String.valueOf(amountSessions));
    }
}
