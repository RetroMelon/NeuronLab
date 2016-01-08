package com.joefrew.neuronlab.fishexperiment;

import java.awt.Color;
import java.awt.Graphics2D;

public class Food implements ExperimentRenderable {
	
	protected double x = 0;
	protected double y = 0;
	protected int size = 8;
	
	protected boolean eaten = false;
	
	public Food (double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.GREEN);
		
		g.fillOval((int)(x-size), (int)(y-size), (int)(size * 2), (int)(size * 2));
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
	
	
}
