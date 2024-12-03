package org.zero;

import org.zero.lib.Net;
import org.zero.lib.model.ErrorStrategy;
import org.zero.lib.model.PropagationStrategy;
import org.zero.lib.utils.FitBinaryArray;
import org.zero.utils.*;

import java.util.Scanner;

public class Main {
    private static final double learningSpeed = 0.01;

    public static void main(String[] args) {
        var fit = new FitBinaryArray();
        var print = new PrintPicture();

        int firstImages = 800;
        var iterations = 400;

        PrepareLearningSet prepare = new PrepareLearningSet(5, 7, 4, 0, 2);
        var dataset = prepare.apply(firstImages, 0);

        for (int i = 0; i < dataset.data().length; i++) {
            dataset.data()[i] = fit.apply(dataset.data()[i]);
        }

        var ai = new Net(ErrorStrategy.MSE, true)
                .inputs(784)
                .addLayer(82, PropagationStrategy.RELU, learningSpeed)
                .addLayer(prepare.getNumbers().length, PropagationStrategy.SIGMOID, learningSpeed)
                .learn(dataset, iterations);

        System.out.println();
        System.out.println("Pictures for learning -> "+dataset.target().length);
        var testDataset = prepare.apply(200, firstImages);
        System.out.println("Pictures for test -> "+testDataset.target().length);

        var sc = new Scanner(System.in);
        var tablePrinter = new PrintTable();
        int currentIteration = 0;
        while (currentIteration < testDataset.data().length) {
            System.out.println();
            System.out.print("Enter to see next (" + currentIteration + "/" + testDataset.data().length + ") ");
            var id = sc.nextLine();
            print.accept(testDataset.data()[currentIteration]);
            var predict = ai.predict(fit.apply(testDataset.data()[currentIteration]));
            tablePrinter.print(prepare.getNumbers(), predict);
            currentIteration++;
        }
    }
}