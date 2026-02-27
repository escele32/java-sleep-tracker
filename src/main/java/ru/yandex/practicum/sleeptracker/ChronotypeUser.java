package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.time.Period;
import java.util.List;

public class ChronotypeUser implements SleepAnalysisFunction {

    private final List<SleepingSession> sleepingSessionList;

    public ChronotypeUser(List<SleepingSession> sleepingSessionList) {
        this.sleepingSessionList = sleepingSessionList;
    }

    public List<SleepingSession> getSleepingSessionList() {
        return sleepingSessionList;
    }

    @Override
    public String toString() {
        return "Функция для определения хронотипа пользователя. Результат: ";
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionsList) {
        int numberJoint = (int) sleepingSessionsList.stream()
                .filter(sleepingSession -> {
                    Period period = Period.between(sleepingSession.getBeginningSleepDateTime().toLocalDate(),
                            sleepingSession.getEndSleepDateTime().toLocalDate().plusDays(1));
                    return period.getDays() > 1;
                })
                .count();
        int numberOwl = (int) sleepingSessionsList.stream()
                .filter(sleepingSession -> {
                    Period period = Period.between(sleepingSession.getBeginningSleepDateTime().toLocalDate(),
                            sleepingSession.getEndSleepDateTime().toLocalDate().plusDays(1));
                    return period.getDays() > 1
                            && sleepingSession.getBeginningSleepDateTime().toLocalTime()
                            .isAfter(LocalTime.of(23, 0))
                            && sleepingSession.getEndSleepDateTime().toLocalTime()
                            .isAfter(LocalTime.of(9, 0));
                })
                .count();
        int numberLark = (int) sleepingSessionsList.stream()
                .filter(sleepingSession -> {
                    Period period = Period.between(sleepingSession.getBeginningSleepDateTime().toLocalDate(),
                            sleepingSession.getEndSleepDateTime().toLocalDate().plusDays(1));
                    return period.getDays() > 1
                            && sleepingSession.getBeginningSleepDateTime().toLocalTime()
                            .isBefore(LocalTime.of(22, 0))
                            && sleepingSession.getEndSleepDateTime().toLocalTime()
                            .isBefore(LocalTime.of(7, 0));
                })
                .count();
        int numberDove = numberJoint - numberOwl - numberLark;
        if (numberOwl > numberLark && numberOwl > numberDove) {
            return new SleepAnalysisResult(toString(), TypeNights.СОВА);
        } else if (numberOwl < numberLark && numberLark > numberDove) {
            return new SleepAnalysisResult(toString(), TypeNights.ЖАВОРОНОК);
        } else if (numberOwl == numberLark || (numberDove > numberLark && numberDove > numberOwl)) {
            return new SleepAnalysisResult(toString(), TypeNights.ГОЛУБЬ);
        }
        return null;
    }

}
