package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ProcessRepresentationChainAction extends ChainAction
{
	public ProcessRepresentationChainAction(File representationFile, File processedRepresentationFile, int representationSize)
	{
		super();
		
		initializeAttributes(representationFile, processedRepresentationFile, representationSize);
	}

	public ProcessRepresentationChainAction(File representationFile, File processedRepresentationFile, int representationSize, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(representationFile, processedRepresentationFile, representationSize);
	}

	private File representationFile = null;
	
	private File processedRepresentationFile = null;
	
	private int representationSize = 0;
	
	private void initializeAttributes(File representationFile, File processedRepresentationFile, int representationSize)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(processedRepresentationFile, "processedRepresentationFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(processedRepresentationFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFile, "representationFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFile);
		
		this.processedRepresentationFile = processedRepresentationFile;
		this.representationFile = representationFile;
		this.representationSize = representationSize;
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
					new java.io.FileWriter(this.processedRepresentationFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				String codifiedLine = processLine(parsedLine);
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

	protected String codifyLine(List<Double[]> representationItems, int classNumber) {
		StringBuilder builder = new StringBuilder();
		
		for (Iterator<Double[]> iterator = representationItems.iterator(); iterator.hasNext();) {
			Double[] representation = iterator.next();
			
			for (int i = 0; i < representation.length; i++) {
				builder.append(representation[i]);
				builder.append(',');	
			}
		}
		
		builder.append(classNumber);
		
		return builder.toString();
	}
	
	protected List<Double[]> getRepresentationFromParsedLine(String[] parsedLine) {
		List<Double[]> representationItems = new ArrayList<Double[]>();
		
		for (int i = 0; i < parsedLine.length; i+=this.representationSize) {
			Double[] representation = new Double[this.representationSize];
			
			for (int j = 0; j < representation.length; j++) {
				representation[j] = Double.parseDouble(parsedLine[i+j]);
			}
			
			representationItems.add(representation);
		}
		
		return representationItems;
	}
	
	protected int getClassNumberFromParsedLine(String[] parsedLine)
	{
		return Integer.parseInt(parsedLine[parsedLine.length-1]);
	}
	
	protected int getRepresentationSize()
	{
		return this.representationSize;
	}
	
	protected abstract String processLine(String[] parsedLine);
}
