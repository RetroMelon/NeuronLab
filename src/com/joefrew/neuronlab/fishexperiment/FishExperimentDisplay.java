package com.joefrew.neuronlab.fishexperiment;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


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
	
	JSpinner ticksPerSecondField;
	JSpinner framesPerSecondField;
	
	Fish selectedFish = null;
	
	int controlPanelWidth = 200;
	int inspectorHeight = 200;
	
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
		Color textColor = Color.BLACK;
		int textMargin = 10;
		
		Graphics2D g = (Graphics2D) inspectorBuffer.getDrawGraphics();
	    g.clearRect(0, 0, world.width, world.height);
		
	    
	    if (this.selectedFish == null) {
	    	g.setColor(textColor);
	    	g.drawString("Click a fish to inspect.", textMargin, 15);
	    } else {
	    	//drawing some basic text about the fish
	    	int lineOffset = 15;
	    	int currentLine = lineOffset;
	    	g.setColor(textColor);
	    	
	    	g.drawString("Score: " + selectedFish.getScore(), textMargin,  currentLine);
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
	    	
	    	List<double[]> fishNeurons = selectedFish.getBrain().getNeuronValues();
	    	//render the neuron values in the brain
	    	renderNeurons(g, fishNeurons.toArray(new double[fishNeurons.size()][]), textMargin, currentLine, controlPanelWidth-textMargin*2, 100);
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
	
	private void renderNeurons(Graphics2D g, double[][] neurons, int startX, int startY, int maxX, int maxY) {
    	double highest = Double.MIN_VALUE;
    	double smallest = Double.MAX_VALUE;
    	int largestLayer = 1;
    	for (double[] layer : neurons) {
    		for (double value : layer) {    			
    			if (value > highest) {highest = value;}
    			if (value < smallest) {smallest = value;}
    		}
    		if (layer.length > largestLayer) {
    			largestLayer = layer.length;
    		}
    	}
    	
    	double range = highest - smallest;
    	int[][] colors = new int[neurons.length][];
    	//iterating over each layer. working out
    	for (int i = 0; i < neurons.length; i++) {
    		double[] layer = neurons[i];
    		int[] colorLayer = new int[layer.length];
    		for (int j = 0; j < layer.length; j++) {
    			double value = layer[j];
    			colorLayer[j] = (int)(255 * ((value - smallest)/range));
    		}
    		colors[i] = colorLayer;
    	}
    	
    	//number of pixels margin for the genome
    	int blockWidth = (maxX)/largestLayer;
    	int blockHeight = maxY/colors.length;
    	for (int i = 0; i < colors.length; i++) {
    		int[] colorLayer = colors[i];
    		int layerStartX = (maxX/2) - (blockWidth * colorLayer.length)/2;
    		for (int j = 0; j < colorLayer.length; j++) {
    			int color = colorLayer[j];
        		g.setColor(new Color(color, color, color));
        		g.fillRect(startX + layerStartX + j * blockWidth, startY + blockHeight * i, blockWidth, blockHeight);
    		}
    		
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
		controlPanel.setBorder(new EmptyBorder(0, 0, 0, 0)); //getting rid of the border on the control panel
		
		panel.add(controlPanel, BorderLayout.LINE_END);
		
		//setting up the inspector canvas in the control panel 
		inspectorCanvas = new Canvas();
		//inspectorCanvas.setBackground(Color.BLACK);
		inspectorCanvas.setBounds(0, 0, controlPanelWidth, inspectorHeight);
		inspectorCanvas.setIgnoreRepaint(true);
		
		controlPanel.add(inspectorCanvas);
		
		//setting up the field for ticks per gen
		SpinnerModel ticksPerGenModel =
		        new SpinnerNumberModel(experiment.getSimTicksPerGeneration(), 100, Integer.MAX_VALUE, 100);
		final JSpinner ticksPerGenField = new JSpinner(ticksPerGenModel);
		ticksPerGenField.getEditor().getComponent(0).addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
            	if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            		Integer val = (Integer)ticksPerGenField.getValue();
            		experiment.setSimTicksPerGeneration(val);            		
            	}
            }
        });
		
		ticksPerGenField.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	Integer val = (Integer)ticksPerGenField.getValue();
        		experiment.setSimTicksPerGeneration(val);  
	        }
	    });
		
		JLabel ticksLabel = new JLabel("Ticks Per Generation:");
		ticksLabel.setHorizontalAlignment(JLabel.LEFT);
		ticksLabel.setLabelFor(ticksPerGenField);
		
		controlPanel.add(ticksLabel);
		controlPanel.add(ticksPerGenField);
		
		//setting up the ticks per second field
		final JLabel ticksPerSecondLabel = new JLabel("Tick Rate:     ");
		SpinnerModel ticksModel =
		        new SpinnerNumberModel(experiment.getPreferredSimTicks(), 0, 100000, 50);
		final JSpinner finalTicksPerSecField = new JSpinner(ticksModel);
		finalTicksPerSecField.getEditor().getComponent(0).addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
            	if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            		Integer val = (Integer)finalTicksPerSecField.getValue();
            		experiment.setPreferredSimTicks(val);    
            		
            		ticksPerSecondLabel.setText("Tick Rate:     ");
            	} else {
            		ticksPerSecondLabel.setText("Tick Rate:*    ");
            	}
            }
        });

		finalTicksPerSecField.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	Integer val = (Integer)finalTicksPerSecField.getValue();
        		experiment.setPreferredSimTicks(val);   
        		
        		ticksPerSecondLabel.setText("Tick Rate:     ");
	        }
	    });
		
		ticksPerSecondLabel.setHorizontalAlignment(JLabel.LEFT);
		ticksPerSecondLabel.setLabelFor(finalTicksPerSecField);
		
		controlPanel.add(ticksPerSecondLabel);
		controlPanel.add(finalTicksPerSecField);
		ticksPerSecondField = finalTicksPerSecField;
		
		
		//setting up the frames per second field
		final JLabel framesPerSecondLabel = new JLabel("Frame Rate: ");
		SpinnerModel framesModel =
		        new SpinnerNumberModel(experiment.getPreferredFrameRate(), 0, 100000, 10);
		final JSpinner finalFramesPerSecField = new JSpinner(framesModel);
		finalFramesPerSecField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
						
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					Integer val = (Integer)finalFramesPerSecField.getValue();
					experiment.setPreferredFrameRate(val);
					
					framesPerSecondLabel.setText("Frame Rate: ");
				} else {
					framesPerSecondLabel.setText("Frame Rate:*");
				}
			}
		});
		
		finalFramesPerSecField.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	Integer val = (Integer)finalFramesPerSecField.getValue();
				experiment.setPreferredFrameRate(val); 
				
				framesPerSecondLabel.setText("Frame Rate: ");
	        }
	    });

		framesPerSecondLabel.setHorizontalAlignment(JLabel.LEFT);
		framesPerSecondLabel.setLabelFor(finalFramesPerSecField);

		controlPanel.add(framesPerSecondLabel);
		controlPanel.add(finalFramesPerSecField);
		framesPerSecondField = finalFramesPerSecField;

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
			
		case KeyEvent.VK_SPACE :
			experiment.setPaused(!experiment.isPaused());
			break;
		
		//DECREMENT PREFERRED TICKS/S
		case KeyEvent.VK_N :
			int simDecTicks = experiment.getPreferredSimTicks() - tickRateChangeFactor;
			experiment.setPreferredSimTicks(simDecTicks);
			ticksPerSecondField.setValue(Integer.valueOf(simDecTicks));
			break;
		
		//INCREMENT PREFERRED TICKS/s
		case KeyEvent.VK_M :
			int simIncTicks = experiment.getPreferredSimTicks() + tickRateChangeFactor;
			experiment.setPreferredSimTicks(simIncTicks);
			ticksPerSecondField.setValue(Integer.valueOf(simIncTicks));
			break;
			
		//DECREMENT PREFERRED FRAMES/S
		case KeyEvent.VK_K :
			int frames = experiment.getPreferredFrameRate() - frameRateChangeFactor;
			experiment.setPreferredFrameRate(frames);
			framesPerSecondField.setValue(Integer.valueOf(frames));
			break;
		
		//INCREMENT PREFERRED TICKS/S
		case KeyEvent.VK_L :
			int framesInc = experiment.getPreferredFrameRate() + frameRateChangeFactor;
			experiment.setPreferredFrameRate(framesInc);
			framesPerSecondField.setValue(Integer.valueOf(framesInc));
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
