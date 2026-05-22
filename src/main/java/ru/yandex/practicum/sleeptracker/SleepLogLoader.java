package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

public class SleepLogLoader {

    private final String filename;

    public SleepLogLoader(String filename) {
        this.filename = filename;
    }

    public List<String> getLog() throws IncomingLogReadException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8)) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IncomingLogReadException("Метод getLog - ошибка чтения лога сна: " + filename);
        }

    }

}