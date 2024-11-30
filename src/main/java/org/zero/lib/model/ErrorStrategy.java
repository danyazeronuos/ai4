package org.zero.lib.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.zero.lib.utils.MSE;

@Getter
@RequiredArgsConstructor
public enum ErrorStrategy {
    MSE(new MSE());

    private final Error error;
}
