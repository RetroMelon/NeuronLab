package com.joefrew.neuronlab.fishexperiment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FishSimulation {
	
	//it's a good idea to have a random for general use
	Random random = new Random();
	
	private long currentSimTick = 0;
	private World world;
	
	//food will spawn about once every 500 simulation ticks
	private double foodSpawnChance = 1/1000.0;
	
	
	public FishSimulation(World world) {
		this.world = world;
	}
	
	public void tick() {
		currentSimTick++;

		//spawning food if we haven't had one in a while.
		if (random.nextDouble() < foodSpawnChance) {
			world.food.add(new Food(random.nextDouble()*world.width, random.nextDouble()*world.height));
		}
		
		//updating all of the fish
		for (Fish fish : world.fish) {
			fish.update(world);
		}
		
		//removing any eaten food from the world.
		List<Food> eatenFood = new ArrayList<Food>();
		for (Food food : world.food) {
			if (food.isEaten()) {
				eatenFood.add(food);
			}
		}
		
		world.food.removeAll(eatenFood);
	}

	public double getFoodSpawnChance() {
		return foodSpawnChance;
	}

	public void setFoodSpawnChance(double foodSpawnChance) {
		this.foodSpawnChance = foodSpawnChance;
	}

	public long getCurrentSimTick() {
		return currentSimTick;
	}
	
	

}
