package org.zero.utils;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ProgressBar implements BiConsumer<Integer, Integer> {
    @Override
    public void accept(Integer currentEpoch, Integer totalEpochs) {
        int barLength = 50; // Length of the progress bar
        double progress = (double) currentEpoch / totalEpochs;
        int completedLength = (int) (barLength * progress);

        // Build the progress bar
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < completedLength) {
                bar.append("#"); // Filled part
            } else {
                bar.append(" "); // Empty part
            }
        }
        bar.append("]");

        // Calculate and display percentage
        int percentage = (int) (progress * 100);
        System.out.print("\rEpoch " + currentEpoch + "/" + totalEpochs + " " + bar + " " + percentage + "%");    }
}
