package com.apokk.ui;

public interface UIElement {
    void draw();
    void update();
    void init();
    void target(float value);
    void setFailureMode(int mode);
    String getID();
}