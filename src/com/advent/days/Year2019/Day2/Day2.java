package com.advent.days.Year2019.Day2;

import com.advent.Logger;
import com.advent.days.Day;

import java.util.ArrayList;
import java.util.List;

public class Day2 extends Day {
    public List<Integer> setupInput(List<String> input) {
        String[] strProgram = input.get(0).split(",");
        List<Integer> program = new ArrayList<>();
        for (String s: strProgram) {
            program.add(Integer.parseInt(s));
        }
        return program;
    }

    public String runPart1(List<String> input) {
        List<Integer> program = setupInput(input);
        program.set(1, 12);
        program.set(2, 2);
        Integer[] output = runProgram(program.toArray(new Integer[0]));
        return output[0] + "";
    }

    public String runPart2(List<String> input) {
        List<Integer> program = setupInput(input);
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                program.set(1, noun);
                program.set(2, verb);
                Integer[] output = runProgram(program.toArray(new Integer[0]));
                if (output[0] == 19690720) {
                    return (100 * noun + verb) + "";
                }
            }
        }
        return null;
    }

    /**
     * OpCodes:
     *      Stop Code: 99
     *      Add: 1
     *      Multiply: 1
     * @param initialProgram
     * @return
     */
    public Integer[] runProgram(Integer[] initialProgram) {
        int instructionPointer = 0;
        Integer[] program = initialProgram.clone();
        while (program[instructionPointer] != 99) {
            int operator = program[instructionPointer];
            int operand1index = program[instructionPointer + 1];
            int operand2index = program[instructionPointer + 2];
            int output = program[instructionPointer + 3];
            int operand1 = program[operand1index];
            int operand2 = program[operand2index];
            if (operator == 1) {
                program[output] = operand1 + operand2;
            } else if (operator == 2) {
                program[output] = operand1 * operand2;
            } else {
                Logger.log("ERROR invalid operator: " + operator);
            }
            instructionPointer += 4;
            instructionPointer %= program.length;
        }
        return program;
    }
}
