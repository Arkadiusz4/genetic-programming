package com.example;

import java.util.Random;

public class Constants {
    public static final int ADD = 110;
    public static final int SUB = 111;
    public static final int MUL = 112;
    public static final int DIV = 113;
    public static final int SIN = 114;
    public static final int COS = 115;
    public static final int FSET_START = ADD;
    public static final int FSET_END = COS;
    public static final int MAX_LEN = 20;
    public static final int POPSIZE = 100000;
    public static final int DEPTH = 2;
    public static final int GENERATIONS = 100;
    public static final int TSIZE = 2;
    public static final double PMUT_PER_NODE = 0.05;
    public static final double CROSSOVER_PROB = 0.9;

    static double minrandom, maxrandom;
    static int varnumber, fitnesscases, randomnumber;
    static long seed;
    static Random rd = new Random();
    static char[] program;
    static int PC;
    static double fbestpop = 0.0, favgpop = 0.0;
    static double avg_len;
    static char[] buffer = new char[MAX_LEN];

}
