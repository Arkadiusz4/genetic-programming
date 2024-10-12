package com.example;

import java.io.IOException;

import static com.example.Constants.rd;

public class TinyGP {
    public TinyGP(String fname, long s) {
        Constants.seed = s;
        if (Constants.seed >= 0) Constants.rd.setSeed(Constants.seed);
        FitnessCalculator.setupFitness(fname);
        for (int i = 0; i < Constants.FSET_START; i++)
            FitnessCalculator.x[i] = (Constants.maxrandom - Constants.minrandom) * rd.nextDouble() + Constants.minrandom;
    }

    public void evolveAndExportExcel(String fname, String excelFilePath) {
        PopulationManager populationManager = new PopulationManager();
        populationManager.evolve();

        char[] bestIndividual = populationManager.getBestIndividual();
        String bestExpression = Helpers.convertIndivToString(bestIndividual);

        try {
            ExcelGenerator.createExcelFromGeneratedData(fname, excelFilePath, bestExpression);
            System.out.println("Excel file created.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        String fname = "tiny-gp/data/generated_data.dat";
        String excelFilePath = "tiny-gp/output/output.xlsx";
        long s = -1;

        if (args.length == 2) {
            fname = args[0];
            s = Long.parseLong(args[1]);
        } else if (args.length == 1) {
            fname = args[0];
        }

        TinyGP gp = new TinyGP(fname, s);
        gp.evolveAndExportExcel(fname, excelFilePath);
    }
}
