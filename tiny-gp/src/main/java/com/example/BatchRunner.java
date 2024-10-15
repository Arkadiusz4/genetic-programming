package com.example;

import java.io.File;
import java.nio.file.Paths;

public class BatchRunner {

    public static void main(String[] args) {
        String folderPath = "../assets/data";
        String outputFolder = "/output/new";
        long s = -1;

        String[] substrings = {"f2"};

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
                String outputFilePath = generateOutputPath(file, outputFolder);

                runTinyGP(dataFilePath, outputFilePath, s);
            }
        }
    }

    public static String generateOutputPath(File dataFile, String outputFolder) {
        String fileNameWithoutExtension = dataFile.getName().replace(".dat", "");
        return Paths.get(outputFolder, fileNameWithoutExtension + ".xlsx").toString();
    }

    public static void runTinyGP(String dataFilePath, String outputFilePath, long s) {
        System.out.println("Uruchamianie TinyGP dla pliku: " + dataFilePath);
        System.out.println("Zapis wyników do: " + outputFilePath);

        TinyGP gp = new TinyGP(dataFilePath, s);
        gp.evolveAndExportExcel(dataFilePath, outputFilePath);
    }
}
