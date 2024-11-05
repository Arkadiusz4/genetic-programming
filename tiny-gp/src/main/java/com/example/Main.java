package com.example;

public class Main {
    public static void main(String[] args) {
        String expression = "((--1.6608426616076275) + -3.474261958877094)";
        String simplified = Optimizer.simplifyExpression(expression);
        System.out.println("Original: " + expression);
        System.out.println("Simplified: " + simplified);  // Oczekiwany wynik: "-1.8134192972694665"
    }
}
