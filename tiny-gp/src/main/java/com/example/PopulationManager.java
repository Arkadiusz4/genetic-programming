package com.example;

import java.util.ArrayList;
import java.util.List;

public class PopulationManager {
    double[] fitness = new double[Constants.POPSIZE];
    char[][] pop;
    List<Double> bestFitnessPerGen = new ArrayList<>();
    List<Double> avgFitnessPerGen = new ArrayList<>();

    public PopulationManager() {
        pop = createRandomPop(Constants.POPSIZE, Constants.DEPTH, fitness);
    }

    char[] createRandomIndiv(int depth) {
        char[] ind;
        int len;

        len = GeneticOperations.grow(Constants.buffer, 0, Constants.MAX_LEN, depth);

        while (len < 0)
            len = GeneticOperations.grow(Constants.buffer, 0, Constants.MAX_LEN, depth);

        ind = new char[len];

        System.arraycopy(Constants.buffer, 0, ind, 0, len);
        return (ind);
    }

    private char[][] createRandomPop(int n, int depth, double[] fitness) {
        char[][] pop = new char[n][];
        int i;

        for (i = 0; i < n; i++) {
            pop[i] = createRandomIndiv(depth);
            fitness[i] = FitnessCalculator.fitnessFunction(pop[i]);
        }
        return (pop);
    }

//    public boolean evolve() {
//        Helpers.printParameters();
//        stats(fitness, pop, 0);
//        for (int gen = 1; gen < Constants.GENERATIONS; gen++) {
//            if (Constants.fbestpop > -1e-1) {
//                System.out.print("PROBLEM SOLVED\n");
////                return zeby nie konczylo dzialania calego programu
////                System.exit(0);
//                return true;
//            }
//            for (int indivs = 0; indivs < Constants.POPSIZE; indivs++) {
//                char[] newind;
//                if (Constants.rd.nextDouble() < Constants.CROSSOVER_PROB) {
//                    int parent1 = tournament(fitness, Constants.TSIZE);
//                    int parent2 = tournament(fitness, Constants.TSIZE);
//                    newind = crossover(pop[parent1], pop[parent2]);
//                } else {
//                    int parent = tournament(fitness, Constants.TSIZE);
//                    newind = mutation(pop[parent], Constants.PMUT_PER_NODE);
//                }
//                double newfit = FitnessCalculator.fitnessFunction(newind);
//                int offspring = negativeTournament(fitness, Constants.TSIZE);
//                pop[offspring] = newind;
//                fitness[offspring] = newfit;
//            }
//            stats(fitness, pop, gen);
//        }
//        System.out.print("PROBLEM *NOT* SOLVED\n");
////        System.exit(1);
//        return false;
//    }


    public boolean evolve() {
        Helpers.printParameters();
        stats(fitness, pop, 0);
        for (int gen = 1; gen < Constants.GENERATIONS; gen++) {
            stats(fitness, pop, gen);

            if (Constants.fbestpop > -0.5) {
                System.out.print("PROBLEM SOLVED\n");
//                return zeby nie konczylo dzialania calego programu
//                System.exit(0);
                return true;
            }

            // Logika ewolucji
            for (int indivs = 0; indivs < Constants.POPSIZE; indivs++) {
                char[] newind;
                if (Constants.rd.nextDouble() < Constants.CROSSOVER_PROB) {
                    int parent1 = tournament(fitness, Constants.TSIZE);
                    int parent2 = tournament(fitness, Constants.TSIZE);
                    newind = crossover(pop[parent1], pop[parent2]);
                } else {
                    int parent = tournament(fitness, Constants.TSIZE);
                    newind = mutation(pop[parent], Constants.PMUT_PER_NODE);
                }
                double newfit = FitnessCalculator.fitnessFunction(newind);
                int offspring = negativeTournament(fitness, Constants.TSIZE);
                pop[offspring] = newind;
                fitness[offspring] = newfit;
            }
        }
        System.out.print("PROBLEM *NOT* SOLVED\n");
//        System.exit(1);
        return false;
    }

    public void stats(double[] fitness, char[][] pop, int gen) {
        int i, best = Constants.rd.nextInt(Constants.POPSIZE);
        int node_count = 0;
        Constants.fbestpop = fitness[best];
        Constants.favgpop = 0.0;

        for (i = 0; i < Constants.POPSIZE; i++) {
            node_count += GeneticOperations.traverse(pop[i], 0);
            Constants.favgpop += fitness[i];
            if (fitness[i] > Constants.fbestpop) {
                best = i;
                Constants.fbestpop = fitness[i];
            }
        }
        Constants.avg_len = (double) node_count / Constants.POPSIZE;
        Constants.favgpop /= Constants.POPSIZE;

        bestFitnessPerGen.add(Constants.fbestpop);
        avgFitnessPerGen.add(Constants.favgpop);

        System.out.print("Generation=" + gen + " Avg Fitness=" + (-Constants.favgpop) +
                " Best Fitness=" + (-Constants.fbestpop) + " Avg Size=" + Constants.avg_len +
                "\nBest Individual: ");
        Helpers.printIndiv(pop[best], 0);
        System.out.print("\n");
        System.out.flush();
    }

    public int tournament(double[] fitness, int tsize) {
        int best = Constants.rd.nextInt(Constants.POPSIZE), i, competitor;
        double fbest = -1.0e34;

        for (i = 0; i < tsize; i++) {
            competitor = Constants.rd.nextInt(Constants.POPSIZE);
            if (fitness[competitor] > fbest) {
                fbest = fitness[competitor];
                best = competitor;
            }
        }
        return (best);
    }

    public int negativeTournament(double[] fitness, int tsize) {
        int worst = Constants.rd.nextInt(Constants.POPSIZE), i, competitor;
        double fworst = 1e34;

        for (i = 0; i < tsize; i++) {
            competitor = Constants.rd.nextInt(Constants.POPSIZE);
            if (fitness[competitor] < fworst) {
                fworst = fitness[competitor];
                worst = competitor;
            }
        }
        return (worst);
    }

    public char[] crossover(char[] parent1, char[] parent2) {
        int xo1start, xo1end, xo2start, xo2end;
        char[] offspring;
        int len1 = GeneticOperations.traverse(parent1, 0);
        int len2 = GeneticOperations.traverse(parent2, 0);
        int lenoff;

        xo1start = Constants.rd.nextInt(len1);
        xo1end = GeneticOperations.traverse(parent1, xo1start);

        xo2start = Constants.rd.nextInt(len2);
        xo2end = GeneticOperations.traverse(parent2, xo2start);

        lenoff = xo1start + (xo2end - xo2start) + (len1 - xo1end);

        offspring = new char[lenoff];

        System.arraycopy(parent1, 0, offspring, 0, xo1start);
        System.arraycopy(parent2, xo2start, offspring, xo1start,
                (xo2end - xo2start));
        System.arraycopy(parent1, xo1end, offspring,
                xo1start + (xo2end - xo2start),
                (len1 - xo1end));

        return (offspring);
    }

    char[] mutation(char[] parent, double pmut) {
        int len = GeneticOperations.traverse(parent, 0), i;
        int mutsite;
        char[] parentcopy = new char[len];

        System.arraycopy(parent, 0, parentcopy, 0, len);
        for (i = 0; i < len; i++) {
            if (Constants.rd.nextDouble() < pmut) {
                mutsite = i;
                if (parentcopy[mutsite] < Constants.FSET_START)
                    parentcopy[mutsite] = (char) Constants.rd.nextInt(Constants.varnumber + Constants.randomnumber);
                else
                    switch (parentcopy[mutsite]) {
                        case Constants.ADD:
                        case Constants.SUB:
                        case Constants.MUL:
                        case Constants.DIV:
                            parentcopy[mutsite] =
                                    (char) (Constants.rd.nextInt(Constants.FSET_END - Constants.FSET_START + 1)
                                            + Constants.FSET_START);
                    }
            }
        }
        return (parentcopy);
    }


    public char[] getBestIndividual() {
        int bestIndex = 0;
        for (int i = 1; i < Constants.POPSIZE; i++) {
            if (fitness[i] > fitness[bestIndex]) {
                bestIndex = i;
            }
        }
        return pop[bestIndex];
    }

}