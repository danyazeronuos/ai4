package org.zero.lib.model;

public interface Activation {
    double activate(double x);
    double deactivate(double e, double x);
}
