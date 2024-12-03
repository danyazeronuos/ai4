package org.zero.lib;

import org.zero.lib.utils.WeightGenerator;
import org.zero.lib.model.Activation;

import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

public class Neuron {
    private final double a;
    private final double[] weightArray;
    private final Activation activation;

    public Neuron(
            Activation activation,
            int inputs,
            double a
    ) {
        this.activation = activation;
        this.a = a;

        weightArray = WeightGenerator.getInstance().apply(inputs);
    }

    public Double apply(double[] inputArray) {
        var neuronSum = IntStream.range(0, inputArray.length)
                .mapToDouble(getMultiplication(inputArray, weightArray))
                .sum();

        return activation.activate(neuronSum);
    }

    public double[] learn(double e, double[] prevOutput, double value) {
        double[] weightUpdates = new double[weightArray.length];

        for (int i = 0; i < weightArray.length; i++) {
            weightUpdates[i] = e * weightArray[i];
            weightArray[i] -= this.a * e * this.activation.deactivate(value) * prevOutput[i];
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
