package org.zero.model;

public interface Activation {
    double activate(double x);
    double deactivate(double e, double x);
}
