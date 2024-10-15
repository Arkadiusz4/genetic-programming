package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExcelGenerator {

    public static void createExcelFromGeneratedData(String txtFilePath, String excelFilePath, String bestExpression,
                                                    List<Double> bestFitnessPerGen, List<Double> avgFitnessPerGen) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(txtFilePath));

        File file = new File(excelFilePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // Tworzy wszystkie brakujące katalogi
        }


        Workbook workbook = new XSSFWorkbook();
        Sheet resultSheet = workbook.createSheet("Porówanie wyników");

        // Tworzenie nagłówków dla arkusza z wynikami funkcji
        String[] headers = lines.get(1).split("\\s+");
        Row headerRow = resultSheet.createRow(0);
        for (int colIndex = 0; colIndex < headers.length - 1; colIndex++) {
            Cell headerCell = headerRow.createCell(colIndex);
            headerCell.setCellValue("X" + (colIndex + 1));
        }

        Cell lastHeaderCell = headerRow.createCell(headers.length - 1);
        lastHeaderCell.setCellValue("Wartość oczekiwana");

        Cell functionHeaderCell = headerRow.createCell(headers.length);
        functionHeaderCell.setCellValue("Wynik funkcji");

        int rowIndex = 1;
        for (int i = 1; i < lines.size(); i++) {
            Row row = resultSheet.createRow(rowIndex++);
            String line = lines.get(i);

            String[] columns = line.split("\\s+");

            for (int colIndex = 0; colIndex < columns.length - 1; colIndex++) {
                Cell cell = row.createCell(colIndex);
                String valueWithComma = columns[colIndex].replace(".", ",");
                try {
                    double numericValue = Double.parseDouble(columns[colIndex]);
                    cell.setCellValue(numericValue);
                } catch (NumberFormatException e) {
                    cell.setCellValue(valueWithComma);
                }
            }

            Cell expectedValueCell = row.createCell(columns.length - 1);
            String expectedValueWithComma = columns[columns.length - 1].replace(".", ",");
            try {
                double numericExpectedValue = Double.parseDouble(columns[columns.length - 1]);
                expectedValueCell.setCellValue(numericExpectedValue);
            } catch (NumberFormatException e) {
                expectedValueCell.setCellValue(expectedValueWithComma);
            }

            String expressionWithCellReferences = replaceVariablesWithCellReferences(bestExpression, rowIndex);
            Cell functionResultCell = row.createCell(columns.length);
            functionResultCell.setCellFormula(expressionWithCellReferences);
        }

        // Tworzenie nowego arkusza dla danych fitness
        Sheet fitnessSheet = workbook.createSheet("Fitness Data");
        Row fitnessHeaderRow = fitnessSheet.createRow(0);
        fitnessHeaderRow.createCell(0).setCellValue("Generacja");
        fitnessHeaderRow.createCell(1).setCellValue("Best Fitness");
        fitnessHeaderRow.createCell(2).setCellValue("Average Fitness");

        // Zapis danych fitness
        for (int gen = 0; gen < bestFitnessPerGen.size(); gen++) {
            Row fitnessRow = fitnessSheet.createRow(gen + 1);  // +1 bo nagłówki są w wierszu 0
            fitnessRow.createCell(0).setCellValue(gen + 1);  // Generacja (zaczyna się od 1)
            fitnessRow.createCell(1).setCellValue(bestFitnessPerGen.get(gen));  // Best Fitness
            fitnessRow.createCell(2).setCellValue(avgFitnessPerGen.get(gen));  // Average Fitness
        }

        // Zapis pliku Excel
        try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    public static String replaceVariablesWithCellReferences(String expression, int rowNumber) {
        for (int i = 1; expression.contains("X" + i); i++) {
            String columnLetter = getColumnLetter(i);
            expression = expression.replace("X" + i, columnLetter + rowNumber);
        }

        return expression;
    }

    private static String getColumnLetter(int columnNumber) {
        return String.valueOf((char) ('A' + columnNumber - 1));
    }
}
