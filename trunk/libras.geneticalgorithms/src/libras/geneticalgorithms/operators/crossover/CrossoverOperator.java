package libras.geneticalgorithms.operators.crossover;

import libras.geneticalgorithms.chromossomes.Chromossome;
import libras.utils.Pair;

public abstract class CrossoverOperator {
	public abstract Pair<Chromossome, Chromossome> generatePair(Chromossome firstParent, Chromossome secondParent);
}
