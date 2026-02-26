package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.util.List;

public class MiddleSleepingSession implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public MiddleSleepingSession(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция для подсчёта средней продолжительности сессии сна (в минутах). Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionsList) {
        long sumSleepAllSessionMinutes = sleepingSessionsList.stream()
                .map(sleepingSession ->
                        Duration.between(sleepingSession.getBeginningSleepDateTime(),
                                sleepingSession.getEndSleepDateTime()).toMinutes())
                .toList()
                .stream()
                .reduce(0L,Long::sum);
        long avgMinutes = sleepingSessionsList.isEmpty() ? 0 : sumSleepAllSessionMinutes / sleepingSessionList.size();
        return new SleepAnalysisResult(toString(), avgMinutes);
    }

}
