package com.advent.days.Year2019.Day8;

import java.util.ArrayList;
import java.util.List;
import com.advent.Logger;
import com.advent.days.Day;

public class Day8 extends Day{
    List<Integer[][]> layers = new ArrayList<>();
    int height = 6;
    int width = 25;
    public String runPart1(List<String> input) {
        String[] encoding = input.get(0).split("");
        int encodingIndex = 0;
        int layerIndex = 0;
        int layerx = 0;
        int layery = 0;
        while (encodingIndex < encoding.length) {
            if (layerx == 0 && layery == 0) {
                layers.add(new Integer[height][width]);
            }
            Integer[][] layer = layers.get(layerIndex);
            layer[layery][layerx] = Integer.parseInt(encoding[encodingIndex]);
            layers.set(layerIndex, layer);
            layerx++;
            if (layerx > width - 1) {
                layerx = 0;
                layery++;
                if (layery > height - 1) {
                    layery = 0;
                    layerIndex++;
                }
            }
            encodingIndex++;
        }

        int minzeros = Integer.MAX_VALUE;
        int onestimestwos = 0;
        for (Integer[][] layer: layers) {
            int zeros = 0;
            int ones = 0;
            int twos = 0;
            for (Integer[] row: layer) {
                for (Integer i: row) {
                    if (i == 0) {
                        zeros++;
                    } else if (i == 1) {
                        ones++;
                    } else if (i == 2) {
                        twos++;
                    }
                }
            }
            if (zeros < minzeros) {
                minzeros = zeros;
                onestimestwos = ones*twos;
            }
        }
        return onestimestwos + "";
    }

    public Object runPart2(List<String> input) {
        Integer[][] output = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            for (int e = 0; e < width; e++) {
                output[i][e] = 2;
            }
        }
        for (Integer[][] layer: layers) {
            for (int i = 0; i < height; i++) {
                for (int e = 0; e < width; e++) {
                    if (output[i][e] == 2) {
                        output[i][e] = layer[i][e];
                    }
                }
            }
        }
        String[][] betterOutput = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int e = 0; e < width; e++) {
                if (output[i][e] == 0) {
                    betterOutput[i][e] = "-";
                } else {
                    betterOutput[i][e] = "X";
                }
            }
        }
        Logger.log(betterOutput);
        return betterOutput;
    }
}
