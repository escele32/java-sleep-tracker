package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountAllSleepingSession implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public CountAllSleepingSession(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция подсчёта сессий сна за представленный период. Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionsList) {
        return new SleepAnalysisResult(toString(), getSleepingSessionList().size());
    }

}
