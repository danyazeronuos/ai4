package org.zero.lib.utils;

public class WeightGenerator {
    private static final WeightGenerator INSTANCE = new WeightGenerator();
    private static double maxWeight = 1;
    private static double minWeight = -1;

    private WeightGenerator() {}

    public static WeightGenerator getInstance() {
        return INSTANCE;
    }

    public static void setMinWeight(double minWeight) {
        WeightGenerator.minWeight = minWeight;
    }

    public static void setMaxWeight(double maxWeight) {
        WeightGenerator.maxWeight = maxWeight;
    }

    public double[] apply(Integer weights) {
        double[] generatedWeights = new double[weights];
        for (int i = 0; i < weights; i++) {
            generatedWeights[i] = getRandomWeight(minWeight, maxWeight);
        }
        return generatedWeights;
    }

    private static double getRandomWeight(double min, double max) {
        return (Math.random() * (max - min)) + min;

    }
}
