package org.zero.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zero.utils.LinearActivation;
import org.zero.utils.RELUActivation;
import org.zero.utils.SigmoidActivation;


@Getter
@AllArgsConstructor
public enum PropagationStrategy {
    SIGMOID(new SigmoidActivation()),
    LINEAR(new LinearActivation()),
    RELU(new RELUActivation());

    private final Activation function;

}
