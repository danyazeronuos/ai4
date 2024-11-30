package org.zero.utils;

import org.zero.lib.model.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;

public class PrepareLearningSet implements BiFunction<Integer, Integer, Dataset> {
    private final double[] numbers;

    public double[] getNumbers() {
        return numbers;
    }

    public PrepareLearningSet(double... numbers) {

        this.numbers = numbers;
    }

    @Override
    public Dataset apply(Integer limit, Integer offset) {
        ExtractLearningSet extract = new ExtractLearningSet();
        var extracted = extract.apply(limit, offset);
        var categoryArray = this.getAllCategoryMarkArray(this.numbers);

        var elementsList = new ArrayList<Picture>();
        for (double number : this.numbers) {
            for (int j = 0; j < extracted.a().length; j++) {
                if (extracted.a()[j] != number) continue;

                var picture = new Picture(extracted.a()[j], extracted.images()[j]);
                elementsList.add(picture);
            }
        }
        Collections.shuffle(elementsList);

        var imageArray = elementsList.stream().map(Picture::image).map(element -> {
            var newArr = new double[element.length];
            for (int i = 0; i < element.length; i++) {
                newArr[i] = element[i];
            }
            return newArr;
        }).toArray(double[][]::new);

        double[][] labelArray = new double[imageArray.length][this.numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < elementsList.size(); j++) {
                if (elementsList.get(j).label() != this.numbers[i]) continue;

                labelArray[j] = categoryArray[i];
            }
        }


        return new Dataset(imageArray, labelArray);
    }

    public double[][] getAllCategoryMarkArray(double[] numbers) {
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

    public record Picture(int label, int[] image) {};
}
