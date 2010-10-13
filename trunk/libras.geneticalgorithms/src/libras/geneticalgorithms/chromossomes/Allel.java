package libras.geneticalgorithms.chromossomes;

public class Allel {
	private int group = 0;
	private double[] data;
	private String type = null;
	
	public Allel(int allelsize) {
		data = new double[allelsize];
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public double getData(int index) {
		return this.data[index];
	}
	
	public void setData(int index, double value) {
		this.data[index] = value;
	}
	
	public int getGroup() {
		return this.group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
	
	public int getDataSize() {
		return this.data.length;
	}
}