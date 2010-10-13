package libras.geneticalgorithms;

import java.security.InvalidParameterException;

import libras.geneticalgorithms.chromossomes.*;
import libras.geneticalgorithms.factories.ChromossomeFactory;
import libras.geneticalgorithms.operators.crossover.CrossoverOperator;
import libras.geneticalgorithms.operators.mutation.MutationOperator;
import libras.geneticalgorithms.operators.selection.SelectionOperator;
import libras.utils.Pair;


public class GeneticAlgorithm {
	public GeneticAlgorithm(int populationSize,  
			ChromossomeFactory factory, MutationOperator mutationOperator, 
			CrossoverOperator crossoverOperator, SelectionOperator selectionOperator) {
		
		if (populationSize % 2 == 1)
			throw new InvalidParameterException("The populationSize parameter must be even.");
		
		this.population = new Chromossome[populationSize];
		this.populationFitness = new double[populationSize];
		
		this.crossoverOperator = crossoverOperator;
		this.mutationOperator = mutationOperator;
		this.selectionOperator = selectionOperator;
		
		this.prototypeFactory = factory;
	}
	
	private Chromossome[] population = null;
	
	private double[] populationFitness = null;

	private CrossoverOperator crossoverOperator = null;
	
	private MutationOperator mutationOperator = null;
	
	private SelectionOperator selectionOperator = null;
	
	private ChromossomeFactory prototypeFactory = null;
	
	public Chromossome execute(int generations) throws Exception {
		Chromossome prototype = this.prototypeFactory.create();
		this.initializePopulation(prototype);

		Chromossome bestChromossome = null;
		
		for (int i = 0; i < generations; i++) {	
			this.evaluatePopulation();
			
			bestChromossome = this.selectBestChromossome();

			if (bestChromossome.matchRequirements())
				break;
			
			Chromossome[] parents = this.selectParents();
            Chromossome[] children = this.generateChildenFromParents(parents, i);
			
			this.renewPopulation(parents, children);
		}

		return bestChromossome;
	}

	
	protected void initializePopulation(Chromossome prototype)
	{
		for (int i = 0; i < this.getPopulation().length; i++) {			
			this.getPopulation()[i] = prototype.randomClone();
		}
	}
	
	protected void evaluatePopulation() {
		for (int i = 0; i < this.population.length;i++) {
			this.populationFitness[i] = this.population[i].fitness();
		}
	}
	
	protected Chromossome selectBestChromossome() {
		int maxFitnessIndex = 0;

		for (int i = 1; i < this.populationFitness.length; i++) {
			if (this.populationFitness[i] > this.populationFitness[maxFitnessIndex]) {
				maxFitnessIndex = i;
			}
		}

		return this.population[maxFitnessIndex];
	}
	
	protected void renewPopulation(Chromossome[] parents, Chromossome[] children) {
		int[] parentsToDie = new int[children.length];
		
		for (int i = 0; i < parentsToDie.length; i++) {
			double minorFitness = this.populationFitness[0];
			int minorFitnessIndex = 0;
			
			for (int j = 0; j < this.populationFitness.length; j++){
				if (this.populationFitness[j] < minorFitness){
					boolean alreadySelected = false;
					
					for (int k = 0; k < i; k++) {
						if (j == parentsToDie[k]) {
							alreadySelected = true;
							break;
						}
					}
					
					if (!alreadySelected) {
						minorFitnessIndex = j;
						minorFitness = this.populationFitness[j];
					}
				}
			}
			
			parentsToDie[i] = minorFitnessIndex;
		}
		
		for (int i = 0; i < parentsToDie.length; i++) {
			//"Kill" the parent, replacing it by one child
			this.population[parentsToDie[i]] = children[i];
		}
	}

	protected Chromossome[] selectParents() {
		int parentsToChoose = this.population.length / 2;
		if (parentsToChoose % 2 == 1) parentsToChoose++;
		
		return this.selectionOperator.select(
				this.population, this.populationFitness, parentsToChoose);
	}
	
	protected Chromossome[] generateChildenFromParents(Chromossome[] parents, int currentGeneration) {
		Chromossome[] children = new Chromossome[parents.length];

		for (int i = 0; i < parents.length; i+=2) {
			Chromossome firstParent = parents[i];
			Chromossome secondParent = parents[i+1];
			
			Pair<Chromossome, Chromossome> newChromossomes = 
				this.crossoverOperator.generatePair(firstParent, secondParent);
			
			children[i] = newChromossomes.getFirstItem();
			children[i+1] = newChromossomes.getSecondItem();
		}
		
		this.mutationOperator.mutatePopulation(children, currentGeneration);

		return children;
	}

	protected Chromossome[] getPopulation() {
		return this.population;
	}
}
