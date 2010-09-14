package libras.utils.validation;

import java.util.List;

public class Fold<T> {
	
	public Fold(List<T[]> data) {
		this.data = data;
	}

	List<T[]> data = null;

	public List<T[]> getData() {
		return data;
	}
}
