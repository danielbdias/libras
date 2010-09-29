package flvq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Vetor implements Iterable<Dado>{
	
	private ArrayList<Dado> v;
	private int tamanho = 0;
	private int dimensao = 0;
	public static String pasta;
		
	public ArrayList<Dado> getV() {
		return v;
	}

	public Vetor(){
		v = new ArrayList<Dado>();
	}
	
	public Vetor(String nomeArquivo, boolean supervisionada) throws IOException{
		v = new ArrayList<Dado>();
		carregaArquivo(nomeArquivo, supervisionada);
	}
	
	public Vetor(int tamanho, int dimensao, double min, double max){
		v = new ArrayList<Dado>();
		inicializa(tamanho, dimensao);
		inicializaAleatorio(min, max);
	}
		
	public Vetor(ArrayList<NeuronioPorClasse> neuroniosPorClasse, int dimensao, double min, double max){
		int contador = 0;
		v = new ArrayList<Dado>();
		for(NeuronioPorClasse neuronio : neuroniosPorClasse){
			for(int i = 0; i < neuronio.getNumeroDeNeuronios(); i++){
				v.add(new Dado(neuronio.getClasse()));
				contador++;
			}
		}
		tamanho = contador;
		this.dimensao = dimensao;
		inicializaAleatorio(min, max);
	}
	
	public Vetor(ArrayList<NeuronioPorClasse> neuroniosPorClasse, Vetor dados){
		int contador = 0;
		v = new ArrayList<Dado>();
		for(NeuronioPorClasse neuronio : neuroniosPorClasse){
			for(int i = 0; i < neuronio.getNumeroDeNeuronios(); i++){
				v.add(new Dado(neuronio.getClasse()));
				contador++;
			}
		}
		tamanho = contador;
		this.dimensao = dados.dimensao();
		inicializaAPriori(dados);
	}
	
	private Vetor(ArrayList<Dado> vetor){
		v = vetor;
		tamanho = vetor.size();
		dimensao = vetor.get(0).getV().size();
	}
	
	public void addAll(Vetor vetor){
		for(int i = 0; i < vetor.tamanho(); i++)
			add(vetor.get(i));
	}
	
	public void add(Dado d){
		v.add(new Dado(d.getV(), d.getCl()));
		tamanho++;
		if(dimensao == 0) dimensao = d.getV().size();
	}
	
	public void set(Dado d, int indice){
		v.set(indice, d);
	}
	
	public Dado get(int indice){
		return v.get(indice);
	}
	
	public void set(int i, int j, double num){
		v.get(i).getV().set(j, num);
	}
	
	public double get(int i, int j){
		return v.get(i).getV().get(j);
	}
	
	public int tamanho(){
		return tamanho;
	}
	
	public int dimensao(){
		return dimensao;
	}
	
	public void imprime(){
		for(Dado d : v){
			System.out.println(d.getV().toString());
		}
	}
	
		
	public void inicializa(int tamanho, int dimensao){
		this.tamanho = tamanho;
		this.dimensao = dimensao;
		for(int i = 0; i < tamanho; i++){
			v.add(new Dado(new ArrayList<Double>(), ""));
			for(int j = 0; j < dimensao; j++){
				v.get(i).getV().add(0.0);
			}
		}
	}
	
	public void inicializaAleatorio(double min, double max){
		
		for(int i = 0; i < tamanho; i++){
			v.get(i).setV(new ArrayList<Double>());
			for(int j = 0; j < dimensao; j++){
				v.get(i).getV().add(0.0);
				set(i, j, (Math.random() * (max - min))+ min);
			}
		}
	}
	
	public void inicializaCentro(double min, double max){
		
		for(int i = 0; i < tamanho; i++){
			v.get(i).setV(new ArrayList<Double>());
			for(int j = 0; j < dimensao; j++){
				v.get(i).getV().add(0.5);
			}
		}
	}
	
	public void inicializaAPriori(Vetor dados){		
		int i;
		for(Dado e : this){
			i = dados.indexOf(e);
			e.setV(dados.get(i).getV());
			//dados.remove(i);
		}
	}
	
	public double min(){
		double min = Double.POSITIVE_INFINITY;
		double atual;
		for(Dado d : v){
			atual = Collections.min(d.getV());
			if(atual < min)
				min = atual;
		}
		return min;
	}
	
	public double max(){
		double max = Double.NEGATIVE_INFINITY;
		double atual;
		for(Dado d : v){
			atual = Collections.max(d.getV());
			if(atual > max)
				max = atual;
		}
		return max;
	}
	
	public void embaralha(){
		Collections.shuffle(v);
	}
	
	public void normaliza(double min, double max){
		for(int i = 0; i < tamanho; i++){
			for(int j = 0; j < dimensao; j++){
				set(i, j, (get(i, j)/max - get(i,j)/min));
			}
		}
	}
	
	public Vetor copia(){
		ArrayList<Dado> copia = new ArrayList<Dado>();
		for(Dado d : v){
			copia.add(new Dado(d.getV(), d.getCl()));
		}
		Vetor novo = new Vetor(copia);
		return novo;		
	}
	
	public ArrayList<String> retornaClasses(){
		ArrayList<String> classes = new ArrayList<String>();
		for(Dado d : v){
			if(!classes.contains(d.getCl())) classes.add(d.getCl());
		}
		return classes;
	}
	
	public void carregaArquivo(String nomeArq, boolean supervisionada) throws IOException{
		File arq = new File(pasta + nomeArq);
		//File arq = new File(nomeArq);
		
		String linha; 
		String[] palavra;
		ArrayList<Double> vetor;
		int fimVetor = 0;
		String classe;
		
			BufferedReader e = new BufferedReader(new FileReader(arq));
			while((linha = e.readLine()) != null){
				vetor = new ArrayList<Double>();
				
				palavra = linha.split(",");
				if(supervisionada){
					fimVetor = palavra.length - 1;
					classe = palavra[fimVetor];
				}
				else{
					fimVetor = palavra.length;
					classe = "x";
				}				
				for(int i = 0; i < fimVetor; i++)
					vetor.add(Double.parseDouble(palavra[i]));
	
				v.add(new Dado(vetor, classe));
			}
			if(v.size() > 0) dimensao = v.get(0).getV().size();
			tamanho = v.size();
			e.close();
	}
	
	public void salvaArquivo(String nomeArq, boolean supervisionado){
		/*File pasta = new File("arquivos");
	    System.out.println(pasta.mkdir());*/
		File arq = new File(pasta + nomeArq);
		
		String linha;
		Dado d;
		double num;
		try{
			BufferedWriter s = new BufferedWriter(new FileWriter(arq));
			for(int i = 0; i < tamanho; i++){
				d = get(i);
				linha = "" + d.getV().get(0);
				for(int j = 1; j < dimensao; j++){
					num = d.getV().get(j);
					linha = linha + "," + num; 
				}
				if(supervisionado) linha = linha + "," + d.getCl();
				s.write(linha);
				s.newLine();
			}
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<Dado> iterator() {
		return v.iterator();
	}	
	
	public int indexOf(Dado e){
		return v.indexOf(e);
	}
	
	public void remove(int i){
		v.remove(i);
		this.tamanho--;
	}
	
}
