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
	FishSimulation simulation;
	
	//the preferred number of simulation ticks per second
	int preferredSimTicks = 1000;
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
	double foodSpawnChance = 1/1000.0;
	
	public void run() throws Exception {
		//setting up the display
		this.display = new FishExperimentDisplay(this.world.width, this.world.height);
		this.simulation = new FishSimulation(this.world);
		
		//setting up an initial generation of fish
		
		//setting up a genetic algorithm to breed/mutate the generations
		
		
		//while true, put the fish in a simulation and run it. then get a new generation from the genetic algorithm
		//and run it in a new simulation
		//while (true) {
			//simulating the fish
			//world.fish = currentGeneration.getFish();
			//world.food.clear();
		
			//runSimulation();
		
			//getting a new generation from the genetic algorithm
			//currentGeneration = geneticAlgorithm.newGeneration(currentGeneration);
		//}
		
		
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
		this.simulation.tick();
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
