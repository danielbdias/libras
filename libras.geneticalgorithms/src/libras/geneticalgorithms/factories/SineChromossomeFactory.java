package libras.geneticalgorithms.factories;

import libras.geneticalgorithms.chromossomes.Chromossome;
import libras.geneticalgorithms.chromossomes.SineChromossome;

public class SineChromossomeFactory extends ChromossomeFactory {

	@Override
	public Chromossome create() {
		return new SineChromossome(); 
	}

}
