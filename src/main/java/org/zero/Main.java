package org.zero;

import org.zero.model.ErrorStrategy;
import org.zero.model.PropagationStrategy;
import org.zero.utils.FitBinaryIntArray;
import org.zero.utils.Layer;
import org.zero.utils.PrepareLearningSet;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final double a = 0.001;

    public static void main(String[] args) {

        double[][] learnData = {
                {2.0},
                {3.0},
                {4.0},
                {5.0},
                {6.0},
                {7.0},
                {8.0},
                {9.0},
                {10.0},
        };

        double[][] answer = {
                {6.0},
                {9.0},
                {12.0},
                {15.0},
                {18.0},
                {21.0},
                {24.0},
                {27.0},
                {30.0},
        };

        PrepareLearningSet prepare = new PrepareLearningSet(5, 7);
        var dataset = prepare.apply(200, 0);

        var ai = new Sequential(ErrorStrategy.MSE);
        ai.addLayer(new Layer("Layer-1", 169, 785, PropagationStrategy.RELU, a));
        ai.addLayer(new Layer("Layer-3", 2, 169, PropagationStrategy.SIGMOID, a));

        var fit = new FitBinaryIntArray();
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < dataset.matrixs().length; j++) {

                var res = ai.predict(fit.apply(dataset.matrixs()[j]));
                System.out.print(Arrays.toString(res) + " -> " + Arrays.toString(dataset.answers()[j]) + " -> ");
                ai.learn(res, dataset.answers()[j]);
            }
        }

        System.out.println(dataset.answers().length);
      PrepareLearningSet prepareTest = new PrepareLearningSet(5, 7);
        var testDataset = prepareTest.apply(200, 200);
        System.out.println(testDataset.answers().length);

        var sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.print("Enter -> ");
            var id = sc.nextInt();
            var predict = ai.predict(fit.apply(testDataset.matrixs()[id]));
            System.out.println();
            System.out.printf("%s -> %.2f, %.2f", Arrays.toString(testDataset.answers()[id]), predict[0], predict[1]);
        }
    }
}