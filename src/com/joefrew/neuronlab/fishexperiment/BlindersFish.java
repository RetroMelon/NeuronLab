package com.joefrew.neuronlab.fishexperiment;

import java.awt.geom.Point2D;

import com.joefrew.neuralnet.BiasNetwork;

public class BlindersFish extends SimpleFish {

	public BlindersFish(BiasNetwork brain) {
		super(brain);
		// TODO Auto-generated constructor stub
	}

	protected double viewAngle = 1.5; //number of radians on either sode of the fish's head it can see.
	/**
	 * 
	 */
	private static final long serialVersionUID = -2873685088602254734L;

	@Override
	public double[] sense(World world) {
		Food closestFood = null;
		int closestDistance = Integer.MAX_VALUE;
		
		//finding the closes piece of food
		for (Food food : world.food) {
			int differenceX = (int)this.getX() - (int)food.getX();
			int differenceY = (int)this.getY() - (int)food.getY();
			
			double foodAngle = Math.atan2(differenceX, differenceY);//calculating the food angle. measured from horizontal
			double angleDifference = Math.abs((rotation + foodAngle) % (2 * Math.PI));//rotation is measured from vertical
			
			//if it is outwith the valid range, continue to the next piece of food.
			if (angleDifference > viewAngle && angleDifference < 2*Math.PI - viewAngle) {
				continue;
			}
			
			int distance = (int)Math.sqrt((differenceX*differenceX)+(differenceY*differenceY));
			
			if (distance < closestDistance) {
				closestFood = food;
				closestDistance = distance;
			}
		}
		
		currentFood = closestFood;
		
		//if there was no piece of food to detect, return max_values
		if (closestFood == null) {
			return new double[numberOfEyes];
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
	
	

}
