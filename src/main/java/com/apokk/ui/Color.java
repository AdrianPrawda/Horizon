package com.apokk.ui;

public class Color {
    private int hex;
    private int alpha;

    public Color(int r, int g, int b) {
        hex = b;
        hex += g << 8;
        hex += r << 16;
        alpha = 255;
    }

    public Color(int r, int g, int b, int a) {
        hex = b;
        hex += g << 8;
        hex += r << 16;
        alpha = a;
    }

    public Color(int hex) {
        this.hex = hex;
        alpha = 255;
    }

    public Color(int hex, int a) {
        this.hex = hex;
        alpha = a;
    }

    public int hex() {
        return hex;
    }

    public int alpha() {
        return alpha;
    }
}