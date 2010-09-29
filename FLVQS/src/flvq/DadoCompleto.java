package flvq;

import java.util.ArrayList;

public class DadoCompleto {
	
	String nome;
	ArrayList<Double> configuracao;
	ArrayList<Double> movimento;
	ArrayList<Double> pertinenciaConfiguracao;
	ArrayList<Double> pertinenciaMovimento;
	ArrayList<Double> pertinenciaOrientacao;
	String letraClassificada;
	String letraCorreta;
	String classeConfiguracao;
	String classeMovimento;
	String classeOrientacao;
	
	public DadoCompleto(String nome){
		this.nome = nome;
		this.letraCorreta = nome.substring(nome.length()-1);
	}

	
	
	public String getClasseConfiguracao() {
		return classeConfiguracao;
	}



	public void setClasseConfiguracao(String classeConfiguracao) {
		this.classeConfiguracao = classeConfiguracao;
	}



	public String getClasseMovimento() {
		return classeMovimento;
	}



	public void setClasseMovimento(String classeMovimento) {
		this.classeMovimento = classeMovimento;
	}



	public String getClasseOrientacao() {
		return classeOrientacao;
	}



	public void setClasseOrientacao(String classeOrientacao) {
		this.classeOrientacao = classeOrientacao;
	}



	public String getLetraCorreta() {
		return letraCorreta;
	}

	public String getLetraClassificada() {
		return letraClassificada;
	}

	public void setLetraClassificada(String letraClassificada) {
		this.letraClassificada = letraClassificada;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Double> getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ArrayList<Double> configuracao) {
		this.configuracao = configuracao;
	}

	public ArrayList<Double> getMovimento() {
		return movimento;
	}

	public void setMovimento(ArrayList<Double> movimento) {
		this.movimento = movimento;
	}

	public ArrayList<Double> getPertinenciaConfiguracao() {
		return pertinenciaConfiguracao;
	}

	public void setPertinenciaConfiguracao(ArrayList<Double> pertinenciaConfiguracao) {
		this.pertinenciaConfiguracao = pertinenciaConfiguracao;
	}

	public ArrayList<Double> getPertinenciaMovimento() {
		return pertinenciaMovimento;
	}

	public void setPertinenciaMovimento(ArrayList<Double> pertinenciaMovimento) {
		this.pertinenciaMovimento = pertinenciaMovimento;
	}

	public ArrayList<Double> getPertinenciaOrientacao() {
		return pertinenciaOrientacao;
	}

	public void setPertinenciaOrientacao(ArrayList<Double> pertinenciaOrientacao) {
		this.pertinenciaOrientacao = pertinenciaOrientacao;
	}

	@Override
	public boolean equals(Object obj) {
		DadoCompleto dC = (DadoCompleto)obj;
		return this.getNome().equals(dC.getNome());
	}
	
	
	
}
