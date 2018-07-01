package com.apokk.ui;

public class Color {
    private int hex;
    private int alpha;

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(int r, int g, int b, int a) {
        hex = b;
        hex += g << 8;
        hex += r << 16;
        hex += a << 24;
        alpha = a;
    }

    public Color(Color c) {
        this(c.hex());
    }

    public Color(Color c, int alpha) {
        this(c.hex(), alpha);
    }

    public Color(int hex) {
        this.hex = hex + 0xff000000;
        alpha = 255;
    }

    public Color(int hex, int a) {
        this.hex = hex + (a << 24);
        alpha = a;
    }

    public void setAlpha(int alpha) {
        hex = hex << 8;
        hex = hex >> 8;
        hex += alpha << 24;
    }

    public int hex() {
        return hex;
    }

    public int alpha() {
        return alpha;
    }
}