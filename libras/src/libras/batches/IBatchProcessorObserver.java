package libras.batches;

import libras.batches.taskfiles.models.Task;

public interface IBatchProcessorObserver {
	void receiveResult(BatchTaskResult result, Task task);
}
