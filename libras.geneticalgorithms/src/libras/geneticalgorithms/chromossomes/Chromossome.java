package libras.geneticalgorithms.chromossomes;

public abstract class Chromossome extends Object implements Cloneable {
	private Object[] alleles = null;

	/**
	 * Creates a new chromossome with a specified number of allels. 
	 * @param allelesCount Number of alleles in this chromossome.
	 */
	public Chromossome(Object[] alleles) {
		this.alleles = alleles;
	}

	/**
	 * Gets the number of alleles in this chromossome.
	 * @return Number of alleles in this chromossome.
	 */
	public int allelesCount() {
		return this.alleles.length;
	}
	
	/**
	 * Verify if this chromossome match the requirements of a "good" chromossome. 
	 * @return true, if this chromossome match the requirements of a "good" chromossome. Otherwise, false.
	 */
	public abstract boolean matchRequirements();

	public Object getAllel(int index) {
		return this.alleles[index];
	}

	public void setAllel(int index, Object value) {
		this.alleles[index] = value;
	}
	
	public abstract void mutateAllel(int allelToMutate);

	public abstract double fitness();

	@Override
	public abstract Object clone() throws CloneNotSupportedException;
	
	public abstract Chromossome randomClone();
	
	public abstract boolean[] allelComparison(Chromossome secondChromossome);
}
