/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import libras.batches.taskfiles.models.TaskFile;

/**
 * Execute the items of a batch file, executing the training and validation of each item of a batch file. 
 * @author Daniel Baptista Dias
 */
public class BatchProcessor
{
	public BatchProcessor() {
		this(null);
	}
	
	public BatchProcessor(IBatchProcessorObserver observer) {
		this(observer, 1);
	}
	
	public BatchProcessor(IBatchProcessorObserver observer, int threadsUsedInTaskExecution) {
		this.observer = observer;
		this.threadsUsedInTaskExecution = threadsUsedInTaskExecution;
	}
	
	private IBatchProcessorObserver observer = null;
	
	private int threadsUsedInTaskExecution = 0;
	
	/**
	 * Process a batch file, executing the training and validation of each item of a batch file.
	 * @param batch Batch file to be processed.
	 * @return The result of each process.
	 */
	public BatchTaskResult[] process(TaskFile taskFile)
	{
		final int SLEEP_TIME = 1000;
		
		List<TaskExecution> tasks = new ArrayList<TaskExecution>();
		List<Future<?>> tasksStatus = new ArrayList<Future<?>>();
		
		ExecutorService pool = Executors.newFixedThreadPool(this.threadsUsedInTaskExecution);
		
		for (int i = 0; i < taskFile.size(); i++)
		{
			tasks.add(new TaskExecution(taskFile.get(i), this.observer));
			Future<?> future = pool.submit(tasks.get(i));
			tasksStatus.add(future);
		}
		
		while (!this.taskExecutionFinished(tasksStatus))
		{
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		pool.shutdown();
		
		BatchTaskResult[] results = new BatchTaskResult[taskFile.size()];
		
		for (int i = 0; i < taskFile.size(); i++) {
			results[i] = tasks.get(i).getResult();
		}
		
		return results;
	}

	private boolean taskExecutionFinished(List<Future<?>> tasksStatus) {
		for (int i = 0; i < tasksStatus.size(); i++) {
			if (!tasksStatus.get(i).isDone()) return false;
		}
		
		return true;
	}
}
