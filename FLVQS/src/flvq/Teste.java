package flvq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Teste {
	
	private Vetor dados;
	private Vetor pesos;
	private int confusao[][];
	private ArrayList<String> classesPesos;
	private ArrayList<String> classesDados;
	private double porcentagem;
	private double certo;
	private double total;
	private String resultado;
	private ArrayList<String> neuroniosNaoUtilizados;
	public ArrayList<Integer>[] erros;
	public ArrayList<Integer>[] erros2;
	public Vetor[] terceiraCamada;
	
	@SuppressWarnings("unchecked")
	public Teste(String arqPesos, String arqDados) throws IOException{
		dados = new Vetor(arqDados, true);
		pesos = new Vetor(arqPesos, true);
		classesPesos = new ArrayList<String>();
		classesDados = new ArrayList<String>();
		neuroniosNaoUtilizados = new ArrayList<String>();
		for(Dado peso : pesos){
			if(!classesPesos.contains(peso.getCl()))
				classesPesos.add(peso.getCl());
		}
		Collections.sort(classesPesos);
		for(Dado dado : dados){
			if(!classesDados.contains(dado.getCl()))
				classesDados.add(dado.getCl());
		}
		Collections.sort(classesDados);
		erros = new ArrayList[63];
		erros2 = new ArrayList[63];
		inicializaErros();
	}
	
	@SuppressWarnings("unchecked")
	public Teste(Vetor pesos, Vetor dados) throws IOException{
		this.dados = dados;
		this.pesos = pesos;
		classesPesos = new ArrayList<String>();
		classesDados = new ArrayList<String>();
		neuroniosNaoUtilizados = new ArrayList<String>();
		for(Dado peso : pesos){
			if(!classesPesos.contains(peso.getCl()))
				classesPesos.add(peso.getCl());
		}
		Collections.sort(classesPesos);
		for(Dado dado : dados){
			if(!classesDados.contains(dado.getCl()))
				classesDados.add(dado.getCl());
		}
		Collections.sort(classesDados);
		erros = new ArrayList[63];
		erros2 = new ArrayList[63];
		inicializaErros();
	}
	
	@SuppressWarnings("unchecked")
	public Teste(String arqPesos, Vetor dados) throws IOException{
		this.dados = dados;
		pesos = new Vetor(arqPesos, true);
		classesPesos = new ArrayList<String>();
		classesDados = new ArrayList<String>();
		neuroniosNaoUtilizados = new ArrayList<String>();
		for(Dado peso : pesos){
			if(!classesPesos.contains(peso.getCl()))
				classesPesos.add(peso.getCl());
		}
		Collections.sort(classesPesos);
		for(Dado dado : dados){
			if(!classesDados.contains(dado.getCl()))
				classesDados.add(dado.getCl());
		}
		Collections.sort(classesDados);
		erros = new ArrayList[63];
		erros2 = new ArrayList[63];
		inicializaErros();
	}
		
	public double getPorcentagem() {
		return porcentagem;
	}

	public double getCerto() {
		return certo;
	}
	
	public double getTotal() {
		return total;
	}

	public String getResultado() {
		return resultado;
	}

	public ArrayList<String> getNeuroniosNaoUtilizados() {
		return neuroniosNaoUtilizados;
	}
	
	private void inicializaErros(){
		for(int i = 0; i < erros.length; i++){
			erros[i] = new ArrayList<Integer>();
			erros2[i] = new ArrayList<Integer>();
		}
	}
	
	public void imprimeErros(){
		for(int i = 0; i < erros.length; i++){
			System.out.println((i+1) + ": " + erros[i]);
		}
		for(int i = 0; i < erros2.length; i++){
			System.out.println((i+1) + ": " + erros2[i]);
		}
	}
	
	public void somaErros(ArrayList<Integer> erroAnterior[], ArrayList<Integer> erroAnterior2[]){
		erros = erroAnterior;
		erros2 = erroAnterior2;
	}
	
	public ArrayList<Integer>[] getErros(){
		return erros;
	}
	
	public ArrayList<Integer>[] getErros2(){
		return erros2;
	}

	private void criaMatrizConfusao(){		
		confusao = new int[classesPesos.size()][classesDados.size()];
		for(int i = 0; i < classesPesos.size(); i++)
			for(int j = 0; j < classesDados.size(); j++)
				confusao[i][j] = 0;
	}
	
	public void imprimeMatrizConfusao(){
		System.out.println();
		System.out.print("\t");
		for(String dado : classesDados){
			System.out.print(dado + "\t");								
		}
		System.out.println();
		for(int i = 0; i < confusao.length; i++){			
			System.out.print(classesPesos.get(i) + "\t");	
			
			for(int j = 0; j < confusao[0].length; j++){
				System.out.print(confusao[i][j] + "\t");
			}
			
			System.out.println();
		}
		
	}
	
	public void testa(){
		int escolhido;
		certo = 0;
		total = 0;
		criaMatrizConfusao();
		boolean esc[] = new boolean[pesos.tamanho()];
		for(int k = 0; k < pesos.tamanho(); k++)
			esc[k] = false;
		for(int i = 0; i < dados.tamanho(); i++){
			escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);
			esc[escolhido] = true;
			if(pesos.get(escolhido).getCl().equals(dados.get(i).getCl())) certo++;			
			confusao[classesPesos.indexOf(pesos.get(escolhido).getCl())][classesDados.indexOf(dados.get(i).getCl())]++;
			
			//if(pesos.getV().contains(new Dado(dados.get(i).getCl())))
				total++;
			resultado = resultado + pesos.get(escolhido).getCl() + " ";
		}
		for(int k = 0; k < pesos.tamanho(); k++)
			if(esc[k] == false) neuroniosNaoUtilizados.add(Integer.toString(k));
		porcentagem = certo*100/total;
		imprimeMatrizConfusao();
	}
	
	public void testa(boolean[][] certos){
		int escolhido;
		certo = 0;
		total = 0;
		criaMatrizConfusao();
		boolean esc[] = new boolean[pesos.tamanho()];
		for(int k = 0; k < pesos.tamanho(); k++)
			esc[k] = false;
		for(int i = 0; i < dados.tamanho(); i++){
			escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);
			esc[escolhido] = true;
			if(pesos.get(escolhido).getCl().equals(dados.get(i).getCl())){
				certo++;
			}
			/*if(!erros[Integer.parseInt(pesos.get(escolhido).getCl())-1].contains(Integer.parseInt(dados.get(i).getCl()))){
				erros[Integer.parseInt(pesos.get(escolhido).getCl())-1].add(Integer.parseInt(dados.get(i).getCl()));
			}
			if(!erros2[Integer.parseInt(dados.get(i).getCl()) - 1].contains(Integer.parseInt(pesos.get(escolhido).getCl()))){
				erros2[Integer.parseInt(dados.get(i).getCl()) - 1].add(Integer.parseInt(pesos.get(escolhido).getCl()));
			}*/
			confusao[classesPesos.indexOf(pesos.get(escolhido).getCl())][classesDados.indexOf(dados.get(i).getCl())]++;
			
			//if(pesos.getV().contains(new Dado(dados.get(i).getCl())))
				total++;
			resultado = resultado + pesos.get(escolhido).getCl() + " ";
		}
		for(int k = 0; k < pesos.tamanho(); k++)
			if(esc[k] == false) neuroniosNaoUtilizados.add(Integer.toString(k));
		porcentagem = certo*100/total;
		imprimeMatrizConfusao();
	}
	
	public void testaEQ(){
		int escolhido;
		double eq = 0;
		certo = 0;
		total = 0;
		criaMatrizConfusao();
		boolean esc[] = new boolean[pesos.tamanho()];
		for(int k = 0; k < pesos.tamanho(); k++)
			esc[k] = false;
		for(int i = 0; i < dados.tamanho(); i++){
			escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);
			esc[escolhido] = true;
			if(pesos.get(escolhido).getCl().equals(dados.get(i).getCl())){
				certo++;
			}
			eq = eq + OpVetor.distancia(pesos.get(escolhido).getV(), dados.get(i).getV());
			confusao[classesPesos.indexOf(pesos.get(escolhido).getCl())][classesDados.indexOf(dados.get(i).getCl())]++;
			total++;
			resultado = resultado + pesos.get(escolhido).getCl() + " ";
		}
		for(int k = 0; k < pesos.tamanho(); k++)
			if(esc[k] == false) neuroniosNaoUtilizados.add(Integer.toString(k));
		porcentagem = certo/total;		
		imprimeMatrizConfusao();
		System.out.println("Erro = " + (1 - porcentagem));
		System.out.println("EQ = " + eq/total);
	}
	
	public void testaSemImpressao(){
		int escolhido;
		certo = 0;
		total = 0;
		criaMatrizConfusao();
		boolean esc[] = new boolean[pesos.tamanho()];
		for(int k = 0; k < pesos.tamanho(); k++)
			esc[k] = false;
		for(int i = 0; i < dados.tamanho(); i++){
			escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);
			esc[escolhido] = true;
			if(pesos.get(escolhido).getCl().equals(dados.get(i).getCl())) certo++;
			/*if(!erros[Integer.parseInt(pesos.get(escolhido).getCl())-1].contains(Integer.parseInt(dados.get(i).getCl()))){
				erros[Integer.parseInt(pesos.get(escolhido).getCl())-1].add(Integer.parseInt(dados.get(i).getCl()));
			}
			if(!erros2[Integer.parseInt(dados.get(i).getCl()) - 1].contains(Integer.parseInt(pesos.get(escolhido).getCl()))){
				erros2[Integer.parseInt(dados.get(i).getCl()) - 1].add(Integer.parseInt(pesos.get(escolhido).getCl()));
			}*/
			confusao[classesPesos.indexOf(pesos.get(escolhido).getCl())][classesDados.indexOf(dados.get(i).getCl())]++;
			
			//if(pesos.getV().contains(new Dado(dados.get(i).getCl())))
				total++;
			resultado = resultado + pesos.get(escolhido).getCl() + " ";
		}
		for(int k = 0; k < pesos.tamanho(); k++)
			if(esc[k] == false) neuroniosNaoUtilizados.add(Integer.toString(k));
		porcentagem = certo*100/total;
		//imprimeMatrizConfusao();
	}
		
	public Vetor[] testaSepara(Vetor classeSeparada[]) throws Exception{
		int escolhido;
		certo = 0;
		total = 0;
		criaMatrizConfusao();
		boolean esc[] = new boolean[pesos.tamanho()];
		for(int k = 0; k < pesos.tamanho(); k++)
			esc[k] = false;
		for(int i = 0; i < dados.tamanho(); i++){
			escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);
			esc[escolhido] = true;
			if(pesos.get(escolhido).getCl().equals(dados.get(i).getCl())) certo++;
			total++;
			
			classeSeparada[classesPesos.indexOf((pesos.get(escolhido).getCl()))].add(new Dado(dados.get(i).getV(), dados.get(i).getCl()));
			/*if(!erros[Integer.parseInt(pesos.get(escolhido).getCl())-1].contains(Integer.parseInt(dados.get(i).getCl()))){
				erros[Integer.parseInt(pesos.get(escolhido).getCl())-1].add(Integer.parseInt(dados.get(i).getCl()));
			}
			if(!erros2[Integer.parseInt(dados.get(i).getCl()) - 1].contains(Integer.parseInt(pesos.get(escolhido).getCl()))){
				erros2[Integer.parseInt(dados.get(i).getCl()) - 1].add(Integer.parseInt(pesos.get(escolhido).getCl()));
			}*/
			confusao[classesPesos.indexOf(pesos.get(escolhido).getCl())][classesDados.indexOf(dados.get(i).getCl())]++;
			resultado = resultado + pesos.get(escolhido).getCl() + " ";
		}
		for(int k = 0; k < pesos.tamanho(); k++)
			if(esc[k] == false) neuroniosNaoUtilizados.add(Integer.toString(k));
		porcentagem = certo*100/total;
		imprimeMatrizConfusao();
		return classeSeparada;
	}
	
	public String[][] testar() throws Exception{
		String[][] retorno = new String[this.dados.tamanho()][];
		
		for(int i = 0; i < dados.tamanho(); i++){
			int escolhido = OpVetor.minimo(dados.get(i).getV(), pesos);

			retorno[i] = new String[] { dados.get(i).getCl(), pesos.get(escolhido).getCl()};
		}
		
		return retorno;
	}
}
