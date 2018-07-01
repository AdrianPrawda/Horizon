package com.apokk.ui;

import processing.core.PShape;
import com.apokk.ui.Color;

final public class ShapeUtil {

    public static void fillAll(int color, PShape... shapes) {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i].setFill(color);
        }
    }

    public static void fillAll(Color color, PShape... shapes) {
        ShapeUtil.fillAll(color.hex(), shapes);
    }

    public static void setStrokes(int color, PShape... shapes) {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i].setStroke(color);
        }
    }

    public static void setStrokes(Color color, PShape... shapes) {
        ShapeUtil.setStrokes(color, shapes);
    }

    public static void strokeWeights(float weight, PShape... shapes) {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i].setStrokeWeight(weight);
        }
    }
}