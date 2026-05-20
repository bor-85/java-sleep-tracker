package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    static List<SleepSession> sleepSessionList = new ArrayList<>();
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    static List<Function<List<SleepSession>, SleepAnalysisResult>> listFunctions = List.of(new SleepSessionsAmount(),
            new SleepSessionsMinDuration(),
            new SleepSessionsMaxDuration(),
            new SleepSessionsAvgDuration(),
            new SleepSessionsBadQualityCount(),
            new SleepSessionsChronotype(),
            new SleepSessionSleeplessNights()
    );

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            System.out.println("Не передан путь к файлу лога");
            return;
        }

        try {
            //прием пути к расположению файла из аргумента командной строки
            String pathToFile = args[0];

            //Подготовка данных
            SleepLogLoader sleepLogLoader = new SleepLogLoader(pathToFile);

            //1.Загрузка данных о сессиях из файла в список
            List<String> listLogStrings = sleepLogLoader.getLog();

            //2.Преобразование строковых данных в объекты SleepSession
            sleepLogParse(listLogStrings);
            if (sleepSessionList.isEmpty()) {
                throw new NoSleepSessionsException("Main - Лог сна пуст, анализ невозможен.");
            }

            //3.Проход по всем функциям и вывод информации на экран
            calculateMetrics();

        } catch (IncomingLogReadException e) {
            System.err.println("Ошибка загрузки данных из файла лога сна: " + e.getMessage());
        } catch (NoSleepSessionsException e) {
            System.err.println("Ошибка списка сессий: " + e.getMessage());
        } catch (ReadLogException e) {
            System.err.println("Ошибка чтения лога: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //метод парсит список строк файла и заполняет список объектов SleepSession
    public static void sleepLogParse(List<String> listLogStrings) throws ReadLogException {
        try {
            sleepSessionList.addAll(
                    listLogStrings.stream()
                            .map(line -> line.split(";"))
                            .map(parts -> new SleepSession(LocalDateTime.parse(parts[0], dateTimeFormatter),
                                    LocalDateTime.parse(parts[1], dateTimeFormatter),
                                    parts[2]))
                            .toList()
            );
        } catch (Exception e) {
            throw new ReadLogException("Ошибка парсинга строки лога", e);
        }

    }

    //метод проходит по списку функций и выполняет их
    public static void calculateMetrics() {
        listFunctions.forEach(function -> {
            SleepAnalysisResult result = function.apply(sleepSessionList);
            System.out.println(result);
        });
    }
}