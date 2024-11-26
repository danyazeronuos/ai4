package org.zero.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.zero.utils.MAE;
import org.zero.utils.MSE;

@Getter
@RequiredArgsConstructor
public enum ErrorStrategy {
    MSE(new MSE()),
    MAE(new MAE());

    private final Error error;
}
