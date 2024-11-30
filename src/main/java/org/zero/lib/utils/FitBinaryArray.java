package org.zero.lib.utils;

import java.util.function.Function;

public class FitBinaryArray implements Function<double[], double[]> {
    @Override
    public double[] apply(double[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i] > 0 ? 0.1 : 0;
        }
        return input;
    }
}
