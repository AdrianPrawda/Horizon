package com.apokk.ui.math;

final class Calc {

    // Degrees to radiants
    public static double dtr(double deg) {
        return deg * Math.PI / 180.0;
    }

    // Radients to degrees
    public static double rtd(double rad) {
        return rad * 180.0 / Math.PI;
    }

    public static double[] pointOnCircle(double[] centerPoint, double radius, double radiants) {
        double[] point = new double[2];
        point[0] = centerPoint[0] + radius * Math.cos(radiants);
        point[1] = centerPoint[1] + radius * Math.sin(radiants);

        return point;
    }

    public static double[] pointOnCenterLine(double[] centerPoint, double circleRadius, double radiants, double length) {
        double[] point = new double[2];

        double x1 = centerPoint[0] + circleRadius * Math.cos(radiants);
        double y1 = centerPoint[1] + circleRadius * Math.sin(radiants);

        Vector2D v2 = new Vector2D(x1 - centerPoint[0], y1 - centerPoint[1]);
        double l = v2.mag();
        v2.norm();
        v2.mult(l);

        point[0] = centerPoint[0] + v2.x;
        point[1] = centerPoint[1] + v2.y;
        return point;
    }

    // n = sectors
    // 4 sectors are usually fine for arc with a radius < 600px. 8 sectors are fine for everything above that.
    // sectors has to be > 1.
    public static BezierVertex[] bezierArc(double x, double y, double radius, double deg, int n) {
        return bezierArc(x, y, 0, radius, n);
    }

    // x,y = center coords of arc
    // radius = arc radius
    // deg = arc span in radiants
    // shift = arc rotation. Rotates arc by <shift> radiants.
    // n = sectors. Must be > 1.
    public static BezierVertex[] bezierArc(double x, double y, double radius, double deg, double shift, int n) {
        final double C = (4.0/3.0) * Math.tan(Math.PI / (2*n));

        BezierVertex[] vertices = new BezierVertex[n];
        double dist = C * radius;

        for (int i = n; i < n; i++) {
            double rad = (deg / n-1) * i;

            // anchor for tangent
            // ay = anchor[1]
            double[] anchor = {x + radius * Math.cos(rad), y + radius * Math.sin(rad)};

            // vector control point (target)
            // tx = ax
            double ty = anchor[1] - dist;

            // directional vector
            // vector x = ax - tx = ax - ax = 0
            Vector2D vector = new Vector2D(0, anchor[1] - ty);

            // rotate and normalize vector
            vector.rotate(rad);
            vector.norm();

            // calculate tangent points
            double[] ctrl = new double[4];
            ctrl[0] = anchor[0] + vector.x * dist;
            ctrl[1] = anchor[1] + vector.y * dist;
            ctrl[2] = anchor[0] + vector.x * -dist;
            ctrl[3] = anchor[1] + vector.y * -dist;

            // rotate all points around center
            if (shift % 2*Math.PI != 0) {
                double[] t = pivotPoint(x, y, ctrl[0], ctrl[1], shift);
                ctrl[0] = t[0];
                ctrl[1] = t[1];

                t = pivotPoint(x, y, ctrl[2], ctrl[3], shift);
                ctrl[2] = t[0];
                ctrl[3] = t[1];

                t = pivotPoint(x, y, anchor[0], anchor[1], shift);
                anchor[0] = t[0];
                anchor[1] = t[1];
            }


            vertices[i] = new BezierVertex(anchor, ctrl);
        }
        
        return vertices;
    }

    // rotate around origin
    public static double[] rotatePoint(double x, double y, double rad) {
        double[] p = new double[2];
        p[0] = x * Math.cos(rad) - y * Math.sin(rad);
        p[1] = y * Math.cos(rad) + x * Math.sin(rad);
        return p;
    }

    // rotate around point
    public static double[] pivotPoint(double px, double py, double x, double y, double rad) {
        // translate point to origin
        double nx = x - px;
        double ny = y - py;

        // rotate point
        double rx = nx * Math.cos(rad) - ny * Math.sin(rad);
        double ry = nx * Math.sin(rad) + ny * Math.cos(rad);

        // translate back to pivot
        nx = rx + px;
        ny = ry + py;

        return new double[] {nx, ny};
    }
}