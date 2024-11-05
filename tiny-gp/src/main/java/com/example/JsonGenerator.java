package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonGenerator {

    // Zapisuje dane funkcji do JSON
    public static void createFunctionDataJson(String txtFilePath, String jsonFilePath, String bestExpression) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(txtFilePath));

        // Lista, która przechowa wiersze danych funkcji
        List<Map<String, Object>> functionData = new ArrayList<>();

        // Przetwarzanie danych funkcji
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] columns = line.split("\\s+");
            Map<String, Object> dataRow = new LinkedHashMap<>();

            // Dodanie wartości X1, X2, ..., wartości oczekiwanej, wyniku funkcji
            for (int colIndex = 0; colIndex < columns.length - 1; colIndex++) {
                dataRow.put("X" + (colIndex + 1), Double.parseDouble(columns[colIndex]));
            }

            // Wartość oczekiwana
            dataRow.put("Wartość oczekiwana", Double.parseDouble(columns[columns.length - 1]));

            // Wynik funkcji (zamiana zmiennych na odwołania do danych)
            String expressionWithCellReferences = replaceVariablesWithCellReferences(bestExpression, i);
            dataRow.put("Wynik funkcji", expressionWithCellReferences);

            // Dodanie wiersza do listy
            functionData.add(dataRow);
        }

        // Konwersja danych do JSON i zapis do pliku
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            gson.toJson(functionData, file);
        }
    }

    // Zamienia zmienne X na odwołania do danych
    public static String replaceVariablesWithCellReferences(String expression, int rowNumber) {
        for (int i = 1; expression.contains("X" + i); i++) {
            expression = expression.replace("Dane" + rowNumber + "X" + i, "X" + i);
        }
        return expression;
    }

}

