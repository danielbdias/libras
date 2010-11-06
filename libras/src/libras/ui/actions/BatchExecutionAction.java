/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import libras.batches.*;
import libras.batches.taskfiles.TaskFileParser;
import libras.batches.taskfiles.models.Task;
import libras.batches.taskfiles.models.TaskFile;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Executes the batch training of a neural network.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="batchexec",
	commandExample="batchexec -dir=[taskfiledir_path]",
	helpDescription="Executes a batch based in a task file script with various tasks.",
	requiredArgs={ "dir" },
	needUserInput=true)
public class BatchExecutionAction extends Action implements IBatchProcessorObserver
{
	/**
	 * Creates a new instance of this action passing the batch file
	 * with the configuration of the training
	 * @param arguments Arguments of this action
	 */
	public BatchExecutionAction(Hashtable<String, String> arguments) 
	{
		this.dir = arguments.get("dir");
		
		if (arguments.containsKey("threads"))
		{
			String threadsAsString = arguments.get("threads");
			this.threadsToUse = Integer.parseInt(threadsAsString);
		}
	}
	
	private String dir = null;
	private int threadsToUse = 1;
	
	/**
	 * Executes the batch training.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute() throws Exception
	{
		List<File> filesToExecute = this.getFilesToExecute(this.dir);
		
		for (File file : filesToExecute) {
			executeTaskFile(file);	
		}	
	}

	private List<File> getFilesToExecute(String dirPath) {
		File dir = new File(dirPath);
		
		List<File> files = new ArrayList<File>();
		
		this.getFilesToExecuteRecursive(dir, files);
		
		return files;
	}
	
	private void getFilesToExecuteRecursive(File dir, List<File> files) {
		File[] dirs = dir.listFiles(
				new FileFilter() {
					@Override
					public boolean accept(File arg) {
						return arg.isDirectory();
					}
				});
		
		for (File subDir : dirs)
			this.getFilesToExecuteRecursive(subDir, files);
		
		File[] outputFiles = dir.listFiles(
				new FileFilter() {
					@Override
					public boolean accept(File arg) {
						return arg.getName().endsWith(".xml");
					}
				});
		
		for (File file : outputFiles)
			files.add(file);
	}
	
	private void executeTaskFile(File taskFile) throws Exception {
		System.out.printf("Executing batch: [%s] ...\r\n", taskFile);
		
		TaskFile batch = null;
		
		try
		{
			TaskFileParser parser = new TaskFileParser();
			batch = parser.parseFile(taskFile.getAbsolutePath());
		}
		catch (Exception e)
		{
			throw new Exception(
				"An unknown error occurs when recognizing batch file. See Cause for more details.", e);
		}
		
		if (taskFile != null)
		{
			BatchProcessor processor = new BatchProcessor(this, this.threadsToUse);
			
			BatchTaskResult[] results = processor.process(batch);
			
			int successfulTasks = 0;
			
			for (int i = 0; i < results.length; i++)
			{
				BatchTaskResult result = results[i];
				
				if (result != null && result.isCompleted())
					successfulTasks++;
			}
			
			System.out.printf("%d of %d items executed with success.", successfulTasks, results.length);
			System.out.println();
		}
		
		System.out.println("Batch executed.");
	}
	
	@Override
	public void receiveResult(BatchTaskResult result, Task item) {
		System.out.printf("Item [%s] completed ", item.getName());
		
		if (result != null && result.isCompleted())
		{
			System.out.printf("without errors.\r\n");
		}
		else
		{
			System.out.printf("with errors.\r\n");
			
			if (result != null) {
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
			else {
				System.out.printf("The result is null.\r\n");
			}
		}
	}
}
