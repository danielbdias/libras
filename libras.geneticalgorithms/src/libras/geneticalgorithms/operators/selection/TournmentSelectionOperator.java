package libras.geneticalgorithms.operators.selection;

import java.util.Random;

import libras.geneticalgorithms.chromossomes.Chromossome;


public class TournmentSelectionOperator extends SelectionOperator {

	@Override
	public Chromossome[] select(Chromossome[] population,
			double[] populationFitness, int parentsToChoose) {
		Chromossome[] result = new Chromossome[parentsToChoose];
		
		for (int i = 0; i < result.length; i++) {
			Random rnd = new Random();
			
			int firstParentIndex = rnd.nextInt(population.length - 1);
			int secondParentIndex = rnd.nextInt(population.length - 1);
			
			while (firstParentIndex == secondParentIndex)
				secondParentIndex = rnd.nextInt(population.length - 1);
			
			if (populationFitness[firstParentIndex] > populationFitness[secondParentIndex])
				result[i] = population[firstParentIndex];
			else
				result[i] = population[secondParentIndex];
		}
		
		return result;
	}
}