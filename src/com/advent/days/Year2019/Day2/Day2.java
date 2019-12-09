package com.advent.days.Year2019.Day2;

import com.advent.Logger;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day2 extends Day {


    public String runPart1(List<String> input) {
        HashMap<Long, Long> program = IntCode.setupInput(input);
        program.put(1l, 12l);
        program.put(2l, 2l);
        HashMap<Long, Long> output = IntCode.runProgramExec(program, 0);
        return output.get(0) + "";
    }

    public String runPart2(List<String> input) {
        HashMap<Long, Long> program = IntCode.setupInput(input);
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                program.put(1l, (long) noun);
                program.put(2l, (long) verb);
                HashMap<Long, Long> output = IntCode.runProgramExec(program, 0);
                if (output.get(0) == 19690720) {
                    return (100 * noun + verb) + "";
                }
            }
        }
        return null;
    }
}
