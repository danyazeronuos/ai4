package org.zero.lib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zero.utils.LinearActivation;
import org.zero.lib.utils.RELUActivation;
import org.zero.lib.utils.SigmoidActivation;


@Getter
@AllArgsConstructor
public enum PropagationStrategy {
    SIGMOID(new SigmoidActivation()),
    LINEAR(new LinearActivation()),
    RELU(new RELUActivation());

    private final Activation function;

}
