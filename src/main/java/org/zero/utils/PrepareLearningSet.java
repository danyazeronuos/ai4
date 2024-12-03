package org.zero.utils;

import org.zero.lib.model.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        int[] sizes = new int[this.numbers.length];
        for (int i = 0; i < this.numbers.length; i++) {
            int currentSize = 0;
            for (int j = 0; j < extracted.a().length; j++) {
                if (extracted.a()[j] != this.numbers[i]) continue;

                var picture = new Picture(extracted.a()[j], extracted.images()[j]);
                elementsList.add(picture);
                currentSize++;
            }
            sizes[i] = currentSize;
        }
        int maxSetSize = this.getMinimalValueOfArray(sizes);
        var fitBySetSize = new ArrayList<Picture>();
        for (int i = 0; i < this.numbers.length; i++) {
            final double current = this.numbers[i];
            List<Picture> fittedBySetSize = elementsList.stream()
                    .filter(element -> element.label == current)
                    .limit(maxSetSize)
                    .toList();
            fitBySetSize.addAll(fittedBySetSize);
        }
        Collections.shuffle(fitBySetSize);

        var imageArray = fitBySetSize.stream().map(Picture::image).map(element -> {
            var newArr = new double[element.length];
            for (int i = 0; i < element.length; i++) {
                newArr[i] = element[i];
            }
            return newArr;
        }).toArray(double[][]::new);

        double[][] labelArray = new double[imageArray.length][this.numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < fitBySetSize.size(); j++) {
                if (fitBySetSize.get(j).label() != this.numbers[i]) continue;

                labelArray[j] = categoryArray[i];
            }
        }


        return new Dataset(imageArray, labelArray);
    }

    private int getMinimalValueOfArray(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) min = array[i];
        }
        return min;
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
