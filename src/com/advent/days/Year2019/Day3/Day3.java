package com.advent.days.Year2019.Day3;

import java.util.ArrayList;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import javafx.util.Pair;

public class Day3 extends Day{
    public String runPart1(List<String> input) {
        Location[][] diagram = drawWires(input);
        List<Pair<Integer, Integer>> intersections = new ArrayList<>();
        Pair<Integer, Integer> center = new Pair(0,0);
        for (int x = 0; x < diagram.length; x++) {
            for (int y = 0; y < diagram[x].length; y++) {
                if (diagram[x][y] != null) {
                    if (diagram[x][y].symbol.equals("X")) {
                        intersections.add(new Pair(x, y));
                    } else if (diagram[x][y].symbol.equals("o")) {
                        center = new Pair(x, y);
                    }
                }
            }
        }

        int minDist = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> intersection: intersections) {
            minDist = Math.min(minDist, Math.abs(intersection.getKey() - center.getKey()) + Math.abs(intersection.getValue() - center.getValue()));
        }
        return "" + minDist;
    }

    public String runPart2(List<String> input) {
        Location[][] diagram = drawWires(input);
        int minDist = Integer.MAX_VALUE;
        for (int x = 0; x < diagram.length; x++) {
            for (int y = 0; y < diagram[x].length; y++) {
                if (diagram[x][y] != null && diagram[x][y].symbol.equals("X")) {
                    minDist = Math.min(minDist, diagram[x][y].getTotalLength());
                }
            }
        }

        return "" + minDist;
    }

    public Location[][] drawWires(List<String> wires) {
        int maxHeight = Integer.MIN_VALUE;
        int minHeight = Integer.MAX_VALUE;
        int maxWidth = Integer.MIN_VALUE;
        int minWidth = Integer.MAX_VALUE;
        List<List<Direction>> directions = new ArrayList<>();
        for (int i = 0; i < wires.size(); i++) {
            String[] split = wires.get(i).split(",");
            directions.add(new ArrayList<>());
            int indexx = 0;
            int indexy = 0;
            for (String s: split) {
                Direction p = new Direction(s.substring(0, 1), Integer.parseInt(s.substring(1)));
                directions.get(i).add(p);
                switch (p.direction) {
                    case "U":
                        indexx -= p.amount;
                        minHeight = Math.min(minHeight, indexx);
                        break;
                    case "D":
                        indexx += p.amount;
                        maxHeight = Math.max(maxHeight, indexx);
                        break;
                    case "L":
                        indexy -= p.amount;
                        minWidth = Math.min(minWidth, indexy);
                        break;
                    case "R":
                        indexy += p.amount;
                        maxWidth = Math.max(maxWidth, indexy);
                        break;
                }
            }
        }
        int height = maxHeight - Math.min(0, minHeight) + 10;
        int width = maxWidth - Math.min(0, minWidth) + 10;
        Location[][] diagram = new Location[height][width];
        try {
            for (int i = 0; i < directions.size(); i++) {
                List<Direction> directs = directions.get(i);
                int indexx = height - Math.abs(maxHeight) - 5;
                int indexy = width - Math.abs(maxWidth) - 5;
                int length = 0;
                diagram[indexx][indexy] = new Location("o");
                for (Direction d: directs) {
                    for (int c = 0; c < d.amount; c++) {
                        length++;
                        switch (d.direction) {
                            case "U":
                                indexx--;
                                break;
                            case "D":
                                indexx++;
                                break;
                            case "L":
                                indexy--;
                                break;
                            case "R":
                                indexy++;
                                break;
                        }
                        if (diagram[indexx][indexy] == null) {
                            diagram[indexx][indexy] = new Location("0");
                        }
                        diagram[indexx][indexy].addLength("" + (i + 1), length);
                    }
                }
            }
        } catch (Exception e) {
            Logger.error(e);
        }
        return diagram;
    }

    private class Direction {
        public String direction;
        public Integer amount;
        public Direction(String direction, Integer amount) {
            this.direction = direction;
            this.amount = amount;
        }
    }

    private class Location {
        String symbol;
        List<Integer> lengths;
        public Location(String symbol) {
            this.symbol = symbol;
            this.lengths = new ArrayList<>();
        }
        public void addLength(String wire, Integer length) {
            this.lengths.add(length);
            if (lengths.size() == 1 || this.symbol.equals(wire)) {
                this.symbol = wire;
            } else {
                this.symbol = "X";
            }
        }

        public int getTotalLength() {
            int total = 0;
            for (Integer l: lengths) {
                total += l;
            }
            return total;
        }
    }
}


