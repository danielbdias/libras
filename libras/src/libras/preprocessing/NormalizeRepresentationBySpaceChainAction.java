package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import libras.utils.Pair;

public class NormalizeRepresentationBySpaceChainAction extends
		NormalizeRepresentationChainAction {

	public NormalizeRepresentationBySpaceChainAction(File representationFile, 
			File normalizedFile) {
		super(representationFile, normalizedFile);
	}
	
	@Override
	protected String normalizeLine(String[] parsedLine) {
		List<Pair<Double, Double>> centroids = getCoordinatesFromParsedLine(parsedLine);
		
		centroids = this.normalizeCentroidBySpace(centroids);
		
		int classNumber = Integer.parseInt(parsedLine[parsedLine.length-1]);
			
		return this.codifyLine(centroids, classNumber);
	}

	/**
	 * Returns a normalized centroid list.
	 */
	protected List<Pair<Double, Double>> normalizeCentroidBySpace(List<Pair<Double, Double>> centroidList)
	{
		Pair<Pair<Double, Double>, Pair<Double, Double>> normalizationParameters =
			this.getNormalizationParameters(centroidList);
			
		Pair<Double, Double> abcissaNormalizationParameter = normalizationParameters.getFirstElement();
		Pair<Double, Double> ordinateNormalizationParameter = normalizationParameters.getSecondElement();
		
		ArrayList<Pair<Double, Double>> normalizedCentroidList = new ArrayList<Pair<Double,Double>>();

		for (Pair<Double, Double> centroid : centroidList)
		{
			double abcissa = centroid.getFirstElement();
			double ordinate = centroid.getSecondElement();
			
			abcissa = (abcissa - abcissaNormalizationParameter.getFirstElement()) / abcissaNormalizationParameter.getSecondElement();
			ordinate = (ordinate - ordinateNormalizationParameter.getFirstElement()) / ordinateNormalizationParameter.getSecondElement();
			
			normalizedCentroidList.add(new Pair<Double, Double>(abcissa, ordinate));
		}
		
		return normalizedCentroidList;
	}
	
	private Pair<Pair<Double, Double>, Pair<Double, Double>> getNormalizationParameters(List<Pair<Double, Double>> centroidList)
	{
		double[] abcissaValues = new double[centroidList.size()];
		double[] ordinateValues = new double[centroidList.size()];
		
		for (int i = 0; i < centroidList.size(); i++)
		{
			Pair<Double, Double> point = centroidList.get(i);
			
			abcissaValues[i] = point.getFirstElement();
			ordinateValues[i] = point.getSecondElement();
		}
		
		Arrays.sort(abcissaValues);
		Arrays.sort(ordinateValues);
		
		double abcissaMinimum = abcissaValues[0], abcissaMaximum = abcissaValues[abcissaValues.length-1];
		
		double ordinateMinimum = ordinateValues[0], ordinateMaximum = ordinateValues[ordinateValues.length-1];
		
		if (abcissaMinimum == abcissaMaximum)
			abcissaMaximum = 1.0;
		else
			abcissaMaximum = abcissaMaximum - abcissaMinimum;
		
		if (ordinateMinimum == ordinateMaximum) 
			ordinateMaximum = 1.0;
		else
			ordinateMaximum = ordinateMaximum - ordinateMinimum;
		
		return new Pair<Pair<Double, Double>, Pair<Double, Double>>(
				new Pair<Double, Double>(abcissaMinimum, abcissaMaximum), 
				new Pair<Double, Double>(ordinateMinimum, ordinateMaximum));
	}
}
