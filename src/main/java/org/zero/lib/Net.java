package org.zero.lib;

import org.zero.lib.model.*;
import org.zero.lib.model.Error;
import org.zero.lib.utils.WeightGenerator;
import org.zero.utils.ProgressBar;

import java.time.Instant;
import java.util.Arrays;
public class Net {

    private final Error error;
    private int inputs = 1;
    private final boolean bias;
    private Layer lastLayer = null;
    private Layer firstLayer = null;

    public Net(ErrorStrategy errorStrategy, boolean bias) {
        this.error = errorStrategy.getError();
        this.bias = bias;
    }

    public Net minWeight(double minWeight) {
        WeightGenerator.setMinWeight(minWeight);

        return this;
    }

    public Net maxWeight(double maxWeight) {
        WeightGenerator.setMaxWeight(maxWeight);

        return this;
    }

    public Net inputs(Integer inputs) {
        if (this.bias) this.inputs = inputs + 1;
        else this.inputs = inputs;

        return this;
    }

    public Net addLayer(int neurons, PropagationStrategy strategy, double speed) {
        if (neurons < 0) throw new AIException("Neurons can't be less than 1");

        if (this.lastLayer == null) {
            var newLayer = new Layer(neurons, this.inputs, strategy, speed);
            this.lastLayer = newLayer;
            this.firstLayer = newLayer;
            return this;
        }
        var newLayer = new Layer(neurons, this.lastLayer.getNeuronsCount(), strategy, speed);
        this.lastLayer.setChild(newLayer);
        newLayer.setParent(this.lastLayer);
        this.lastLayer = newLayer;
        return this;
    }

    public double[] predict(double[] input) {

        int inputLength = input.length;
        if (this.bias) inputLength += 1;

        if (inputLength != this.inputs) throw new AIException("Incorrect input length");

        var inputWithBias = new double[inputLength];
        System.arraycopy(input, 0, inputWithBias, 0, input.length);
        if (this.bias) inputWithBias[input.length] = 1.0;

        return firstLayer.predict(inputWithBias);
    }

    public Net learn(Dataset dataset, int epochs) {
        if (epochs < 1) throw new AIException("Epochs can't be less than 1");
        if (dataset.data().length != dataset.target().length) throw new AIException("Dataset has incorrect format");

        var progress = new ProgressBar();

        long time = 0;
        for (int i = 0; i <= epochs; i++) {
            var start = Instant.now().toEpochMilli();
            progress.accept(i, epochs, time);
            for (int j = 0; j < dataset.data().length; j++) {
                var predict = this.predict(dataset.data()[j]);
                var error = this.learn(predict, dataset.target()[j]);
//                System.out.println(Arrays.toString(predict) + " -> " + Arrays.toString(dataset.target()[j]) + " -> " + Arrays.toString(error));
            }
            var end = Instant.now().toEpochMilli();
            time += Math.abs(end - start);

        }

        return this;
    }


    private double[] learn(double[] prediction, double[] target) {
        if (prediction.length != target.length) throw new AIException("Incorrect length of target or prediction");
        var error = this.error.calculate(prediction, target).toArray();
        lastLayer.learn(error, prediction);
        return error;
    }
}
