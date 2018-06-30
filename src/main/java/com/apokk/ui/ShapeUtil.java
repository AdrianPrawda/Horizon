package com.apokk.ui;

import processing.core.PShape;

final public class ShapeUtil {

    public void fill(int color, PShape... shapes) {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i].setFill(color);
        }
    }
}