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

    public static String convertIndivToString(char[] buffer) {
        StringBuilder sb = new StringBuilder();
        convertIndivToStringHelper(buffer, 0, sb);
        return sb.toString();
    }

    private static int convertIndivToStringHelper(char[] buffer, int buffercounter, StringBuilder sb) {
        int a1 = 0, a2;
        if (buffer[buffercounter] < Constants.FSET_START) {
            if (buffer[buffercounter] < Constants.varnumber)
                sb.append("X").append(buffer[buffercounter] + 1).append(" ");
            else
                sb.append(FitnessCalculator.x[buffer[buffercounter]]);
            return (++buffercounter);
        }
        switch (buffer[buffercounter]) {
            case Constants.ADD:
                sb.append("(");
                a1 = convertIndivToStringHelper(buffer, ++buffercounter, sb);
                sb.append(" + ");
                break;
            case Constants.SUB:
                sb.append("(");
                a1 = convertIndivToStringHelper(buffer, ++buffercounter, sb);
                sb.append(" - ");
                break;
            case Constants.MUL:
                sb.append("(");
                a1 = convertIndivToStringHelper(buffer, ++buffercounter, sb);
                sb.append(" * ");
                break;
            case Constants.DIV:
                sb.append("(");
                a1 = convertIndivToStringHelper(buffer, ++buffercounter, sb);
                sb.append(" / ");
                break;
        }
        a2 = convertIndivToStringHelper(buffer, a1, sb);
        sb.append(")");
        return a2;
    }


}
