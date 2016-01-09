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
import com.joefrew.neuralnet.genetics.GeneticAlgorithm;
import com.joefrew.neuralnet.genetics.Mutator;
import com.joefrew.neuronlab.Experiment;

public class FishExperiment implements Experiment {
	
	Random random = new Random();
	
	World world = new World(1000, 700);
	
	FishExperimentDisplay display;
	FishSimulation simulation;
	
	//experiment variables like fish per generation, etc.
	int simTicksPerGeneration = 100000;
	
	int genomesPerGeneration = 15;
	int selectedGenomes = 4; //how many fish get to breed
	
	int[] brainTopology = new int[]{2, 3, 2};
	int genomeLength = (new BiasNetwork(brainTopology)).getGenome().length; //TODO: eliminate the need for this
	
	//the preferred number of simulation ticks per second
	int preferredSimTicks = 1000;
	long nanosPerSimTick = (1000 * 1000 * 1000) / preferredSimTicks;
	
	//the preferred frame rate per second and the number of milliseconds per frame
	int preferredFrameRate = 100;
	long nanosPerFrame = (1000 * 1000 * 1000)/preferredFrameRate;

	long currentFrame = 0;
	
	//the times in nanos of the last tick and frame.
	long lastSimTick = 0;
	long lastFrame = 0;
	
	//whether the experiment has finished or is still in progress.
	boolean running = false;
	
	//whether the experiment has been temporarily paused.
	boolean paused = false;
	int lastPreferredSimTicks = 0; //the last preferred sim ticks before the sim was paused.
	
	int currentGeneration = 1;
	
	public void run() throws Exception {
		//setting up the display
		this.display = new FishExperimentDisplay(this);
		
		//setting up a genetic algorithm which will breed/mutate the generations
		Mutator mutator = new Mutator(0.2, 0.2, 0.2);
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(mutator, selectedGenomes);
		
		
		//setting up an initial generation of genomes
		List<double[]> genomes = new ArrayList<double[]>();
		for (int i = 0; i < genomesPerGeneration; i++) {
			genomes.add(mutator.mutate(new double[genomeLength], 1, 1, 0.5)); //mutating every gene but +/- (1 +/- 0.5)
		}
		
		//while true, put the fish in a simulation and run it. then get a new generation from the genetic algorithm
		//and run it in a new simulation
		while (true) {
			//generating new fish and brains from the set of genomes
			List<Fish> fish = new ArrayList<Fish>();
			for (double[] genome : genomes) {
				BiasNetwork brain = new BiasNetwork(brainTopology);
				brain.setGenome(genome);
				
				fish.add(new Fish(brain, random.nextDouble() * world.width, random.nextDouble() * world.height));
			}
			
			//setting up the world with the fish and clearing the food
			world.fish = fish;
			world.food.clear();
			
			//creating a new simulation
			this.simulation = new FishSimulation(this.world);
		
			//running the fish simulation for a finite number of ticks
			runSimulation();
		
			//getting a new generation of genomes from the genetic algorithm
			genomes = geneticAlgorithm.process(world.fish, genomesPerGeneration);
			currentGeneration++;
		}
		
		
		//setting up a fish brain
//		BiasNetwork network = new BiasNetwork(2, 2, 2);
//		Mutator mutator = new Mutator();
//		
//		double[] mutatedGenome = mutator.mutate(network.getGenome(), 1, 5, 5);
//				
//		System.out.println(Arrays.toString(mutatedGenome));
//		
//		network.setGenome(mutatedGenome);
		
		
//		world.fish.add(new Fish(network, 100, 100));
		
	}

	private void runSimulation() {
		running = true;
		while(this.simulation.getCurrentSimTick() < simTicksPerGeneration && running) {
			//checking if we are due to perform another sim tick
			long currentTime = System.nanoTime();
			if (currentTime - lastSimTick > nanosPerSimTick) {
				lastSimTick = currentTime;
				this.simulation.tick();
			}
			
			currentTime = System.nanoTime();
			if (currentTime - lastFrame > nanosPerFrame) {
				lastFrame = currentTime;
				render();
			}
		}
	}

	
	private void render() {
		currentFrame++;
		
		display.render();
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
		
		if (this.preferredSimTicks <= 0) {
			this.preferredSimTicks = 0;
			this.nanosPerSimTick = Long.MAX_VALUE; //this will have the effect of pausing.
		} else {
			this.nanosPerSimTick = (1000 * 1000 * 1000) / preferredSimTicks;
		}
	}

	public int getPreferredFrameRate() {
		return preferredFrameRate;
	}

	public void setPreferredFrameRate(int preferredFrameRate) {
		this.preferredFrameRate = preferredFrameRate;
		
		if (this.preferredFrameRate <= 0) {
			this.preferredFrameRate = 0;
			this.nanosPerFrame = Long.MAX_VALUE;
		} else {
			this.nanosPerFrame = (1000 * 1000 * 1000)/preferredFrameRate;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		//if we're trying to set it to its current state then do nothing.
		if (paused == this.paused) {
			return;
		}
		
		//if the current state is unpaused, then record the preferredsimtick rate and set it to zero.
		if (!this.paused) {			
			lastPreferredSimTicks = this.getPreferredSimTicks();
			this.setPreferredSimTicks(0);
			this.paused = true;
		} else { //if current state is paused, then set the preferred sim ticks 
			this.setPreferredSimTicks(lastPreferredSimTicks);
			this.paused = false;
		}
		
	}

	public long getCurrentSimTick() {
		return this.simulation.getCurrentSimTick();
	}

	public long getCurrentFrame() {
		return currentFrame;
	}
	
	public int getCurrentGeneration() {
		return currentGeneration;
	}	

}
