package libras.preprocessing;

import java.io.File;

public abstract class RepresentationBuilderChainAction extends ChainAction
{
	public RepresentationBuilderChainAction(File centroidFile, File representationFile)
	{
		super();
		
		initializeAttributes(centroidFile, representationFile);
	}

	public RepresentationBuilderChainAction(File centroidFile, File representationFile, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(centroidFile, representationFile);
	}
	
	private File centroidFile = null;
	
	private File representationFile = null;
	
	private void initializeAttributes(File centroidFile, File representationFile)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(centroidFile, "centroidFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(centroidFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFile, "representationFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFile);
		
		this.centroidFile = centroidFile;
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
					new java.io.FileReader(this.centroidFile));
			
			writer =
				new java.io.BufferedWriter(
					new java.io.FileWriter(this.representationFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				String codifiedLine = codifyLine(parsedLine);
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

	protected abstract String codifyLine(String[] parsedLine);
}
