package com.joefrew.neuronlab.fishexperiment;

/**
 * A point collidable is an object which can detect whether a point is contained within its bounds, and hence, if it is colliding with it.
 * @author joe
 *
 */
public interface PointCollidable {
	
	public boolean pointCollision(double x, double y);

}
