package org.zero.utils;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ProgressBar {
    public void accept(Integer currentEpoch, Integer totalEpochs, long timeNanoseconds) {
        int barLength = 50;
        double progress = (double) currentEpoch / totalEpochs;
        int completedLength = (int) (barLength * progress);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < completedLength) {
                bar.append("#");
            } else {
                bar.append(" ");
            }
        }
        bar.append("]");

        int percentage = (int) (progress * 100);
        System.out.print("\rEpoch " + currentEpoch + "/" + totalEpochs + " " + bar + " " + percentage + "%" + ", Left ≈ " + getCalculatedLeftTime(timeNanoseconds, currentEpoch, totalEpochs) + "s");
    }

    private long getCalculatedLeftTime(long timeForEpoch, int epoch, int totalEpochs) {
        if (timeForEpoch <= 0) return 0;

        long middleValue = timeForEpoch / epoch;
        int leftIterations = totalEpochs - epoch;


        return  (middleValue * leftIterations) / 1000;
    }

}
