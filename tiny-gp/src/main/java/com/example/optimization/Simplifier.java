package com.example.optimization;

public class Simplifier {

    private String orgProg;
    private String newProg;
    private int subFormLocIndex;
    private double lfVal;
    private String ctFunction;
    private double rtVal;
    private double result;

    private int initialSubFormulaCount;


    public Simplifier() {
    }

    public String simplify(String program) {

        orgProg = program;

        newProg = orgProg.substring(1, orgProg.length() - 1);
        this.initialSubFormulaCount = countSimplifiableSubFormulasInNewProgram();

        while (countSimplifiableSubFormulasInNewProgram() > 0) {
            locateAvailableSubFormula();
            pullAllSubformulaTokens();
            calcResultOfSubformula();
            replaceSubFormulaWithItsResult();
        }

        newProg = "(" + newProg + ")";

        return newProg;
    }

    public String toString() {

        String newline = System.lineSeparator();

        int programSizeReduction = orgProg.length() - newProg.length();

        int percentReduced = (int) (((float) programSizeReduction / (float) orgProg.length()) * 100f);
        String str = "";

        str += "org. program size:" + this.orgProg.length() + newline;
        str += "new program size :" + this.newProg.length() + newline;
        str += "size reduction   :" + programSizeReduction + " (" + percentReduced + ")%" + newline;
        str += "initial subformula count and simplifications: " + initialSubFormulaCount + newline;
        str += "simplified program: " + newline;
        str += this.newProg + newline;
        str += "original program: " + newline;
        str += this.orgProg;

        return str;
    }

    public int countSimplifiableSubFormulasInNewProgram() {

        int iFirstParen;
        int iSecondParen;
        int count = 0;

        for (int i = 0; i < this.newProg.length(); i++) {
            iFirstParen = locNextParen(newProg, i);
            if (iFirstParen == -1) break;
            iSecondParen = locNextParen(newProg, iFirstParen + 1);
            if (iSecondParen == -1) break;
            if (isSimplifiableSubFormula(iFirstParen, iSecondParen)) {
                count++;
                i = iSecondParen;
            } else {
                i = iSecondParen - 1;
            }
        }

        return count;
    }

    public void locateAvailableSubFormula() {

        int iFirstParen;
        int iSecondParen;

        for (int i = 0; i < this.newProg.length(); i++) {
            iFirstParen = locNextParen(newProg, i);
            if (iFirstParen == -1) break;
            iSecondParen = locNextParen(newProg, iFirstParen + 1);
            if (iSecondParen == -1) break;
            if (isSimplifiableSubFormula(iFirstParen, iSecondParen)) {
                subFormLocIndex = iFirstParen;
                return;
            }
            i = iSecondParen - 1;
        }

        subFormLocIndex = -1;
    }

    public boolean isSimplifiableSubFormula(int iFirstParen, int iSecondParen) {

        boolean isOpenParFound = (newProg.charAt(iFirstParen) == '(');
        boolean isCloseParFound = (newProg.charAt(iSecondParen) == ')');
        boolean isFreeOfVars = true;

        for (int i = iFirstParen; i < iSecondParen; i++) {
            if (newProg.charAt(i) == 'X') {
                isFreeOfVars = false;
                break;
            }
        }

        return (isOpenParFound && isCloseParFound && isFreeOfVars);
    }

    public int locNextParen(String str, int i) {

        String ss;
        for (int j = i; j < str.length(); j++) {
            ss = str.substring(j, j + 1);
            if (ss.equals(")")) return j;
            if (ss.equals("(")) return j;
        }

        return -1;
    }

    public void pullAllSubformulaTokens() {

        String str = newProg.substring(subFormLocIndex, locNextParen(newProg, subFormLocIndex + 1) + 1);
        String[] tokens = str.split(" +");

        tokens[0] = tokens[0].substring(1);
        tokens[2] = tokens[2].substring(0, tokens[2].length() - 1);

        lfVal = Double.parseDouble(tokens[0]);
        ctFunction = tokens[1];
        rtVal = Double.parseDouble(tokens[2]);
    }

    public void calcResultOfSubformula() {

        switch (ctFunction) {
            case "+":
                result = lfVal + rtVal;
                break;
            case "-":
                result = lfVal - rtVal;
                break;
            case "*":
                result = lfVal * rtVal;
                break;
            case "/":
                result = lfVal / rtVal;
                break;
            default:
                throw new IllegalArgumentException("Invalid function character found:" + ctFunction);
        }
    }

    public void replaceSubFormulaWithItsResult() {

        String str = newProg.substring(subFormLocIndex, locNextParen(newProg, subFormLocIndex + 1) + 1);
        newProg = newProg.replace(str, String.valueOf(result));
    }
}
