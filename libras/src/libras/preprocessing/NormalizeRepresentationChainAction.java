package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import libras.utils.Pair;

public class NormalizeRepresentationChainAction extends ChainAction
{
	public NormalizeRepresentationChainAction(File representationFile, File normalizedFile)
	{
		super();
		
		initializeAttributes(representationFile, normalizedFile);
	}

	public NormalizeRepresentationChainAction(File representationFile, File normalizedFile, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(representationFile, normalizedFile);
	}

	private File representationFile = null;
	
	private File normalizedFile = null;
	
	private void initializeAttributes(File representationFile, File normalizedFile)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(normalizedFile, "normalizedFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(normalizedFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFile, "representationFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFile);
		
		this.normalizedFile = normalizedFile;
		this.representationFile = representationFile;
	}
	
	@Override
	protected void doExecuteAction()
	{
		java.io.BufferedReader reader = null;
		java.io.BufferedWriter writer = null;
		
		try
		{
			reader =
				new java.io.BufferedReader(
					new java.io.FileReader(this.representationFile));
			
			writer =
				new java.io.BufferedWriter(
					new java.io.FileWriter(this.normalizedFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				String codifiedLine = normalizeLine(parsedLine);
				writer.write(codifiedLine);
			}
			
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}

	private String normalizeLine(String[] parsedLine)
	{
		// TODO Auto-generated method stub
		return null;
	}

	protected void normalizeCentroidListBySampling(List<Pair<Double, Double>> centroidList, int maxCentroidsPerList)
	{
		int keepOrder = centroidList.size() / maxCentroidsPerList;
		
		if (keepOrder > 0)
		{
			List<Pair<Double, Double>> tempList = new ArrayList<Pair<Double, Double>>();
			
			//Add until fill the max number count
			for (int i = 0; i < centroidList.size() && tempList.size() < maxCentroidsPerList; i+=keepOrder)
				tempList.add(centroidList.get(i));
			
			if (tempList.size() < maxCentroidsPerList)
			{
				for (int i = maxCentroidsPerList - tempList.size(); i > 0; i--)
					tempList.add(centroidList.get(centroidList.size() - i));
			}
			
			centroidList.clear();
			centroidList.addAll(tempList);
		}
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
