package com.apokk.ui.horizon.eicas;

import com.apokk.ui.UIElement;

import java.text.DecimalFormat;

import com.apokk.ui.Color;
import com.apokk.ui.Colors;
import com.apokk.ui.ShapeUtil;
import com.apokk.ui.math.Calc;
import processing.core.PShape;
import processing.core.PApplet;

public class FanSpeedIndicator extends PShape implements UIElement {

	// consts
	public final String DEFAULT_FORMAT = "#";
	private final float C_DEG = PI + QUARTER_PI * 0.70f;

	// internal
	private PApplet parent;
	private String id;

	// geometry
	private float radius;
	private float deg;
	private float[] center;

	// format
	private DecimalFormat format;

	// parameters
	private float[] limits;
	private float val;
	private float target;

	// shapes
	private PShape border;
	private PShape textBox;
	private PShape limitWarn;
	private PShape limitDanger;

	// colors
	private Color[] shadowColors;
	private Color[] needleColors;

    public FanSpeedIndicator(PApplet parent, String id, float x, float y, float radius, float lwarn, float ldanger, float lmax) {
		this.parent = parent;
		this.id = id;
		this.radius = radius;
		deg = C_DEG;

		format = new DecimalFormat(DEFAULT_FORMAT);

		center = new float[] {x, y};
		limits = new float[] {lwarn, ldanger, lmax};

		// For some reason, syntax highlighting breaks if I initialize the array with values. :/
		shadowColors = new Color[3];
		shadowColors[0] = Colors.B_BLUE.color();
		shadowColors[1] = Colors.B_YELLOW.color();
		shadowColors[2] = Colors.B_RED.color();

		needleColors = new Color[3];
		needleColors[0] = Colors.B_WHITE.color();
		needleColors[1] = Colors.B_YELLOW.color();
		needleColors[2] = Colors.B_RED.color();

		shadowColors[0].setAlpha(40);
		shadowColors[1].setAlpha(40);
		shadowColors[2].setAlpha(40);
	}
	
	public void slim(boolean b) {
		if (b) {
			ShapeUtil.strokeWeights(1.0f, limitWarn, limitDanger, border, textBox);
		} else {
			ShapeUtil.strokeWeights(2.0f, limitWarn, limitDanger, border, textBox);
		}
	} 

	public void setLimits(float warning, float danger, float max, int frames) {
		limits = new float[] {warning, danger, max};
	}

	public void setDecimalFormat(String format) {
		this.format = new DecimalFormat(format);
	}

	public void setErrorText(String text) {

	}

	@Override
	public void init() {
		border = parent.createShape(ARC, center[0], center[1], radius*2, radius*2, 0, C_DEG, OPEN);
		textBox = parent.createShape(RECT, center[0], center[1] - radius/1.7f, radius * 1.16f, radius/2);

		// limits
		float[] pw = Calc.pointOnCircle(center, radius, calcDeg(limits[0]));
		float[] pw2 = Calc.pointOnCenterLine(center, radius, calcDeg(limits[0]), radius * 1.16f);

		float[] pd = Calc.pointOnCircle(center, radius, calcDeg(limits[1]));
		float[] pd2 = Calc.pointOnCenterLine(center, radius, calcDeg(limits[1]), radius * 1.205f);

		limitWarn = parent.createShape(LINE, pw[0], pw[1], pw2[0], pw2[1]);
		limitDanger = parent.createShape(LINE, pd[0], pd[1], pd2[0], pd2[1]);

		limitWarn.setStroke(Colors.B_YELLOW.color().hex());
		limitDanger.setStroke(Colors.B_RED.color().hex());

		ShapeUtil.fillAll(0, border, textBox);
		ShapeUtil.strokeWeights(2.0f, limitWarn, limitDanger);
		ShapeUtil.strokeWeights(1.0f, border, textBox);
	}

	private float calcDeg(float egt) {
		if (egt >= limits[2]) {
			return deg;
		}
		return deg * (egt/limits[2]);
	}

	private int calcShadowColor(float egt) {
		if (egt >= limits[0] && egt < limits[1]) {
			return shadowColors[1].hex();
		} else if (egt >= limits[1]) {
			return shadowColors[2].hex();
		}
		return shadowColors[0].hex();
	}

	private int calcNeedleColor(float egt) {
		if (egt >= limits[0] && egt < limits[1]) {
			return needleColors[1].hex();
		} else if (egt >= limits[1]) {
			return needleColors[2].hex();
		}
		return needleColors[0].hex();
	}

	private void renderText(float egt) {
		if (egt >= limits[0] && egt < limits[1]) {
			parent.fill(Colors.B_YELLOW.hex());
		} else if (egt >= limits[1]) {
			parent.fill(Colors.B_RED.hex());
		}

		parent.text(format.format(egt).replace(',', '.'), center[0] + 3, center[1] - 10);
		parent.fill(255); // remove
	}

    @Override
    public void draw() {
		// calculate
		float[] rp = Calc.pointOnCircle(center, radius, calcDeg(val));

		// dynamic UI components
		PShape needle = parent.createShape(LINE, center[0], center[1], rp[0], rp[1]);
		needle.setStrokeWeight(2.0f);
		needle.setStroke(calcNeedleColor(val));

		PShape shadow = parent.createShape(ARC, center[0], center[1], radius*2, radius*2, 0, calcDeg(val), PIE);
		shadow.setStroke(false);
		shadow.setFill(calcShadowColor(val));

		// draw UI components
		parent.shape(limitWarn);
		parent.shape(limitDanger);
		parent.shape(border);
		parent.shape(shadow);
		parent.shape(needle);
		parent.shape(textBox);

		// text
		renderText(val);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void target(float value) {
		this.target = value;
		this.val = value; // temporary
	}

	@Override
	public void setFailureMode(int mode) {
		
	}

	@Override
	public String getID() {
		return id;
	}

	public float[] getLimits() {
		return limits;
	}
}