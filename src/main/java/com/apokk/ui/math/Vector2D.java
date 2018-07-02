package com.apokk.ui.math;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector) {
        this(vector.x, vector.y);
    }

    public Vector2D() {
        this(0, 0);
    }

    public void add(Vector2D vector) {
        x = x + vector.x;
        y = y + vector.y;
    }

    public void sub(Vector2D vector) {
        x = x - vector.x;
        y = y - vector.y;
    }

    public float cross(Vector2D vector) {
        return x * vector.x + y * vector.y;
    }

    public float div(Vector2D vector) {
        return x / vector.x + y / vector.y;
    }

    public void mult(float s) {
        x = s * x;
        y = s * y;
    }

    public void rotate(double rad) {
        float nx = (float) (x * Math.cos(rad) - y * Math.sin(rad));
        float ny = (float) (x * Math.sin(rad) + y * Math.cos(rad));
        x = nx;
        y = ny;
    }

    // length of vector
    public float mag() {
        return (float) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public void norm() {
        float l = (float) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
        if (l != 0) {
            x = x / l;
            y = y / l;
        }
    }
}