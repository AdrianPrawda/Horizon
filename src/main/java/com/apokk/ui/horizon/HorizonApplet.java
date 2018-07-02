package com.apokk.ui.horizon;

import processing.core.PApplet;

import com.apokk.ui.horizon.eicas.*;

// Primarily used for testing purposes
public class HorizonApplet extends PApplet {

    private int f = 0;
    
    public static void main(String[] args) {
        PApplet.main("com.apokk.ui.horizon.HorizonApplet");
    }

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        background(0);
        textSize(19);
    }

    public void draw() {
        f++;
        if (f > 600) {
            f = 0;
        }
        
        stroke(255);
        background(0);

        FanSpeedIndicator s1 = new FanSpeedIndicator(this, "s", 100, 100, 50, 900, 998, 999);
        FanSpeedIndicator s2 = new FanSpeedIndicator(this, "s", 250, 100, 50, 900, 998, 999);
        FanSpeedIndicator s3 = new FanSpeedIndicator(this, "s", 100, 250, 50, 900, 998, 999);
        FuelFlowIndicator ff1 = new FuelFlowIndicator(this, "ff ind", 255, 255, 40, 10, 12);

        s1.target(1.9f*f);
        s2.target(1.8f*f);
        s3.target(2.2f*f);
        ff1.target(0.03f*f);

        s1.init();
        s2.init();
        s3.init();
        ff1.init();
        
        s1.draw();
        s2.draw();
        s3.draw();
        ff1.draw();
    }
}