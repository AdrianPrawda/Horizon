package com.apokk.ui.math;

class BezierVertex {
    public float[] ctrlPoints;
    public float[] anchor;

    public BezierVertex(float ax, float ay, float cx1, float cy1, float cx2, float cy2) {
        ctrlPoints[0] = cx1;
        ctrlPoints[1] = cy1;
        ctrlPoints[2] = cx2;
        ctrlPoints[3] = cy2;

        anchor[0] = ax;
        anchor[1] = ay;
    }

    public BezierVertex(float[] anchor, float[] ctrl) {
        this(anchor[0], anchor[1], ctrl[0], ctrl[1], ctrl[2], ctrl[3]);
    }
}