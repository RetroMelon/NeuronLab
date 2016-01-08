package com.joefrew.neuronlab.fishexperiment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import com.joefrew.neuralnet.BiasNetwork;
import com.joefrew.neuralnet.genetics.Genomic;
import com.joefrew.neuralnet.genetics.Scorable;

public class Fish implements ExperimentRenderable, Genomic, Scorable {
	
	BiasNetwork brain;

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
	Food currentFood = null;
	
	public Fish(BiasNetwork brain, double x, double y) {
		this.brain = brain;
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics2D g) {
		//drawing body
		g.setColor(Color.RED);
		g.fillOval((int)(x-size), (int)(y-size), (int)(size * 2), (int)(size * 2));
		

		
		Point2D eye1Location = this.getEyeLocation(0);
		Point2D eye2Location = this.getEyeLocation(1);
		
		if (this.currentFood != null) {			
			//drawing lines to the food is there is any
			g.setColor(Color.YELLOW);
			
			g.drawLine((int)(eye1Location.getX()), (int)(eye1Location.getY()), (int)(currentFood.getX()), (int)(currentFood.getY()));
			g.drawLine((int)(eye2Location.getX()), (int)(eye2Location.getY()), (int)(currentFood.getX()), (int)(currentFood.getY()));
		}
		
		//drawing eyes
		g.setColor(Color.BLUE);
		
		g.fillOval((int)(eye1Location.getX() - eyeSize), (int)(eye1Location.getY() - eyeSize), (int)(eyeSize * 2), (int)(eyeSize * 2));
		g.fillOval((int)(eye2Location.getX() - eyeSize), (int)(eye2Location.getY() - eyeSize), (int)(eyeSize * 2), (int)(eyeSize * 2));
	}
	
	/**
	 * Sense, perform brain function, move and collide.
	 * @param world
	 */
	public void update(World world) {
		double[] brainInputs = sense(world);
		
		System.out.println("\t\t" + Arrays.toString(brainInputs));
		
		double[] brainOutputs = brain(brainInputs);
		
		System.out.println(Arrays.toString(brainOutputs));
		
		if (brainOutputs != null) {
			this.speed = brainOutputs[0] * maxSpeed;
			this.rotationSpeed = brainOutputs[1] * maxRotationSpeed;
			
			if (speed > maxSpeed) {
				speed = maxSpeed;
			} else if (speed < -maxSpeed) {
				speed = -maxSpeed;
			}
			
			if (rotationSpeed > maxRotationSpeed) {
				rotationSpeed = maxRotationSpeed;
			} else if (rotationSpeed < -maxRotationSpeed) {
				rotationSpeed = -maxRotationSpeed;
			}
		}
		
		move(world);
		collide(world);
	}
	
	/**
	 * sense detects the closes piece of food to the fish, then gets the distance from each of the eyes to the piece of food.
	 * 
	 * @return a pair of double values which are the distances between each eye and the food. If there is no food, the distances are maxValue.
	 */
	public double[] sense(World world) {
		Food closestFood = null;
		int closestDistance = Integer.MAX_VALUE;
		
		//finding the closes piece of food
		for (Food food : world.food) {
			int differenceX = (int)this.x - (int)food.getX();
			int differenceY = (int)this.y - (int)food.getY();
			
			int distance = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
			
			if (distance < closestDistance) {
				closestFood = food;
				closestDistance = distance;
			}
		}
		
		//if there was no piece of food to detect, return max_values
		if (closestFood == null) {
			return new double[]{Double.MAX_VALUE, Double.MAX_VALUE};
		} else {
			this.currentFood = closestFood;
		}
		
		//we now have the closest piece of food, so finding the distance between it and the two eyes
		Point2D eye0Location = this.getEyeLocation(0);
		int differenceX = (int)eye0Location.getX() - (int)closestFood.getX();
		int differenceY = (int)eye0Location.getY() - (int)closestFood.getY();
		
		int distanceEye0 = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
		
		Point2D eye1Location = this.getEyeLocation(1);
		differenceX = (int)eye1Location.getX() - (int)closestFood.getX();
		differenceY = (int)eye1Location.getY() - (int)closestFood.getY();
		
		int distanceEye1 = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
		
		return new double[]{distanceEye0, distanceEye1};
	}
	
	public double[] brain(double[] input) {
		try {
			return this.brain.activate(input);			
		} catch (Exception e) {
			return null;
		}
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

}
