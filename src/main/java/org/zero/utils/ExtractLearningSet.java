package org.zero.utils;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ExtractLearningSet implements BiFunction<Integer, Integer, ExtractLearningSet.Pair> {


    public static int[][] readImages(String filePath, int limit, int offset) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int magic = dis.readInt();
            int numberOfImages = dis.readInt();
            if (numberOfImages > limit + offset) numberOfImages = limit;
            int rows = dis.readInt();
            int cols = dis.readInt();

            // Читаем данные изображений
            int[][] images = new int[numberOfImages][rows * cols];
            for (int i = 0; i < offset * (rows * cols); i++) {
                dis.readUnsignedByte();
            }
            for (int i = 0; i < numberOfImages; i++) {
                for (int j = 0; j < rows * cols; j++) {
                    images[i][j] = dis.readUnsignedByte();
                }
            }
            return images;
        }
    }

    public static int[] readLabels(String filePath, int limit, int offset) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int magic = dis.readInt();
            var numberOfLabels = dis.readInt();
            if (numberOfLabels > limit + offset) numberOfLabels = limit;

            int[] labels = new int[numberOfLabels];
            for (int i = 0; i < offset; i++) {
                dis.readUnsignedByte();
            }
            for (int i = 0; i < numberOfLabels; i++) {
                labels[i] = dis.readUnsignedByte();
            }
            return labels;
        }
    }


    @Override
    public Pair apply(Integer limit, Integer offset) {
        try {
            String imagesPath = "src/main/resources/train-images-idx3-ubyte";
            String labelsPath = "src/main/resources/train-labels-idx1-ubyte";

            int[][] images = readImages(imagesPath, limit, offset);
            int[] labels = readLabels(labelsPath, limit, offset);

            return new Pair(labels, images);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected record Pair(int[] a, int[][] images){};
}
