package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public interface SleepTrackerFunction extends Function<List<SleepSession>, SleepAnalysisResult> {
}
