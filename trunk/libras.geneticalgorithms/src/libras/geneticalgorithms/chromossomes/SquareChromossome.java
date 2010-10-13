package libras.geneticalgorithms.chromossomes;

import libras.utils.RandomUtil;

public class SquareChromossome extends Chromossome {

	public SquareChromossome(double maximumValue) {
		super(new Double[1]);
		
		this.maximumValue = maximumValue;
		
		double limit = Math.sqrt(this.maximumValue); 
		this.setAllel(0, RandomUtil.randomInRange(-limit, limit));
	}
	
	private double maximumValue = 0.0;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		SquareChromossome chromossome = new SquareChromossome(this.maximumValue);
		chromossome.setAllel(0, this.getAllel(0));
		return chromossome;
	}

	@Override
	public double fitness() {
		double value = (Double) this.getAllel(0);
		return value * value;
//		return (-1) * value * value;
	}

	@Override
	public boolean matchRequirements() {
		return this.fitness() == this.maximumValue;
		//return this.fitness() == 0;
	}

	@Override
	public void mutateAllel(int allelToChange) {
		double limit = Math.sqrt(this.maximumValue); 
		this.setAllel(allelToChange, RandomUtil.randomInRange(-limit, limit));
	}

	@Override
	public Chromossome randomClone() {
		SquareChromossome clone = new SquareChromossome(this.maximumValue);
		
		double limit = Math.sqrt(this.maximumValue); 
		clone.setAllel(0, RandomUtil.randomInRange(-limit, limit));
	
		return clone;
	}
	
	public Double getX() {
		return (Double) this.getAllel(0);
	}
	
	@Override
	public boolean[] allelComparison(Chromossome secondChromossome) {
		if (secondChromossome instanceof SquareChromossome) {
			boolean[] result = new boolean[this.allelesCount()];
			
			for (int i = 0; i < result.length; i++) {
				result[i] = this.getAllel(i).equals(secondChromossome.getAllel(i));
			}
		}
		
		return null;
	}
}
