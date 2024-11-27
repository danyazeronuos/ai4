package org.zero.utils;

public class PrintTable {

    public void print(double[] header, double[] data) {

        int columnWidth = 8;

        // Рисуем таблицу
        printLine(header.length, columnWidth); // Верхняя граница
        printRow(header, columnWidth);   // Заголовки
        printLine(data.length, columnWidth); // Разделитель
        printRow(data, columnWidth);     // Первый ряд
        printLine(data.length, columnWidth);
    }

    private static void printLine(int columns, int columnWidth) {
        for (int i = 0; i < columns; i++) {
            System.out.print("+");
            System.out.print("-".repeat(columnWidth));
        }
        System.out.println("+");
    }

    private static void printRow(double[] row, int columnWidth) {
        for (double cell : row) {
            System.out.print("|");
            System.out.printf("%" + columnWidth + ".2f", cell);
        }
        System.out.println("|");
    }
}
