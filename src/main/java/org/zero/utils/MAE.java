package org.zero.utils;

import org.zero.model.Error;

import java.util.stream.IntStream;

public class MAE implements Error {
    @Override
    public double calculate(double[] predicted, double[] actual) {
        return -IntStream.range(0, predicted.length)
                .mapToDouble(i -> Math.abs(predicted[i] - actual[i]))
                .average()
                .orElse(0.0);
    }
}
