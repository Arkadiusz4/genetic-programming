package com.example;

public class Helpers {
    public static void printParameters() {
        System.out.print("-- TINY GP (Java version) --\n");
        System.out.print("SEED=" + Constants.seed + "\nMAX_LEN=" + Constants.MAX_LEN +
                "\nPOPSIZE=" + Constants.POPSIZE + "\nDEPTH=" + Constants.DEPTH +
                "\nCROSSOVER_PROB=" + Constants.CROSSOVER_PROB +
                "\nPMUT_PER_NODE=" + Constants.PMUT_PER_NODE +
                "\nMIN_RANDOM=" + Constants.minrandom +
                "\nMAX_RANDOM=" + Constants.maxrandom +
                "\nGENERATIONS=" + Constants.GENERATIONS +
                "\nTSIZE=" + Constants.TSIZE +
                "\n----------------------------------\n");
    }

    public static int printIndiv(char[] buffer, int buffercounter) {
        int a1 = 0, a2;
        if (buffer[buffercounter] < Constants.FSET_START) {
            if (buffer[buffercounter] < Constants.varnumber)
                System.out.print("X" + (buffer[buffercounter] + 1) + " ");
            else
                System.out.print(FitnessCalculator.x[buffer[buffercounter]]);
            return (++buffercounter);
        }
        switch (buffer[buffercounter]) {
            case Constants.ADD:
                System.out.print("(");
                a1 = printIndiv(buffer, ++buffercounter);
                System.out.print(" + ");
                break;
            case Constants.SUB:
                System.out.print("(");
                a1 = printIndiv(buffer, ++buffercounter);
                System.out.print(" - ");
                break;
            case Constants.MUL:
                System.out.print("(");
                a1 = printIndiv(buffer, ++buffercounter);
                System.out.print(" * ");
                break;
            case Constants.DIV:
                System.out.print("(");
                a1 = printIndiv(buffer, ++buffercounter);
                System.out.print(" / ");
                break;
        }
        a2 = printIndiv(buffer, a1);
        System.out.print(")");
        return (a2);
    }
}
