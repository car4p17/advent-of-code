package com.advent.days.Year2019.Day1;

import com.advent.days.Day;

import java.util.List;

public class Day1 extends Day {
    public String runPart1(List<String> input) {
        String[] rows = input.toArray(new String[0]);
        long runningSum = 0;
        for (String s: rows) {
            try {
                int i = Integer.parseInt(s);
                runningSum += (i / 3) - 2;
            } catch (Exception e) {
            }

        }
        return "" + runningSum;
    }

    public String runPart2(List<String> input) {
        String[] rows = input.toArray(new String[0]);
        long runningSum = 0;
        for (String s: rows) {
            try {
                int i = Integer.parseInt(s);
                long partFuel = (i / 3) - 2;
                long nextFuel = (partFuel / 3) - 2;
                while (nextFuel > 0) {
                    partFuel += nextFuel;
                    nextFuel = (nextFuel / 3) - 2;
                }
                runningSum += partFuel;
            } catch (Exception e) {
            }

        }
        return "" + runningSum;
    }
}
