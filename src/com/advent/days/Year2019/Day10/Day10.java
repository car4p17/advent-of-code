package com.advent.days.Year2019.Day10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import javafx.util.Pair;

public class Day10 extends Day{
    Pair<Integer, Integer> maxCoords;
    public Object runPart1(List<String> input) {
        // Convert input to map
        String[][] map = readInput(input);
        // For each asteroid do circle cast and see if it is the max
        int max = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].equals("#")) {
                    HashSet<Pair<Integer, Integer>> canSee = circleCast(map, x, y);
                    if (canSee.size() > max) {
                        max = canSee.size();
                        maxCoords = new Pair(x, y);
                    }
                }
            }
        }
        return max;
    }

    private HashSet<Pair<Integer, Integer>> circleCast(String[][] map, int x, int y) {
        HashSet<Pair<Integer, Integer>> hits = new HashSet<>();
        for (int deltax = -1 * map.length; deltax < map.length; deltax++) {
            for (int deltay = -1 * map.length; deltay < map.length; deltay++) {
                Pair<Integer, Integer> hit = raycast(map, x, y, deltax, deltay);
                if (hit != null) {
                    hits.add(hit);
                }
            }
        }
        return hits;
    }

    public Pair<Integer, Integer> raycast(String[][] map, int startx, int starty, long deltax, long deltay) {
        if (deltax == 0 && deltay == 0) {
            return null;
        }
        Pair<Long, Long> slope = reduce(deltax, deltay);
        int x = startx + slope.getKey().intValue();
        int y = starty + slope.getValue().intValue();
        while (y >= 0  && y < map.length && x >= 0 && x < map[y].length) {
            if (map[y][x].equals("#")) {
                return new Pair(x, y);
            }
            x += slope.getKey();
            y += slope.getValue();
        }
        return null;
    }

    private Pair<Long, Long> reduce(long deltax, long deltay) {
        long gcm = Math.abs(Utils.gcm(deltay, deltax));
        return new Pair<>((deltax / gcm), (deltay / gcm));
    }

    public Object runPart2(List<String> input) {
        // Convert input to map
        String[][] map = readInput(input);
        Logger.log(maxCoords);
        // Break asteroids in circle
        int asteroidsDestroyed = 0;
        double degrees = 270;
        HashSet<Pair<Long, Long>> slopes = new HashSet<>();
        while (asteroidsDestroyed < 200) {
            if (Math.round(degrees * 10.0) / 10.0 == 270) {
                slopes.clear();
            }
            Pair<Long, Long> slope = degreesToSlope(degrees);
            if (slopes.add(slope)) {
                Pair<Integer, Integer> hit = raycast(map, maxCoords.getKey(), maxCoords.getValue(), slope.getKey(), slope.getValue());
                if (hit != null) {
                    asteroidsDestroyed++;
                    map[hit.getValue()][hit.getKey()] = ".";
                    Logger.log(asteroidsDestroyed + ": " + hit);
                    if (asteroidsDestroyed == 200) {
                        return hit;
                    }
                }
            }
            degrees+=.1;
            if (degrees > 360) {
                degrees = 1;
            }
        }
        return null;
    }

    private Pair<Long, Long> degreesToSlope(double degrees) {
        if (degrees == 90) {
            return new Pair<>(0l, 1l);
        } else if (degrees == 0) {
            return new Pair<>(1l, 0l);
        } else if (degrees == 270) {
            return new Pair<>(0l, -1l);
        } else if (degrees == 180) {
            return new Pair<>(-1l, 0l);
        }
        double tan = Math.tan(Math.toRadians(degrees));
        Pair<Long, Long> slope = new Pair<>(0l, 1l);
        double diff = Double.MAX_VALUE;
        for(long y = -30; y < 30; y++) {
            for (long x = -30; x < 30; x++) {
                double delta = (1.0 * y) / (1.0 * x);
                if (Math.abs(delta - tan) < diff) {
                    diff = Math.abs(delta - tan);
                    slope = new Pair<>(x, y);
                }
            }
        }

        if (degrees > 90 && degrees < 270) {
            slope = new Pair<>(-1 * Math.abs(slope.getKey()), slope.getValue());
        } else {
            slope = new Pair<>(Math.abs(slope.getKey()), slope.getValue());
        }
        if (degrees > 0 && degrees < 180) {
            slope = new Pair<>(slope.getKey(), Math.abs(slope.getValue()));
        } else {
            slope = new Pair<>(slope.getKey(), -1 * Math.abs(slope.getValue()));
        }
        return reduce(slope.getKey(), slope.getValue());
    }



    private String[][] readInput(List<String> input) {
        String[][] map = new String[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String[] line = input.get(y).split("");
            for (int x = 0; x < line.length; x++) {
                map[y][x] = line[x];
            }
        }
        return map;
    }
}
