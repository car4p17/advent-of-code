package com.advent.days.Year2019.Day11;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;
import javafx.util.Pair;

public class Day11 extends Day{
    String blackString = "Black";
    String whiteString = "White";
    public Object runPart1(List<String> input) {
        HashMap<Pair<Integer, Integer>, String> painted = new HashMap<>();
        painted = paint(input, painted);
        display(painted, null);
        return painted.keySet().size();
    }

    private HashMap<Pair<Integer, Integer>, String> paint(List<String> input, HashMap<Pair<Integer, Integer>, String> painted) {
        Pair<Integer, Integer> cords = new Pair<>(0,0);
        int facing = 90;
        long instructionPointer = 0;
        HashMap<Long, Long> program = IntCode.setupInput(input);
        while (instructionPointer < Long.MAX_VALUE) {
            long[] inputArr = new long[1];
            inputArr[0] = 0;
            if (painted.containsKey(cords) && painted.get(cords).equals(whiteString)) {
                inputArr[0] = 1;
            }
            Pair<Long, HashMap<Long, Long>> result = IntCode.runProgramExec(program, inputArr, instructionPointer);
            program = result.getValue();
            instructionPointer = result.getKey();
            if (IntCode.output.get(0) == 0l) {
                painted.put(new Pair<>(cords.getKey(), cords.getValue()), blackString);
            } else {
                painted.put(new Pair<>(cords.getKey(), cords.getValue()), whiteString);
            }
            if (IntCode.output.get(1) == 0l) {
                facing += 90;
                facing %= 360;
            } else {
                facing -= 90;
                if (facing < 0) {
                    facing = 270;
                }
            }
            int deltax = 0;
            int deltay = 0;
            switch (facing) {
                case 0:
                    deltax = 1;
                    break;
                case 90:
                    deltay = -1;
                    break;
                case 180:
                    deltax = -1;
                    break;
                case 270:
                    deltay = 1;
                    break;
                default:
                    Logger.log("ERRROR");
            }
            cords = new Pair<>(cords.getKey() + deltax, cords.getValue() + deltay);
            display(painted, cords);
            Logger.log("_-----_");
        }
        return painted;
    }

    public Object runPart2(List<String> input) {
        HashMap<Pair<Integer, Integer>, String> painted = new HashMap<>();
        painted.put(new Pair<>(0, 0), whiteString);
        painted = paint(input, painted);
        display(painted, null);

        return painted.keySet().size();
    }

    private void display(HashMap<Pair<Integer, Integer>, String> painted, Pair<Integer, Integer> robotCords) {
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> point: painted.keySet()) {
            maxX = Math.max(point.getKey(), maxX);
            minX = Math.min(point.getKey(), minX);
            maxY = Math.max(point.getValue(), maxY);
            minY = Math.min(point.getValue(), minY);
        }

        if (robotCords != null) {
            maxX = Math.max(robotCords.getKey(), maxX);
            minX = Math.min(robotCords.getKey(), minX);
            maxY = Math.max(robotCords.getValue(), maxY);
            minY = Math.min(robotCords.getValue(), minY);
        }
        for (int y = minY; y <= maxY; y++) {
            String out = "";
            for (int x = minX; x <= maxX; x++) {
                Pair<Integer, Integer> cords = new Pair<>(x, y);
                if (robotCords != null && cords.equals(robotCords)) {
                    out += ">";
                } else {
                    if (painted.containsKey(cords)) {
                        if (painted.get(cords).equals(whiteString)) {
                            out += "#";
                        } else {
                            out += ".";
                        }
                    } else {
                        out += ".";
                    }
                }
            }
            Logger.log(out);
        }
    }
}
