package com.advent.days.Year2019.Day5;

import java.util.ArrayList;
import java.util.List;

import com.advent.Logger;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;

public class Day5 extends Day{

    public String runPart1(List<String> input) {
        IntCode.runProgram(input, 1);
        Logger.log(IntCode.output);
        return IntCode.output.get(IntCode.output.size() - 1);
    }

    public String runPart2(List<String> input) {
        IntCode.runProgram(input, 5);
        Logger.log(IntCode.output);
        return IntCode.output.get(IntCode.output.size() - 1);
    }


}
