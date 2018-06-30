package com.apokk.ui.horizon.eicas;

import com.apokk.ui.UIElement;
import com.apokk.ui.math.Calc;
import processing.core.PShape;
import processing.core.PApplet;

public class CircularDisplay extends PShape implements UIElement {

	private final float C_DEG = PI + QUARTER_PI * 0.70f;

	private PApplet parent;
	private String id;
	private float radius;
	private float deg;
	private float[] center;
	private float[] limits;

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
			limitWarn.strokeWeight(1.0f);
			limitDanger.strokeWeight(1.0f);
			border.setStrokeWeight(1.0f);
			textBox.setStrokeWeight(1.0f);
		} else {
			limitWarn.strokeWeight(2.0f);
			limitDanger.strokeWeight(2.0f);
			border.strokeWeight(2.0f);
			textBox.strokeWeight(2.0f);
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
		textBox = parent.createShape(RECT, center[0], center[1] - radius/2, radius * 1.16f, radius/2);

		// limits
		float[] pw = Calc.pointOnCircle(center, radius, calcDeg(limits[0]));
		float[] pw2 = Calc.pointOnCenterLine(center, radius, calcDeg(limits[0]), radius * 1.16f);
		float[] pd = Calc.pointOnCircle(center, radius, calcDeg(limits[1]));
		float[] pd2 = Calc.pointOnCenterLine(center, radius, calcDeg(limits[1]), radius * 1.16f);

		limitWarn = parent.createShape(LINE, pw[0], pw[1], pw2[0], pw2[1]);
		limitDanger = parent.createShape(LINE, pd[0], pd[1], pd2[0], pd2[1]);

		// stroke weights
		border.setFill(0);
		border.setStrokeWeight(2.0f);
		textBox.setFill(0);
		textBox.setStrokeWeight(2.0f);
		limitWarn.setStrokeWeight(2.0f);
		limitDanger.setStrokeWeight(2.0f);

		//border.beginShape();
		//border.strokeWeight(2.0f);
		//border.noFill();
		//border.endShape();

		//limitWarn.strokeWeight(2.0f);
		//limitDanger.strokeWeight(2.0f);
		//border.strokeWeight(2.0f);
		//textBox.strokeWeight(2.0f);
	}

	private float calcDeg(float egt) {
		if (egt >= limits[2]) {
			return deg;
		}
		return deg * (egt/limits[2]);
	}

    @Override
    public void draw() {
		parent.shape(border);
		parent.shape(textBox);
		parent.shape(limitWarn);
		parent.shape(limitDanger);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void target(double value) {
		
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