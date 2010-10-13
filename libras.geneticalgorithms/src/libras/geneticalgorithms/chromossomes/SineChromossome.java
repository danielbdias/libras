package libras.geneticalgorithms.chromossomes;

import java.util.Random;

public class SineChromossome extends Chromossome {

	public SineChromossome() {
		super(new Double[2]);
		
		this.setAllel(0, new Random().nextInt() * new Random().nextDouble());
		this.setAllel(1, new Random().nextInt() * new Random().nextDouble());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		SineChromossome chromossome = new SineChromossome();
		
		for (int i = 0; i < this.allelesCount(); i++) 
			chromossome.setAllel(i, this.getAllel(i));
		
		return chromossome;
	}

	@Override
	public double fitness() {
		double x = (Double) this.getAllel(0);
		double y = (Double) this.getAllel(1);
		
		return Math.sin(x*y);
	}

	@Override
	public boolean matchRequirements() {
		return Math.abs(this.fitness()) == 1.0;
	}

	@Override
	public void mutateAllel(int allelToMutate) {
		this.setAllel(allelToMutate, new Random().nextInt() * new Random().nextDouble());
	}
	
	@Override
	public Chromossome randomClone() {
		return new SineChromossome();
	}

	public Double getX() {
		return (Double) this.getAllel(0);
	}
	
	public Double getY() {
		return (Double) this.getAllel(1);
	}
	
	@Override
	public boolean[] allelComparison(Chromossome secondChromossome) {
		if (secondChromossome instanceof SineChromossome) {
			boolean[] result = new boolean[this.allelesCount()];
			
			for (int i = 0; i < result.length; i++) {
				result[i] = this.getAllel(i).equals(secondChromossome.getAllel(i));
			}
		}
		
		return null;
	}
}
