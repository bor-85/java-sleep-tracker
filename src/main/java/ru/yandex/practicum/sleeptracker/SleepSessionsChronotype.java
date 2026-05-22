package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SleepSessionsChronotype  implements SleepTrackerFunction {
    private static final LocalTime OWL_SLEEP_TIME = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_TIME = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_TIME = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_TIME = LocalTime.of(7, 0);

    //Вычисление хронотипа
    @Override
    public SleepAnalysisResult apply(List<SleepSession> sleepSessions) {
        Map<Chronotype, Long> chronotypeMap = sleepSessions.stream()
                // фильтры
                .filter(session -> !session.getDateEnd().isBefore(session.getDateBegin()))
                .filter(session -> !session.getDateBegin().toLocalDate()
                        .equals(session.getDateEnd().toLocalDate()))
                // классификация
                .collect(Collectors.groupingBy(
                        session -> {
                            LocalTime sleep = session.getDateBegin().toLocalTime();
                            LocalTime wake = session.getDateEnd().toLocalTime();

                            if (sleep.isAfter(OWL_SLEEP_TIME) &&
                                    wake.isAfter(OWL_WAKE_TIME)) {
                                return Chronotype.OWL;
                            } else if (sleep.isBefore(LARK_SLEEP_TIME) &&
                                    wake.isBefore(LARK_WAKE_TIME)) {
                                return Chronotype.LARK;
                            } else {
                                return Chronotype.PIGEON;
                            }
                        },
                        () -> new EnumMap<>(Chronotype.class),
                        Collectors.counting()
                ));

        long owl = chronotypeMap.getOrDefault(Chronotype.OWL, 0L);
        long lark = chronotypeMap.getOrDefault(Chronotype.LARK, 0L);
        long pigeon = chronotypeMap.getOrDefault(Chronotype.PIGEON, 0L);

        Chronotype result;
        if (owl > lark && owl > pigeon) {
            result = Chronotype.OWL;
        } else if (lark > owl && lark > pigeon) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.PIGEON;
        }

        return new SleepAnalysisResult("Хронотип по логу", result.getRuName());
    }
}
