package flvq;

import java.io.IOException;
import java.util.ArrayList;

public class FLVQS {
	
	private LVQ lvq;
	private ArrayList<ArrayList<Double>> U;
	private double m;
	private double epoca;
	private double epocas;
	private double aprendizagem;
	private double aprendizagemInicial;
	private Vetor pesosAnterior;
	private double mudanca;
	
	public FLVQS(String nomeArquivo, double parametroFuzificacao, double aprendizagem, double mudanca, Vetor pesosIniciais) throws IOException{
		lvq = new LVQ(nomeArquivo, aprendizagem, pesosIniciais);
		m = parametroFuzificacao;
		this.aprendizagem = aprendizagem;
		aprendizagemInicial = aprendizagem;
		this.mudanca = mudanca;
		epoca = 0;
		epocas = Integer.MAX_VALUE;
	}
	
	public FLVQS(String nomeArquivo, double parametroFuzificacao, double aprendizagem, double mudanca) throws IOException{
		lvq = new LVQ(nomeArquivo, aprendizagem);
		m = parametroFuzificacao;
		this.aprendizagem = aprendizagem;
		aprendizagemInicial = aprendizagem;
		this.mudanca = mudanca;
		epoca = 0;
		epocas = Integer.MAX_VALUE;
	}
	
	public FLVQS(String nomeArquivo, double parametroFuzificacao, double aprendizagem, int epocas) throws IOException{
		lvq = new LVQ(nomeArquivo, aprendizagem);
		m = parametroFuzificacao;
		this.aprendizagem = aprendizagem;
		aprendizagemInicial = aprendizagem;
		this.epocas = epocas;
		epoca = 0;
	}
	
	public FLVQS(String nomeArquivo, String arquivoPesos, double parametroFuzificacao, double aprendizagem, int epocas) throws IOException{
		lvq = new LVQ(nomeArquivo, arquivoPesos, aprendizagem);
		m = parametroFuzificacao;
		this.aprendizagem = aprendizagem;
		aprendizagemInicial = aprendizagem;
		this.epocas = epocas;
		epoca = 0;
	}
	
	public FLVQS(Vetor dados, String nomeArquivo, double parametroFuzificacao, double aprendizagem, int epocas){
		lvq = new LVQ(dados, nomeArquivo, aprendizagem);
		m = parametroFuzificacao;
		this.aprendizagem = aprendizagem;
		aprendizagemInicial = aprendizagem;
		this.epocas = epocas;
		epoca = 0;
	}
	
	public FLVQS(Vetor dados, String nomeArquivo, String arquivoPesos, double parametroFuzificacao, double aprendizagem, int epocas) throws IOException{
		lvq = new LVQ(dados, nomeArquivo, arquivoPesos, aprendizagem);
		m = parametroFuzificacao;
		this.aprendizagem = aprendizagem;
		aprendizagemInicial = aprendizagem;
		this.epocas = epocas;
		epoca = 0;
	}
	
	public FLVQS(String arquivoPesos, double m) throws IOException{
		lvq = new LVQ(arquivoPesos);
		this.m = m;
	}
	
	public ArrayList<Double> extraiPertinencia(Dado dado){		
		double u_ij;
		double d_lj;
		double d_ij;
		double d;		
		ArrayList<Double> pertinencia = new ArrayList<Double>();

		Vetor pesos = lvq.getPesos();		
		for(int i = 0; i < lvq.numeroClasses(); i++){	
			u_ij = 0;
			d_ij = OpVetor.distancia(pesos.get(i).getV(), dado.getV());
			if(d_ij == 0) u_ij = 1;
			else{
				for(int l = 0; l < lvq.numeroClasses(); l++){
					d_lj = OpVetor.distancia(pesos.get(l).getV(), dado.getV());
					d = d_lj/d_ij;
					u_ij = u_ij + Math.pow(d, (1/(1 - m)));
				}
				u_ij = 1/u_ij;
			}
			pertinencia.add(i, u_ij);
		}
		return pertinencia;
	}
	
	public ArrayList<String> retornaClassesEmOrdem(){
		ArrayList<String> classes = new ArrayList<String>();
		
		Vetor pesos = lvq.getPesos();
		for(int i = 0; i < lvq.numeroClasses(); i++){
			classes.add(i, pesos.get(i).getCl());
		}
		return classes;
	}
	
	public void treina(){
		criaMatrizPertinencia();
		do{
			atualizaAprendizagem();
			atualizaMatrizPertinencia();
			atualizaPesos();	
			if(epoca == 8) lvq.getPesos().salvaArquivo(lvq.getNomeArquivo() + "_8", true);	
			epoca++;			
		}while(epoca < epocas && mudancaInsatisfatoria());
		System.out.println("Mudança máxima: " + mudanca);
		System.out.println("Epoca: " + epoca);
		lvq.getPesos().salvaArquivo(lvq.getNomeArquivo(), true);		
	}
	
	private void criaMatrizPertinencia(){		
		U = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> auxiliar;
		for(int i = 0; i < lvq.numeroClasses(); i++){
			auxiliar = new ArrayList<Double>();
			for(int j = 0; j < lvq.getDados().tamanho(); j++)
				auxiliar.add(0.0);
			U.add(auxiliar);
		}		
	}
	
	private void atualizaMatrizPertinencia(){
		double u_ij;
		double d_lj;
		double d_ij;
		double d;
		Vetor dados = lvq.getDados();
		Vetor pesos = lvq.getPesos();
		pesosAnterior = lvq.getPesos().copia();
		for(int i = 0; i < lvq.numeroClasses(); i++){
			for(int j = 0; j < dados.tamanho(); j++){		
				u_ij = 0;
				d_ij = OpVetor.distancia(pesos.get(i).getV(), dados.get(j).getV());
				if(d_ij == 0) u_ij = 1;
				else{
					for(int l = 0; l < lvq.numeroClasses(); l++){
						d_lj = OpVetor.distancia(pesos.get(l).getV(), dados.get(j).getV());
						d = d_lj/d_ij;
						u_ij = u_ij + Math.pow(d, (1/(1 - m)));
					}
					u_ij = 1/u_ij;
				}
				U.get(i).set(j, u_ij);
			}
		}
	}
	
	private void atualizaPesos(){
		ArrayList<Double> soma;
		double uij_m;
		Vetor dados = lvq.getDados();
		Vetor pesos = lvq.getPesos();
		for(int i = 0; i < pesos.tamanho(); i++){
			for(int j = 0; j < dados.tamanho(); j++){
				int aux = lvq.getClasses().indexOf(new NeuronioPorClasse(pesos.get(i).getCl()));
				uij_m = U.get(aux).get(j);
				uij_m = Math.pow(uij_m, m);
				try {
					soma = OpVetor.subtrai(dados.get(j).getV(), pesos.get(i).getV());
					if(dados.get(j).getCl().equals(pesos.get(i).getCl()))
						soma = OpVetor.multiplica(soma, (1 - uij_m));
					else
						soma = OpVetor.multiplica(soma, (0 - uij_m));
					soma = OpVetor.multiplica(soma, aprendizagem);
					soma = OpVetor.soma(pesos.get(i).getV(), soma);
					lvq.getPesos().get(i).setV(soma);
				} catch (Exception e) { e.printStackTrace(); }				
			}
		}
	}
	
	private void atualizaAprendizagem(){
		aprendizagem = aprendizagemInicial *(1-(epoca/epocas));
	}
	
	private boolean mudancaInsatisfatoria(){
		ArrayList<Double> subtracao;
		for(int i = 0; i < lvq.getPesos().tamanho(); i++){
			try {
				subtracao = OpVetor.subtrai(lvq.getPesos().get(i).getV(), pesosAnterior.get(i).getV());
				for(int j = 0; j < lvq.getPesos().dimensao(); j++)
					if(Math.abs(subtracao.get(j)) > mudanca) return true;
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return false;
	}
	

	public static void main(String args[]){
		try{
			Vetor.pasta = "RITA//";
			String arquivo = "Data4";
			
			Vetor pesosIniciais = new Vetor();
			ArrayList<Double> dado = new ArrayList<Double>();
			dado.add(0.1493);
			dado.add(0.8143);
			pesosIniciais.add(new Dado(dado, "1"));
			dado = new ArrayList<Double>();
			dado.add(0.2575);
			dado.add(0.2435);
			pesosIniciais.add(new Dado(dado, "2"));
			dado = new ArrayList<Double>();
			dado.add(0.8407);
			dado.add(0.9293);
			pesosIniciais.add(new Dado(dado, "3"));
			
			for(int i = 0; i < 1; i++){
				FLVQS treinamento = new FLVQS(arquivo + ".txt", 2, 0.01, 0.001);
				treinamento.treina();
				System.out.println(arquivo);
				System.out.println("Inicialização Padrão");
				System.out.println("Aprendizado: 0.01");
				System.out.println("Parâmetro de Fuzzificação: 2");
				Teste treino = new Teste(arquivo + "-flvq.txt", arquivo + ".txt");
				treino.testaEQ();		
			}
		}catch(Exception e){
			System.out.println("Erro");
		}
	}
}
