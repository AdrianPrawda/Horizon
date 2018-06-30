package com.apokk.ui.math;

class BezierVertex {
    public double[] ctrlPoints;
    public double[] anchor;

    public BezierVertex(double ax, double ay, double cx1, double cy1, double cx2, double cy2) {
        ctrlPoints[0] = cx1;
        ctrlPoints[1] = cy1;
        ctrlPoints[2] = cx2;
        ctrlPoints[3] = cy2;

        anchor[0] = ax;
        anchor[1] = ay;
    }

    public BezierVertex(double[] anchor, double[] ctrl) {
        this(anchor[0], anchor[1], ctrl[0], ctrl[1], ctrl[2], ctrl[3]);
    }
}