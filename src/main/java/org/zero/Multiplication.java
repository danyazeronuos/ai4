package org.zero;

import org.zero.lib.Net;
import org.zero.lib.model.Dataset;
import org.zero.lib.model.ErrorStrategy;
import org.zero.lib.model.PropagationStrategy;

import java.util.Arrays;
import java.util.Scanner;

public class Multiplication {
    private static final double learningSpeed = 0.001;
    public static void main(String[] args) {
        double[][] data = {
                {1},
                {2},
                {3},
                {4},
                {5},
                {6},
                {7},
                {8},
                {9}
        };

        double[][] target = {
                {3},
                {6},
                {9},
                {12},
                {15},
                {18},
                {21},
                {24},
                {27}
        };

        var dataset = new Dataset(data, target);

        var ai = new Net(ErrorStrategy.MSE, true)
                .inputs(1)
                .minWeight(-1)
                .maxWeight(1)
                .addLayer(1, PropagationStrategy.RELU, learningSpeed)
                .learn(dataset, 1000);

        var sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            var a = sc.nextInt();
            double[] _data = new double[1];
            _data[0] = a;
            var predict = ai.predict(_data);
            System.out.println(Arrays.toString(predict));
        }
    }
}
