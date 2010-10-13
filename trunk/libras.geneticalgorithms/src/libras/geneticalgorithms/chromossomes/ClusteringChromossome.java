package libras.geneticalgorithms.chromossomes;

import java.util.*;

import libras.utils.RandomUtil;


public class ClusteringChromossome extends Chromossome implements Cloneable {

	/**
	 * Creates a new chromossome with a specified number of allels. 
	 * @param allelesCount Number of alleles in this chromossome.
	 */
	public ClusteringChromossome(int allelesCount, int clusters) {
		super(new Allel[allelesCount]);
		this.numberOfClusters = clusters;
	}
	
	private int numberOfClusters = 0;
	
	/**
	 * Verify if this chromossome match the requirements of a "good" chromossome. 
	 * @return true, if this chromossome match the requirements of a "good" chromossome. Otherwise, false.
	 */
	public boolean matchRequirements() {
		return this.fitness() == 0;
	}

	public Allel getAllel(int index) {
		return (Allel) super.getAllel(index);
	}

	public void mutateAllel(int allelToChange) {
		int cluster = (int) RandomUtil.randomInRange(1, this.numberOfClusters+1);
		if (cluster > this.numberOfClusters) cluster--;
		this.getAllel(allelToChange).setGroup(cluster);
	}

	public double fitness(){
		double value=0;
		Allel media;
		double valor=0;
		int div=0;
		
		LinkedList<Integer> pilha= new LinkedList<Integer>();


		for(int i=0;i<this.allelesCount();i++){ 
			if(pilha.contains(this.getAllel(i).getGroup())==false){
				pilha.add(this.getAllel(i).getGroup());
				media=new Allel(this.getAllel(0).getDataSize());
				//somam totas as instancias do grupo
				div=0;
				for(int j=i;j<this.allelesCount();j++){
					//se for igual soma
					if(this.getAllel(i).getGroup()==this.getAllel(j).getGroup()){
						for(int h=0;h<this.getAllel(0).getDataSize();h++){
							valor=0;
							valor=media.getData(h);
							valor=valor+this.getAllel(j).getData(h);
							media.setData(h,valor);
							media.setGroup(this.getAllel(j).getGroup());
						}
						div++;
					}

				}
				//divide cada objeto do dado por div
				this.calcMedia(media, div);
				div=0;
				//faz vetor do grupo - a media


				for(int j=i;j<this.allelesCount();j++){
					//subtrai
					Allel somatoria=new Allel(this.getAllel(0).getDataSize());
					if(this.getAllel(i).getGroup()==this.getAllel(j).getGroup()){
						for(int h=0;h<this.getAllel(0).getDataSize();h++){
							valor=0;
							somatoria.setData(h,this.getAllel(j).getData(h)-media.getData(h));
						}
						//calcula valor da mult da matriz de dados com a transp
						value=value+this.calcTransp(somatoria);
					}
				}
			}
		}
		
		return (-1) * value;
	}

	public double calcTransp(Allel alelo){
		double valor=0;
		for(int i=0;i<alelo.getDataSize();i++){
			valor=valor+(alelo.getData(i)*alelo.getData(i));
		}
		return valor;
	}

	public void calcMedia(Allel media,int divisor){
		double valor=0;
		for(int i=0;i<media.getDataSize();i++){
			valor=0;
			valor=media.getData(i);
			valor=valor/divisor;
			media.setData(i, valor);
		}
	}

	public void setAllel(int index, Allel value) {
		super.setAllel(index, (Object) value);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		ClusteringChromossome copy = new ClusteringChromossome(this.allelesCount(), this.numberOfClusters);
		
		for (int i = 0; i < this.allelesCount(); i++){	
			Allel newAllel = new Allel(this.getAllel(0).getDataSize());
			
			for (int j = 0; j < this.getAllel(i).getDataSize(); j++) {
				newAllel.setData(j, this.getAllel(i).getData(j));
				newAllel.setGroup(this.getAllel(i).getGroup());
				newAllel.setType(this.getAllel(i).getType());
			}
			
			copy.setAllel(i, newAllel);
		}
		
		return copy;
	}

	@Override
	public Chromossome randomClone() {
		ClusteringChromossome aux = new ClusteringChromossome(this.allelesCount(), this.numberOfClusters);
		
		for (int j = 0; j < this.allelesCount(); j++) {
			Allel newAllel = new Allel(this.getAllel(j).getDataSize());
			
			for (int h = 0; h < this.getAllel(j).getDataSize(); h++) {
				newAllel.setData(h, this.getAllel(j).getData(h));
				newAllel.setType(this.getAllel(j).getType());
				
				//Assign the allel to a random cluster
				int cluster = (int) RandomUtil.randomInRange(1, this.numberOfClusters+1);
				if (cluster > this.numberOfClusters) cluster--;
				newAllel.setGroup(cluster);
			}
			
			aux.setAllel(j, newAllel);
		}
		
		return aux;
	}
	
	@Override
	public boolean[] allelComparison(Chromossome secondChromossome) {
		if (secondChromossome instanceof ClusteringChromossome) {
			ClusteringChromossome c = (ClusteringChromossome) secondChromossome;
			
			boolean[] result = new boolean[this.allelesCount()];
			
			for (int i = 0; i < result.length; i++) {
				result[i] = (this.getAllel(i).getGroup() == c.getAllel(i).getGroup());
			}
		}
		
		return null;
	}
	
	public void printTypes() {
        for( int i=0; i<this.allelesCount(); i++) {
            if( this.getAllel(i).getType().equals("Iris-setosa") )
                System.out.print(";7");
            if( this.getAllel(i).getType().equals("Iris-versicolor") )
                System.out.print(";8");
            if( this.getAllel(i).getType().equals("Iris-virginica") )
                System.out.print(";9");
        }
        System.out.println(";FIM");
    }

    public void printGroups() {
        for( int i=0; i<this.allelesCount(); i++) {
            System.out.print(";"+this.getAllel(i).getGroup());
        }
        System.out.println(";FIM");
    }
}
