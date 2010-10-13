package libras.geneticalgorithms.operators.selection;

import libras.geneticalgorithms.chromossomes.Chromossome;

public abstract class SelectionOperator {
	public abstract Chromossome[] select(Chromossome[] population, double[] populationFitness, int parentsToChoose);
}
