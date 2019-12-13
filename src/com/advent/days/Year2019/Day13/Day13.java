package com.advent.days.Year2019.Day13;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;
import javafx.util.Pair;

public class Day13 extends Day{
    public Object runPart1(List<String> input) {
        IntCode.runProgram(input, 0);
        HashMap<Pair<Long, Long>, Long> map = new HashMap<>();
        for (int i = 0; i < IntCode.output.size(); i+=3) {
            map.put(new Pair<>(IntCode.output.get(i), IntCode.output.get(i + 1)), IntCode.output.get(i + 2));
        }
        int count = 0;
        for (Long l: map.values()) {
            if (l == 2l) {
                count++;
            }
        }

        return count;
    }

    public Object runPart2(List<String> input) {
        HashMap<Long, Long> program = IntCode.setupInput(input);
        program.put(0l, 2l);
        HashMap<Pair<Long, Long>, Long> map = new HashMap<>();
        long score = 0;
        long instructionPointer = 0;
        long[] in = {0};
        Scanner scn = new Scanner(System.in);
        Long ballX = 0l;
        Long paddleX = 0l;
        while (instructionPointer != Long.MAX_VALUE) {
            Pair<Long, HashMap<Long, Long>> out = IntCode.runProgramExec(program, in, instructionPointer);
            program = out.getValue();
            instructionPointer = out.getKey();
            for (int i = 0; i < IntCode.output.size(); i += 3) {
                long x = IntCode.output.get(i);
                long y = IntCode.output.get(i + 1);
                long z = IntCode.output.get(i + 2);

                if (x == -1l) {
                    score = z;
                } else {
                    map.put(new Pair<>(x, y), z);
                    if (z == 4) {
                        ballX = x;
                    } else if (z == 3) {
                        paddleX = x;
                    }
                }
            }


            Logger.log("--------------------------------------------------------");
            Logger.log("Score: " + score);
            display(map);
            Logger.log("--------------------------------------------------------");

            if (ballX < paddleX) {
                in[0] = -1;
            } else if (ballX > paddleX) {
                in[0] = 1;
            } else {
                in[0] = 0;
            }

            /*Logger.log("Move Paddle");
            Logger.log("1) Left");
            Logger.log("2) Center");
            Logger.log("3) Right");
            String s = scn.next();
            Logger.log("");
            if (s.equals("1")) {
                in[0] = -1;
            } else if (s.equals("2")) {
                in[0] = 0;
            } else if (s.equals("3")) {
                in[0] = 1;
            }*/
        }

        Logger.log("--------------------------------------------------------");
        Logger.log("Score: " + score);
        display(map);
        Logger.log("--------------------------------------------------------");
        return score;
    }

    private void display(HashMap<Pair<Long, Long>, Long> painted) {
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
                if (painted.containsKey(cords)) {
                    out += toDisplay(painted.get(cords));
                } else {
                    out += ".";
                }
            }
            Logger.log(out);
        }
    }

    private String toDisplay(long l) {
        switch ((int) l) {
            case 1:
                return "X";
            case 2:
                return "B";
            case 3:
                return "_";
            case 4:
                return "O";
            default:
                return ".";
        }
    }
}
