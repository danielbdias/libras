package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import libras.utils.Pair;

public class NormalizeRepresentationBySpaceChainAction extends
		ProcessRepresentationChainAction {

	public NormalizeRepresentationBySpaceChainAction(File representationFile, 
			File processedRepresentationFile, int representationSize) {
		super(representationFile, processedRepresentationFile, representationSize);
	}
	
	@Override
	protected String processLine(String[] parsedLine) {
		List<Double[]> representationList = this.getRepresentationFromParsedLine(parsedLine);
		
		this.normalizeRepresentationListBySpace(representationList);
		
		int classNumber = Integer.parseInt(parsedLine[parsedLine.length-1]);
			
		return this.codifyLine(representationList, classNumber);
	}

	/**
	 * Returns a normalized centroid list.
	 */
	protected void normalizeRepresentationListBySpace(List<Double[]> representationList)
	{
		List<Pair<Double, Double>> normalizationParameters =
			this.getNormalizationParameters(representationList);

		for (Double[] representation : representationList)
		{
			for (int i = 0; i < representation.length; i++) {
				Pair<Double, Double> normalizationParameter = normalizationParameters.get(i);
				
				double min = normalizationParameter.getFirstElement();
				double max = normalizationParameter.getSecondElement();
				double value = representation[i];
				
				representation[i] = (value - min) / max;
			}
		}
	}
	
	private List<Pair<Double, Double>> getNormalizationParameters(List<Double[]> representationList)
	{
		List<Pair<Double, Double>> normalizationParameters = new ArrayList<Pair<Double,Double>>();
		
		for (int dimension = 0; dimension < this.getRepresentationSize(); dimension++) {
			Double[] values = new Double[representationList.size()];
			
			for (int j = 0; j < values.length; j++) {
				Double[] representation = representationList.get(j);
				
				values[j] = representation[dimension]; 
			}
			
			Arrays.sort(values);
			
			double minimum = values[0];
			double maximum = values[values.length-1];
			
			if (minimum == maximum)
				maximum = 1.0;
			else
				maximum = maximum - minimum;
			
			normalizationParameters.add(new Pair<Double, Double>(minimum, maximum));
		}
		
		return normalizationParameters;
	}
}
