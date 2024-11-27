package org.zero.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class PrepareLearningSet implements BiFunction<Integer, Integer, PrepareLearningSet.Pair> {
    private int[] numbers;

    public PrepareLearningSet(int... numbers) {
        this.numbers = numbers;
    }

    @Override
    public Pair apply(Integer limit, Integer offset) {
        ExtractLearningSet extract = new ExtractLearningSet();
        var extracted = extract.apply(limit, offset);
        System.out.println("extracted: " + extracted.images().length);
        var categoryArray = this.getAllCategoryMarkArray(this.numbers);

        var elementsList = new ArrayList<Picture>();
        for (int number : this.numbers) {
            for (int j = 0; j < extracted.a().length; j++) {
                if (extracted.a()[j] != number) continue;

                var picture = new Picture(extracted.a()[j], extracted.images()[j]);
                elementsList.add(picture);
            }
        }
        System.out.println("Found " + elementsList.size() + " elements");
        Collections.shuffle(elementsList);

        var imageArray = elementsList.stream().map(Picture::image).toArray(int[][]::new);

        double[][] labelArray = new double[imageArray.length][this.numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < elementsList.size(); j++) {
                if (elementsList.get(j).label() != this.numbers[i]) continue;

                labelArray[j] = categoryArray[i];
            }
        }


        return new Pair(imageArray, labelArray);
    }

    public double[][] getAllCategoryMarkArray(int[] numbers) {
        var categoryArray = new double[numbers.length][numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            categoryArray[i] = this.getCategoryMark(i, numbers.length);
        }
        return categoryArray;
    }

    public double[] getCategoryMark(int index, int length) {
        var mark = new double[length];
        mark[index] = 1;
        return mark;
    }

    public record Picture(int label, int[] image) {
    }

    ;

    public record Pair(int[][] matrixs, double[][] answers) {
    }

    ;
}
