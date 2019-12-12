package com.advent.days.Year2019.IntCode;

import com.advent.Logger;
import com.advent.Utils;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IntCode {
    private static boolean info = false;
    private static long instructionPointer = 0;
    private static long relativeBase = 0;
    private static HashMap<Long, Long> program;
    public static List<Long> output = new ArrayList<>();
    private static final int STOP_CODE = 99;
    private static final int ADD_CODE = 1;
    private static final int MULTIPLY_CODE = 2;
    private static final int INPUT_CODE = 3;
    private static final int OUTPUT_CODE = 4;
    private static final int JUMP_IF_TRUE_CODE = 5;
    private static final int JUMP_IF_FALSE_CODE = 6;
    private static final int LESS_THAN_CODE = 7;
    private static final int EQUALS_CODE = 8;
    private static final int RELATIVE_BASE_CODE = 9;

    public static HashMap<Long, Long> setupInput(List<String> input) {
        String[] strProgram = input.get(0).split(",");
        HashMap<Long, Long> program = new HashMap<>();
        for (int i = 0; i < strProgram.length; i++) {
            program.put((long) i, Long.parseLong(strProgram[i]));
        }
        return program;
    }

    private static int getOpcode(long instruction) {
        return (int) instruction % 100;
    }

    private static int[] getModes(long instruction, int numParams) {
        String[] modesStr = Utils.pad("" + instruction / 100, numParams, "0").split("");
        int[] modes = new int[numParams];
        for (int i = 0; i < numParams; i++) {
            modes[numParams - 1 - i] = Integer.parseInt(modesStr[i]);
        }
        return modes;
    }

    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;
    private static final int RELATIVE_MODE = 2;
    // Returns a lits of pairs (index, value);
    private static Pair<Long, Long>[] getParams(int[] modes) {
        Pair<Long, Long>[] params = new Pair[modes.length];
        for (int i = 0; i < modes.length; i++) {
            long param = program.get(instructionPointer + i + 1);
            switch (modes[i]) {
                case POSITION_MODE:
                    params[i] = new Pair<>(param, program.get(param));
                    break;
                case IMMEDIATE_MODE:
                    params[i] = new Pair<>(instructionPointer + i + 1, param);
                    break;
                case RELATIVE_MODE:
                    long index = param + relativeBase;
                    params[i] = new Pair<>(index, program.get(index));
            }
            if (params[i].getValue() == null) {
                params[i] = new Pair<>(params[i].getKey(), 0l);
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
            case RELATIVE_BASE_CODE:
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

    public static HashMap<Long, Long> runProgram(List<String> inputData, int input) {
        return runProgramExec(setupInput(inputData), input);
    }

    public static Pair<Long, HashMap<Long, Long>> runProgram(List<String> inputData, long[] input, long instructionPointerVal) {
        return runProgramExec(setupInput(inputData), input, instructionPointerVal);
    }

    public static HashMap<Long, Long> runProgramExec(List<Integer> initialProgram, int input) {
        long[] ar = {input};
        return runProgramExec(listToMap(initialProgram), ar, 0).getValue();
    }
    public static HashMap<Long, Long> runProgramExec(HashMap<Long, Long> initialProgram, int input) {
        long[] ar = {input};
        return runProgramExec(initialProgram, ar, 0).getValue();
    }

    private static HashMap<Long, Long> listToMap(List<Integer> initialProgram) {
        HashMap<Long, Long> map = new HashMap<>();
        for (int i = 0; i < initialProgram.size(); i++) {
            map.put((long) i, (long) initialProgram.get(i));
        }
        return map;
    }

    public static Pair<Long, HashMap<Long, Long>> runProgramExec(HashMap<Long, Long> initialProgram, long[] input, long instructionPointerVal) {
        instructionPointer = instructionPointerVal;
        relativeBase = 0;
        output = new ArrayList<>();
        program = (HashMap<Long, Long>) initialProgram.clone();
        int opcode = 0;
        int inputIndex = 0;
        do  {
            long instruction = program.get(instructionPointer);
            opcode = getOpcode(instruction);
            int numParams = getNumParams(opcode);
            int[] modes = getModes(instruction, numParams);
            Pair<Long, Long>[] params = getParams(modes);
            boolean incrementIP = true;
            switch(opcode) {
                case ADD_CODE:
                    if (info) {
                        Logger.log("ADD " + params[0].getValue() + " " + params[1].getValue() + " " + params[2].getKey());
                    }
                    program.put(params[2].getKey(), params[0].getValue() + params[1].getValue());
                    break;
                case MULTIPLY_CODE:
                    if (info) {
                        Logger.log("MUL " + params[0].getValue() + " " + params[1].getValue() + " " + params[2].getKey());
                    }
                    program.put(params[2].getKey(), params[0].getValue() * params[1].getValue());
                    break;
                case INPUT_CODE:
                    if (inputIndex < input.length) {
                        if (info) {
                            Logger.log("IN " + params[0].getKey());
                        }
                        program.put(params[0].getKey(), (long) input[inputIndex]);
                        inputIndex++;
                    } else {
                        if (info) {
                            Logger.log("PROGRAM WAITING FOR INPUT");
                        }
                        return new Pair<>(instructionPointer, (HashMap<Long, Long>) program.clone());
                    }
                    break;
                case OUTPUT_CODE:
                    if (info) {
                        Logger.log("OUT " + params[0].getValue());
                    }
                    output.add(params[0].getValue());
                    break;
                case JUMP_IF_TRUE_CODE:
                    if (info) {
                        Logger.log("JIFTRUE " + params[0].getValue() + " " + params[1].getValue());
                    }
                    if (params[0].getValue() != 0) {
                        instructionPointer = params[1].getValue();
                        incrementIP = false;
                    }
                    break;
                case JUMP_IF_FALSE_CODE:
                    if (info) {
                        Logger.log("JIFFALSE " + params[0].getValue() + " " + params[1].getValue());
                    }
                    if (params[0].getValue() == 0) {
                        instructionPointer = params[1].getValue();
                        incrementIP = false;
                    }
                    break;
                case LESS_THAN_CODE:
                    if (info) {
                        Logger.log("LESS " + params[0].getValue() + " " + params[1].getValue() + " " + params[2].getKey());
                    }
                    program.put(params[2].getKey(), (params[0].getValue().compareTo(params[1].getValue()) < 0 ? 1l : 0l));
                    break;
                case EQUALS_CODE:
                    if (info) {
                        Logger.log("EQ " + params[0].getValue() + " " + params[1].getValue() + " " + params[2].getKey());
                    }
                    program.put(params[2].getKey(), (params[0].getValue().equals(params[1].getValue()) ? 1l : 0l));
                    break;
                case RELATIVE_BASE_CODE:
                    if (info) {
                        Logger.log("RELATIVE_BASE " + params[0].getValue());
                    }
                    relativeBase += params[0].getValue();
                    break;
            }
            if (incrementIP) {
                instructionPointer += numParams + 1;
                instructionPointer %= program.size();
            }
        } while (opcode != STOP_CODE);
        return new Pair<>(Long.MAX_VALUE, (HashMap<Long, Long>) program.clone());
    }
}
