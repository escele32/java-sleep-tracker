package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {

    private SleepQuality sleepQuality;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    private LocalDateTime beginningSleepDateTime;
    private LocalDateTime endSleepDateTime;

    public SleepingSession(String beginningSleep, String endSleep, SleepQuality sleepQuality) {
        this.beginningSleepDateTime = LocalDateTime.parse(beginningSleep, formatter);
        this.endSleepDateTime = LocalDateTime.parse(endSleep, formatter);
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getBeginningSleepDateTime() {
        return beginningSleepDateTime;
    }

    public LocalDateTime getEndSleepDateTime() {
        return endSleepDateTime;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    @Override
    public String toString() {
        return beginningSleepDateTime.format(formatter) + ";" + endSleepDateTime.format(formatter) + ";" + sleepQuality;
    }

}
