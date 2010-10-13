package libras.geneticalgorithms.operators.mutation;

import libras.geneticalgorithms.chromossomes.Chromossome;

public abstract class MutationOperator {
	public abstract void mutatePopulation(Chromossome[] population, int currentGeneration);
}
