package org.zero.utils;

import org.zero.model.Activation;

import java.util.function.Function;

public class SigmoidActivation implements Activation {
    @Override
    public double activate(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    @Override
    public double deactivate(double e, double x) {
        return e * x * (1 - x);
    }
}
