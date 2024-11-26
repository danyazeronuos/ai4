package org.zero;

import org.zero.model.Error;
import org.zero.model.ErrorStrategy;
import org.zero.utils.Layer;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sequential {
    private final Error error;
    private Layer lastLayer = null;
    private Layer firstLayer = null;

    public Sequential(ErrorStrategy errorStrategy) {
        this.error = errorStrategy.getError();
    }

    public Sequential addLayer(Layer layer) {
        if (this.lastLayer == null) {
            this.lastLayer = layer;
            this.firstLayer = layer;
            return this;
        }
        this.lastLayer.setChild(layer);
        layer.setParent(this.lastLayer);
        this.lastLayer = layer;
        return this;
    }

    public double[] predict(double[] input) {
        var inputWithBias = new double[input.length+1];
        System.arraycopy(input, 0, inputWithBias, 0, input.length);
        inputWithBias[input.length] = 1.0;
        return firstLayer.predict(inputWithBias);
    }

    public void learn(double[] prediction, double[] target) {
        if (prediction.length != target.length) return;
        var error = this.error.calculate(prediction, target);
        System.out.println(Arrays.toString(error));


       lastLayer.learn(error);
    }
}
