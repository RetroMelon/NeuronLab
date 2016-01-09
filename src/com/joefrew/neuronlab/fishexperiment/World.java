package com.joefrew.neuronlab.fishexperiment;

import java.util.ArrayList;
import java.util.List;

public class World {
	
	public List<Fish> fish = new ArrayList<Fish>();
	public List<Food> food = new ArrayList<Food>();
	
	public int width = 640;
	public int height = 480;
	
	public World() {
		
	}
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
