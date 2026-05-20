package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public class SleepSession {
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private String dreamStatus;

    public SleepSession(LocalDateTime dateBegin, LocalDateTime dateEnd, String dreamStatus) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.dreamStatus = dreamStatus;
    }

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public String getDreamStatus() {
        return dreamStatus;
    }

    @Override
    public String toString() {
        return "SleepSession{" +
                "dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                ", dreamStatus='" + dreamStatus + '\'' +
                '}';
    }
}
