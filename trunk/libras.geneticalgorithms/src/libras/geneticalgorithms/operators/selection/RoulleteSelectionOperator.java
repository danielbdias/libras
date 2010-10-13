package libras.geneticalgorithms.operators.selection;

import libras.geneticalgorithms.chromossomes.Chromossome;

public class RoulleteSelectionOperator extends SelectionOperator {
	@Override
	public Chromossome[] select(Chromossome[] population, double[] populationFitness, int parentsToChoose) {
		Chromossome[] parents = new Chromossome[parentsToChoose];

		double fitnessSum = 0.0;
		for (int i = 0; i < populationFitness.length; i++) 
			fitnessSum += populationFitness[i];

		for (int i = 0; i < parents.length; i++) {
			int index = this.chooseParent(populationFitness, fitnessSum);	
			parents[i] = population[index];
		}

		return parents;
	}
	
	private int chooseParent(double[] populationFitness, double fitnessSum) {
		double limit = Math.random() * fitnessSum;
		double fitnessCount = 0.0; 
		int i = 0;

		for (i = 0; (i < populationFitness.length && fitnessCount > limit); i++) {
			fitnessCount += populationFitness[i];
		}
		
		if (i != 0) --i;
		
		return i;
	}
}
