package com.advent;

import com.advent.days.Day;
import com.advent.days.DayTemp;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        LocalDateTime today = LocalDateTime.now();
        int year = today.getMonthValue() == 12 ? today.getYear() : today.getYear() - 1; // If it is december use current year else last year
        int dayOfMonth = Math.min(today.getDayOfMonth(), 25);
        String className = "com.advent.days.Year" + year + ".Day" + dayOfMonth + ".Day" + dayOfMonth;
        Day day;
        try {
            day = Day.class.cast(Class.forName(className).newInstance());
        } catch (ClassNotFoundException cnfe){
            // Set up everything for the days problem
            Logger.log("Class was not found: " + className);
            Logger.log("Attempting to create from template");
            if (createDayFile(year, dayOfMonth)) {
                Logger.log("Created File for today");
            }
            Input.readInput(year, dayOfMonth, new DayTemp().inputFile);
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Logger.log("Opening Browser to today's problem");
                    Desktop.getDesktop().browse(new URI("https://adventofcode.com/" + year + "/day/" + dayOfMonth));
                }
            } catch (Exception e) {

            }
            return;
        } catch (InstantiationException
                | IllegalAccessException e) {
            Logger.error(e);
            return;
        }
        List<String> input = Input.readInput(year, dayOfMonth, day.inputFile);
        Logger.log("Running class: " + className);
        Logger.log("---------------Part1-----------------");
        long startTimeNano = System.nanoTime();
        Logger.log("Output: " + day.runPart1(input));
        Logger.log("Runtime: " + (System.nanoTime() - startTimeNano) + " ns");
        Logger.log("---------------Part2-----------------");
        startTimeNano = System.nanoTime();
        Logger.log("Output: " + day.runPart2(input));
        Logger.log("Runtime: " + (System.nanoTime() - startTimeNano) + " ns");
    }

    private static boolean createDayFile(int year, int day) {
        String yearFolderString = "src/com/advent/days/Year" + year;
        File yearFile = new File(yearFolderString);
        if (!yearFile.exists()) {
            yearFile.mkdir();
        }
        String dayFolderString = yearFolderString + "/Day" + day;
        File dayFile = new File(dayFolderString);
        if (!dayFile.exists()) {
            dayFile.mkdir();
        }
        try (
            BufferedReader reader = new BufferedReader(new FileReader("src/com/advent/days/DayTemp.java"));
            FileWriter fw = new FileWriter(dayFolderString + "/Day" + day + ".java");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String fixedLine = line;
                fixedLine = fixedLine.replaceAll("DayTemp", "Day" + day);
                fixedLine = fixedLine.replaceAll("package com.advent.days", "package com.advent.days.Year" + year + ".Day" + day);
                out.println(fixedLine);
            }
            return true;
        } catch (IOException e) {
            Logger.error(e);
        }
        return false;
    }
}
