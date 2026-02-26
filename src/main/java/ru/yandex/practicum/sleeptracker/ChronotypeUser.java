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
        int countJoint = (int) sleepingSessionsList.stream()
                .filter(sleepingSession -> {
                    Period period = Period.between(sleepingSession.getBeginningSleepDateTime().toLocalDate(),
                            sleepingSession.getEndSleepDateTime().toLocalDate().plusDays(1));
                    return period.getDays() > 1;
                })
                .count();
        int countOwl = (int) sleepingSessionsList.stream()
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
        int countLark = (int) sleepingSessionsList.stream()
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
        int countDove = countJoint - countOwl - countLark;
        if (countOwl > countLark && countOwl > countDove) {
            return new SleepAnalysisResult(toString(), TypeNights.СОВА);
        } else if (countOwl < countLark && countLark > countDove) {
            return new SleepAnalysisResult(toString(), TypeNights.ЖАВОРОНОК);
        } else if (countOwl == countLark || (countDove > countLark && countDove > countOwl)) {
            return new SleepAnalysisResult(toString(), TypeNights.ГОЛУБЬ);
        }
        return null;
    }

}
