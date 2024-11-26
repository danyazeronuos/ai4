package org.zero.utils;

import org.zero.model.Error;

import java.util.stream.IntStream;

public class MSE implements Error {

    @Override
    public double calculate(double[] predicted, double[] actual) {
        return -IntStream.range(0, predicted.length)
                .mapToDouble(i -> Math.pow(predicted[i] - actual[i], 2))
                .average()
                .orElse(0.0);
    }
}
