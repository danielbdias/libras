package flvq;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LVQ {
	
	private Vetor dados;
	private Vetor pesos;
	private ArrayList<NeuronioPorClasse> classes;
	private int epoca;
	private int epocas;
	private double aprendizagem;
	private double aprendizagemFinal;
	private boolean porEpoca;
	private String nomeArquivo;
	private double mudanca;
	private Vetor pesosAnterior;
	
	public LVQ(String nomeArquivo, double aprendizagemInicial, double aprendizagemFinal) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-lvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 2);
		pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		this.aprendizagemFinal = aprendizagemFinal;
		aprendizagem = aprendizagemInicial;
		porEpoca = false;
	}
	
	public LVQ(double aprendizagemInicial, double mudanca, String nomeArquivo) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-lvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		//pesos = new Vetor(classes, dados);
		aprendizagem = aprendizagemInicial;
		this.epocas = Integer.MAX_VALUE;
		this.mudanca = mudanca;
		porEpoca = true;
	}
	
	public LVQ(double aprendizagemInicial, double mudanca, String nomeArquivo, Vetor pesosIniciais) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-lvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		//pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		//pesos = new Vetor(classes, dados);
		pesos = pesosIniciais;
		aprendizagem = aprendizagemInicial;
		this.epocas = Integer.MAX_VALUE;
		this.mudanca = mudanca;
		porEpoca = true;
	}
	
	public LVQ(String nomeArquivo, double aprendizagemInicial, int epocas) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-lvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		//pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		pesos = new Vetor(classes, dados);
		aprendizagem = aprendizagemInicial;
		this.epocas = epocas;
		porEpoca = true;
	}
	
	public LVQ(String nomeArquivo, String arquivoPesos, int nroNeuronios, Vetor dados, double aprendizagemInicial, int epocas) throws IOException{
		this.nomeArquivo = nomeArquivo;
		this.dados = dados;
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, nroNeuronios);
		pesos = new Vetor(arquivoPesos, true);
		aprendizagem = aprendizagemInicial;
		this.epocas = epocas;
		porEpoca = true;
	}
	
	public LVQ(String nomeArquivo, String arquivoPesos, int nroNeuronios, Vetor dados, double aprendizagemInicial, double aprendizagemFinal) throws IOException{
		this.nomeArquivo = nomeArquivo;
		this.dados = dados;
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, nroNeuronios);
		pesos = new Vetor(arquivoPesos, true);
		aprendizagem = aprendizagemInicial;
		this.aprendizagemFinal = aprendizagemFinal;
		porEpoca = false;
	}
	
	public LVQ(String nomeArquivo, Vetor dados, double aprendizagemInicial, int epocas){
		this.nomeArquivo = nomeArquivo;
		this.dados = dados;
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 2);
		pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());	
		aprendizagem = aprendizagemInicial;
		this.epocas = epocas;
		porEpoca = true;
	}
	
	public LVQ(String nomeArquivo, Vetor dados, double aprendizagemInicial, double aprendizagemFinal){
		this.nomeArquivo = nomeArquivo;
		this.dados = dados;
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 2);
		pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());	
		aprendizagem = aprendizagemInicial;
		this.aprendizagemFinal = aprendizagemFinal;
		porEpoca = false;
	}
	
	public LVQ(String nomeArquivo, String arquivoPesos, double aprendizagemInicial, int epocas) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-lvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 2);
		//pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		pesos = new Vetor(arquivoPesos, true);
		aprendizagem = aprendizagemInicial;
		this.epocas = epocas;
		porEpoca = true;
	}
	
	public LVQ(String nomeArquivo, double aprendizagemInicial) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-flvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		//pesos = new Vetor(classes, dados);
		aprendizagem = aprendizagemInicial;
	}
	
	public LVQ(String nomeArquivo, double aprendizagemInicial, Vetor pesosIniciais) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-flvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		pesos = pesosIniciais;
		aprendizagem = aprendizagemInicial;
	}
	
	public LVQ(String nomeArquivo, String arquivoPesos, double aprendizagemInicial) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-flvq.");
		dados = new Vetor(nomeArquivo, true);
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		//pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		pesos = new Vetor(arquivoPesos, true);
		aprendizagem = aprendizagemInicial;
	}
	
	public LVQ(Vetor dados, String nomeArquivo, double aprendizagemInicial){
		this.nomeArquivo = nomeArquivo.replace(".", "-flvq.");
		this.dados = dados;
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		//pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		pesos = new Vetor(classes, dados);
		aprendizagem = aprendizagemInicial;
	}
	
	public LVQ(Vetor dados, String nomeArquivo,String arquivoPesos, double aprendizagemInicial) throws IOException{
		this.nomeArquivo = nomeArquivo.replace(".", "-flvq.");
		this.dados = dados;
		ArrayList<String> classe = dados.retornaClasses();
		//pedeNumeroDeNeuronios(classe);
		nroNeuronioPorClasse(classe, 1);
		//pesos = new Vetor(classes, dados.dimensao(), dados.min(), dados.max());
		pesos = new Vetor(arquivoPesos, true);
		aprendizagem = aprendizagemInicial;
	}
	
	public LVQ(String arqPesos, String arqDados) throws IOException{
		dados = new Vetor(arqDados, true);
		pesos = new Vetor(arqPesos, true);
	}
	
	public LVQ(String arqPesos) throws IOException{
		pesos = new Vetor(arqPesos, true);
		ArrayList<String> classe = pesos.retornaClasses();
		nroNeuronioPorClasse(classe, 1);
	}
	
	public void treina(){		
		Dado atual;
		int neuronioEscolhido;
		//double coeficiente = 1;
		if(porEpoca){
			do{
				pesosAnterior = pesos.copia();
				//if(epoca > 50) coeficiente = 0.3;
				for(int i = 0; i < dados.tamanho(); i++){
					atual = dados.get(i);
					neuronioEscolhido = OpVetor.minimo(atual.getV(), pesos);
					atualizaPeso(atual, neuronioEscolhido);					
				}
				//reduzAprendizado(coeficiente);
				if(epoca == 8) pesos.salvaArquivo(nomeArquivo + "_8", true);
				epoca++;
			}while(epoca < epocas && mudancaInsatisfatoria());
			System.out.println("Epocas = " + epoca);
		}
		else{
			do{
				for(int i = 0; i < dados.tamanho(); i++){
					atual = dados.get(i);
					neuronioEscolhido = OpVetor.minimo(atual.getV(), pesos);
					atualizaPeso(atual, neuronioEscolhido);
				}
				reduzAprendizado(0.3);
				epoca++;
			}while(aprendizagem > aprendizagemFinal);
		}
		
		pesos.salvaArquivo(nomeArquivo, true);
	}
	
	public String[] testa(){
		int escolhido;
		int certo = 0;
		int total = 0;
		String[] resultado = new String[2];
		boolean esc[] = new boolean[pesos.tamanho()];
		String escolhidos = "";
		for(int k = 0; k < pesos.tamanho(); k++)
			esc[k] = false;
		for(int i = 0; i < dados.tamanho(); i++){
			escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);
			esc[escolhido] = true;
			if(pesos.get(escolhido).getCl().equals(dados.get(i).getCl())) certo++;
			total++;
			resultado[0] = resultado[0] + pesos.get(escolhido).getCl() + " ";
		}
		for(int k = 0; k < pesos.tamanho(); k++)
			if(esc[k] == false) escolhidos = escolhidos + Integer.toString(k) + " ";
		resultado[1] = "Certos: " + certo + " Total: " + total + " Porcentagem de acerto: " + (certo*100/total) + "% Neurônios não utilizados: " + escolhidos;
		return resultado;
	}
	
	private void atualizaPeso(Dado dado, int neuronioEscolhido){
		Dado neuronio = pesos.get(neuronioEscolhido);
		ArrayList<Double> resultado = dado.getV();
		try {
			resultado = OpVetor.subtrai(resultado, neuronio.getV());
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultado = OpVetor.multiplica(resultado, aprendizagem);
		
		if(dado.getCl().equals(neuronio.getCl()))
			try {
				resultado = OpVetor.soma(neuronio.getV(), resultado);
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			try {
				resultado = OpVetor.subtrai(neuronio.getV(), resultado);
			} catch (Exception e) {
				e.printStackTrace();
			}
		pesos.get(neuronioEscolhido).setV(resultado);
	}
	
	private void reduzAprendizado(double coeficienteDeReducao){
		aprendizagem = aprendizagem * coeficienteDeReducao;
	}
	
	protected void pedeNumeroDeNeuronios(ArrayList<String> classe){
		int aux;
		Scanner sc = new Scanner(System.in);
		classes = new ArrayList<NeuronioPorClasse>();
		for(String c : classe){
			System.out.println("Numero de neuronios para a classe " + c + ": ");
			aux = sc.nextInt();
			classes.add(new NeuronioPorClasse(c, aux));
		}
	}
	
	private void nroNeuronioPorClasse(ArrayList<String> classe, int num){
		classes = new ArrayList<NeuronioPorClasse>();
		for(String c : classe){
			classes.add(new NeuronioPorClasse(c, num));
		}
	}
	
	public int epoca(){
		return epoca;
	}
	
	public int numeroClasses(){
		return classes.size();
	}	
		
	public ArrayList<NeuronioPorClasse> getClasses() {
		return classes;
	}

	public Vetor getDados() {
		return dados;
	}

	public Vetor getPesos() {
		return pesos;
	}

	public void setPesos(Vetor pesos) {
		this.pesos = pesos;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public void salvaResultados(String nomeArq, String numArq, String[] resTreinamento, String[] resTeste){
		File pasta = new File("resultado");
		System.out.println(pasta.mkdir());
		File arq = new File("resultado/" + nomeArq);
		try{
			BufferedWriter s = new BufferedWriter(new FileWriter(arq, true));
			s.newLine();
			s.write("#" + numArq);
			s.newLine();
			s.write(resTreinamento[0].replace("null", ""));
			s.newLine();
			s.write(resTreinamento[1]);
			s.newLine();
			s.write(resTeste[0].replace("null", ""));
			s.newLine();
			s.write(resTeste[1]);
			s.newLine();
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private boolean mudancaInsatisfatoria(){
		ArrayList<Double> subtracao;
		for(int i = 0; i < pesos.tamanho(); i++){
			try {
				subtracao = OpVetor.subtrai(pesos.get(i).getV(), pesosAnterior.get(i).getV());
				for(int j = 0; j < pesos.dimensao(); j++)
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
			String arquivo = "Data5";
			
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
			//pesosIniciais.add(new Dado(dado, "3"));
			
			for(int i = 0; i < 1; i++){
				LVQ treinamento = new LVQ(0.1, 0.001, arquivo + ".txt");
				treinamento.treina();
				System.out.println(arquivo);
				System.out.println("Inicialização Padrão");
				System.out.println("Aprendizado: 0.1");
				System.out.println("Iterações: 8");
				Teste treino = new Teste(arquivo + "-lvq.txt", arquivo + ".txt");
				treino.testaEQ();		
			}
		}catch(Exception e){
			System.out.println("Erro");
		}
	}
	
}
