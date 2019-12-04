package com.advent.days.Year2019.Day4;

import java.util.ArrayList;
import java.util.List;
import com.advent.Logger;
import com.advent.Utils;
import com.advent.days.Day;

public class Day4 extends Day{
    public String runPart1(List<String> input) {
        String[] range = input.get(0).split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);
        int count = 0;
        for (int i = start; i < end; i++) {
            boolean hasDouble = false;
            boolean increasing = true;
            int[] digits = Utils.digits(i);
            for (int e = 1; e < digits.length; e++) {
                if (digits[e] == digits[e - 1]) {
                    hasDouble = true;
                }
                if (digits[e] < digits[e - 1]) {
                    increasing = false;
                }
            }
            if (hasDouble && increasing) {
                count++;
            }
        }
        return "" + count;
    }

    public String runPart2(List<String> input) {
        String[] range = input.get(0).split("-");
        int start = Integer.parseInt(range[0]);
        int end = Integer.parseInt(range[1]);
        ArrayList<Integer> passing = new ArrayList<>();
        for (int i = start; i < end; i++) {
            boolean hasDouble = false;
            boolean increasing = true;
            int[] digits = Utils.digits(i);
            for (int e = 1; e < digits.length; e++) {
                if (digits[e] == digits[e - 1] && (digits.length <= e + 1 || digits[e + 1] != digits[e]) && (e == 1 || digits[e - 2] != digits[e])) {
                    hasDouble = true;
                }
                if (digits[e] < digits[e - 1]) {
                    increasing = false;
                }
            }
            if (hasDouble && increasing) {
                passing.add(i);
            }
        }
        return "" + passing.size();
    }
}
