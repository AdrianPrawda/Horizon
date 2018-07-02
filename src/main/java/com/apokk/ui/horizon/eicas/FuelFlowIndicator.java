package com.apokk.ui.horizon.eicas;

import java.text.DecimalFormat;

import com.apokk.ui.Color;
import com.apokk.ui.Colors;
import com.apokk.ui.ShapeUtil;
import com.apokk.ui.math.Calc;

import processing.core.PApplet;
import processing.core.PShape;

public class FuelFlowIndicator extends PShape {

    public final String DEFAULT_FORMAT = "#.##";
    private final float C_DEG = PI + QUARTER_PI * 0.50f;

    // internal
    private PApplet parent;
    private String id;

    // format
    private DecimalFormat format;
    private DecimalFormat markingsFormat;

    // geometry
    private float radius;
    private float deg;
    private float[] center;

    // params
    private float[] limits;
    private float val;

    // shapes
    private PShape border;
	private PShape textBox;
    private PShape limitDanger;
    private PShape[] markings;
    
    // colors
    private Color shadowColors[];
    private Color needleColors[];

    public FuelFlowIndicator(PApplet parent, String id, float x, float y, float radius, float ldanger, float lmax) {
        this.parent = parent;
        this.id = id;

        format = new DecimalFormat(DEFAULT_FORMAT);
        markingsFormat = new DecimalFormat("#");

        this.radius = radius;
        deg = C_DEG;
        center = new float[] {x, y};
        limits = new float[] {ldanger, lmax};

        shadowColors = new Color[2];
		shadowColors[0] = Colors.B_BLUE.color();
		shadowColors[1] = Colors.B_RED.color();

		needleColors = new Color[2];
		needleColors[0] = Colors.B_WHITE.color();
		needleColors[1] = Colors.B_RED.color();

		shadowColors[0].setAlpha(40);
		shadowColors[1].setAlpha(40);
    }

    public void init() {
        border = parent.createShape(ARC, center[0], center[1], radius*2, radius*2, 0, C_DEG, OPEN);
        textBox = parent.createShape(RECT, center[0], center[1] - radius/1.7f, radius * 1.16f, radius/2);
        
        float[] pd = Calc.pointOnCircle(center, radius, calcDeg(limits[0]));
        float[] pd2 = Calc.pointOnCenterLine(center, radius, calcDeg(limits[0]), radius * 1.205f);
        limitDanger = parent.createShape(LINE, pd[0], pd[1], pd2[0], pd2[1]);

        markings = new PShape[7];
        for (int i = 0; i < 7; i++) {
            float l = (limits[1]/6)*i;

            float[] p1 = Calc.pointOnCircle(center, radius, calcDeg(l));
            float[] p2 = Calc.pointOnCenterLine(center, radius, calcDeg(l), radius * 0.78f);
            markings[i] = parent.createShape(LINE, p1[0], p1[1], p2[0], p2[1]);

            if (i == 4 || i == 6) {
                float[] tp = Calc.pointOnCenterLine(center, radius, calcDeg(l), radius * 0.71f);

                if (i == 6) { tp[1] += radius * 0.1f; }
                renderTextMarking(tp[0], tp[1], markingsFormat.format((int)l));
            } else if (i == 2) {
                float[] tp = Calc.pointOnCenterLine(center, radius, calcDeg(l), radius * 0.66f);
                tp[0] -= radius * 0.1f;
                renderTextMarking(tp[0], tp[1], markingsFormat.format((int)l));
            }
        }

        ShapeUtil.noFillAll(border, textBox);
        ShapeUtil.strokeWeights(1.0f, border, textBox);

        limitDanger.setStrokeWeight(2.0f);
        limitDanger.setStroke(Colors.B_RED.hex());
    }

    public void draw() {
        // static
        parent.shape(border);
        parent.shape(textBox);
        parent.shape(limitDanger);
        for (PShape shape : markings) {
            parent.shape(shape);
        }

        // dynamic
        float[] cp = Calc.pointOnCircle(center, radius, calcDeg(val));

        PShape needle = parent.createShape(LINE, center[0], center[1], cp[0], cp[1]);
        needle.setStrokeWeight(2.0f);
        needle.setStroke(calcNeedleColor(val));

        PShape shadow = parent.createShape(ARC, center[0], center[1], radius*2, radius*2, 0, calcDeg(val), PIE);
        shadow.setStroke(false);
        shadow.setFill(calcShadowColor(val));

        parent.shape(shadow);
        parent.shape(needle);

        // text
        renderTextValue(val);
    }

    public void target(float value) {
        val = value;
    }

    public String getID() {
        return id;
    }

    private float calcDeg(float ff) {
		if (ff >= limits[1]) {
			return deg;
		}
		return deg * (ff/limits[1]);
    }
    
    private int calcShadowColor(float ff) {
        if (ff >= limits[0]) {
			return shadowColors[1].hex();
		}
		return shadowColors[0].hex();
	}

	private int calcNeedleColor(float ff) {
		if (ff >= limits[0]) {
			return needleColors[1].hex();
		}
		return needleColors[0].hex();
    }
    
    private void renderTextValue(float ff) {
        if (ff >= limits[0]) {
            parent.fill(Colors.B_RED.hex());
        }

        parent.textSize(15);
        parent.text(format.format(ff).replace(',', '.'), center[0] + 3, center[1] - radius * 0.19f);

        parent.textSize(19); // remove 
        parent.fill(255); // remove-
    }
    
    private void renderTextMarking(float x, float y, String text) {
        parent.textSize(8);
        parent.text(text, x, y);
        parent.textSize(19); // remove
    }
}