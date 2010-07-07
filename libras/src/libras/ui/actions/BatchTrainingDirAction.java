/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.io.*;

import libras.ui.actions.annotations.ActionDescription;

/**
 * Find training files in a directory and execute a training action for each file found. 
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="batchdir",
	commandExample="batchdir=[batchfile_dir_path]",
	helpDescription="Executes a batch training for each batch file script found in the directory.",
	needUserInput=true)
public class BatchTrainingDirAction extends Action
{
	/**
	 * Creates a new instance of this action passing the directory
	 * where the training files are located
	 * @param batchDir Directory where the training files are located. 
	 * This directory must have only training files and subdirectory with more training files.
	 */
	public BatchTrainingDirAction(String batchDir) 
	{
		this.batchDir = batchDir;
	}
	
	private String batchDir = null;

	/**
	 * Find the training files and execute the training action for each file found.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute() throws Exception
	{	
		this.processBatchFiles(batchDir);
	}
	
	/**
	 * Resursive method to process each file found in directory.
	 * @param batchDir Directory with files / subdirectories to use the training action
	 * @throws Exception Exception indicating which an unexpcted error occurred.
	 */
	private void processBatchFiles(String batchDir) throws Exception
	{
		File directory = new File(batchDir);
		
		if (directory.exists())
		{
			String[] dirItems = directory.list();
			
			for (String item : dirItems)
			{
				File fileSystemEntry = new File(batchDir + "\\" + item);
				
				if (fileSystemEntry.isFile())
				{
					BatchTrainingAction action = new BatchTrainingAction(fileSystemEntry.getAbsolutePath());
					action.execute();
				}
				else if (fileSystemEntry.isDirectory())
					this.processBatchFiles(batchDir + "\\" + item);
			}
		}
	}
}
