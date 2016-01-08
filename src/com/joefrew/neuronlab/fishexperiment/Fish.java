package com.joefrew.neuronlab.fishexperiment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

public class Fish implements ExperimentRenderable {

	double x = 300;
	double y = 300;
	int size = 20; // the radius of the 
	double rotation = 0; //rotation in radians
	
	int eyeDistance = (int)(size * 1.2);
	double eyeSplay = 0.8; //the offset of the eyes from centre in radians
	int eyeSize = 5;
	
	//The size of the collision radius for food
	int collisionSize = (int)(size * 1.8);
	
	double maxSpeed = 2.0; //2 pixels per tick pax speed
	double speed = 0; //this will be linked to the brain so can vary between maxSpeed and -maxSpeed 
	double maxRotationSpeed = 0.01; //0.01 radians rotation per tick
	double rotationSpeed = 0;
	
	int foodEaten = 0;
	
	public void render(Graphics2D g) {
		//drawing body
		g.setColor(Color.RED);
		g.fillOval((int)(x-size), (int)(y-size), (int)(size * 2), (int)(size * 2));
		
		//drawing eyes
		g.setColor(Color.BLUE);
		
		Point2D eye1Location = this.getEyeLocation(0);
		g.fillOval((int)(eye1Location.getX() - eyeSize), (int)(eye1Location.getY() - eyeSize), (int)(eyeSize * 2), (int)(eyeSize * 2));
		
		Point2D eye2Location = this.getEyeLocation(1);
		g.fillOval((int)(eye2Location.getX() - eyeSize), (int)(eye2Location.getY() - eyeSize), (int)(eyeSize * 2), (int)(eyeSize * 2));
	}
	
	//update is responsible for sensing, doing brain activity and moving.
	public void update(World world) {
		//sense
		//brain
		double[] brainOutputs = {1, -1};
		
		this.speed = brainOutputs[0] * maxSpeed;
		this.rotationSpeed = brainOutputs[1] * maxRotationSpeed;
		
		move(world);
		collide(world);
	}

	public void move(World world) {
		rotation += rotationSpeed;
		
		//adding on the current speed taking in to account the direction
		x += Math.sin(rotation) * speed;
		y -= Math.cos(rotation) * speed;
		
		//making sure the fish doesn't leave the screen
		if (x > world.width - size) {
			x = world.width - size;
		} else if (x < size) {
			x = size;
		}
		
		if (y > world.height - size) {
			y = world.height - size;
		} else if (y < size) {
			y = size;
		}
	}
	
	/**
	 * The collide method performs any necessary collision detection such as whether the fish collides with some food.
	 * @param world
	 */
	public void collide(World world) {
		for (Food food : world.food) {
			if (food.isEaten()) {
				continue;
			}
			
			int differenceX = (int)this.x - (int)food.getX();
			int differenceY = (int)this.y - (int)food.getY();
			
			int distance = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
			
			if (distance <= collisionSize) {
				foodEaten++;
				food.setEaten(true);
			}
		}
	}
	
	/**
	 * gets a point describing an eye location. the left eye is eye 0 and the right is eye 1.
	 * @param eyeNumber
	 * @return
	 */
	public Point2D getEyeLocation(int eyeNumber) {
		double angle = rotation;
		if (eyeNumber == 0) { //adding on the eye splay depending on which eye it is.
			angle -= eyeSplay;
		} else {
			angle += eyeSplay;
		}
		
		double eyex = x + Math.sin(angle) * eyeDistance; //relative to body centre
		double eyey = y - Math.cos(angle) * eyeDistance;
		
		return new Point2D.Double(eyex, eyey);
	}

}
