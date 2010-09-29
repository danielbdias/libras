package flvq;

import java.io.IOException;
import java.util.ArrayList;

public class ComiteMovimento {

	/*public static void main(String args[]){

		for(int w = 1; w <= 3; w++){
			Vetor.pasta = "D:\\Comites\\T" + w + "\\";
			System.out.println("ASSETS_CONF - E" + w);
			System.out.println();
			try{					

				ArrayList<Integer> erros[] = null;
				ArrayList<Integer> erros2[] = null;

				Vetor arqTeste = new Vetor("mov-smcm_treino.txt", true);
				int tamanho = arqTeste.tamanho();
				String[][] certos = new String[2][tamanho];
				for(int i = 0; i < tamanho; i++){
					certos[0][i] = "errado";
					certos[1][i] = "certo";
				}
				Vetor[] separaClasse = new Vetor[20];
				for(int i = 0; i < 20; i++)
					separaClasse[i] = new Vetor();
				double media;
				double total;
				double certo;
				media = 0;
				certo = 0;
				total = 0;

				FLVQS treinamento = new FLVQS("mov-smcm_treino.txt", 2, 0.07, 200);
				treinamento.treina();
				Teste treino = new Teste("mov-smcm_treino-flvq.txt", "mov-smcm_treino.txt");
				treino.testaSemImpressao();

				Teste teste = new Teste("mov-smcm_treino-flvq.txt", "mov-smcm_teste.txt");
				if(erros != null && erros2 != null) teste.somaErros(erros, erros2);
				separaClasse = teste.testaSepara(separaClasse);
				erros = teste.getErros();
				erros2 = teste.getErros2();
				total = total + teste.getTotal();
				certo = certo + teste.getCerto();
				if(!Double.isNaN(teste.getPorcentagem()))media = media + teste.getPorcentagem();
				System.out.println(teste.getPorcentagem()+ "%");


				System.out.print("Segunda Camada: ");
				System.out.print("Certo = " + certo + "   ");
				System.out.print("Total = " + total + "   ");
				System.out.println("Media = " + certo*100/total + "%");

				System.out.println();
				System.out.println();

			}catch(Exception e){e.printStackTrace();}}

	}*/

	
		
		public double errosPrimeiraCamada(Vetor treino, Vetor teste){
			ArrayList<String> classesTreino = new ArrayList<String>();
			ArrayList<String> classesTeste = new ArrayList<String>();
			ArrayList<String> classesTesteSemTreino = new ArrayList<String>();
			ArrayList<String> classesTreinoSemTeste = new ArrayList<String>();
			
			int certo = 0;
			int total = 0;
			
			for(Dado d : treino){
				if(!classesTreino.contains(d.getCl())) classesTreino.add(d.getCl());
			}
			
			for(Dado d : teste){
				if(!classesTeste.contains(d.getCl())) classesTeste.add(d.getCl());
				if(classesTreino.contains(d.getCl())) certo++;
				total++;
			}
			
			classesTreinoSemTeste.addAll(classesTreino);
			classesTreinoSemTeste.removeAll(classesTeste);
			classesTesteSemTreino.addAll(classesTeste);
			classesTesteSemTreino.removeAll(classesTreino);
			
			//System.out.println("Classes do treinamento: " + classesTreino);
			//System.out.println("Classes do teste: " + classesTeste);
			//System.out.println("Classes do treinamento que não aparecem no teste: " + classesTreinoSemTeste);
			//System.out.println("Classes do teste que não foram treinadas: " + classesTesteSemTreino);
			
			System.out.println("Certo: " + certo + " Total: "+ total + " Porcentagem de acerto: " + ((double)certo/(double)total)*100 + "%");
			return ((double)certo/(double)total)*100;
		}

		public static void main(String args[]){

			for(int w = 1; w <= 9; w++){
				Vetor.pasta = "D:\\Comites\\Final\\MovimentoST2\\ASSETS_CONF - E" + w + "//";
				System.out.println("Mov" + w);
				System.out.println();
			try{
				for(int a = 1; a <= 6; a++){
					System.out.println(a + " clusters");
					try{				
						
						FLVQ treino = new FLVQ("treino.txt", a, 1.25, 1.01, 30, 0.001);
						treino.clusteriza("treino-fld" + a + ".txt");
						treino.testaSemImpressao();
						System.out.println("DB-index FLVQ: " + Double.toString(treino.DBIndex(treino.separaDados())));
						System.out.println(treino.DBIndex(treino.separaDados()));
						FLVQ tTreino = new FLVQ("treino.txt", "treino-fld" + a + ".txt");
						tTreino.escreveArquivos("fld_" + a +"_.txt", tTreino.separaDados(), "treino");
						
						FLVQ tTeste = new FLVQ("teste.txt", "treino-fld" + a + ".txt");
						tTeste.escreveArquivos("fld_" + a +"_.txt", tTeste.separaDados(), "teste");				
						
					}catch(IOException ioe){
						ioe.printStackTrace();
					}
					
					
					ArrayList<Integer> erros[] = null;
					ArrayList<Integer> erros2[] = null;
					
					Vetor arqTeste = new Vetor("teste.txt", true);
					int tamanho = arqTeste.tamanho();
					String[][] certos = new String[2][tamanho];
					for(int i = 0; i < tamanho; i++){
						certos[0][i] = "errado";
						certos[1][i] = "certo";
					}
					
					String numArq;
					Vetor[] separaClasse = new Vetor[20];
					for(int i = 0; i < 20; i++)
						separaClasse[i] = new Vetor();
					double media;
					double total;
					double certo;
					int i;
					media = 0;
					certo = 0;
					total = 0;
					for(i = 0; i < a; i++){
						System.out.print("Rede " + i + ": ");
						numArq = Integer.toString(i);
						try{
							
							FLVQS treinamento = new FLVQS("fld_" + a +"_treino_"+ numArq +".txt", 1.1, 0.05, 70);
							treinamento.treina();
							Teste treino = new Teste("fld_" + a +"_treino_"+ numArq +"-flvq.txt", "fld_" + a +"_treino_"+ numArq +".txt");
							treino.testaSemImpressao();
									
							Teste teste = new Teste("fld_" + a +"_treino_"+ numArq +"-flvq.txt", "fld_" + a +"_teste_"+ numArq +".txt");
							if(erros != null && erros2 != null) teste.somaErros(erros, erros2);
							separaClasse = teste.testaSepara(separaClasse);
							erros = teste.getErros();
							erros2 = teste.getErros2();
							total = total + teste.getTotal();
							certo = certo + teste.getCerto();
							if(!Double.isNaN(teste.getPorcentagem()))media = media + teste.getPorcentagem();
							System.out.println(teste.getPorcentagem()+ "%");
						}
						catch(IOException ioe){
							ioe.printStackTrace();
						}
					}
					System.out.print("Segunda Camada: ");
					System.out.print("Certo = " + certo + "   ");
					System.out.print("Total = " + total + "   ");
					System.out.println("Media = " + certo*100/total + "%");
					
					System.out.println();
					System.out.println();
				}
			}catch(Exception e){e.printStackTrace();}}

		}
	}


