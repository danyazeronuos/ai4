package org.zero.lib.utils;

import org.zero.lib.model.Activation;

public class SigmoidActivation implements Activation {
    @Override
    public double activate(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    @Override
    public double deactivate(double x) {
        return x * (1 - x);
    }
}
