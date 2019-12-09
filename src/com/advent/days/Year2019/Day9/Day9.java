package com.advent.days.Year2019.Day9;

import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;
import com.advent.days.Year2019.IntCode.IntCode;

public class Day9 extends Day{
    public Object runPart1(List<String> input) {
        IntCode.runProgram(input, 1);
        return IntCode.output;
    }

    public Object runPart2(List<String> input) {
        IntCode.runProgram(input, 2);
        return IntCode.output;
    }
}
