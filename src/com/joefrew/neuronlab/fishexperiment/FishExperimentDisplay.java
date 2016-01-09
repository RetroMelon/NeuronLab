package com.joefrew.neuronlab.fishexperiment;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


/**
 * The fish experiment display class sets up and handles the entire user interface for the fish experiment. It also includes features such as clicking on fish to view more information about them.
 * 
 * @author joe
 *
 */
public class FishExperimentDisplay implements KeyListener, MouseListener {
	
	FishExperiment experiment;
	
	JFrame window;
	Canvas canvas;
	Canvas inspectorCanvas;
	BufferStrategy buffer;
	BufferStrategy inspectorBuffer;
	
	Fish selectedFish = null;
	
	int controlPanelWidth = 200;
	int inspectorHeight = 250;
	
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
		
		//we need to check that the selected fish is still actually in the world. if it is not, set it to null
		if (selectedFish != null && !world.fish.contains(selectedFish)) {
			selectedFish = null;
		}
		
		renderMain();
		renderInspector();
		
	}
	
	
	private void renderMain() {
		World world = this.experiment.getWorld();
		
		//setting up the graphics object to be passed to the drawables.
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
	    g.clearRect(0, 0, world.width, world.height);
	    
	    //iterating over all of the food and passing the graphics object to them
	    for (Food food : world.food) {
	    	food.render(g);
	    }
	    
	    //iterating over all of the fish and passing the graphics object to them
	    for (Fish fish : world.fish) {
	    	if (fish == selectedFish) {	    		
	    		fish.render(g, 1);
	    	} else {
	    		fish.render(g);
	    	}
	    }
	    
	    //if our selected fish is still in the world, draw a circle around it.
	    if (selectedFish != null) {
	    	g.setColor(Color.GREEN);
	    	int size = 20;
	    	int x = (int) selectedFish.getX();
	    	int y = (int) selectedFish.getY();
	    	g.drawOval(x - size, y - size, size * 2, size * 2);
	    }
	    
	    
	    //drawing the text
	    g.setColor(Color.WHITE);

	    //current tick and generation
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
	
	private void renderInspector() {
		World world = this.experiment.getWorld();
		Color textColor = Color.WHITE;
		int textMargin = 10;
		
		Graphics2D g = (Graphics2D) inspectorBuffer.getDrawGraphics();
	    g.clearRect(0, 0, world.width, world.height);
		
	    
	    if (this.selectedFish == null) {
	    	g.setColor(textColor);
	    	g.drawString("No fish selected.", textMargin, 15);
	    } else {
	    	//drawing some basic text about the fish
	    	int lineOffset = 15;
	    	int currentLine = lineOffset;
	    	g.setColor(textColor);
	    	
	    	g.drawString("Food Eaten: " + selectedFish.getFoodEaten(), textMargin,  currentLine);
	    	currentLine += lineOffset;
	    	
	    	g.drawString("Brain Topology: " + Arrays.toString(selectedFish.getBrain().getTopology()), textMargin,  currentLine);
	    	currentLine += lineOffset;	    	
	    	
	    	g.drawString("Genome: ", textMargin,  currentLine);
	    	currentLine += lineOffset;	
	    	
	    	//rendering the genome as a series of black and white boxes
	    	//getting the genome, finding the highest and smallest, and then scaling the whiteness factor appropriately
	    	int margin = 10;
	    	renderGenome(g, selectedFish.getGenome(), textMargin, currentLine-10, controlPanelWidth-textMargin*2, 10);
	    	currentLine += lineOffset;
	    	
	    	
	    	//render the neuron values in the brain
	    	
	    }
	    
	    
	    
	    
	    g.dispose();
	    inspectorBuffer.show();
	}
	
	private void renderGenome(Graphics2D g, double[] genome, int startX, int startY, int maxX, int maxY) {
    	double highest = Double.MIN_VALUE;
    	double smallest = Double.MAX_VALUE;
    	for (double gene : genome) {
    		if (gene > highest) {highest = gene;}
    		if (gene < smallest) {smallest = gene;}
    	}
    	
    	double range = highest - smallest;
    	int[] colors = new int[genome.length];
    	for (int i = 0; i < genome.length; i++) {
    		double gene = genome[i];
    		colors[i] = (int)(255 * ((gene - smallest)/range));
    	}
    	
    	//number of pixels margin for the genome
    	int pixelsPerColor = (maxX)/colors.length;
    	int blockHeight = (pixelsPerColor >= maxY || pixelsPerColor <= 5?maxY:pixelsPerColor);
    	for (int i = 0; i < colors.length; i++) {
    		int color = colors[i];
    		g.setColor(new Color(color, color, color));
    		g.fillRect(startX + i * pixelsPerColor, startY, pixelsPerColor, blockHeight);
    	}
	}
	
	
	private void setupWindow() {
		//if there is currently a window, get rid of it so we can set up a new one
		if (this.window != null) {
			this.window.setVisible(false);
			this.window.dispose();
		}
		
		World world = this.experiment.getWorld();
		
		window = new JFrame();
		window.setPreferredSize(new Dimension(world.width + controlPanelWidth, world.height));
		window.setTitle("Fishies");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//getting the panel inside the window so we can add the canvas to it.
		JPanel panel = (JPanel) window.getContentPane();
		//panel.setLayout(null); //we want the default which is a border layout
		
		
		//setting up the canvas and adding it to the window
		canvas = new Canvas();
		canvas.setBackground(Color.DARK_GRAY);
		canvas.setBounds(0, 0, world.width, world.height);
		canvas.setIgnoreRepaint(true);
		
		//adding this as a key and mouse lostener to the canvas
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		
		panel.add(canvas, BorderLayout.LINE_START);
		
		
		//Setting up the panel which will contain a variety of controls.
		JPanel controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(controlPanelWidth, world.height));
		controlPanel.setBorder(new EmptyBorder(-5, 0, 0, 0)); //getting rid of the border on the control panel
		
		panel.add(controlPanel, BorderLayout.LINE_END);
		
		//setting up the inspector canvas in the control panel 
		inspectorCanvas = new Canvas();
		inspectorCanvas.setBackground(Color.BLACK);
		inspectorCanvas.setBounds(0, 0, controlPanelWidth, inspectorHeight);
		inspectorCanvas.setIgnoreRepaint(true);
		
		controlPanel.add(inspectorCanvas);
		
		//packing up the window content and setting it to visible
		window.setResizable(false);
	    window.pack();
		
		window.setVisible(true);
		
		//setting up buffer strategy for the canvas and focussing on it
		canvas.createBufferStrategy(2);
	  	buffer = canvas.getBufferStrategy();
	  	
		inspectorCanvas.createBufferStrategy(2);
	  	inspectorBuffer = inspectorCanvas.getBufferStrategy();
	      
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


	public void mouseClicked(MouseEvent e) {
		
	}


	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			//checking each fish in the world for a collision
			boolean collision = false;
			
			for (Fish fish : experiment.getWorld().fish) {
				if (fish.pointCollision(e.getX(), e.getY())) {
					collision = true;
					this.selectedFish = fish; 
					break;
				}
			}
			
			//no fish collided so setting selectedFish to null
			if (!collision) {				
				selectedFish = null;
			}
		}
	}


	public void mouseReleased(MouseEvent e) {
		
	}


	public void mouseEntered(MouseEvent e) {
		
	}


	public void mouseExited(MouseEvent e) {
		
	}
	

}
