package com.advent.days.Year2019.Day10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import javafx.util.Pair;

public class Day10 extends Day{
    public Object runPart1(List<String> input) {
        // Convert input to map
        String[][] map = new String[input.size()][input.get(0).length()];
        for (int y = 0; y < input.size(); y++) {
            String[] line = input.get(y).split("");
            for (int x = 0; x < line.length; x++) {
                map[y][x] = line[x];
            }
        }
        // For each asteroid do circle cast and see if it is the max
        int max = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x].equals("#")) {
                    HashSet<Pair<Integer, Integer>> canSee = circleCast(map, x, y);
                    max = Math.max(max, canSee.size());
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

    public Pair<Integer, Integer> raycast(String[][] map, int startx, int starty, int deltax, int deltay) {
        if (deltax == 0 && deltay == 0) {
            return null;
        }
        Pair<Integer, Integer> slope = reduce(deltax, deltay);
        int x = startx + slope.getKey();
        int y = starty + slope.getValue();
        while (y >= 0  && y < map.length && x >= 0 && x < map[y].length) {
            if (map[y][x].equals("#")) {
                return new Pair(x, y);
            }
            x += slope.getKey();
            y += slope.getValue();
        }
        return null;
    }

    private Pair<Integer, Integer> reduce(int deltax, int deltay) {
        int gcm = Math.abs(Utils.gcm(deltay, deltax));
        return new Pair((deltax / gcm), (deltay / gcm));
    }

    public Object runPart2(List<String> input) {
        return null;
    }
}
