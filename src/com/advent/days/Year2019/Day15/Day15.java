package com.advent.days.Year2019.Day15;

import java.util.HashMap;
import java.util.List;
import com.advent.Logger;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;
import javafx.util.Pair;

public class Day15 extends Day{
    HashMap<Pair<Long, Long>, String> maze = new HashMap<>();
    Pair<Long, Long> oxygen = new Pair<>(0l, 0l);
    public Object runPart1(List<String> input) {
        HashMap<Long, Long> program = IntCode.setupInput(input);
        long instructionPointer = 0;
        maze.put(new Pair<>(0l, 0l), ".");
        Pair<Long, Long> robotCords = new Pair<>(0l, 0l);
        long iterations = 0;
        while (instructionPointer != Long.MAX_VALUE && iterations < 1000000) {
            // Figure out where to move
            long direction = (long) Math.floor(Math.random() * 4 + 1);
            if (maze.get(new Pair<>(robotCords.getKey(), robotCords.getValue() - 1)) == null) {
                direction = 1;
            } else if (maze.get(new Pair<>(robotCords.getKey(), robotCords.getValue() + 1)) == null) {
                direction = 2;
            } else if (maze.get(new Pair<>(robotCords.getKey() - 1, robotCords.getValue())) == null) {
                direction = 3;
            } else if (maze.get(new Pair<>(robotCords.getKey() + 1, robotCords.getValue())) == null) {
                direction = 4;
            }
            long[] in = {direction};

            // Move that direction
            Pair<Long, HashMap<Long, Long>> out = IntCode.runProgramExec(program, in, instructionPointer);
            program = out.getValue();
            instructionPointer = out.getKey();
            // Update the map with new information
            long info = IntCode.output.get(0);
            Pair<Long, Long> tested = robotCords;
            if (direction == 1) {
                tested = new Pair(robotCords.getKey(), robotCords.getValue() - 1);
            } else if (direction == 2) {
                tested = new Pair(robotCords.getKey(), robotCords.getValue() + 1);
            } else if (direction == 3) {
                tested = new Pair(robotCords.getKey() - 1, robotCords.getValue());
            } else if (direction == 4) {
                tested = new Pair(robotCords.getKey() + 1, robotCords.getValue());
            } else {
                Logger.log("error invalid direction: " + direction);
            }
            if (info == 0) {
                maze.put(tested, "X");
            } else if (info == 1) {
                maze.put(tested, ".");
                robotCords = tested;
            } else if (info == 2) {
                maze.put(tested, "O");
                oxygen = tested;
                robotCords = tested;
            }
            iterations++;
        }
        floodFill(maze, new Pair<>(0l,0l));
        display(maze);
        long min = Long.MAX_VALUE;
        Pair<Long, Long> up = new Pair<>(oxygen.getKey(), oxygen.getValue() - 1);
        Pair<Long, Long> down = new Pair<>(oxygen.getKey(), oxygen.getValue() + 1);
        Pair<Long, Long> left = new Pair<>(oxygen.getKey() - 1, oxygen.getValue());
        Pair<Long, Long> right = new Pair<>(oxygen.getKey() + 1, oxygen.getValue());
        if (filled.containsKey(up)){
            min = Math.min(min, filled.get(up));
        } else if (filled.containsKey(down)){
            min = Math.min(min, filled.get(down));
        } else if (filled.containsKey(left)){
            min = Math.min(min, filled.get(left));
        } else if (filled.containsKey(right)){
            min = Math.min(min, filled.get(right));
        }
        return min + 1;
    }

    HashMap<Pair<Long, Long>, Long> filled = new HashMap<>();
    public HashMap<Pair<Long, Long>, Long> floodFill(HashMap<Pair<Long, Long>, String> maze, Pair<Long, Long> current) {
        filled.clear();
        floodFill(maze, current, 0);
        return filled;
    }
    public void floodFill(HashMap<Pair<Long, Long>, String> maze, Pair<Long, Long> current, long depth) {
        if (!filled.containsKey(current) || filled.get(current) > depth) {
            filled.put(current, depth);
            Pair<Long, Long> up = new Pair<>(current.getKey(), current.getValue() - 1);
            Pair<Long, Long> down = new Pair<>(current.getKey(), current.getValue() + 1);
            Pair<Long, Long> left = new Pair<>(current.getKey() - 1, current.getValue());
            Pair<Long, Long> right = new Pair<>(current.getKey() + 1, current.getValue());
            long newDepth = depth + 1;
            if (maze.get(up) != null && maze.get(up).equals(".")) {
                floodFill(maze, up, newDepth);
            }
            if (maze.get(down) != null && maze.get(down).equals(".")) {
                floodFill(maze, down, newDepth);
            }
            if (maze.get(left) != null && maze.get(left).equals(".")) {
                floodFill(maze, left, newDepth);
            }
            if (maze.get(right) != null && maze.get(right).equals(".")) {
                floodFill(maze, right, newDepth);
            }
        }
    }

    public Object runPart2(List<String> input) {
        floodFill(maze, oxygen);
        long max = Long.MIN_VALUE;
        for (Long l: filled.values()) {
            max = Math.max(l, max);
        }
        return max;
    }

    private <T> void display(HashMap<Pair<Long, Long>, T> painted) {
        long maxX = Integer.MIN_VALUE;
        long minX = Integer.MAX_VALUE;
        long maxY = Integer.MIN_VALUE;
        long minY = Integer.MAX_VALUE;
        for (Pair<Long, Long> point: painted.keySet()) {
            maxX = Math.max(point.getKey(), maxX);
            minX = Math.min(point.getKey(), minX);
            maxY = Math.max(point.getValue(), maxY);
            minY = Math.min(point.getValue(), minY);
        }

        for (long y = minY; y <= maxY; y++) {
            String out = "";
            for (long x = minX; x <= maxX; x++) {
                Pair<Long, Long> cords = new Pair<>(x, y);
                if (cords.getKey() == 0 && cords.getValue() == 0) {
                    out += "S";
                } else if (painted.containsKey(cords)) {
                    out += painted.get(cords);
                } else {
                    out += " ";
                }
            }
            Logger.log(out);
        }
    }
}
