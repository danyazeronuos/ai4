package org.zero.lib;

import lombok.Getter;
import lombok.Setter;
import org.zero.lib.model.PropagationStrategy;
import org.zero.lib.utils.WeightGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Layer {
    private String name;
    @Setter
    private Layer parent;
    @Setter
    private Layer child;
    private double[] predictions;
    private final Neuron[] neurons;

    public Layer(
            Integer neurons,
            Integer inputs,
            PropagationStrategy strategy,
            double learningSpeed
    ) {
        this.neurons = new Neuron[neurons];
        for (int i = 0; i < neurons; i++) {
            Neuron newNeuron = new Neuron(
                    strategy.getFunction(),
                    inputs,
                    learningSpeed
            );
            this.neurons[i] = newNeuron;
        }
    }

    public int getNeuronsCount() {
        return neurons.length;
    }

    public double[] predict(double[] inputs) {
        this.predictions = inputs;
        double[] predictions = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            predictions[i] = neurons[i].apply(inputs);
        }

        if (this.child != null) return this.child.predict(predictions);

        return predictions;
    }

    public double[] learn(double[] e, double[] value) {
        double[] prevGenerationDelta = new double[0];
        for (int i = 0; i < neurons.length; i++) {
            prevGenerationDelta = neurons[i].learn(e[i], predictions, value[i]);
        }

        if (parent != null) parent.learn(prevGenerationDelta, this.predictions);
        return prevGenerationDelta;
    }
}
