package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static List<SleepingSession> listSleepingSession = new ArrayList<>();
    private static Path filePath = Paths.get("./src/main/resources/sleep_log.txt");
    private static SleepingSession sleepingSession;
    private static SleepAnalysisResult sleepAnalysisResult;
    private static List<SleepAnalysisFunction> functionList;

    public static void main(String[] args) throws Exception {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toFile(),
                StandardCharsets.UTF_8));) {
            listSleepingSession = bufferedReader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                        String[] splitArray = line.split(";");
                        if (splitArray.length != 3) {
                            return null;
                        }
                        try {
                            return new SleepingSession(
                                    LocalDateTime.parse(splitArray[0].trim(), formatter),
                                    LocalDateTime.parse(splitArray[1].trim(), formatter),
                                    SleepQuality.valueOf(splitArray[2].trim())
                            );
                        } catch (Exception exception) {
                            return null;
                        }
                    })
                    .filter(session -> session != null)
                    .collect(Collectors.toList());
            listSleepingSession.forEach(System.out::println);
        } catch (Exception exception) {
            System.out.printf("Ошибка при чтении файла %s: %s\n", filePath.getFileName(), exception.getMessage());
        }

        functionList = List.of(new CountAllSleepingSession(listSleepingSession),
                new CountBadSleepingSession(listSleepingSession),
                new MinSleepingSession(listSleepingSession),
                new MaxSleepingSession(listSleepingSession),
                new AverageSleepingSession(listSleepingSession),
                new SleeplessNights(listSleepingSession),
                new ChronotypeUser(listSleepingSession));

        functionList.forEach(function -> {
                sleepAnalysisResult = function.apply(listSleepingSession);
                System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                        sleepAnalysisResult.getResultFunction());
                });
    }

}