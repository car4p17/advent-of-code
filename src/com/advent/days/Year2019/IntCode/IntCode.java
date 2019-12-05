package com.advent.days.Year2019.IntCode;

import com.advent.Logger;
import com.advent.Utils;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class IntCode {
    private static int instructionPointer = 0;
    private static List<Integer> program;
    public static List<String> output = new ArrayList<>();
    private static final int STOP_CODE = 99;
    private static final int ADD_CODE = 1;
    private static final int MULTIPLY_CODE = 2;
    private static final int INPUT_CODE = 3;
    private static final int OUTPUT_CODE = 4;
    private static final int JUMP_IF_TRUE_CODE = 5;
    private static final int JUMP_IF_FALSE_CODE = 6;
    private static final int LESS_THAN_CODE = 7;
    private static final int EQUALS_CODE = 8;

    public static List<Integer> setupInput(List<String> input) {
        String[] strProgram = input.get(0).split(",");
        List<Integer> program = new ArrayList<>();
        for (String s: strProgram) {
            program.add(Integer.parseInt(s));
        }
        return program;
    }

    private static int getOpcode(int instruction) {
        return instruction % 100;
    }

    private static int[] getModes(int instruction, int numParams) {
        String[] modesStr = Utils.pad("" + instruction / 100, numParams, "0").split("");
        int[] modes = new int[numParams];
        for (int i = 0; i < numParams; i++) {
            modes[numParams - 1 - i] = Integer.parseInt(modesStr[i]);
        }
        return modes;
    }

    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;
    // Returns a lits of pairs (index, value);
    private static Pair<Integer, Integer>[] getParams(int[] modes) {
        Pair<Integer, Integer>[] params = new Pair[modes.length];
        for (int i = 0; i < modes.length; i++) {
            int param = program.get(instructionPointer + i + 1);
            switch (modes[i]) {
                case POSITION_MODE:
                    params[i] = new Pair(param, program.get(param));
                    break;
                case IMMEDIATE_MODE:
                    params[i] = new Pair(instructionPointer + i + 1, param);
                    break;
            }
        }
        return params;
    }

    private static int getNumParams(int opcode) {
        switch(opcode) {
            case ADD_CODE:
            case MULTIPLY_CODE:
            case LESS_THAN_CODE:
            case EQUALS_CODE:
                return 3;
            case INPUT_CODE:
                return 1;
            case OUTPUT_CODE:
                return 1;
            case STOP_CODE:
                return 0;
            case JUMP_IF_TRUE_CODE:
            case JUMP_IF_FALSE_CODE:
                return 2;
            default:
                return 0;
        }
    }

    public static List<Integer> runProgram(List<String> inputData, int input) {
        return runProgramExec(setupInput(inputData), input);
    }

    public static List<Integer> runProgramExec(List<Integer> initialProgram, int input) {
        instructionPointer = 0;
        output = new ArrayList<>();
        program = Utils.clone(initialProgram);
        int opcode = 0;
        do  {
            int instruction = program.get(instructionPointer);
            opcode = getOpcode(instruction);
            int numParams = getNumParams(opcode);
            int[] modes = getModes(instruction, numParams);
            Pair<Integer, Integer>[] params = getParams(modes);
            boolean incrementIP = true;
            switch(opcode) {
                case ADD_CODE:
                    program.set(params[2].getKey(), params[0].getValue() + params[1].getValue());
                    break;
                case MULTIPLY_CODE:
                    program.set(params[2].getKey(), params[0].getValue() * params[1].getValue());
                    break;
                case INPUT_CODE:
                    program.set(params[0].getKey(), input);
                    break;
                case OUTPUT_CODE:
                    output.add("" + params[0].getValue());
                    break;
                case JUMP_IF_TRUE_CODE:
                    if (params[0].getValue() != 0) {
                        instructionPointer = params[1].getValue();
                        incrementIP = false;
                    }
                    break;
                case JUMP_IF_FALSE_CODE:
                    if (params[0].getValue() == 0) {
                        instructionPointer = params[1].getValue();
                        incrementIP = false;
                    }
                    break;
                case LESS_THAN_CODE:
                    program.set(params[2].getKey(), (params[0].getValue().compareTo(params[1].getValue()) < 0 ? 1 : 0));
                    break;
                case EQUALS_CODE:
                    program.set(params[2].getKey(), (params[0].getValue().equals(params[1].getValue()) ? 1 : 0));
                    break;
            }
            if (incrementIP) {
                instructionPointer += numParams + 1;
                instructionPointer %= program.size();
            }
        } while (opcode != STOP_CODE);
        return program;
    }
}
