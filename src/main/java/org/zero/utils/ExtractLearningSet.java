package org.zero.utils;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Function;

public class ExtractLearningSet implements Function<Integer, ExtractLearningSet.Pair> {


    public static int[][] readImages(String filePath, int targetCount) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int magic = dis.readInt();
            int numberOfImages = dis.readInt();
            if (numberOfImages > targetCount) numberOfImages = targetCount;
            int rows = dis.readInt();
            int cols = dis.readInt();

            // Читаем данные изображений
            int[][] images = new int[numberOfImages][rows * cols];
            for (int i = 0; i < numberOfImages; i++) {
                for (int j = 0; j < rows * cols; j++) {
                    images[i][j] = dis.readUnsignedByte();
                }
            }
            return images;
        }
    }

    public static int[] readLabels(String filePath, int targetCount) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int magic = dis.readInt();
            var numberOfLabels = dis.readInt();
            if (numberOfLabels > targetCount) numberOfLabels = targetCount;

            int[] labels = new int[numberOfLabels];
            for (int i = 0; i < numberOfLabels; i++) {
                labels[i] = dis.readUnsignedByte();
            }
            return labels;
        }
    }


    @Override
    public Pair apply(Integer imagesCount) {
        try {
            String imagesPath = "src/main/resources/train-images-idx3-ubyte";
            String labelsPath = "src/main/resources/train-labels-idx1-ubyte";

            int[][] images = readImages(imagesPath, imagesCount);
            int[] labels = readLabels(labelsPath, imagesCount);

            return new Pair(labels, images);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    protected record Pair(int[] a, int[][] images){};
}
