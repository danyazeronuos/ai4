package org.zero.utils;

import lombok.Getter;
import lombok.Setter;
import org.zero.model.PropagationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Layer {
    private String name;
    @Setter
    @Getter
    private Layer parent;
    @Setter
    @Getter
    private Layer child;
    private double[] predictions;
    private final List<Neuron> neurons = new ArrayList<>();

    public Layer(
            String name,
            Integer neurons,
            Integer inputs,
            PropagationStrategy strategy,
            double a
    ) {
        this.name = name;
        for (int i = 0; i < neurons; i++) {
            Neuron newNeuron = new Neuron(
                    strategy.getFunction(),
                    inputs,
                    a
            );
            this.neurons.add(newNeuron);
        }
    }

    public double[] predict(double[] inputs) {
        this.predictions = inputs;
//        System.out.println(this.name + " -> " +Arrays.toString(inputs));
        double[] predictions = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            predictions[i] = neurons.get(i).apply(inputs);
        }

        if (this.child != null) return this.child.predict(predictions);

        return predictions;
    }

    public void learn(double[] e) {
//        System.out.println("Start learning -> "+this.name);
        double[] prevGenerationDelta = new double[0];
        for (int i = 0; i < neurons.size(); i++) {
//            System.out.println("Neurons size -> " + neurons.size() + ", Errors size -> " + e.length + ", Predictions size -> " + predictions.length);
            var neuronDelta = neurons.get(i).learn(e[i], predictions);
            prevGenerationDelta = new double[neuronDelta.length];
            for (int j = 0; j < neuronDelta.length; j++) {
                prevGenerationDelta[j] += neuronDelta[j];
            }
        }

        if (parent != null) parent.learn(prevGenerationDelta);
    }

}
