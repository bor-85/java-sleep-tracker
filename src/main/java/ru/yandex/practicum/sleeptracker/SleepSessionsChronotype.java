package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepSessionsChronotype  implements Function<List<SleepSession>,SleepAnalysisResult> {

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

                            if (sleep.isAfter(LocalTime.of(23, 0)) &&
                                    wake.isAfter(LocalTime.of(9, 0))) {
                                return Chronotype.OWL;
                            } else if (sleep.isBefore(LocalTime.of(22, 0)) &&
                                    wake.isBefore(LocalTime.of(7, 0))) {
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

        return new SleepAnalysisResult("Хронотип по логу", String.valueOf(result.getRuName()));
    }
}
