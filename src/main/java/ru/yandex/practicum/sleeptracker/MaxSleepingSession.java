package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MaxSleepingSession implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public MaxSleepingSession(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция для подсчёта максимальной продолжительности сессии сна (в минутах). Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionsList) {
        long maxSleepMinutes = sleepingSessionsList.stream()
                .map(sleepingSession ->
                     Duration.between(sleepingSession.getBeginningSleepDateTime(),
                            sleepingSession.getEndSleepDateTime()).toMinutes())
                .toList()
                .stream()
                .max(Long::compare).orElse(0L);
        return new SleepAnalysisResult(toString(), maxSleepMinutes);
    }

}
