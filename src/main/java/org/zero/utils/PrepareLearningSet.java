package org.zero.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public class PrepareLearningSet implements Function<Integer, PrepareLearningSet.Pair> {

    @Override
    public Pair apply(Integer imagesCount) {
        ExtractLearningSet extract = new ExtractLearningSet();
        var extracted = extract.apply(imagesCount);

        var fiveMatrixList = new ArrayList<int[]>();
        var sevenMatrixList = new ArrayList<int[]>();
        for (int i = 0; i < extracted.a().length; i++) {
            if (extracted.a()[i] == 5) fiveMatrixList.add(extracted.images()[i]);
            if (extracted.a()[i] == 7) sevenMatrixList.add(extracted.images()[i]);
        }
        var arr = Stream.of(fiveMatrixList, sevenMatrixList).flatMap(Collection::stream).toArray(int[][]::new);
        var answers = new double[arr.length][2];
        var first = new double[]{0, 1};
        var second = new double[]{1, 0};

        for (int i = 0; i < arr.length; i++) {
            if (i < fiveMatrixList.size()) answers[i] = first;
            else answers[i] = second;
        }

        return new Pair(arr, answers);
    }

    public record Pair(int[][] matrixs, double[][] answers){};
}
