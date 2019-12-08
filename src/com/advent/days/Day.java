package com.advent.days;

import java.util.List;

public abstract class Day {
    public String inputFile = "input.txt";
    public abstract Object runPart1(List<String> input);
    public abstract Object runPart2(List<String> input);
}
