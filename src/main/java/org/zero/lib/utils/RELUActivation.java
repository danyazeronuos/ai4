package org.zero.lib.utils;

import org.zero.lib.model.Activation;

public class RELUActivation implements Activation {
    @Override
    public double activate(double x) {
        return Math.max(0, x);
    }

    @Override
    public double deactivate(double e, double x) {
        return x > 0 ? 1 : 0;
    }
}
