package com.apokk.ui.math;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public void add(Vector2D vector) {
        x = x + vector.x;
        y = y + vector.y;
    }

    public void sub(Vector2D vector) {
        x = x - vector.x;
        y = y - vector.y;
    }

    public double cross(Vector2D vector) {
        return x * vector.x + y * vector.y;
    }

    public double div(Vector2D vector) {
        return x / vector.x + y / vector.y;
    }

    public void mult(double s) {
        x = s * x;
        y = s * y;
    }

    public void rotate(double rad) {
        double nx = x * Math.cos(rad) - y * Math.sin(rad);
        double ny = x * Math.sin(rad) + y * Math.cos(rad);
        x = nx;
        y = ny;
    }

    // length of vector
    public double mag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void norm() {
        double l = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if (l != 0) {
            x = x / l;
            y = y / l;
        }
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }
}