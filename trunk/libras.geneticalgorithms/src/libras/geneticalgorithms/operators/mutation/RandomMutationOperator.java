package libras.geneticalgorithms.operators.mutation;

import libras.geneticalgorithms.chromossomes.Chromossome;
import libras.utils.RandomUtil;

public class RandomMutationOperator extends MutationOperator {
	
	public RandomMutationOperator(double mutationChance) {
		this.mutationChance = mutationChance;
	}
	
	private double mutationChance = 0.0;
	
	public double getMutationChance() {
		return this.mutationChance;
	}
	
	@Override
	public void mutatePopulation(Chromossome[] population, int currentGeneration) {
		for(int i = 0; i < population.length; i++){
			double mutation = Math.random();
			
			if (mutation < this.mutationChance){
				Chromossome c = population[i];
				
				int allelToMutate = (int) RandomUtil.randomInRange(0, c.allelesCount()-1); 
				
				c.mutateAllel(allelToMutate);
			}
		}
	}

}
