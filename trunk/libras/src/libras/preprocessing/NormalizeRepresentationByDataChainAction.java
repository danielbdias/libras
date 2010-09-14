package libras.preprocessing;

import java.io.File; 

public class NormalizeRepresentationByDataChainAction extends ChainAction {

	public NormalizeRepresentationByDataChainAction(
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
				
				double[] maxMin = this.findMaximumAndMinimum(values);
				
				double minimum = maxMin[0];
				double maximum = maxMin[1];
				
				String codifiedLine = normalizeAndCodifyLine(values, classNumber, maximum, minimum);
				writer.write(codifiedLine);
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
	
	private String normalizeAndCodifyLine(Double[] values, int classNumber, double minimum, double maximum) {
		
		//perform the normalization in each value
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null) {
				
				if (maximum - minimum != 0)
					values[i] = (values[i] - minimum) / (maximum - minimum);
				else
					values[i] = (values[i] - minimum);
			}
		}
		
		//build line
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null)
				builder.append(values[i]);
			else
				builder.append('?');
			builder.append(',');
		}
		
		builder.append(classNumber);
		
		return builder.toString();
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
