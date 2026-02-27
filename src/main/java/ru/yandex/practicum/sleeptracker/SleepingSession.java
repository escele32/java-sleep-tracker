package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {

    private SleepQuality sleepQuality;
    private LocalDateTime beginningSleepDateTime;
    private LocalDateTime endSleepDateTime;

    public SleepingSession(LocalDateTime beginningSleep, LocalDateTime endSleep, SleepQuality sleepQuality) {
        this.beginningSleepDateTime = beginningSleep;
        this.endSleepDateTime = endSleep;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        return beginningSleepDateTime.format(formatter) + ";" + endSleepDateTime.format(formatter) + ";" + sleepQuality;
    }

}
