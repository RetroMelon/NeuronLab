package com.joefrew.neuronlab.fishexperiment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.joefrew.neuralnet.BiasNetwork;
import com.joefrew.neuralnet.genetics.Genomic;
import com.joefrew.neuralnet.genetics.Scorable;

/**
 * An abstract class that implements some of the very basic fish functionality, but nothing like
 * @author joe
 *
 */
public abstract class Fish implements ExperimentRenderable, Genomic, Scorable, PointCollidable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4667492156793268919L;

	BiasNetwork brain;

	private transient double x = 300;
	private transient double y = 300;
	private transient int size = 15; // the radius of the 

	//The size of the collision radius for food
	private transient int collisionSize = (int)(size * 1.8);
	
	private transient int foodEaten = 0;
	
	public Fish(BiasNetwork brain) {
		this.brain = brain;
		this.x = x;
		this.y = y;
	}
	
	public abstract void render(Graphics2D g);

	public abstract void render(Graphics2D g, int debugLevel);
	
	public abstract void update(World world);

	public double getScore() {
		return this.foodEaten;
	}

	public double[] getGenome() {
		return this.brain.getGenome();
	}

	public void setGenome(double[] genome) {
		try {
			this.brain.setGenome(genome);
		} catch (Exception e) {
			e.printStackTrace();
		}		
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

	public BiasNetwork getBrain() {
		return brain;
	}
	
	public boolean pointCollision(double x, double y) {
		int differenceX = (int)this.x - (int)x;
		int differenceY = (int)this.y - (int)y;
		
		int distance = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
		
		return distance <= this.collisionSize;
	}

}
