package com.example.io;

import com.example.optimization.Simplifier;
import com.example.core.TinyGP;
import com.example.fitness.Helpers;
import com.example.fitness.PopulationManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BatchRunner {

    public static void main(String[] args) {
        String folderPath = "assets/data";
        String outputFolder = "tiny-gp/regressions/f6";
        long s = -1;

        String[] substrings = {"f6"};

        processFolder(folderPath, outputFolder, s, substrings);
    }

    public static void processFolder(String folderPath, String outputFolder, long s, String[] substrings) {
        File folder = new File(folderPath);

        System.out.println("Pełna ścieżka folderu: " + folder.getAbsolutePath());

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder nie istnieje lub nie jest katalogiem.");
            return;
        }

        File[] files = folder.listFiles((dir, name) -> {
            if (!name.endsWith(".dat")) {
                return false;
            }
            for (String substring : substrings) {
                if (name.contains(substring)) {
                    return true;
                }
            }
            return false;
        });

        if (files != null) {
            for (File file : files) {
                String dataFilePath = file.getAbsolutePath();
                runTinyGP(dataFilePath, outputFolder, s);
            }
        }
    }

    public static void runTinyGP(String dataFilePath, String outputFolder, long s) {
        System.out.println("Uruchamianie TinyGP dla pliku: " + dataFilePath);

        TinyGP gp = new TinyGP(dataFilePath, s);
        PopulationManager populationManager = gp.evolve();

        char[] bestIndividual = populationManager.getBestIndividual();
        String bestExpression = Helpers.convertIndivToString(bestIndividual);

        Simplifier simplifier = new Simplifier();
        String simplifiedExpression = simplifier.simplify(bestExpression);

        System.out.println("Oryginalne wyrażenie: " + bestExpression);
        System.out.println("Uproszczone wyrażenie: " + simplifiedExpression);

        String baseFileName = new File(dataFilePath).getName().replace(".dat", "");
        String functionDataJsonPath = Paths.get(outputFolder, baseFileName + "-values.json").toString();
        String fitnessDataJsonPath = Paths.get(outputFolder, baseFileName + "-fitness.json").toString();

        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try {
            JsonGenerator.createFunctionDataJson(dataFilePath, functionDataJsonPath, simplifiedExpression);
            FitnessJsonGenerator.createFitnessDataJson(fitnessDataJsonPath, populationManager.bestFitnessPerGen, populationManager.avgFitnessPerGen);
            System.out.println("Dane zapisano do JSON w folderze: " + outputFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
