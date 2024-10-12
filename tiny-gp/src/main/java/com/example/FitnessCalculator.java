package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

public class FitnessCalculator {
    static double[][] targets;
    static double[] x = new double[Constants.FSET_START];

    public static void setupFitness(String fname) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));
            String line = in.readLine();
            StringTokenizer tokens = new StringTokenizer(line);
            Constants.varnumber = Integer.parseInt(tokens.nextToken().trim());
            Constants.randomnumber = Integer.parseInt(tokens.nextToken().trim());
            Constants.minrandom = Double.parseDouble(tokens.nextToken().trim());
            Constants.maxrandom = Double.parseDouble(tokens.nextToken().trim());
            Constants.fitnesscases = Integer.parseInt(tokens.nextToken().trim());
            targets = new double[Constants.fitnesscases][Constants.varnumber + 1];

            if (Constants.varnumber + Constants.randomnumber >= Constants.FSET_START)
                System.out.println("too many variables and constants");

            for (int i = 0; i < Constants.fitnesscases; i++) {
                line = in.readLine();
                tokens = new StringTokenizer(line);
                for (int j = 0; j <= Constants.varnumber; j++)
                    targets[i][j] = Double.parseDouble(tokens.nextToken().trim());
            }

            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Please provide a data file");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("ERROR: Incorrect data format");
            System.exit(0);
        }
    }

    public static double fitnessFunction(char[] Prog) {
        double fit = 0.0;
        int len = GeneticOperations.traverse(Prog, 0);
        for (int i = 0; i < Constants.fitnesscases; i++) {
            for (int j = 0; j < Constants.varnumber; j++) x[j] = targets[i][j];
            Constants.program = Prog;
            Constants.PC = 0;
            double result = GeneticOperations.run();
            fit += Math.abs(result - targets[i][Constants.varnumber]);
        }
        return -fit;
    }
}
