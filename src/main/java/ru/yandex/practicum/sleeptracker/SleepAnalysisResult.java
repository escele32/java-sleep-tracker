package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T, V> {

    private T functionSleepTracker;
    private V resultFunction;

    public SleepAnalysisResult(T functionSleepTracker, V resultFunction) {
        this.functionSleepTracker = functionSleepTracker;
        this.resultFunction = resultFunction;
    }

    public V getResultFunction() {
        return resultFunction;
    }

    public T getFunctionSleepTracker() {
        return functionSleepTracker;
    }

}
