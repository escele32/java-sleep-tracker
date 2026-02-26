package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountBadSleepingSession implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public CountBadSleepingSession(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция подсчёта плохих сессий сна за представленный период. Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        int count = (int) sleepingSessionList.stream()
                .filter(sleepingSession -> sleepingSession.getSleepQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult(toString(), count);
    }

}
