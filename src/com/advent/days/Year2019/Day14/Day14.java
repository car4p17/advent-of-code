package com.advent.days.Year2019.Day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;

public class Day14 extends Day{
    public Object runPart1(List<String> input) {
        List<Reagent> have = new ArrayList<>();
        have.clear();
        have.add(new Reagent(1, "FUEL"));
        return reverseToOre(input, have);
    }

    private Long reverseToOre(List<String> input, List<Reagent> have) {
        HashMap<String, Long> starts = new HashMap<>();
        HashMap<String, List<Reagent>> equations = new HashMap<>();
        // Read equations
        for (String s: input) {
            String[] sides = s.split(" => ");
            List<Reagent> reagents = new ArrayList<>();
            for (String p: sides[0].split(", ")) {
                reagents.add(new Reagent(p));
            }
            Reagent start = new Reagent(sides[1]);
            starts.put(start.name, start.amount);
            equations.put(start.name, reagents);
        }

        // Run equations backwords
        HashMap<String, Integer> depths = new HashMap<>();
        depths.put("FUEL", 0);
        while (have.size() != 1 || !have.get(0).name.equals("ORE")) {
            int index = 0;
            boolean done = false;
            while (!done && index < have.size()) {
                Reagent start = have.get(index);
                String name = start.name;
                Long requiredAmount = starts.get(name);
                if (requiredAmount != null && start.amount >= requiredAmount) {
                    long times = start.amount / requiredAmount;
                    start.amount -= requiredAmount * times;
                    if (start.amount <= 0) {
                        have.remove(index);
                    }
                    for (Reagent r: equations.get(name)) {
                        // Update depth
                        int current = Integer.MIN_VALUE;
                        if (depths.containsKey(r.name)) {
                            current = depths.get(r.name);
                        }
                        depths.put(r.name, Math.max(current, depths.get(name) + 1));
                        // Update or add new Reagent to have list
                        boolean found = false;
                        for (Reagent h: have) {
                            if (h.name.equals(r.name)) {
                                h.amount += r.amount * times;
                                found = true;
                            }
                        }
                        if (!found) {
                            Reagent n = (Reagent) r.clone();
                            n.amount *= times;
                            have.add(n);
                        }
                    }
                    done = true;
                }
                index++;
            }
            if (!done) {
                // Find shallowest reagent
                String shallowest = have.get(0).name;
                int shallowestI = 0;
                for (int i = 0; i < have.size(); i++) {
                    Reagent r = have.get(i);
                    if (depths.get(r.name) < depths.get(shallowest)) {
                        shallowest = r.name;
                        shallowestI = i;
                    }
                }
                // Add 1 of it
                have.get(shallowestI).amount++;
            }
        }
        return have.get(0).amount;
    }

    public Object runPart2(List<String> input) {
        //2959788
        Long trillion = 1000000000000l;
        Long amount = 3687716l;
        while (true) {
            List<Reagent> have = new ArrayList<>();
            have.add(new Reagent(amount, "FUEL"));
            Long ore = reverseToOre(input, have);
            if (ore > trillion) {
                return amount - 1;
            } else {
                Logger.log(amount + " FUEL takes " + ore + " ORE");
            }
            amount ++;
        }
    }

    private class Reagent {
        public long amount;
        public String name;
        public Reagent(long amount, String name) {
            this.amount = amount;
            this.name = name;
        }

        public Reagent(String s) {
            String[] parts = s.split(" " );
            amount = Long.parseLong(parts[0]);
            name = parts[1];
        }

        @Override
        public String toString() {
            return amount + " " + name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Reagent r = (Reagent) o;
            return name == r.name;
        }

        @Override
        public Object clone() {
            return new Reagent(amount, name);
        }
    }
}
