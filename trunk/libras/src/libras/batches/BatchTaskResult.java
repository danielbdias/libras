/**
 * Package that provide classes to automatize batch process.
 */
package libras.batches;

/**
 * Represents a result of the process of a batch item.
 * @author Daniel Baptista Dias
 */
public class BatchTaskResult
{
	/**
	 * Creates a new instance with the completed status and name of the item
	 * @param completed Completed status of the item.
	 * @param batchItem Item name.
	 */
	BatchTaskResult(boolean completed, String batchItem)
	{
		this.completed = completed;
		this.batchItem = batchItem;
	}
	
	/**
	 * Creates a new instance with the exception occured and name of the item
	 * @param error Exception occured during the item execution.
	 * @param batchItem Item name.
	 */
	public BatchTaskResult(Exception error, String batchItem)
	{
		this.error = error;
		this.batchItem = batchItem;
	}
	
	private boolean completed = false;
	private Exception error = null;
	private String batchItem = null;
	
	/**
	 * Indicate if the process completed for this item.
	 * @return true, if the process is complete, otherwise, false.
	 */
	public boolean isCompleted()
	{
		return completed;
	}
	
	/**
	 * Get the error that occured in the process. 
	 * @return Error that occured in the process.
	 */
	public Exception getError()
	{
		return error;
	}
	
	/**
	 * Get the name of the processed item.
	 * @return Name of the processed item.
	 */
	public String getBatchItem()
	{
		return batchItem;
	}
}
