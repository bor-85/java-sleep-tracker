package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class SleepSessionsAmount implements SleepTrackerFunction {

    //количество сессий сна, зафиксированных в логе
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        int amountSessions = sleepSessions.size();
        return new SleepAnalysisResult("Количество сессий сна", amountSessions);
    }
}
