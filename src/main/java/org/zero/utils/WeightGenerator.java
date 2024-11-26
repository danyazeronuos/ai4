package org.zero.utils;

import java.util.function.Function;

public class WeightGenerator implements Function<Integer, double[]> {
    private static double min = 0;
    private static double max = 1;
    @Override
    public double[] apply(Integer weights) {
        double[] generatedWeights = new double[weights];
        for (int i = 0; i < weights; i++) {
            generatedWeights[i] = getRandomWeight();
        }
        return generatedWeights;
    }

    private static double getRandomWeight() {
        return (Math.random() * (max - min)) + min;

    }
}
