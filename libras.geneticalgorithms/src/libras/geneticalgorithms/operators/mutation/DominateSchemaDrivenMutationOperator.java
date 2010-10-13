package libras.geneticalgorithms.operators.mutation;

import libras.geneticalgorithms.chromossomes.Chromossome;

public class DominateSchemaDrivenMutationOperator extends MutationOperator {

	public DominateSchemaDrivenMutationOperator(int generationToActivate) {
		this.generationToActivate = generationToActivate;
	}
	
	private int generationToActivate = 0;
	
	@Override
	public void mutatePopulation(Chromossome[] population, int currentGeneration) {
		if (this.generationToActivate > currentGeneration) return; 

		boolean[] dominantSchema = null;
		
		for (int i = 0; i < population.length; i+=2) {
			Chromossome first = population[i];
			Chromossome second = population[i+1];
			
			boolean[] comparison = first.allelComparison(second);
			
			if (comparison != null) {
				if (dominantSchema == null) {
					dominantSchema = comparison;
				} else {
					dominantSchema = this.executeXNor(dominantSchema, comparison);
				} 
			}
		}
		
		if (dominantSchema != null) {
			for (int i = 0; i < population.length; i++) {
				for (int j = 0; j < dominantSchema.length; j++) {
					if (dominantSchema[j])
						population[i].mutateAllel(j);
				}
			}
		}
	}
	
	private boolean[] executeXNor(boolean[] first, boolean[] second) {
		if (first.length == second.length) {
			boolean[] result = new boolean[first.length];
			
			for (int i = 0; i < result.length; i++) {
				result[i] = !(first[i] ^ second[i]);
			}
			
			return result;
		}
		
		return null;
	}
}
