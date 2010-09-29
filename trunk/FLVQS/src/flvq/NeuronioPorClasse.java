package flvq;

public class NeuronioPorClasse {
	
	private String classe;
	private int numeroDeNeuronios;
	
	public NeuronioPorClasse(String classe){
		this.classe = classe;
	}
	
	public NeuronioPorClasse(String classe, int numeroDeNeuronios){
		this.classe = classe;
		this.numeroDeNeuronios = numeroDeNeuronios;
	}

	public int getNumeroDeNeuronios() {
		return numeroDeNeuronios;
	}

	public void setNumeroDeNeuronios(int numeroDeNeuronios) {
		this.numeroDeNeuronios = numeroDeNeuronios;
	}
	
	public void adicionaNeuronio(){
		numeroDeNeuronios++;
	}

	public String getClasse() {
		return classe;
	}
	
	public boolean equals(Object obj){
		NeuronioPorClasse npc = (NeuronioPorClasse)obj;
		return npc.getClasse().equals(classe);
	}
	
}
