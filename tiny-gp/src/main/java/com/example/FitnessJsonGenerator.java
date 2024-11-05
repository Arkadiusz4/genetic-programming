package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FitnessJsonGenerator {

    // Zapisuje dane Best Fitness i Average Fitness do JSON
    public static void createFitnessDataJson(String jsonFilePath, List<Double> bestFitnessPerGen, List<Double> avgFitnessPerGen) throws IOException {
        // Lista przechowująca dane fitness dla każdego pokolenia
        List<Map<String, Object>> fitnessData = new ArrayList<>();

        // Przetwarzanie danych fitness
        for (int gen = 0; gen < bestFitnessPerGen.size(); gen++) {
            Map<String, Object> fitnessRow = new LinkedHashMap<>();
            fitnessRow.put("Generacja", gen + 1);
            fitnessRow.put("Best Fitness", bestFitnessPerGen.get(gen));
            fitnessRow.put("Average Fitness", avgFitnessPerGen.get(gen));

            // Dodanie wiersza do listy
            fitnessData.add(fitnessRow);
        }

        // Konwersja danych do JSON i zapis do pliku
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            gson.toJson(fitnessData, file);
        }
    }
}

