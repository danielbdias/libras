/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import libras.neuralnetworks.batches.*;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Executes the batch training of a neural network.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="batchfile",
	commandExample="batchfile=[batchfile_path]",
	helpDescription="Executes a batch training based in the batch file script.",
	needUserInput=true)
public class BatchTrainingAction extends Action
{
	/**
	 * Creates a new instance of this action passing the batch file
	 * with the configuration of the training
	 * @param batchFile Batch file with the training parameters
	 */
	public BatchTrainingAction(String batchFile) 
	{
		this.batchFile = batchFile;
	}
	
	private String batchFile = null;
	
	/**
	 * Executes the batch training.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute() throws Exception
	{
		System.out.printf("Executing batch: [%s] ...\r\n", batchFile);
		
		Batch batch = null;
		
		try
		{
			BatchFileParser parser = new BatchFileParser();
			batch = parser.parseBatchFile(batchFile);
		}
		catch (Exception e)
		{
			throw new Exception("An unknown error occurs when recognizing batch file. See Cause for more details.", e);
		}
		
		if (batch != null)
		{
			BatchProcessor processor = new BatchProcessor();
			
			BatchProcessResult[] results = processor.process(batch);
			
			for (int i = 0; i < results.length; i++)
			{
				BatchProcessResult result = results[i];
				
				System.out.printf("%d - Item [%s] completed ", i+1, result.getBatchItem());
				
				if (result.isCompleted())
				{
					System.out.printf("without errors.\r\n");
				}
				else
				{
					System.out.printf("with errors.\r\n");
					System.out.printf("Error: %s \r\n", result.getError().toString());
					result.getError().printStackTrace(System.out);
					System.out.println();
					
					Throwable err = (Throwable) result.getError();
					
					while((err = err.getCause()) != null)
					{
						System.out.printf("Cause: %s \r\n", err.toString());
						err.printStackTrace(System.out);
						System.out.println();
					}
				}
			}
		}
		
		System.out.println("Batch executed.");
	}
}
