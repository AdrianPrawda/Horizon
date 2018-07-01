package com.apokk.ui.horizon;

import processing.core.PApplet;
import processing.core.PShape;

import com.apokk.ui.horizon.eicas.*;

// Primarily used for testing purposes
public class HorizonApplet extends PApplet {
    
    public static void main(String[] args) {
        PApplet.main("com.apokk.ui.horizon.HorizonApplet");
    }

    public void settings() {
        size(200, 200);
    }

    public void setup() {
        background(0);
    }

    public void draw() {
        stroke(255);
        background(0);

        CircularDisplay s = new CircularDisplay(this, "s", 100, 100, 50, 780, 800, 999);
        s.target(650);
        s.init();
        s.draw();
    }
}