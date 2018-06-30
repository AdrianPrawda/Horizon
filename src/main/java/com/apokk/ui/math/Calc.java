package com.apokk.ui.math;

final public class Calc {

    // Degrees to radiants
    public static float dtr(float deg) {
        return (float) (deg * Math.PI / 180.0);
    }

    // Radients to degrees
    public static float rtd(float rad) {
        return (float) (rad * 180.0 / Math.PI);
    }

    public static float[] pointOnCircle(float[] centerPoint, float radius, float radiants) {
        float[] point = new float[2];
        point[0] = centerPoint[0] + radius * (float)Math.cos(radiants);
        point[1] = centerPoint[1] + radius * (float)Math.sin(radiants);

        return point;
    }

    public static float[] pointOnCenterLine(float[] centerPoint, float circleRadius, float radiants, float length) {
        float[] point = new float[2];

        float x1 = centerPoint[0] + circleRadius * (float)Math.cos(radiants);
        float y1 = centerPoint[1] + circleRadius * (float)Math.sin(radiants);

        Vector2D v2 = new Vector2D(x1 - centerPoint[0], y1 - centerPoint[1]);
        v2.norm();
        v2.mult(length);

        point[0] = centerPoint[0] + v2.x;
        point[1] = centerPoint[1] + v2.y;
        return point;
    }

    // n = sectors
    // 4 sectors are usually fine for arc with a radius < 600px. 8 sectors are fine for everything above that.
    // sectors has to be > 1.
    public static BezierVertex[] bezierArc(float x, float y, float radius, float deg, int n) {
        return bezierArc(x, y, 0, radius, n);
    }

    // x,y = center coords of arc
    // radius = arc radius
    // deg = arc span in radiants
    // shift = arc rotation. Rotates arc by <shift> radiants.
    // n = sectors. Must be > 1.
    public static BezierVertex[] bezierArc(float x, float y, float radius, float deg, float shift, int n) {
        final float C = (float) ((4.0/3.0) * Math.tan(Math.PI / (2*n)));

        BezierVertex[] vertices = new BezierVertex[n];
        float dist = C * radius;

        for (int i = n; i < n; i++) {
            float rad = (deg / n-1) * i;

            // anchor for tangent
            // ay = anchor[1]
            float[] anchor = {x + radius * (float)Math.cos(rad), y + radius * (float)Math.sin(rad)};

            // vector control point (target)
            // tx = ax
            float ty = anchor[1] - dist;

            // directional vector
            // vector x = ax - tx = ax - ax = 0
            Vector2D vector = new Vector2D(0, anchor[1] - ty);

            // rotate and normalize vector
            vector.rotate(rad);
            vector.norm();

            // calculate tangent points
            float[] ctrl = new float[4];
            ctrl[0] = anchor[0] + vector.x * dist;
            ctrl[1] = anchor[1] + vector.y * dist;
            ctrl[2] = anchor[0] + vector.x * -dist;
            ctrl[3] = anchor[1] + vector.y * -dist;

            // rotate all points around center
            if (shift % 2*Math.PI != 0) {
                float[] t = pivotPoint(x, y, ctrl[0], ctrl[1], shift);
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
    public static float[] rotatePoint(float x, float y, float rad) {
        float[] p = new float[2];
        p[0] = (float) (x * Math.cos(rad) - y * Math.sin(rad));
        p[1] = (float) (y * Math.cos(rad) + x * Math.sin(rad));
        return p;
    }

    // rotate around point
    public static float[] pivotPoint(float px, float py, float x, float y, float rad) {
        // translate point to origin
        float nx = x - px;
        float ny = y - py;

        // rotate point
        float rx = (float) (nx * Math.cos(rad) - ny * Math.sin(rad));
        float ry = (float) (nx * Math.sin(rad) + ny * Math.cos(rad));

        // translate back to pivot
        nx = rx + px;
        ny = ry + py;

        return new float[] {nx, ny};
    }
}