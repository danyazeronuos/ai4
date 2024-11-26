package org.zero.utils;

import java.util.function.Function;

public class FitBinaryIntArray implements Function<int[], double[]> {
    @Override
    public double[] apply(int[] input) {
        double[] result = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i] > 0 ? 0.1 : 0;
        }
        return result;
    }
}
