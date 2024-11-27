package org.zero;

import org.zero.model.ErrorStrategy;
import org.zero.model.PropagationStrategy;
import org.zero.utils.*;

import java.util.Scanner;

public class Main {
    private static final double a = 0.001;

    public static void main(String[] args) {
        var fit = new FitBinaryIntArray();
        var progress = new ProgressBar();
        var print = new PrintPicture();

        int firstImages = 1200;
        var iterations = 300;

        PrepareLearningSet prepare = new PrepareLearningSet(5, 7, 2);
        var dataset = prepare.apply(firstImages, 0);

        var ai = new Sequential(ErrorStrategy.MSE);
        ai.addLayer(new Layer("Layer-1", 169, 785, PropagationStrategy.RELU, a));
        ai.addLayer(new Layer("Layer-3", prepare.getNumbers().length, 169, PropagationStrategy.SIGMOID, a));


        for (int i = 0; i <= iterations; i++) {
            progress.accept(i, iterations);
            for (int j = 0; j < dataset.matrixs().length; j++) {

                var res = ai.predict(fit.apply(dataset.matrixs()[j]));
//                System.out.print(Arrays.toString(res) + " -> " + Arrays.toString(dataset.answers()[j]) + " -> ");
                ai.learn(res, dataset.answers()[j]);
            }
        }

        System.out.println();
        System.out.println("Used pictures for learning -> " + dataset.answers().length);
        var testDataset = prepare.apply(200, firstImages);
        System.out.println("Pictures for test -> "+testDataset.answers().length);

        var sc = new Scanner(System.in);
        var tablePrinter = new PrintTable();
        while (true) {
            System.out.println();
            System.out.print("Enter -> ");
            var id = sc.nextInt();
            print.accept(testDataset.matrixs()[id]);
            var predict = ai.predict(fit.apply(testDataset.matrixs()[id]));
            tablePrinter.print(prepare.getNumbers(), predict);
        }
    }
}