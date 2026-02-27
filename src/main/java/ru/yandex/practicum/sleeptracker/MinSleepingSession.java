package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MinSleepingSession implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public MinSleepingSession(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция для подсчёта минимальной продолжительности сессии сна (в минутах). Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionsList) {
        long minSleepMinutes = sleepingSessionsList.stream()
                .map(sleepingSession ->
                        Duration.between(sleepingSession.getBeginningSleepDateTime(),
                                sleepingSession.getEndSleepDateTime()).toMinutes())
                .toList()
                .stream()
                .min(Long::compare).orElse(0L);
        return new SleepAnalysisResult(toString(), minSleepMinutes);
    }

}
