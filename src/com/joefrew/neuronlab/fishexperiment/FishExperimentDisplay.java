package com.joefrew.neuronlab.fishexperiment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The fish experiment display class sets up and handles the entire user interface for the fish experiment. It also includes features such as clicking on fish to view more information about them.
 * 
 * @author joe
 *
 */
public class FishExperimentDisplay implements KeyListener {
	
	FishExperiment experiment;
	
	JFrame window;
	Canvas canvas;
	BufferStrategy buffer;
	
	public FishExperimentDisplay (FishExperiment experiment) {
		this.setExperiment(experiment);
		this.setupWindow();
	}
	
	
	public void setExperiment(FishExperiment experiment) {
//		int width = -1;
//		int height = -1;
//		if (this.experiment != null) {
//			width = this.experiment.getWorld().width;
//			height = this.experiment.getWorld().height;
//		}
		
		this.experiment = experiment;
		
		//if the experiment has changed width/height then we need to set up the ui again
//		if ((width != -1 && height != -1) && (this.experiment.getWorld().width != width || this.experiment.getWorld().height != height)) {
//			this.experiment = experiment;
//			this.setupWindow();
//		}
	}
	
	/**
	 * Render updates the display showing the fishes and food. It is blocking, and uses the world class and the datastructures inside of it.
	 * @param world
	 */
	public void render() {
		World world = this.experiment.getWorld();
		
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
	    
	    g.setColor(Color.WHITE);
	    //drawing the current tick and generation
	    g.drawString("" + experiment.getCurrentSimTick(), 10, 10);
	    g.drawString("Generation: " + experiment.getCurrentGeneration(), world.width-150, 10);
	    
	    //drawing current target tick and frame rate
	    if (experiment.isPaused()) {
	    	g.setColor(Color.YELLOW);
	    	g.drawString("Paused", 10, world.height-10);
	    }
	    else {
	    	g.drawString("Target Ticks/s: " + experiment.getPreferredSimTicks() + "\t       Target Frames/s: " + experiment.getPreferredFrameRate(), 10, world.height-10);
	    }
	    
	    //disposing of the graphics objects and showing the buffer on screen.
	    g.dispose();
	    buffer.show();
	}
	
	
	private void setupWindow() {
		//if there is currently a window, get rid of it so we can set up a new one
		if (this.window != null) {
			this.window.setVisible(false);
			this.window.dispose();
		}
		
		World world = this.experiment.getWorld();
		
		window = new JFrame();
		window.setPreferredSize(new Dimension(world.width, world.height));
		window.setTitle("Fishies");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//getting the panel inside the window so we can add the canvas to it.
		JPanel panel = (JPanel) window.getContentPane();
		panel.setLayout(null);
		
		//setting up the canvas and adding it to the window
		canvas = new Canvas();
		canvas.setBackground(Color.DARK_GRAY);
		canvas.setBounds(0, 0, world.width, world.height);
		canvas.setIgnoreRepaint(true);
		
		//adding this as a key listener to the canvas.
		canvas.addKeyListener(this);
		
		panel.add(canvas);
		
		
		//packing up the window content and setting it to visible
		window.setResizable(false);
	    window.pack();
		
		window.setVisible(true);
		
		//setting up buffer strategy for the canvas and focussing on it
		canvas.createBufferStrategy(2);
      	buffer = canvas.getBufferStrategy();
	      
	    canvas.requestFocus();
	}

	public void keyTyped(KeyEvent e) {
	
	}

	
	boolean shiftPressed = false;
	
	public void keyPressed(KeyEvent e) {
		//the rate of change of the ticks/frames when pres
		int tickRateChangeFactor = 10;
		int frameRateChangeFactor = 1;
		
		if (shiftPressed) {
			tickRateChangeFactor = 500;
			frameRateChangeFactor = 10;
		}
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_P :
			experiment.setPaused(!experiment.isPaused());
			break;
		
		//DECREMENT PREFERRED TICKS/S
		case KeyEvent.VK_N :
			int simTicks = experiment.getPreferredSimTicks() - tickRateChangeFactor;
			experiment.setPreferredSimTicks(simTicks);
			break;
		
		//INCREMENT PREFERRED TICKS/s
		case KeyEvent.VK_M :
			experiment.setPreferredSimTicks(experiment.getPreferredSimTicks() + tickRateChangeFactor);
			break;
			
		//DECREMENT PREFERRED FRAMES/S
		case KeyEvent.VK_K :
			int frames = experiment.getPreferredFrameRate() - frameRateChangeFactor;
			experiment.setPreferredFrameRate(frames);
			break;
		
		//INCREMENT PREFERRED TICKS/S
		case KeyEvent.VK_L :
			experiment.setPreferredFrameRate(experiment.getPreferredFrameRate() + frameRateChangeFactor);
			break;
		
		//TOGGLE SHIFT (increases and decreases change rates)
		case KeyEvent.VK_SHIFT :
			this.shiftPressed = true;
			break;
		}	
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SHIFT :
			this.shiftPressed = false;
			break;
		}	
	}
	

}
