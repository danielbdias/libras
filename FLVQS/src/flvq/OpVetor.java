package flvq;

import java.util.ArrayList;
import java.util.Collections;


public class OpVetor {
	
	public static ArrayList<Double> soma(ArrayList<Double> x, ArrayList<Double> y) throws Exception{
		if(x.size() != y.size()) throw new Exception("Tamanho de vetores diferentes");
		
		ArrayList<Double> soma = new ArrayList<Double>();
		for(int i = 0; i < x.size(); i++){
			soma.add(x.get(i) + y.get(i));
		}
		return soma;
	}
	
	public static ArrayList<Double> subtrai(ArrayList<Double> x, ArrayList<Double> y) throws Exception{
		if(x.size() != y.size()) throw new Exception("Tamanho de vetores diferentes");
		
		ArrayList<Double> sub = new ArrayList<Double>();
		for(int i = 0; i < x.size(); i++){
			sub.add(x.get(i) - y.get(i));
		}
		return sub;
	}
	
	public static ArrayList<Double> multiplica(ArrayList<Double> x, double y){
		
		ArrayList<Double> produto = new ArrayList<Double>();
		for(int i = 0; i < x.size(); i++){
			produto.add(x.get(i) * y);
		}
		return produto;
	}
	
	public static double distancia(ArrayList<Double> x, ArrayList<Double> y){
		return distanciaEuclidiana(x, y);
	}
	
	public static double distanciaManhattan(ArrayList<Double> x, ArrayList<Double> y){
		int i;
		double soma = 0;
		for(i = 0; i < x.size(); i++){
			soma = soma + Math.abs(x.get(i) - y.get(i));			
		}
		return soma;
	}
	
	public static double distanciaEuclidiana(ArrayList<Double> x, ArrayList<Double> y){
		int i;
		double soma = 0;
		for(i = 0; i < x.size(); i++){
			soma = soma + Math.pow(x.get(i) - y.get(i), 2);			
		}
		return Math.sqrt(soma);
	}
	
	public static double distanciaCamberra(ArrayList<Double> x, ArrayList<Double> y){
		int i;
		double soma = 0;
		for(i = 0; i < x.size(); i++){
			if(x.get(i) == 0 && y.get(i) == 0) return 0;
			soma = soma + (Math.abs(x.get(i) - y.get(i))/(Math.abs(x.get(i)) + Math.abs(y.get(i))));			
		}
		return soma;
	}
	
	public static double distanciaBrayCurtis(ArrayList<Double> x, ArrayList<Double> y){
		int i;
		double somaN = 0;
		double somaD = 0;
		for(i = 0; i < x.size(); i++){
			if(x.get(i) == 0 && y.get(i) == 0) return 0;
			somaN = somaN + (Math.abs(x.get(i) - y.get(i)));	
			somaD = somaD + (Math.abs(x.get(i) + y.get(i)));	
		}
		return somaN/somaD;
	}
	
	
	public static int minimo(ArrayList<Double> vetorTreinamento, Vetor pesos){
		double min = Double.MAX_VALUE;
		double dist;
		int result = 0;
		int i;
		for(i = 0; i < pesos.tamanho(); i++){
			dist = distancia(vetorTreinamento, pesos.get(i).getV());
			if(dist < min){ 
				min = dist;
				result = i;
			}
		}
		return result;	
	}
	
	public static double variancia(ArrayList<Double> vetor, double media){
		double soma = 0;
		double total = vetor.size();
		for(int i = 0; i < vetor.size(); i++)
			soma = soma + Math.pow((vetor.get(i) - media), 2);
		return soma/total;
	}
	
	public static double media(ArrayList<Double> vetor){
		double soma = 0;
		for(Double a : vetor){
			soma = soma + a;
		}
		return soma/vetor.size();
	}
	
	public static double mediana(ArrayList<Double> vetor){
		ArrayList<Double> copia = new ArrayList<Double>();
		copia.addAll(vetor);
		int mediana = (copia.size()+1)/2;
		Collections.sort(copia);
		return copia.get(mediana);
	}
	
	public static ArrayList<Double> calculaDistancia(Vetor vetor, Dado centroide){
		ArrayList<Double> distancias = new ArrayList<Double>();
		for(Dado d : vetor){
			distancias.add(OpVetor.distancia(d.getV(), centroide.getV()));
		}
		return distancias;
	}
}
