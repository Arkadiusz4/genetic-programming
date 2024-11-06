package com.example.fitness;

import com.example.core.Constants;

public class GeneticOperations {
    public static double run() {
        char primitive = Constants.program[Constants.PC++];
        if (primitive < Constants.FSET_START) return FitnessCalculator.x[primitive];
        switch (primitive) {
            case Constants.ADD:
                return run() + run();
            case Constants.SUB:
                return run() - run();
            case Constants.MUL:
                return run() * run();
            case Constants.DIV: {
                double num = run(), den = run();
                if (Math.abs(den) <= 0.001) return num;
                else return num / den;
            }
            case Constants.SIN:
                return Math.sin(run());
            case Constants.COS:
                return Math.cos(run());
        }
        return 0.0;
    }

    public static int traverse(char[] buffer, int buffercount) {
        if (buffer[buffercount] < Constants.FSET_START) return ++buffercount;
        return switch (buffer[buffercount]) {
            case Constants.ADD, Constants.SUB, Constants.MUL, Constants.DIV ->
                    traverse(buffer, traverse(buffer, ++buffercount));
            case Constants.SIN, Constants.COS -> traverse(buffer, ++buffercount);
            default -> 0;
        };
    }

    public static int grow(char[] buffer, int pos, int max, int depth) {
        char prim = (char) Constants.rd.nextInt(2);
        int one_child;

        if (pos >= max) return (-1);

        if (pos == 0) prim = 1;

        if (prim == 0 || depth == 0) {
            prim = (char) Constants.rd.nextInt(Constants.varnumber + Constants.randomnumber);
            buffer[pos] = prim;
            return (pos + 1);
        } else {
            prim = (char) (Constants.rd.nextInt(Constants.FSET_END - Constants.FSET_START + 1) + Constants.FSET_START);
            switch (prim) {
                case Constants.ADD:
                case Constants.SUB:
                case Constants.MUL:
                case Constants.DIV:
                    buffer[pos] = prim;
                    one_child = grow(buffer, pos + 1, max, depth - 1);
                    if (one_child < 0) return (-1);
                    return (grow(buffer, one_child, max, depth - 1));
                case Constants.SIN:
                case Constants.COS:
                    buffer[pos] = prim;
                    return grow(buffer, pos + 1, max, depth - 1);
            }
        }
        return (0);
    }
}
