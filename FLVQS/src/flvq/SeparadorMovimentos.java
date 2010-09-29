package flvq;

import java.io.File;
import java.io.IOException;

public class SeparadorMovimentos {

	public static void main(String args[]){
		Vetor.pasta = "";
		String raiz;
		File pasta;
		try {			
			for(int i = 1; i <= 3; i++){
				raiz = "D:\\Comites\\T";
			Vetor treino = new Vetor(raiz + i + "\\movimento-treino.txt", true);
			Vetor teste = new Vetor(raiz + i + "\\movimento-teste.txt", true);
			
			for(int j = treino.tamanho() - 1; j >= 0; j--){
				if(treino.get(j).getCl().equals("tre"))
					treino.remove(j);
			}
			for(int j = teste.tamanho() - 1; j >= 0; j--){
				if(teste.get(j).getCl().equals("tre"))
					teste.remove(j);
			}
			
			raiz = "D:\\Comites\\MovST";
			pasta = new File(raiz + i);
		    pasta.mkdir();		    
			treino.salvaArquivo(raiz + i + "\\treino.txt", true);
			teste.salvaArquivo(raiz + i + "\\teste.txt", true);
			
			pasta = new File(raiz + (i+3));
		    pasta.mkdir();		    
			treino.salvaArquivo(raiz + (i+3) + "\\treino.txt", true);
			teste.salvaArquivo(raiz + (i+3) + "\\teste.txt", true);
			
			pasta = new File(raiz + (i+6));
		    pasta.mkdir();		    
			treino.salvaArquivo(raiz + (i+6) + "\\treino.txt", true);
			teste.salvaArquivo(raiz + (i+6) + "\\teste.txt", true);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
