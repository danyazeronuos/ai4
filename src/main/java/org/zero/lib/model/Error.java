package org.zero.lib.model;

import java.util.stream.DoubleStream;

public interface Error {
    DoubleStream calculate(double[] predicted, double[] actual);
}
