package org.zero.utils;

import lombok.RequiredArgsConstructor;
import org.zero.model.Activation;

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class Neuron {
    private double a;
    private double[] weightArray;
    private final Activation activation;

    public Neuron(Activation activation, Integer inputs, double a) {
        this.activation = activation;
        this.a = a;

        var weightGenerator = new WeightGenerator();
        weightArray = weightGenerator.apply(inputs);
    }

    public Double apply(double[] inputArray) {
        var neuronSum = IntStream.range(0, inputArray.length)
                .mapToDouble(getMultiplication(inputArray, weightArray))
                .sum();

        return activation.activate(neuronSum);
    }

    public double[] learn(double e, double[] x) {
        double gradient = e; // Remove multiplication by activation derivative here.
        double[] weightUpdates = new double[weightArray.length];

        for (int i = 0; i < weightArray.length; i++) {
            weightUpdates[i] = gradient * weightArray[i];
            weightArray[i] -= this.a * gradient * x[i]; // Update weights using input gradient.
        }

        return weightUpdates;
    }

    private static IntToDoubleFunction getMultiplication(
            double[] inputArray,
            double[] weightArray
    ) {
        return index -> inputArray[index] * weightArray[index];
    }
}
