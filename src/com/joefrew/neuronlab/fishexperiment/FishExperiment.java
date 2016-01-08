package com.joefrew.neuronlab.fishexperiment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.joefrew.neuralnet.BiasNetwork;
import com.joefrew.neuralnet.genetics.Mutator;
import com.joefrew.neuronlab.Experiment;

public class FishExperiment implements Experiment {
	
	World world = new World();
	
	FishExperimentDisplay display;
	
	Random random = new Random();
	
	//the preferred number of simulation ticks per second
	int preferredSimTicks = 100;
	int nanosPerSimTick = (1000 * 1000 * 1000) / preferredSimTicks;
	
	//the preferred frame rate per second and the number of milliseconds per frame
	int preferredFrameRate = 100;
	int nanosPerFrame = (1000 * 1000 * 1000)/preferredFrameRate;
	
	long currentSimTick = 0;
	long currentFrame = 0;
	
	//the times in nanos of the last tick and frame.
	long lastSimTick = 0;
	long lastFrame = 0;
	
	boolean running = false;
	
	//food will spawn about once every 500 simulation ticks
	double foodSpawnChance = 1/100.0;
	
	public void run() throws Exception {
		this.display = new FishExperimentDisplay(this.world.width, this.world.height);
		
		//setting up a fish brain
		BiasNetwork network = new BiasNetwork(2, 2, 2);
		Mutator mutator = new Mutator();
		
		double[] mutatedGenome = mutator.mutate(network.getGenome(), 1, 5, 5);
				
		System.out.println(Arrays.toString(mutatedGenome));
		
		network.setGenome(mutatedGenome);
		
		
		world.fish.add(new Fish(network, 100, 100));
		running = true;
		while(running) {
			//checking if we are due to perform another sim tick
			long currentTime = System.nanoTime();
			if (currentTime - lastSimTick > nanosPerSimTick) {
				lastSimTick = currentTime;
				simTick();
			}
			
			currentTime = System.nanoTime();
			if (currentTime - lastFrame > nanosPerFrame) {
				lastFrame = currentTime;
				render();
			}
		}
	}
	
	private void simTick() {
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
	
	private void render() {
		currentFrame++;
		
		display.render(world, this);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public int getPreferredSimTicks() {
		return preferredSimTicks;
	}

	public void setPreferredSimTicks(int preferredSimTicks) {
		this.preferredSimTicks = preferredSimTicks;
		this.nanosPerSimTick = (1000 * 1000 * 1000) / preferredSimTicks;
	}

	public int getPreferredFrameRate() {
		return preferredFrameRate;
	}

	public void setPreferredFrameRate(int preferredFrameRate) {
		this.preferredFrameRate = preferredFrameRate;
		this.nanosPerFrame = (1000 * 1000 * 1000)/preferredFrameRate;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public long getCurrentSimTick() {
		return currentSimTick;
	}

	public long getCurrentFrame() {
		return currentFrame;
	}
	
	
	

}
