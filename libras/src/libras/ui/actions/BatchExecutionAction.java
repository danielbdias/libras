/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.util.Hashtable;

import libras.batches.*;
import libras.batches.taskfiles.TaskFileParser;
import libras.batches.taskfiles.models.TaskFile;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Executes the batch training of a neural network.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="batchexec",
	commandExample="batchexec -file=[taskfile_path]",
	helpDescription="Executes a batch based in a task file script with various tasks.",
	requiredArgs={ "file" },
	needUserInput=true)
public class BatchExecutionAction extends Action
{
	/**
	 * Creates a new instance of this action passing the batch file
	 * with the configuration of the training
	 * @param arguments Arguments of this action
	 */
	public BatchExecutionAction(Hashtable<String, String> arguments) 
	{
		this(arguments.get("file"));
	}
	
	/**
	 * Creates a new instance of this action passing the batch file
	 * with the configuration of the training
	 * @param batchFile Batch file with the training parameters
	 */
	public BatchExecutionAction(String taskFile) 
	{
		this.taskFile = taskFile;
	}
	
	private String taskFile = null;
	
	/**
	 * Executes the batch training.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute() throws Exception
	{
		System.out.printf("Executing batch: [%s] ...\r\n", taskFile);
		
		TaskFile batch = null;
		
		try
		{
			TaskFileParser parser = new TaskFileParser();
			batch = parser.parseFile(taskFile);
		}
		catch (Exception e)
		{
			throw new Exception(
				"An unknown error occurs when recognizing batch file. See Cause for more details.", e);
		}
		
		if (taskFile != null)
		{
			BatchProcessor processor = new BatchProcessor();
			
			BatchTaskResult[] results = processor.process(batch);
			
			for (int i = 0; i < results.length; i++)
			{
				BatchTaskResult result = results[i];
				
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
