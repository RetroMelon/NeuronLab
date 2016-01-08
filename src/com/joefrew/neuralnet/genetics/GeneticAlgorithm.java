package com.joefrew.neuralnet.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * A genetic algorithm takes a series of genomes, picks a selected number of
 * top-performing ones, and produces a new generation from them.
 * 
 * It takes a mutator which process the genomes after they've been combined. It produces a list of genomes for the new generation
 * 
 * @author joe
 * 
 */
public class GeneticAlgorithm {

	Combiner combiner;
	Mutator mutator;

	//the number of genomes to select as breeders from each set. 
	int numberToSelect = 4;
	
	public GeneticAlgorithm(Mutator mutator, int numberToSelect) {
		this.combiner = new Combiner();
		this.mutator = mutator;
		this.numberToSelect = numberToSelect;
	}
	
	public <C extends Scorable & Genomic> List<double[]> process(List<C> genomics, int numberToProduce) {
		//selecting the top n items based on score.
		genomics.sort(new Comparator<C>() {

			public int compare(C o1, C o2) {
				if (o1.getScore() > o2.getScore()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		Collections.reverse(genomics);
		
		List<C> successfuls = genomics.subList(0, numberToSelect);
		
		
		List<double[]> newGenomes = new ArrayList<double[]>();
		//iterating over the set and breeding each with oneanother until we have produced the right number.
		while(newGenomes.size() < numberToProduce) {
			for (Genomic genomic: successfuls) {
				for (Genomic genomic2 : successfuls) {
					double[] newGenome = this.combiner.combine(genomic.getGenome(), genomic2.getGenome());
					this.mutator.mutate(newGenome);
					newGenomes.add(newGenome);
					
					if (numberToProduce == newGenomes.size()) {
						return newGenomes;
					}
				}
			}
		}


		return newGenomes;
	}	

}
