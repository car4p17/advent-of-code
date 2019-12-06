package com.advent.days.Year2019.Day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import javafx.util.Pair;
import sun.misc.Queue;

public class Day6 extends Day{
    HashMap<String, Node> orbitMap = new HashMap<>();
    public String runPart1(List<String> input) {
        for (String s: input) {
            String[] nodes = s.split("\\)");
            // nodes[1] orbits nodes[0]
            Node orbits = orbitMap.get(nodes[0]);
            if (orbits == null) {
                orbits = new Node(null);
                orbitMap.put(nodes[0], orbits);
            }
            Node orbitedBy = orbitMap.get(nodes[1]);
            if (orbitedBy != null) {
                orbitedBy.orbits = orbits;
            } else {
                orbitedBy = new Node(orbits);
                orbitMap.put(nodes[1], orbitedBy);
            }
            orbitedBy.linkedTo.add(orbits);
            orbits.linkedTo.add(orbitedBy);
        }

        int orbitsCount = 0;
        for (Node n: orbitMap.values()) {
            orbitsCount += countOrbits(n);
        }
        return "" + orbitsCount;
    }

    public int countOrbits(Node n) {
        if (n.orbitCount != null) {
            return n.orbitCount;
        } else if (n.orbits == null) {
            n.orbitCount = 0;
            return 0;
        }
        n.orbitCount = countOrbits(n.orbits) + 1;
        return n.orbitCount;
    }

    public String runPart2(List<String> input) {
        Node YOU = orbitMap.get("YOU");
        Node SANTA = orbitMap.get("SAN");
        int count = 0;

        // Move out of the branch to hopefully save a few steps
        while (SANTA.linkedTo.size() == 1) {
            SANTA = SANTA.orbits;
            count++;
        }

        HashSet<Node> closed = new HashSet<>();
        Queue<Pair<Node, Integer>> open = new Queue<>();
        Pair<Node, Integer> current = new Pair(YOU, 0);
        boolean done = false;
        while (current.getKey() != SANTA) {
            for (Node n: current.getKey().linkedTo) {
                if (!closed.contains(n)) {
                    open.enqueue(new Pair(n, current.getValue() + 1));
                }
            }
            closed.add(current.getKey());
            try {
                current = open.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count += current.getValue();
        return "" + (count - 2);
    }

    private class Node {
        public Node orbits = null;
        public ArrayList<Node> linkedTo = new ArrayList<>();
        public Integer orbitCount = null;

        public Node(Node orbits) {
            this.orbits = orbits;
        }
    }
}
