package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;

public class NormalizeRepresentationBySamplingChainAction extends ChainAction {

	public NormalizeRepresentationBySamplingChainAction(
		File representationFile, File processedRepresentationFile, 
		int representationSize, int normalizationSize) {
		super();
		this.initializeAttributes(representationFile, processedRepresentationFile, representationSize, normalizationSize);
	}
	
	private File representationFile = null;
	private File processedRepresentationFile = null;
	private int representationSize = 0;
	private int normalizationSize = 0;
	
	private void initializeAttributes(
		File representationFile, File processedRepresentationFile, 
		int representationSize, int normalizationSize)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFile, "representationFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(processedRepresentationFile, "processedRepresentationFile");
		
		if (processedRepresentationFile.exists())
			libras.utils.ValidationHelper.validateIfFileParameterIsFile(processedRepresentationFile);
		
		this.representationFile = representationFile;
		this.processedRepresentationFile = processedRepresentationFile;
		this.representationSize = representationSize;
		this.normalizationSize = normalizationSize;
	}
	
	@Override
	protected void doExecuteAction() {
		java.io.BufferedReader reader = null;
		java.io.BufferedWriter writer = null;
		
		try
		{
			reader =
				new java.io.BufferedReader(
					new java.io.FileReader(this.representationFile));
			
			writer =
				new java.io.BufferedWriter(
					new java.io.FileWriter(this.processedRepresentationFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				String codifiedLine = codifyLine(parsedLine);
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
	
	protected String codifyLine(String[] parsedLine)
	{
		String[][] instance = this.getRepresentationInstance(parsedLine);
		int classNumber = this.getClassNumberFromParsedLine(parsedLine);
		
		String[] sampledInstance = this.doSampling(instance);
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < sampledInstance.length; i++) {
			if (sampledInstance[i] != null)
				builder.append(sampledInstance[i]);
			else
				builder.append('?');
			builder.append(',');
		}
		
		builder.append(classNumber);
		
		return builder.toString();
	}

	private String[][] getRepresentationInstance(String[] parsedLine) {
		String[][] instance = new String[(parsedLine.length-1) / this.representationSize][];
		
		for (int i = 0; i < parsedLine.length-1; i+=this.representationSize) {
			String[] item = new String[this.representationSize];
			
			for (int j = 0; j < item.length; j++) {
				item[j] = parsedLine[i+j];
			}
			
			instance[i/this.representationSize] = item;
		}
		
		return instance;
	}
	
	private int getClassNumberFromParsedLine(String[] parsedLine) {
		return Integer.parseInt(parsedLine[parsedLine.length-1]);
	}
	
	private String[] doSampling(String[][] instance) {
		ArrayList<String[]> sampledInstance = new ArrayList<String[]>();

		int samplingFactor = instance.length / this.normalizationSize;
		
		if (samplingFactor > 0) {
			for (int i = 0; i < instance.length; i+=samplingFactor) {
				if (instance[i] != null)
					sampledInstance.add(instance[i]);
				else
					sampledInstance.add(new String[this.representationSize]);
			}
		}
		
		while (sampledInstance.size() > this.normalizationSize) {
			sampledInstance.remove(sampledInstance.size()-1);
		}
		
		ArrayList<String> result = new ArrayList<String>(); 
		
		int count = 0;
		
		for (String[] item : sampledInstance) {
			for (int i = 0; i < item.length; i++, count++) {
				result.add(item[i]);
			}
		}
		
		return result.toArray(new String[0]);
	}
}
