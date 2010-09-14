package libras.preprocessing;

import java.io.File; 
import java.util.ArrayList;

public class NormalizeRepresentationByPositionChainAction extends ChainAction {

	public NormalizeRepresentationByPositionChainAction(
		File representationFile, File processedRepresentationFile) {
		super();
		this.initializeAttributes(representationFile, processedRepresentationFile);
	}
	
	private File representationFile = null;
	private File processedRepresentationFile = null;
	
	private void initializeAttributes(
			File representationFile, File processedRepresentationFile)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFile, "representationFile");
		
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(processedRepresentationFile, "processedRepresentationFile");
			
		if (processedRepresentationFile.exists())
			libras.utils.ValidationHelper.validateIfFileParameterIsFile(processedRepresentationFile);
		
		this.representationFile = representationFile;
		this.processedRepresentationFile = processedRepresentationFile;
	}
	
	@Override
	protected void doExecuteAction() {
		java.io.BufferedReader reader = null;
		java.io.BufferedWriter writer = null;
		
		try
		{
			reader =
				new java.io.BufferedReader(
					new java.io.FileReader(representationFile));
			
			writer =
				new java.io.BufferedWriter(
					new java.io.FileWriter(processedRepresentationFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				Double[] values = this.getValuesFromParsedLine(parsedLine);
				int classNumber = this.getClassNumberFromParsedLine(parsedLine);
				
				Double[] normalizedValues = this.normalizeValuesByPosition(values);
				
				//build line
				StringBuilder builder = new StringBuilder();
				
				for (int i = 0; i < normalizedValues.length; i++) {
					if (normalizedValues[i] != null)
						builder.append(normalizedValues[i]);
					else
						builder.append('?');
					builder.append(',');
				}
				
				builder.append(classNumber);
				
				writer.write(builder.toString());
				writer.newLine();
			}
			
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}
	
	private Double[] normalizeValuesByPosition(Double[] values) {
		ArrayList<Double> abcissaValues = new ArrayList<Double>();
		ArrayList<Double> ordinateValues = new ArrayList<Double>();
		
		for (int i = 0; i < values.length; i++) {
			if (i % 2 == 0)
				abcissaValues.add(values[i]);
			else
				ordinateValues.add(values[i]);
		}
		
		double[] minMaxAbcissa = this.findMaximumAndMinimum(abcissaValues.toArray(new Double[0]));
		double[] minMaxOrdinate = this.findMaximumAndMinimum(ordinateValues.toArray(new Double[0]));
		
		double minimumAbcissa = minMaxAbcissa[0], maximumAbcissa = minMaxAbcissa[1];
		double minimumOrdinate = minMaxOrdinate[0], maximumOrdinate = minMaxOrdinate[1];
		
		double abcissaCenter = (maximumAbcissa - minimumAbcissa) / 2;
		double ordinateCenter = (maximumOrdinate - minimumOrdinate) / 2;
		
		double abcissaCorrectionTax = abcissaCenter - 0.5;
		double ordinateCorrectionTax = ordinateCenter - 0.5;
		
		Double[] normalizedValues = new Double[values.length];
		
		for (int i = 0; i < normalizedValues.length; i++) {
			if (values[i] != null) {
				if (i % 2 == 0)
					normalizedValues[i] = values[i] - abcissaCorrectionTax;
				else
					normalizedValues[i] = values[i] - ordinateCorrectionTax;
			}
		}
		
		return normalizedValues;
	}

	private double[] findMaximumAndMinimum(Double[] values) {
		
		double maximumValue = Double.MIN_VALUE;
		double minimumValue = Double.MAX_VALUE;
				
		for (int i = 0; i < values.length; i++) {
			
			if (values[i] != null && maximumValue < values[i])
				maximumValue = values[i];
			
			if (values[i] != null && minimumValue > values[i])
				minimumValue = values[i];
		}
		
		return new double[] { minimumValue, maximumValue };
	}

	private int getClassNumberFromParsedLine(String[] parsedLine) {
		return Integer.parseInt(parsedLine[parsedLine.length-1]);
	}
	
	private Double[] getValuesFromParsedLine(String[] parsedLine) {
		Double[] values = new Double[parsedLine.length-1];
		
		for (int i = 0; i < values.length; i++) {
			if (!parsedLine[i].startsWith("?"))
				values[i] = Double.parseDouble(parsedLine[i]);
			else
				values[i] = null;
		}
		
		return values;
	}
}
