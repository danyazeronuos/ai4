package org.zero.utils;

import org.zero.lib.model.Activation;

public class LinearActivation implements Activation {
    @Override
    public double activate(double x) {
        return x;
    }

    @Override
    public double deactivate(double e, double x) {
        return 1;
    }
}
