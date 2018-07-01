package com.apokk.ui.horizon.eicas;

import com.apokk.ui.UIElement;
import com.apokk.ui.Color;
import com.apokk.ui.Colors;
import com.apokk.ui.ShapeUtil;
import com.apokk.ui.math.Calc;
import processing.core.PShape;
import processing.core.PApplet;

public class CircularDisplay extends PShape implements UIElement {

	private final float C_DEG = PI + QUARTER_PI * 0.70f;
	public final String DEFAULT_FORMAT = "#.#";

	private PApplet parent;
	private String id;
	private float radius;
	private float deg;
	private float[] center;
	private float[] limits;

	private float val;
	private float target;

	private PShape border;
	private PShape textBox;
	private PShape limitWarn;
	private PShape limitDanger;

    public CircularDisplay(PApplet parent, String id, float x, float y, float radius, float lwarn, float ldanger, float lmax) {
		this.parent = parent;
		this.id = id;
		this.radius = radius;
		deg = C_DEG;

		center = new float[] {x, y};
		limits = new float[] {lwarn, ldanger, lmax};
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

	public void setErrorText(String text) {

	}

	@Override
	public void init() {
		border = parent.createShape(ARC, center[0], center[1], radius*2, radius*2, 0, C_DEG, OPEN);
		textBox = parent.createShape(RECT, center[0], center[1] - radius/1.8f, radius * 1.16f, radius/2);

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
		ShapeUtil.strokeWeights(2.0f, border, textBox, limitWarn, limitDanger);
	}

	private float calcDeg(float egt) {
		if (egt >= limits[2]) {
			return deg;
		}
		return deg * (egt/limits[2]);
	}

	private int calcColor(float egt, int alpha, Colors def, int defAlpha) {
		Color c;
		if (egt >= limits[0] && egt < limits[1]) {
			c = new Color(Colors.B_YELLOW.color(), alpha);
		} else if (egt >= limits[1]) {
			c = new Color(Colors.B_RED.color(), alpha);
		} else {
			c = new Color(def.color(), defAlpha);
		}

		return c.hex();
	}

    @Override
    public void draw() {
		// calculate
		float[] rp = Calc.pointOnCircle(center, radius, calcDeg(val));

		// dynamic UI components
		PShape needle = parent.createShape(LINE, center[0], center[1], rp[0], rp[1]);
		needle.setStrokeWeight(2.0f);
		needle.setStroke(calcColor(val, 255, Colors.B_WHITE, 255));

		PShape shadow = parent.createShape(ARC, center[0], center[1], radius*2, radius*2, 0, calcDeg(val), PIE);
		shadow.setStroke(false);
		shadow.setFill(calcColor(val, 35, Colors.B_LIGHT_GREY2, 255));

		// static UI components
		parent.shape(limitWarn);
		parent.shape(limitDanger);
		parent.shape(border);
		parent.shape(shadow);
		parent.shape(needle);
		parent.shape(textBox);
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