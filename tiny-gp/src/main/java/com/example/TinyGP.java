package com.example;

import static com.example.Constants.rd;

public class TinyGP {
    public TinyGP(String fname, long s) {
        Constants.seed = s;
        if (Constants.seed >= 0) Constants.rd.setSeed(Constants.seed);
        FitnessCalculator.setupFitness(fname);
        for (int i = 0; i < Constants.FSET_START; i++)
            FitnessCalculator.x[i] = (Constants.maxrandom - Constants.minrandom) * rd.nextDouble() + Constants.minrandom;
        //new PopulationManager();
    }

    public static void main(String[] args) {
        // String fname = "/Users/amika/Documents/studia/5_semestr/programowanie_genetyczne/tiny-gp/src/main/resources/xmxp2.dat";
        String fname = "/Users/amika/Documents/studia/5_semestr/programowanie_genetyczne/tiny-gp/generated_data.txt";
        long s = -1;

        if (args.length == 2) {
            fname = args[0];
            s = Long.parseLong(args[1]);
        } else if (args.length == 1) {
            fname = args[0];
        }

        TinyGP gp = new TinyGP(fname, s);
        PopulationManager populationManager = new PopulationManager();
        populationManager.evolve();
    }
}
