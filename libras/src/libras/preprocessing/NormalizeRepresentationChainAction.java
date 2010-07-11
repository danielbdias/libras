package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import libras.utils.Pair;

public abstract class NormalizeRepresentationChainAction extends ChainAction
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

	protected String codifyLine(List<Pair<Double, Double>> centroids, int classNumber) {
		StringBuilder builder = new StringBuilder();
		
		for (Iterator<Pair<Double, Double>> iterator = centroids.iterator(); iterator.hasNext();) {
			Pair<Double, Double> pair = iterator.next();
			
			builder.append(pair.getFirstElement());
			builder.append(',');
			builder.append(pair.getSecondElement());
			builder.append(',');
		}
		
		builder.append(classNumber);
		
		return builder.toString();
	}
	
	protected List<Pair<Double, Double>> getCoordinatesFromParsedLine(
			String[] parsedLine) {
		List<Pair<Double, Double>> centroids = new ArrayList<Pair<Double,Double>>();
		
		for (int i = 0; i < parsedLine.length; i+=2) {
			Double abcissa = Double.parseDouble(parsedLine[i]);
			Double ordinate = Double.parseDouble(parsedLine[i+1]);
			
			centroids.add(new Pair<Double, Double>(abcissa, ordinate));
		}
		return centroids;
	}
	
	protected int getClassNumberFromParsedLine(String[] parsedLine)
	{
		return Integer.parseInt(parsedLine[parsedLine.length-1]);
	}
	
	protected abstract String normalizeLine(String[] parsedLine);
}
