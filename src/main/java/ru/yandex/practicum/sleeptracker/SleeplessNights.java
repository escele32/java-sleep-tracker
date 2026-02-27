package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class SleeplessNights implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public SleeplessNights(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция подсчёта бессонных ночей за представленный период. Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionsList) {

        List<LocalDate> listAllNights = sleepingSessionsList.get(0).getBeginningSleepDateTime()
                .toLocalDate().datesUntil(sleepingSessionsList.get(sleepingSessionsList.size() - 1)
                        .getEndSleepDateTime().toLocalDate())
                .collect(Collectors.toList());

        int numberSleepNight = (int) sleepingSessionsList.stream()
                .filter(sleepingSession -> {
                    Period period = Period.between(sleepingSession.getBeginningSleepDateTime().toLocalDate(),
                            sleepingSession.getEndSleepDateTime().toLocalDate());
                    return period.getDays() == 1;
                })
                .count();
        int numberNightAsleepAndWokeOneDay = (int) sleepingSessionsList.stream()
                .filter(sleepingSession -> {
                    Period period = Period.between(sleepingSession.getBeginningSleepDateTime().toLocalDate(),
                            sleepingSession.getEndSleepDateTime().toLocalDate());
                    return period.getDays() == 0
                            && sleepingSession.getBeginningSleepDateTime()
                            .toLocalTime().isBefore(LocalTime.of(6,0));
                })
                .count();
        int numberSleeplessNights = listAllNights.size() - numberSleepNight - numberNightAsleepAndWokeOneDay;
        return new SleepAnalysisResult(toString(), numberSleeplessNights);
    }

}
