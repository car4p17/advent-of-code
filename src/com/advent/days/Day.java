package com.advent.days;

import java.util.List;

public abstract class Day {
    public String inputFile = "input.txt";
    public abstract String runPart1(List<String> input);
    public abstract String runPart2(List<String> input);
}
