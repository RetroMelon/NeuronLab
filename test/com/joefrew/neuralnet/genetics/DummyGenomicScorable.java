package com.joefrew.neuralnet.genetics;

public class DummyGenomicScorable implements Genomic, Scorable {
	
	protected double[] genome;
	protected double score;
	
	public DummyGenomicScorable(double[] genome, double score) {
		this.genome = genome;
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	public double[] getGenome() {
		return genome;
	}

	public void setGenome(double[] genome) {
		this.genome = genome;
	}

}
