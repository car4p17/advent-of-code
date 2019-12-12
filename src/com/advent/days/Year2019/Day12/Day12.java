package com.advent.days.Year2019.Day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;

public class Day12 extends Day{
    public Object runPart1(List<String> input) {
        int maxTime = 1000;
        ArrayList<Vec3> positions = new ArrayList<>();
        ArrayList<Vec3> velocities = new ArrayList<>();
        for (String s: input) {
            velocities.add(new Vec3(0, 0,0));
            positions.add(new Vec3(s));
        }
        for (int timestep = 0; timestep < maxTime; timestep++) {
            // Update Gravity
            for (int i = 0; i < positions.size(); i++) {
                for (int e = i + 1; e < positions.size(); e++) {
                    Vec3 diff = positions.get(i).subtract(positions.get(e)).sign();
                    velocities.set(i, velocities.get(i).subtract(diff));
                    velocities.set(e, velocities.get(e).add(diff));
                }
                positions.set(i, positions.get(i).add(velocities.get(i)));
            }
        }

        long totalEnergy = 0;
        for (int i = 0; i < positions.size(); i++) {
            totalEnergy += positions.get(i).energy() * velocities.get(i).energy();
        }
        return totalEnergy;
    }

    public Object runPart2(List<String> input) {
        ArrayList<Vec3> positions = new ArrayList<>();
        ArrayList<Vec3> velocities = new ArrayList<>();
        for (String s: input) {
            velocities.add(new Vec3(0, 0,0));
            positions.add(new Vec3(s));
        }
        long timestep = 0;
        seenBefore(positions, velocities);
        while(true) {
            // Update Gravity
            for (int i = 0; i < positions.size(); i++) {
                for (int e = i + 1; e < positions.size(); e++) {
                    Vec3 diff = positions.get(i).subtract(positions.get(e)).sign();
                    velocities.set(i, velocities.get(i).subtract(diff));
                    velocities.set(e, velocities.get(e).add(diff));
                }
                positions.set(i, positions.get(i).add(velocities.get(i)));
            }
            if (seenBefore(positions, velocities)) {
                Logger.log("Seen Before: " + (timestep));
                break;
            }
            timestep ++;
        }

        long totalEnergy = 0;
        for (int i = 0; i < positions.size(); i++) {
            totalEnergy += positions.get(i).energy() * velocities.get(i).energy();
        }
        return totalEnergy;
    }

    HashSet<String> states = new HashSet<>();
    HashSet<String> smallState = new HashSet<>();
    private boolean seenBefore(ArrayList<Vec3> positions, ArrayList<Vec3> velocities) {
        String state = "";
        for (int i = 0; i < positions.size(); i++) {
            state += positions.get(i);
            state += velocities.get(i);
        }
        return !states.add(state);
    }

    private class Vec3 {
        public long x = 0;
        public long y = 0;
        public long z = 0;

        public Vec3(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vec3(String s) {
            String[] parts = s.substring(1, s.length() - 1).split(", ");
            this.x = Integer.parseInt(parts[0].substring(2));
            this.y = Integer.parseInt(parts[1].substring(2));
            this.z = Integer.parseInt(parts[2].substring(2));
        }

        public long energy() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        public Vec3 add(Vec3 other) {
            return new Vec3(x + other.x, y + other.y, z + other.z);
        }

        public Vec3 subtract(Vec3 other) {
            return new Vec3(x - other.x, y - other.y, z - other.z);
        }

        public Vec3 sign() {
            long newx = 0;
            if (x < 0) {
                newx = -1;
            } else if (x > 0) {
                newx = 1;
            }
            long newy = 0;
            if (y < 0) {
                newy = -1;
            } else if (y > 0) {
                newy = 1;
            }
            long newz = 0;
            if (z < 0) {
                newz = -1;
            } else if (z > 0) {
                newz = 1;
            }
            return new Vec3(newx, newy, newz);
        }

        public String toString() {
            return "<x=" + x + ", y=" + y + ", z=" + z + ">";

        }
    }
}
