package com.example;

import java.io.*;
import java.util.function.DoubleUnaryOperator;

public class DataGenerator {

    public static void generateData(String fileName, DoubleUnaryOperator function, double start, double end, double step) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("1 100 " + start + " " + end + " " + ((int) ((end - start) / step) + 1));
            writer.newLine();

            for (double x = start; x <= end; x += step) {
                double y = function.applyAsDouble(x);
                writer.write(x + "\t" + y);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Przykład generowania danych dla funkcji f(x) = 5*x^3 - 2*x^2 + 3*x - 17 w dziedzinie [-10, 10]
        generateData("tiny-gp/data/generated_data.dat", x -> 5 * Math.pow(x, 3) - 2 * Math.pow(x, 2) + 3 * x - 17, -10, 10, 0.1);
    }
}
