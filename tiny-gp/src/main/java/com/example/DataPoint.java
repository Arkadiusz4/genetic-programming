package com.example;

public class DataPoint {
    public double x;
    public double y;

    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
