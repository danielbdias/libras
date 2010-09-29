package flvq;

import java.io.File;
import java.io.IOException;

public class SeparadorArquivos {
		
	public static void main(String args[]){
		Vetor.pasta = "";
		String nome = "ASSETS_CONF";
		String raiz = "D:\\Comites\\";
		File pasta;
		int arco, cima, lateral, tremular, virar, zigzag, semmov, mov;
		try {
			for(int i = 1; i <= 3; i++){
			Vetor dados = new Vetor(raiz + "movimento.txt", true);
			Vetor treino = new Vetor();
			Vetor teste = new Vetor();
			int contador = 0;
			int tamanho = dados.tamanho();
			
			dados.embaralha();
			
			arco = 0;
			cima = 0;
			lateral = 0;
			tremular = 0;
			virar = 0;
			zigzag = 0;
			semmov = 0;
			mov = 0;
			
			for(Dado d : dados){
				if(d.getCl().equals("arco")){
					if(contador < tamanho*0.7 && arco < 16){					
							treino.add(d);
							arco++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("cima")){
					if(contador < tamanho*0.7 && cima < 46){					
						treino.add(d);
						cima++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("lateral")){
					if(contador < tamanho*0.7 && lateral < 17){					
						treino.add(d);
						lateral++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("tremular")){
					if(contador < tamanho*0.7 &&  tremular < 13){					
						treino.add(d);
						tremular++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("virar")){
					if(contador < tamanho*0.7 && virar < 16){					
						treino.add(d);
						virar++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("zig-zag")){
					if(contador < tamanho*0.7 && zigzag < 16){					
						treino.add(d);
						zigzag++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("sem-mov")){
					if(contador < tamanho*0.7 &&  semmov < 330){					
						treino.add(d);
						semmov++;
					}
					else{
						teste.add(d);
					}
				}
				if(d.getCl().equals("mov")){
					if(contador < tamanho*0.7 &&  mov < 130){					
						treino.add(d);
						mov++;
					}
					else{
						teste.add(d);
					}
				}
				contador++;
			}
			
			/*
			for(Dado d : dados){
				if(contador < tamanho*0.7){
					treino.add(d);
				}
				else{
					teste.add(d);
				}
				contador++;
			}
			*/
			
			pasta = new File(raiz + nome + " - E" + i);
		    pasta.mkdir();
			treino.salvaArquivo(raiz + nome + " - E"+i+"//treino.txt", true);
			teste.salvaArquivo(raiz + nome + " - E"+i+"//teste.txt", true);
			pasta = new File(raiz + nome + " - E" + (i+3));
		    pasta.mkdir();
			treino.salvaArquivo(raiz + nome + " - E"+(i+3)+"//treino.txt", true);
			teste.salvaArquivo(raiz + nome + " - E"+(i+3)+"//teste.txt", true);
			pasta = new File(raiz + nome + " - E" + (i+6));
		    pasta.mkdir();
			treino.salvaArquivo(raiz + nome + " - E"+(i+6)+"//treino.txt", true);
			teste.salvaArquivo(raiz + nome + " - E"+(i+6)+"//teste.txt", true);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
