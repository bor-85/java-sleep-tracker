package ru.yandex.practicum.sleeptracker;

public class NoSleepSessionsException extends RuntimeException {
    public NoSleepSessionsException(String message) {
        super(message);
    }
}