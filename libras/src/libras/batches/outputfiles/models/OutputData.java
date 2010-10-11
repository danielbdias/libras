package libras.batches.outputfiles.models;

import java.util.ArrayList;
import java.util.List;

public class OutputData {
	public OutputData() {
		this.expectedLabels = new ArrayList<String>();
		this.computedLabels = new ArrayList<String>();
	}	
	
	public OutputData(String taskName, double hitTax,
			List<String> expectedLabels, List<String> computedLabels) {
		this.taskName = taskName;
		this.hitTax = hitTax;
		this.expectedLabels = expectedLabels;
		this.computedLabels = computedLabels;
	}

	private String taskName = null;
	private double hitTax = 0.0;
	private List<String> expectedLabels = null;
	private List<String> computedLabels = null;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public double getHitTax() {
		return hitTax;
	}
	public void setHitTax(double hitTax) {
		this.hitTax = hitTax;
	}
	public List<String> getExpectedLabels() {
		return expectedLabels;
	}
	public void setExpectedLabels(List<String> expectedLabels) {
		this.expectedLabels = expectedLabels;
	}
	public List<String> getComputedLabels() {
		return computedLabels;
	}
	public void setComputedLabels(List<String> computedLabels) {
		this.computedLabels = computedLabels;
	} 
}
