package libras.geneticalgorithms.factories;

import libras.geneticalgorithms.chromossomes.Chromossome;
import libras.geneticalgorithms.chromossomes.SquareChromossome;

public class SquareChromossomeFactory extends ChromossomeFactory {

	public SquareChromossomeFactory(double maximumValue) {
		this.maximumValue = maximumValue;
	}
	
	private double maximumValue = 0.0;
	
	@Override
	public Chromossome create() {
		return new SquareChromossome(this.maximumValue);
	}

}
