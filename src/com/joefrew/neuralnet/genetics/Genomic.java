package com.joefrew.neuralnet.genetics;

/**
 * Something which is genomic has a genome which can be retrieved and set. A
 * Genomic object does not guarantee that this genome is a copy, so care should
 * be taken not to make any unwarranted changes to genes.
 * 
 * @author joe
 * 
 */
public interface Genomic {
	
	public double[] getGenome();
	
	public void setGenome(double[] genome);

}
