package com.example;

import java.io.File;
import java.nio.file.Paths;

public class BatchRunner {

    public static void main(String[] args) {
        String folderPath = "tiny-gp/data";
        String outputFolder = "tiny-gp/output";
        long s = -1;

        processFolder(folderPath, outputFolder, s);
    }

    public static void processFolder(String folderPath, String outputFolder, long s) {
        File folder = new File(folderPath);

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".dat"));

        for (File file : files) {
            String dataFilePath = file.getAbsolutePath();
            String outputFilePath = generateOutputPath(file, outputFolder);

            runTinyGP(dataFilePath, outputFilePath, s);
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
