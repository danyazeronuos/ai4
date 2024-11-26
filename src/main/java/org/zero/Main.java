package org.zero;

import org.zero.model.ErrorStrategy;
import org.zero.model.PropagationStrategy;
import org.zero.utils.Layer;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final double a = 0.00001;

    public static void main(String[] args) {

        double[][] learnData = {
                {2.0},
                {3.0},
                {4.0},
                {5.0},
                {6.0},
                {7.0},
                {8.0},
                {9.0}
        };

        double[][] answer = {
                {6.0},
                {9.0},
                {12.0},
                {15.0},
                {18.0},
                {21.0},
                {24.0},
                {27.0}
        };

        var ai = new Sequential(ErrorStrategy.MSE);
        ai.addLayer(new Layer("Layer-1", 2, 2, PropagationStrategy.LINEAR, a));
        ai.addLayer(new Layer("Layer-2", 1, 2, PropagationStrategy.RELU, a));

        for (int j = 0; j < learnData.length; j++) {
            for (int i = 0; i < 1300; i++) {
                var res = ai.predict(learnData[j]);
                ai.learn(res, answer[j]);
            }
        }

        double[] predict = ai.predict(learnData[3]);
        System.out.println();
        System.out.println(Arrays.toString(learnData[3]) + " -> " + predict[0]);
        predict = ai.predict(learnData[4]);
        System.out.println();
        System.out.println(Arrays.toString(learnData[4]) + " -> " + predict[0]);

        var sc = new Scanner(System.in);
        double[] nums = new double[1];
        while (true) {
            var num = sc.nextDouble();
            nums[0] = num;
            double[] _predict = ai.predict(nums);
            System.out.println();
            System.out.println("Answer -> " + Arrays.toString(_predict));
        }
    }
}