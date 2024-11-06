package com.example.core;

import com.example.fitness.FitnessCalculator;
import com.example.fitness.PopulationManager;

import static com.example.core.Constants.rd;

public class TinyGP {

    public TinyGP(String fname, long s) {
        Constants.seed = s;
        if (Constants.seed >= 0) Constants.rd.setSeed(Constants.seed);
        FitnessCalculator.setupFitness(fname);
        for (int i = 0; i < Constants.FSET_START; i++)
            FitnessCalculator.x[i] = (Constants.maxrandom - Constants.minrandom) * rd.nextDouble() + Constants.minrandom;
    }

    public PopulationManager evolve() {
        PopulationManager populationManager = new PopulationManager();
        populationManager.evolve();
        return populationManager;
    }
}
