package libras.geneticalgorithms.operators.crossover;

import libras.geneticalgorithms.chromossomes.*;
import libras.utils.Pair;

public class OnePointCrossoverOperator extends CrossoverOperator {
	@Override
	public Pair<Chromossome, Chromossome> generatePair(Chromossome firstParent, Chromossome secondParent) {
		try {
			Chromossome firstChild = (Chromossome) firstParent.clone();
			Chromossome secondChild = (Chromossome) secondParent.clone();

			int crossOverPoint = (int) (Math.random()*firstParent.allelesCount());
			
			for (int j = 0; j < crossOverPoint; j++) {
				Object allel = firstChild.getAllel(j);

				//Swap alleles
				firstChild.setAllel(j, secondChild.getAllel(j));
				secondChild.setAllel(j,allel);
			}
			
			return new Pair<Chromossome, Chromossome>(firstChild, secondChild);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
