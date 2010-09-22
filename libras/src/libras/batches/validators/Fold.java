package libras.batches.validators;

import java.util.List;

public class Fold<T> {
	
	public Fold(List<T[]> data, List<String> labels) {
		this.data = data;
		this.labels = labels;
	}

	private List<T[]> data = null;

	private List<String> labels = null;
	
	public List<T[]> getData() {
		return data;
	}

	public List<String> getLabels() {
		return labels;
	}
}
