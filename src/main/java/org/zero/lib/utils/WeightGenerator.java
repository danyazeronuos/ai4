package org.zero.lib.utils;

import java.util.function.Function;

public class WeightGenerator {
    private static final WeightGenerator INSTANCE = new WeightGenerator();

    private WeightGenerator() {}

    public static WeightGenerator getInstance() {
        return INSTANCE;
    }

    public double[] apply(Integer weights, double min, double max) {
        double[] generatedWeights = new double[weights];
        for (int i = 0; i < weights; i++) {
            generatedWeights[i] = getRandomWeight(min, max);
        }
        return generatedWeights;
    }

    private static double getRandomWeight(double min, double max) {
        return (Math.random() * (max - min)) + min;

    }
}
