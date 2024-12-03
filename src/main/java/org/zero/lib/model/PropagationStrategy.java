package org.zero.lib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zero.lib.utils.RELUActivation;
import org.zero.lib.utils.SigmoidActivation;


@Getter
@AllArgsConstructor
public enum PropagationStrategy {
    SIGMOID(new SigmoidActivation()),
    RELU(new RELUActivation());

    private final Activation function;

}
