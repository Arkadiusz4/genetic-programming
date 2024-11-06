package com.example.core;

import java.util.Random;

public class Constants {
    public static final int ADD = 110;
    public static final int SUB = 111;
    public static final int MUL = 112;
    public static final int DIV = 113;
    public static final int SIN = 114;
    public static final int COS = 115;
    public static final int FSET_START = ADD;
    public static final int MULTI_ARG_OPERATIONS_END = DIV;
    public static final int SINGLE_ARG_OPERATIONS_START = SIN;
    public static final int FSET_END = COS;
    public static final int MAX_LEN = 20;
    public static final int POPSIZE = 100000;
    public static final int DEPTH = 2;
    public static final int GENERATIONS = 100;
    public static final int TSIZE = 2;
    public static final double PMUT_PER_NODE = 0.05;
    public static final double CROSSOVER_PROB = 0.9;

    public static double minrandom;
    public static double maxrandom;
    public static int varnumber;
    public static int fitnesscases;
    public static int randomnumber;
    public static long seed;
    public static Random rd = new Random();
    public static char[] program;
    public static int PC;
    public static double fbestpop = 0.0;
    public static double favgpop = 0.0;
    public static double avg_len;
    public static char[] buffer = new char[MAX_LEN];
}
