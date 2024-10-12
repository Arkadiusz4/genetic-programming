package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.function.Function;

public class DataGenerator {
    public static int numberOfCases = 100;
    public static int nRand = 10;
    public static double minRand = -5;
    public static double maxRand = 5;
    public static Random random = new Random();

    public static void main(String[] args) {
        try {
            String folderPath = "tiny-gp/data";

            double[][] domains1 = {{-10, 10}, {0, 100}, {-1, 1}, {-1000, 1000}};
            generateData(folderPath, "task1", domains1, 1, x -> 5 * Math.pow(x[0], 3) - 2 * Math.pow(x[0], 2) + 3 * x[0] - 17);

            double[][] domains2 = {{-3.14, 3.14}, {0, 7}, {0, 100}, {-100, 100}};
            generateData(folderPath, "task2", domains2, 1, x -> Math.sin(x[0]) + Math.cos(x[0]));

            double[][] domains3 = {{0, 4}, {0, 9}, {0, 99}, {0, 999}};
            generateData(folderPath, "task3", domains3, 1, x -> 2 * Math.log(x[0] + 1));

            double[][] domains4 = {{0, 1}, {-10, 10}, {0, 100}, {-1000, 1000}};
            generateData(folderPath, "task4", domains4, 2, x -> x[0] + 2 * x[1]);

            double[][] domains5 = {{-3.14, 3.14}, {0, 7}, {0, 100}, {-100, 100}};
            generateData(folderPath, "task5", domains5, 2, x -> Math.sin(x[0] / 2) + 2 * Math.cos(x[0]));

            double[][] domains6 = {{-10, 10}, {0, 100}, {-1, 1}, {-1000, 1000}};
            generateData(folderPath, "task6", domains6, 2, x -> Math.pow(x[0], 2) + 3 * x[0] * x[1] - 7 * x[1] + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateData(String folderPath, String taskName, double[][] domains, int nVar, Function<double[], Double> function) throws IOException {
        for (int i = 0; i < domains.length; i++) {
            String fileName = Paths.get(folderPath, taskName + "." + (i + 1) + ".dat").toString();
            FileWriter fileWriter = new FileWriter(fileName);

            fileWriter.write(nVar + " " + nRand + " " + minRand + " " + maxRand + " " + numberOfCases + "\n");

            for (int j = 0; j < numberOfCases; j++) {

                double[] variables = new double[nVar];

                for (int k = 0; k < nVar; k++) {
                    variables[k] = domains[i][0] + (domains[i][1] - domains[i][0]) * random.nextDouble();
                }
                double result = function.apply(variables);

                for (double var : variables) {
                    fileWriter.write(var + " ");
                }

                fileWriter.write(result + "\n");
            }
            fileWriter.close();
        }
    }
}
