package org.zero.utils;

import java.util.function.Consumer;

public class PrintPicture implements Consumer<double[]> {
    @Override
    public void accept(double[] images) {
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                System.out.print(images[i * 28 + j] > 0 ? "%" : " ");
            }
            System.out.println();
        }
    }
}
