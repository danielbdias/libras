package flvq;

import java.io.IOException;

public class SeparadorOrientacao {
	
	public static void main(String args[]){
		Vetor.pasta = "";
		String raiz = "D:\\Comites\\";
		try {
			
			Vetor dados = new Vetor(raiz + "configuracao.txt", true);
						
			for(Dado d : dados){
				String classe = d.getCl();
				classe = classe.substring(classe.length()-1);
				if(		classe.equals("A") || 
						classe.equals("D") || 
						classe.equals("F") || 
						classe.equals("H") || 
						classe.equals("I") || 
						classe.equals("J") || 
						classe.equals("K") || 
						classe.equals("L") || 
						classe.equals("M") || 
						classe.equals("N") || 
						classe.equals("Q") || 
						classe.equals("R") || 
						classe.equals("T") ||
						classe.equals("V") || 
						classe.equals("W") || 
						classe.equals("Y") )
					d.setCl("cima");
				if(		classe.equals("B") || 
						classe.equals("U") || 
						classe.equals("G") )
					d.setCl("baixo");
				if(		classe.equals("C") || 
						classe.equals("Ç") || 
						classe.equals("E") || 
						classe.equals("O") || 
						classe.equals("S")  )
					d.setCl("redondo");
				if(		classe.equals("P") || 
						classe.equals("X")  )
					d.setCl("lateral");
				if(		classe.equals("M") || 
						classe.equals("N")  )
					d.setCl("diagonal");
			}
			dados.salvaArquivo(raiz + "orientacao.txt", true);						
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
