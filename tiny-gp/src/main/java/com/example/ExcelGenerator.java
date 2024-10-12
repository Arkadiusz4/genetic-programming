package com.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ExcelGenerator {

    public static void createExcelFromGeneratedData(String txtFilePath, String excelFilePath, String bestExpression) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(txtFilePath));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Porówanie wyników");


        String[] headers = lines.get(1).split("\\s+");

        Row headerRow = sheet.createRow(0);
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
            Row row = sheet.createRow(rowIndex++);
            String line = lines.get(i);

            String[] columns = line.split("\\s+");

            for (int colIndex = 0; colIndex < columns.length - 1; colIndex++) {
                Cell cell = row.createCell(colIndex);
//              kropki na przecinki bo excel sie buntuje czasem
                String valueWithComma = columns[colIndex].replace(".", ",");
//              zamiana stringa na double bo excel tez sie buntuje tutaj
                try {
                    double numericValue = Double.parseDouble(columns[colIndex]);
                    cell.setCellValue(numericValue);
                } catch (NumberFormatException e) {
                    cell.setCellValue(valueWithComma);
                }
            }

            // tu tez kropki na przecinki i na double ale wartosci oczekiwane czyli ostatnia kolumna z pliku danych
            Cell expectedValueCell = row.createCell(columns.length - 1);
            String expectedValueWithComma = columns[columns.length - 1].replace(".", ",");
            try {
                double numericExpectedValue = Double.parseDouble(columns[columns.length - 1]);
                expectedValueCell.setCellValue(numericExpectedValue);
            } catch (NumberFormatException e) {
                expectedValueCell.setCellValue(expectedValueWithComma);
            }

            // dodajemy ostatnia kolumne z wynikami funkcji
            String expressionWithCellReferences = replaceVariablesWithCellReferences(bestExpression, rowIndex);

            Cell functionResultCell = row.createCell(columns.length);
            functionResultCell.setCellFormula(expressionWithCellReferences);
        }

        try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    public static String replaceVariablesWithCellReferences(String expression, int rowNumber) {
        for (int i = 1; expression.contains("X" + i); i++) {
//          zamienia Xn na A1 B2 C2 czy co tam jest
            String columnLetter = getColumnLetter(i);
            expression = expression.replace("X" + i, columnLetter + rowNumber);
        }

        return expression;
    }

//    zamienia numer kolumny na litere
    private static String getColumnLetter(int columnNumber) {
        return String.valueOf((char) ('A' + columnNumber - 1));
    }
}

