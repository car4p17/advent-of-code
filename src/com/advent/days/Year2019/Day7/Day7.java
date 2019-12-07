package com.advent.days.Year2019.Day7;

import java.util.ArrayList;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;
import javafx.util.Pair;

public class Day7 extends Day{
    public String runPart1(List<String> input) {
        int max = Integer.MIN_VALUE;
        Integer[] phases = {0, 1, 2, 3, 4};
        List<Integer[]> shuffles = Utils.getAllPermutations(phases);
        for (Integer[] seq: shuffles) {
            int inputVal = 0;
            for (int i = 0; i < seq.length; i++) {
                int[] ar = {seq[i], inputVal};
                IntCode.runProgram(input, ar, 0);
                inputVal = IntCode.output.get(0);
            }
            max = Math.max(max, inputVal);
        }
        return "" + max;
    }

    public String runPart2(List<String> input) {
        int max = Integer.MIN_VALUE;
        Integer[] phases = {5, 6, 7, 8, 9};
        List<Integer> startProgram = IntCode.setupInput(input);
        List<Integer[]> shuffles = Utils.getAllPermutations(phases);

        for (Integer[] seq: shuffles) {
            int inputVal = 0;
            boolean done = false;
            int module = 0;
            int phase = 0;
            Pair<Integer, List<Integer>>[] states = new Pair[phases.length];
            for (int i = 0; i < phases.length; i++) {
                states[i] = new Pair(0, Utils.clone(startProgram));
            }
            while (!done) {
                if (phase < seq.length) {
                    int[] ar = {seq[phase], inputVal};
                    states[module] = IntCode.runProgramExec(states[module].getValue(), ar, states[module].getKey());
                    inputVal = IntCode.output.get(0);
                } else {
                    int[] ar = {inputVal};
                    states[module] = IntCode.runProgramExec(states[module].getValue(), ar, states[module].getKey());
                    inputVal = IntCode.output.get(0);
                }
                module++;
                phase++;
                module%=states.length;
                if (states[module].getKey() == Integer.MAX_VALUE) {
                    done = true;
                }
            }

            max = Math.max(max, inputVal);
        }
        return "" + max;
    }
}
