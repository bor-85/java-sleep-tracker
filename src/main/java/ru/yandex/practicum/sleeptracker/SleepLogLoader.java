package ru.yandex.practicum.sleeptracker;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

public class SleepLogLoader {

    private final String filename;

    public SleepLogLoader(String filename) {
        this.filename = filename;
    }

    public List<String> getLog() throws IncomingLogReadException {
        try {
            return Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IncomingLogReadException("Метод getLog - ошибка чтения лога сна: " + filename);
        }

    }

}