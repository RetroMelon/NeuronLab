package com.joefrew.neuronlab.fishexperiment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The fish experiment display class sets up and handles the entire user interface for the fish experiment.
 * @author joe
 *
 */
public class FishExperimentDisplay {
	
	int width;
	int height;
	
	JFrame window;
	Canvas canvas;
	BufferStrategy buffer;
	
	public FishExperimentDisplay (int width, int height) {
		this.width = width;
		this.height = height;
		
		this.setupWindow();
	}
	
	/**
	 * Render updates the display showing the fishes and food. It is blocking, and uses the world class and the datastructures inside of it.
	 * @param world
	 */
	public void render(World world, FishExperiment experiment) {
		//setting up the graphics object to be passed to the drawables.
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		g.setColor(Color.BLACK);
	    g.clearRect(0, 0, world.width, world.height);
	    
	    //iterating over all of the food and passing the graphics object to them
	    for (Food food : world.food) {
	    	food.render(g);
	    }
	    
	    //iterating over all of the fish and passing the graphics object to them
	    for (Fish fish : world.fish) {
	    	fish.render(g);
	    }
	    
	    //drawing the current tick
	    g.drawString("" + experiment.getCurrentSimTick(), 10, 10);
	    
	    //disposing of the graphics objects and showing the buffer on screen.
	    g.dispose();
	    buffer.show();
	}
	
	
	private void setupWindow() {
		window = new JFrame();
		window.setPreferredSize(new Dimension(width, height));
		window.setTitle("Fishies");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//getting the panel inside the window so we can add the canvas to it.
		JPanel panel = (JPanel) window.getContentPane();
		panel.setLayout(null);
		
		//setting up the canvas and adding it to the window
		canvas = new Canvas();
		canvas.setBounds(0, 0, width, height);
		canvas.setIgnoreRepaint(true);
		
		panel.add(canvas);
		
		
		//packing up the window content and setting it to visible
	    window.pack();
	    window.setResizable(false);
		
		window.setVisible(true);
		
		//setting up buffer strategy for the canvas and focussing on it
		canvas.createBufferStrategy(2);
      	buffer = canvas.getBufferStrategy();
	      
	    canvas.requestFocus();
	}
	

}
