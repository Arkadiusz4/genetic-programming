package com.example.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonGenerator {

    public static void createFunctionDataJson(String txtFilePath, String jsonFilePath, String bestExpression) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(txtFilePath));

        List<Map<String, Object>> functionData = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] columns = line.split("\\s+");
            Map<String, Object> dataRow = new LinkedHashMap<>();

            for (int colIndex = 0; colIndex < columns.length - 1; colIndex++) {
                dataRow.put("X" + (colIndex + 1), Double.parseDouble(columns[colIndex]));
            }

            dataRow.put("Wartość oczekiwana", Double.parseDouble(columns[columns.length - 1]));

            String expressionWithCellReferences = replaceVariablesWithCellReferences(bestExpression, i);
            dataRow.put("Wynik funkcji", expressionWithCellReferences);

            functionData.add(dataRow);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            gson.toJson(functionData, file);
        }
    }

    public static String replaceVariablesWithCellReferences(String expression, int rowNumber) {
        for (int i = 1; expression.contains("X" + i); i++) {
            expression = expression.replace("Dane" + rowNumber + "X" + i, "X" + i);
        }
        return expression;
    }

}

