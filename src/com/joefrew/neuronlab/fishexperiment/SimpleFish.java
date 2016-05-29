package com.joefrew.neuronlab.fishexperiment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.joefrew.neuralnet.BiasNetwork;
/**
 * A very simple fish with a variable number of eyes.
 * The eye distance to the nearest piece of food acts as inputs to the neural net.
 * @author joe
 *
 */
public class SimpleFish extends Fish {

	public SimpleFish(BiasNetwork brain) {		
		super(brain);
	}
	
	transient double rotation = 0; //rotation in radians
	
	int eyeDistance = (int)(getSize() * 1.2);
	double eyeSplay = 1; //the offset of the eyes from centre in radians. NOTE: do not use Math.PI or anything here, because it will cause rounding when calculating eye location.
	int eyeSize = 3;
	
	int numberOfEyes = 3;
	
	//The size of the collision radius for food
	int collisionSize = (int)(getSize() * 2);
	
	double maxSpeed = 2.0; //2 pixels per tick pax speed
	double speed = 0; //this will be linked to the brain so can vary between maxSpeed and -maxSpeed 
	double maxRotationSpeed = 0.01; //0.01 radians rotation per tick
	double rotationSpeed = 0;

	transient int foodEaten = 0;
	Food currentFood = null;
	
	private Color getBodyColor() {
		double foodScore = this.foodEaten / 10.0;
		if (foodScore > 1) {
			foodScore = 1;
		}
		double H = foodScore * 0.4; // Hue (note 0.4 = Green, see huge chart below)
	    double S = 0.9; // Saturation
	    double B = 0.9; // Brightness

	    return Color.getHSBColor((float)H, (float)S, (float)B);
	}
	
	public void render(Graphics2D g) {
		this.render(g, 0);
	}

	public void render(Graphics2D g, int debugLevel) {
		//drawing body
		g.setColor(this.getBodyColor());
		g.fillOval((int)(this.getX()-this.getSize()), (int)(this.getY()-this.getSize()), (int)(this.getSize() * 2), (int)(this.getSize() * 2));
		

		for (int i = 0; i < numberOfEyes; i++) {
			Point2D eyeLocation = this.getEyeLocation(i);
			
			if (debugLevel >= 1 && this.currentFood != null && !this.currentFood.isEaten()) {			
				//drawing lines to the food is there is any
				g.setColor(Color.CYAN);
				
				g.drawLine((int)(eyeLocation.getX()), (int)(eyeLocation.getY()), (int)(currentFood.getX()), (int)(currentFood.getY()));
			}
			
			//drawing eyes
			g.setColor(Color.YELLOW);
			
			g.fillOval((int)(eyeLocation.getX() - eyeSize), (int)(eyeLocation.getY() - eyeSize), (int)(eyeSize * 2), (int)(eyeSize * 2));		
		}
		
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
			int differenceX = (int)this.getX() - (int)food.getX();
			int differenceY = (int)this.getY() - (int)food.getY();
			
			int distance = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
			
			if (distance < closestDistance) {
				closestFood = food;
				closestDistance = distance;
			}
		}
		
		//if there was no piece of food to detect, return max_values
		if (closestFood == null) {
			return new double[numberOfEyes];
		} else {
			this.currentFood = closestFood;
		}
		
		//we now have the closest piece of food, so finding the distance between it and the two eyes
		double[] distances = new double[numberOfEyes];
		
		for (int i = 0; i < numberOfEyes; i++) {
			Point2D eyeLocation = this.getEyeLocation(i);
			int differenceX = (int)eyeLocation.getX() - (int)closestFood.getX();
			int differenceY = (int)eyeLocation.getY() - (int)closestFood.getY();
			
			int distanceEye = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
			
			distances[i] = distanceEye;
		}
		
		
		return distances;
	}
	
	
	
	public void move(World world) {
		rotation += rotationSpeed;
		
		//adding on the current speed taking in to account the direction
		this.setX(this.getX() + Math.sin(rotation) * speed);
		this.setY(this.getY() - Math.cos(rotation) * speed);
		
		//making sure the fish doesn't leave the screen
		if (this.getX() > world.width - this.getSize()) {
			this.setX(world.width - this.getSize());
		} else if (this.getX() < this.getSize()) {
			this.setX(this.getSize());
		}
		
		if (this.getY() > world.height - this.getSize()) {
			this.setY(world.height - this.getSize());
		} else if (this.getY() < this.getSize()) {
			this.setY(this.getSize());
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
			
			if (this.pointCollision(food.getX(), food.getY())) {
				foodEaten++;
				food.setEaten(true);
			}
		}
	}
	
	/**
	 * Sense, perform brain function, move and collide.
	 * @param world
	 */
	public void update(World world) {
		double[] brainInputs = sense(world);
		
		double[] brainOutputs = brain(brainInputs);
		
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
	
	public double[] brain(double[] input) {
		try {
			return this.brain.activate(input);			
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * gets a point describing an eye location. the left eye is eye 0 and the right is eye 1.
	 * @param eyeNumber
	 * @return
	 */
	public Point2D getEyeLocation(int eyeNumber) {
		double angle = (rotation % (2 * Math.PI)); //accounting for the number of eyes
		angle -= (((double)numberOfEyes-1 * eyeSplay) / 2.0); //NOTE: rounding errors tend to happen here if eyeSplay has PI in it.
		angle += (double)eyeNumber * eyeSplay;
		
		double eyex = this.getX() + Math.sin(angle) * eyeDistance; //relative to body centre
		double eyey = this.getY() - Math.cos(angle) * eyeDistance;
		
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

	public double getSpeed() {
		return speed;
	}

}
