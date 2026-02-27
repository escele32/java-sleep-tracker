package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.yandex.practicum.sleeptracker.SleepQuality.GOOD;

public class SleepTrackerAppTest {

    private static List<SleepingSession> testSleepingSessionList = new ArrayList<>();
    private static SleepingSession sleepingSession;
    private static String fileName = "test_sleep_log.txt";

    @BeforeAll
    public static void test() throws IOException {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, StandardCharsets.UTF_8));
            printWriter.write("01.10.25 23:15;02.10.25 07:30;GOOD\n");
            printWriter.write("02.10.25 23:50;03.10.25 06:40;NORMAL\n");
            printWriter.write("03.10.25 14:10;03.10.25 15:00;NORMAL\n");
            printWriter.write("03.10.25 23:40;04.10.25 08:00;BAD\n");
            printWriter.write("05.10.25 00:10;05.10.25 06:20;GOOD\n");
            printWriter.write("05.10.25 13:30;05.10.25 14:15;NORMAL\n");
            printWriter.write("06.10.25 22:30;07.10.25 05:50;GOOD\n");
            printWriter.write("08.10.25 23:50;09.10.25 07:10;GOOD");
            printWriter.close();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
                String[] splitArray = lines.split(";");
                sleepingSession = new SleepingSession(LocalDateTime.parse(splitArray[0].trim(), formatter),
                        LocalDateTime.parse(splitArray[1].trim(), formatter),
                        SleepQuality.valueOf(splitArray[2].trim()));
                testSleepingSessionList.add(sleepingSession);
            }
            testSleepingSessionList.forEach(System.out::println);
            System.out.println();
        } catch (IOException ioException) {
            System.out.printf("Ошибка при чтении файла %s: %s\n", fileName, ioException.getMessage());
        }
    }

    @Test
    public void testCountAllSleepingSession() {
        CountAllSleepingSession countAllSleepingSession = new CountAllSleepingSession(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = countAllSleepingSession.apply(countAllSleepingSession
                .getSleepingSessionList());
        Assertions.assertEquals(8, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testCountBadSleepingSession() {
        CountBadSleepingSession countBadSleepingSession = new CountBadSleepingSession(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = countBadSleepingSession.apply(countBadSleepingSession
                .getSleepingSessionList());
        Assertions.assertEquals(1, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testMinSleepingSession() {
        MinSleepingSession minSleepingSession = new MinSleepingSession(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = minSleepingSession.apply(minSleepingSession.getSleepingSessionList());
        Assertions.assertEquals(45L, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testMaxSleepingSession() {
        MaxSleepingSession maxSleepingSession = new MaxSleepingSession(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = maxSleepingSession.apply(maxSleepingSession.getSleepingSessionList());
        Assertions.assertEquals(500L, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testMiddleSleepingSession() {
        AverageSleepingSession averageSleepingSession = new AverageSleepingSession(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = averageSleepingSession.apply(averageSleepingSession
                .getSleepingSessionList());
        Assertions.assertEquals(343L, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testCountAllSleepingSessionEmpty() {
        List<SleepingSession> listSession = Collections.emptyList();
        CountAllSleepingSession countAllSleepingSession = new CountAllSleepingSession(listSession);
        SleepAnalysisResult sleepAnalysisResult = countAllSleepingSession.apply(countAllSleepingSession
                .getSleepingSessionList());
        Assertions.assertEquals(0, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testCountBadSleepingSessionEmpty() {
        List<SleepingSession> listSession = Collections.emptyList();
        CountBadSleepingSession countBadSleepingSession = new CountBadSleepingSession(listSession);
        SleepAnalysisResult sleepAnalysisResult = countBadSleepingSession.apply(countBadSleepingSession
                .getSleepingSessionList());
        Assertions.assertEquals(0, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testMinSleepingSessionEmpty() {
        List<SleepingSession> listSession = Collections.emptyList();
        MinSleepingSession minSleepingSession = new MinSleepingSession(listSession);
        SleepAnalysisResult sleepAnalysisResult = minSleepingSession.apply(minSleepingSession.getSleepingSessionList());
        Assertions.assertEquals(0L, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testMaxSleepingSessionEmpty() {
        List<SleepingSession> listSession = Collections.emptyList();
        MaxSleepingSession maxSleepingSession = new MaxSleepingSession(listSession);
        SleepAnalysisResult sleepAnalysisResult = maxSleepingSession.apply(maxSleepingSession.getSleepingSessionList());
        Assertions.assertEquals(0L, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testMiddleSleepingSessionEmpty() {
        List<SleepingSession> listSession = Collections.emptyList();
        AverageSleepingSession averageSleepingSession = new AverageSleepingSession(listSession);
        SleepAnalysisResult sleepAnalysisResult = averageSleepingSession.apply(averageSleepingSession
                .getSleepingSessionList());
        Assertions.assertEquals(0L, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testChronotypeUser() {
        ChronotypeUser chronotypeUser = new ChronotypeUser(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = chronotypeUser.apply(chronotypeUser.getSleepingSessionList());
        Assertions.assertEquals(TypeNights.ГОЛУБЬ, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testChronotypeUserOwl() {
        List<SleepingSession> sessionList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        SleepingSession session = new SleepingSession(LocalDateTime.parse("01.10.25 23:50", formatter),
                LocalDateTime.parse("02.10.25 10:10", formatter), GOOD);
        SleepingSession session1 = new SleepingSession(LocalDateTime.parse("03.10.25 21:50", formatter),
                LocalDateTime.parse("04.10.25 06:00", formatter), GOOD);
        SleepingSession session2 = new SleepingSession(LocalDateTime.parse("06.10.25 22:30", formatter),
                LocalDateTime.parse("07.10.25 08:05", formatter), GOOD);
        SleepingSession session3 = new SleepingSession(LocalDateTime.parse("08.10.25 23:50", formatter),
                LocalDateTime.parse("09.10.25 09:10", formatter), GOOD);
        sessionList.add(session);
        sessionList.add(session1);
        sessionList.add(session2);
        sessionList.add(session3);
        ChronotypeUser chronotypeUser = new ChronotypeUser(sessionList);
        SleepAnalysisResult sleepAnalysisResult = chronotypeUser.apply(chronotypeUser.getSleepingSessionList());
        Assertions.assertEquals(TypeNights.СОВА, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        sessionList.forEach(System.out::println);
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }

    @Test
    public void testSleeplessNights() {
        SleeplessNights sleeplessNights = new SleeplessNights(testSleepingSessionList);
        SleepAnalysisResult sleepAnalysisResult = sleeplessNights.apply(sleeplessNights.getSleepingSessionList());
        Assertions.assertEquals(2, sleepAnalysisResult.getResultFunction());
        System.out.println();
        System.out.println("Если список не пустой!");
        System.out.printf("%s%s\n", sleepAnalysisResult.getFunctionSleepTracker(),
                sleepAnalysisResult.getResultFunction());
    }
}