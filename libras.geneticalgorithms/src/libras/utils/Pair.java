package libras.utils;

public class Pair<FirstT, SecondT> {
	
	public Pair(FirstT firstItem, SecondT secondItem) {
		this.firstItem = firstItem;
		this.secondItem = secondItem;
	}

	private FirstT firstItem;
	private SecondT secondItem;
	
	public FirstT getFirstItem() {
		return firstItem;
	}
	
	public SecondT getSecondItem() {
		return secondItem;
	}
}
