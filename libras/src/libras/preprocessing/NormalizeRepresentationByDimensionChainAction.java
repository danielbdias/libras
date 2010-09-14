package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;

public class NormalizeRepresentationByDimensionChainAction extends ChainAction {

	public NormalizeRepresentationByDimensionChainAction(
		File[] representationFiles, File processedRepresentationFiles[]) {
		super();
		this.initializeAttributes(representationFiles, processedRepresentationFiles);
	}
	
	private File[] representationFiles = null;
	private File[] processedRepresentationFiles = null;
	
	private void initializeAttributes(
			File[] representationFiles, File[] processedRepresentationFiles)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFiles, "representationFile");
		
		for (int i = 0; i < representationFiles.length; i++) {
			libras.utils.ValidationHelper.validateIfParameterIsNull(representationFiles[i], "representationFile[" + i + "]");
			libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFiles[i]);
		}

		libras.utils.ValidationHelper.validateIfParameterIsNull(processedRepresentationFiles, "processedRepresentationFiles");
		
		for (int i = 0; i < processedRepresentationFiles.length; i++) {
			libras.utils.ValidationHelper.validateIfParameterIsNull(processedRepresentationFiles[i], "processedRepresentationFiles[" + i + "]");
			
			if (processedRepresentationFiles[i].exists())
				libras.utils.ValidationHelper.validateIfFileParameterIsFile(processedRepresentationFiles[i]);
		}
		
		this.representationFiles = representationFiles;
		this.processedRepresentationFiles = processedRepresentationFiles;
	}
	
	@Override
	protected void doExecuteAction() {
		ArrayList<Double> maximumValues = new ArrayList<Double>();
		ArrayList<Double> minimumValues = new ArrayList<Double>();
		
		this.findMaximumAndMinimum(this.representationFiles, maximumValues, minimumValues);
		
		for (int i = 0; i < this.representationFiles.length; i++) {
			this.normalizeFile(representationFiles[i], processedRepresentationFiles[i], maximumValues, minimumValues);
		}
	}
	
	private void findMaximumAndMinimum(
		File[] representationFiles, ArrayList<Double> maximumValues, ArrayList<Double> minimumValues) {
		
		for (File representationFile : representationFiles) {
			java.io.BufferedReader reader = null;
			
			try
			{
				reader =
					new java.io.BufferedReader(
						new java.io.FileReader(representationFile));
				
				String line = null;
				
				while ((line = reader.readLine()) != null)
				{
					String[] parsedLine = line.split(",");
					
					Double[] values = this.getValuesFromParsedLine(parsedLine);
					
					for (int i = 0; i < values.length; i++) {
						if (maximumValues.size() < i+1)
							maximumValues.add(values[i]);
						else if (maximumValues.get(i) == null ||
								(maximumValues.get(i) != null && values[i] != null && maximumValues.get(i) < values[i]))
							maximumValues.set(i, values[i]);
						
						if (minimumValues.size() < i+1)
							minimumValues.add(values[i]);
						else if (minimumValues.get(i) == null ||
								(minimumValues.get(i) != null && values[i] != null && minimumValues.get(i) > values[i]))
							minimumValues.set(i, values[i]);
					}
				}
				
				if (reader != null) reader.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace(System.err);
			}
			
		}
	}
	
	private void normalizeFile(
		File representationFile, File processedRepresentationFile,
		ArrayList<Double> maximumValues, ArrayList<Double> minimumValues) {
		
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
				
				String codifiedLine = normalizeAndCodifyLine(parsedLine, maximumValues, minimumValues);
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
	
	private String normalizeAndCodifyLine(String[] parsedLine, ArrayList<Double> maximumValues, ArrayList<Double> minimumValues) {
		Double[] values = this.getValuesFromParsedLine(parsedLine);
		int classNumber = this.getClassNumberFromParsedLine(parsedLine);
		
		//perform the normalization in each value
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null) {
				double maximum = maximumValues.get(i);
				double minimum = minimumValues.get(i);
				
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
