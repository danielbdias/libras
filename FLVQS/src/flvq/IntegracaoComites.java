package flvq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class IntegracaoComites {

	public static void main(String args[]){
		IntegracaoComites c;
		/*= new IntegracaoComites();
		try {
			c.carregarDados();
			c.separarDados();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		try {
			for(int i = 15; i <= 15; i++)
				for(int j = 4; j <= 4; j++){
					c = new IntegracaoComites();
					System.out.println("Comites: Configuracao " + i + " - Movimento " + j);
					c.carregarDados(i,j);
					c.aplicarRegras();
					//c.comparaOrientacao();
					System.out.println();
				}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	public void comparaOrientacao(){
		double certo = 0;
		double total = 0;
		for(DadoCompleto d : dados){
			if(compara(d.getLetraCorreta(), d.getClasseOrientacao())){
				certo++;
			}
			total++;			
		}
		System.out.println("Certo: " + certo + " Total: " + total + " Media: " + certo/total + "%");
	}
	
	public boolean compara(String classe, String orientacao){
		String intermediario = "";
		if(		classe.equals("Q") ||
				classe.equals("M") || 
				classe.equals("N") )
			intermediario = "cima";
		if(		classe.equals("U") || 
				classe.equals("G") )
			intermediario = "baixo";
		if(		classe.equals("A") || 
				classe.equals("B") || 
				classe.equals("C") || 
				classe.equals("Ç") || 				
				classe.equals("D") ||
				classe.equals("E") ||
				classe.equals("F") ||  
				classe.equals("I") || 
				classe.equals("J") ||
				classe.equals("H") || 
				classe.equals("K") || 
				classe.equals("L") ||
				classe.equals("O") ||
				classe.equals("R") ||  
				classe.equals("S") ||
				classe.equals("T") ||
				classe.equals("V") || 				 
				classe.equals("W") || 
				classe.equals("Y") ||
				classe.equals("Z") )
			intermediario = "vertical";
		if(		classe.equals("P") || 
				classe.equals("X") )
			intermediario = "horizontal";

			System.out.println("Classe: " + classe + " Orientacao: " + orientacao + 
				" Intermediario: " + intermediario);
		
		if(		intermediario.equals("cima") && 
				orientacao.equals("cima"))
			return true;
		if(		intermediario.equals("baixo") && 
				orientacao.equals("baixo"))
			return true;
		if(		intermediario.equals("vertical") && 
				(orientacao.equals("cima") || 
				 orientacao.equals("baixo")))
			return true;		
		if(		intermediario.equals("horizontal") && 
				(orientacao.equals("esquerda") || 
				 orientacao.equals("direita")))
			return true;

		System.out.println("**********************************ERRO**********************************");
		return false;
	}
	
	public void carregarDados() throws IOException{
		Vetor.pasta = "";
		Vetor configuracao = new Vetor("D:\\Comites\\configuracao.txt", true);
		//Vetor movimento = new Vetor("D:\\Comites\\movimento.txt", true);
				
		DadoCompleto atual;
		//int procurado;
		
		for(Dado dado : configuracao){
			atual = new DadoCompleto(dado.getCl());
			atual.setConfiguracao(dado.getV());		
			atual.setPertinenciaOrientacao(calculaPertinenciaOrientacao(dado));
			atual.setClasseOrientacao(orientacoes.get(indiceMaior(atual.getPertinenciaOrientacao())));
			dados.add(atual);
		}
		
		/*for(Dado dado : movimento){
			atual = new DadoCompleto(dado.getCl());
			procurado = dados.indexOf(atual);
			atual = dados.get(procurado);
			atual.setMovimento(dado.getV());
		}*/
	}
	
	public void separarDados(){
		Vetor configuracaoTreino = new Vetor();
		Vetor configuracaoTeste = new Vetor();
		Vetor movimentoTreino = new Vetor();
		Vetor movimentoTeste = new Vetor();
		int contador = 0;
		
		Collections.shuffle(dados);
		
		for(DadoCompleto dado : dados){
			if(contador < dados.size()*0.7){
				configuracaoTreino.add(new Dado(dado.getConfiguracao(), dado.getNome()));
				movimentoTreino.add(new Dado(dado.getMovimento(), dado.getNome()));
			}
			else{
				configuracaoTeste.add(new Dado(dado.getConfiguracao(), dado.getNome()));
				movimentoTeste.add(new Dado(dado.getMovimento(), dado.getNome()));
			}
			contador++;			
		}
		Vetor.pasta = "D:\\Comites\\";
		configuracaoTreino.salvaArquivo("configuracao_treino.txt", true);
		configuracaoTeste.salvaArquivo("configuracao_teste.txt", true);
		movimentoTreino.salvaArquivo("movimento_treino.txt", true);
		movimentoTeste.salvaArquivo("movimento_teste.txt", true);
		
	}
	
	ArrayList<String> classes = classesDados();
	ArrayList<String> configuracoes = classesConfiguracoes();
	ArrayList<String> movimentos = classesMovimentos();
	ArrayList<String> orientacoes = classesOrientacoes();
	ArrayList<DadoCompleto> dados = new ArrayList<DadoCompleto>();
	int[][] confusao;
	
	private ArrayList<String> classesDados(){
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("A");
		classes.add("B");
		classes.add("C");
		classes.add("D");
		classes.add("E");
		classes.add("F");
		classes.add("G");
		classes.add("H");
		classes.add("I");
		classes.add("J");
		classes.add("K");
		classes.add("L");
		classes.add("M");
		classes.add("N");
		classes.add("O");
		classes.add("P");
		classes.add("Q");
		classes.add("R");
		classes.add("S");
		classes.add("T");
		classes.add("U");
		classes.add("V");
		classes.add("W");
		classes.add("X");
		classes.add("Y");
		classes.add("Z");
		classes.add("Ç");
		return classes;
	}
	
	private ArrayList<String> classesConfiguracoes(){
		ArrayList<String> configuracoes = new ArrayList<String>();
		configuracoes.add("A");
		configuracoes.add("B");
		configuracoes.add("C/Ç");
		configuracoes.add("D");
		configuracoes.add("E");
		configuracoes.add("F");
		configuracoes.add("G/Q/Z");
		configuracoes.add("H/K/P");
		configuracoes.add("I/J");
		configuracoes.add("L");
		configuracoes.add("M");
		configuracoes.add("N/U");
		configuracoes.add("O");
		configuracoes.add("R");
		configuracoes.add("S");
		configuracoes.add("T");
		configuracoes.add("V");
		configuracoes.add("W");
		configuracoes.add("X");
		configuracoes.add("Y");
		return configuracoes;
	}
	
	private ArrayList<String> classesMovimentos(){
		ArrayList<String> movimentos = new ArrayList<String>();
		movimentos.add("arco");
		movimentos.add("cima");
		movimentos.add("lat");
		movimentos.add("sm");
		//movimentos.add("tre");
		movimentos.add("vira");
		movimentos.add("zig");
		return movimentos;
	}
	
	private ArrayList<String> classesOrientacoes(){
		ArrayList<String> orientacoes = new ArrayList<String>();
		orientacoes.add("baixo");
		orientacoes.add("cima");
		orientacoes.add("direita");
		orientacoes.add("esquerda");
		return orientacoes;
	}
	
	public ArrayList<Double> calculaPertinenciaConfiguracao(Dado dado, int comite){
		ArrayList<Double> pertinenciaParcial = new ArrayList<Double>();
		ArrayList<String> classesParcial = new ArrayList<String>();		
		ArrayList<Double> pertinencia = new ArrayList<Double>();
		try{				
			int cluster;		
			FLVQ primeiraCamada = new FLVQ("D:\\Comites\\Final\\Assinatura\\ASSETS_CONF - E4\\treino-fld" + comite + ".txt");
			cluster = primeiraCamada.defineCluster(dado);
			FLVQS segundaCamada = new FLVQS("D:\\Comites\\Final\\Assinatura\\ASSETS_CONF - E4\\fld_" + comite + "_treino_" + cluster + "-flvq.txt", 1.15);
			pertinenciaParcial = segundaCamada.extraiPertinencia(dado);
			classesParcial = segundaCamada.retornaClassesEmOrdem();
			configuracoes = classesConfiguracoes();
			for(int i = 0; i < configuracoes.size(); i++){
				int indice = classesParcial.indexOf(configuracoes.get(i));
				if(indice < 0) pertinencia.add(i, 0.0);
				else pertinencia.add(i, pertinenciaParcial.get(indice));
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}		
		return pertinencia;
	}
	
	public ArrayList<Double> calculaPertinenciaMovimento(Dado dado, int comite){
		ArrayList<Double> pertinenciaParcial = new ArrayList<Double>();
		ArrayList<String> classesParcial = new ArrayList<String>();		
		ArrayList<Double> pertinencia = new ArrayList<Double>();
		try{				
			int cluster;		
			FLVQ primeiraCamada = new FLVQ("D:\\Comites\\Final\\MovimentoST\\ASSETS_CONF - E4\\treino-fld" + comite + ".txt");
			cluster = primeiraCamada.defineCluster(dado);
			FLVQS segundaCamada = new FLVQS("D:\\Comites\\Final\\MovimentoST\\ASSETS_CONF - E4\\fld_" + comite + "_treino_" + cluster + "-flvq.txt", 1.15);
			pertinenciaParcial = segundaCamada.extraiPertinencia(dado);
			classesParcial = segundaCamada.retornaClassesEmOrdem();
			movimentos = classesMovimentos();
			for(int i = 0; i < movimentos.size(); i++){
				int indice = classesParcial.indexOf(movimentos.get(i));
				if(indice < 0) pertinencia.add(i, 0.0);
				else pertinencia.add(i, pertinenciaParcial.get(indice));
			}
			pertinencia.set(movimentos.indexOf("sm"), Math.pow(pertinencia.get(movimentos.indexOf("sm")), 3));			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}		
		return pertinencia;
	}
	
	public ArrayList<Double> calculaPertinenciaOrientacao(Dado dado){
		ArrayList<Double> pertinencia = new ArrayList<Double>();
		ArrayList<Double> orientacao = dado.getV();
		double baixo = 0;
		double cima = 0;
		double direita = 0;
		double esquerda = 0;
		double total = 0;
		double media = 0;
		double dp2 = 0;
		int i;		
		
		for(i = 0; i < 36; i++){						
			total = total + orientacao.get(i);
		}
		media = total/36;
		
		for(i = 0; i < 36; i++){						
			dp2 = dp2 + Math.pow((orientacao.get(i) - media),2);
		}
		dp2 = 2*Math.sqrt(dp2/36);
				
		for(i = 0; i < 36; i++){
			if(i >= 4 && i <= 14) cima = cima + realmente(orientacao.get(i), dp2);
			if(i >= 13 && i <= 23) direita = direita + realmente(orientacao.get(i), dp2);
			if(i >= 22 && i <= 32) baixo = baixo + realmente(orientacao.get(i), dp2);
			if(i >= 31 || i <= 5) esquerda = esquerda + realmente(orientacao.get(i), dp2);
			total = total + realmente(orientacao.get(i), dp2);
		}
		
		pertinencia.add(baixo/total);
		pertinencia.add(cima/total);
		pertinencia.add(direita/total);
		pertinencia.add(esquerda/total);
		return pertinencia;
	}
	
	private double realmente(double num, double dp2){
		/*if(num >= dp2) return Math.pow(num, 1/4);
		else if(num >= dp2/2) return Math.pow(num, 1);
		else return Math.pow(num, 4);*/
		if(num > 0.5) return Math.pow(num, 0.5);
		else return Math.pow(num, 2);
		//return num;
		
	}
	
	public void carregarDados(int comiteConfiguracao, int comiteMovimento) throws IOException{
		Vetor.pasta = "";
		Vetor configuracao = new Vetor("D:\\Comites\\Final\\T2\\conf_teste.txt", true);
		Vetor movimento = new Vetor("D:\\Comites\\Final\\T2\\mov_teste.txt", true);
				
		DadoCompleto atual;
		int procurado;
		
		for(Dado dado : configuracao){
			atual = new DadoCompleto(dado.getCl());
			atual.setConfiguracao(dado.getV());
			atual.setPertinenciaConfiguracao(calculaPertinenciaConfiguracao(dado, comiteConfiguracao));
			atual.setPertinenciaOrientacao(calculaPertinenciaOrientacao(dado));
			atual.setClasseConfiguracao(configuracoes.get(indiceMaior(atual.getPertinenciaConfiguracao())));
			atual.setClasseOrientacao(orientacoes.get(indiceMaior(atual.getPertinenciaOrientacao())));
			dados.add(atual);
		}
		for(Dado dado : movimento){
			atual = new DadoCompleto(dado.getCl());
			procurado = dados.indexOf(atual);
			atual = dados.get(procurado);
			atual.setMovimento(dado.getV());
			atual.setPertinenciaMovimento(calculaPertinenciaMovimento(dado, comiteMovimento));
			atual.setClasseMovimento(movimentos.get(indiceMaior(atual.getPertinenciaMovimento())));
		}
	}
	
	public int indiceMaior(ArrayList<Double> vetor){
		int indice = 0;
		double maior = 0;
		for(int i = 0; i < vetor.size(); i++){
			if(vetor.get(i) > maior){
				indice = i;
				maior = vetor.get(i);
			}
		}
		return indice;
	}
	
	public void aplicarRegras(){
		ArrayList<Double> regras;
		int ganhadora;		
		int certo = 0;
		int total = 0;
		criaMatrizConfusao();
		
		for(DadoCompleto dado : dados){
			regras = new ArrayList<Double>();
			
			regras.add(0, regraA(dado));
			regras.add(1, regraB(dado));
			regras.add(2, regraC(dado));
			regras.add(3, regraD(dado));
			regras.add(4, regraE(dado));
			regras.add(5, regraF(dado));
			regras.add(6, regraG(dado));
			regras.add(7, regraH(dado));
			regras.add(8, regraI(dado));
			regras.add(9, regraJ(dado));
			regras.add(10, regraK(dado));
			regras.add(11, regraL(dado));
			regras.add(12, regraM(dado));
			regras.add(13, regraN(dado));
			regras.add(14, regraO(dado));
			regras.add(15, regraP(dado));
			regras.add(16, regraQ(dado));
			regras.add(17, regraR(dado));
			regras.add(18, regraS(dado));
			regras.add(19, regraT(dado));
			regras.add(20, regraU(dado));
			regras.add(21, regraV(dado));
			regras.add(22, regraW(dado));
			regras.add(23, regraX(dado));
			regras.add(24, regraY(dado));
			regras.add(25, regraZ(dado));
			//regras.add(26, regraCedilha(dado));
			
			/*regras.add(0, regraCrispA(dado));
			regras.add(1, regraCrispB(dado));
			regras.add(2, regraCrispC(dado));
			regras.add(3, regraCrispD(dado));
			regras.add(4, regraCrispE(dado));
			regras.add(5, regraCrispF(dado));
			regras.add(6, regraCrispG(dado));
			regras.add(7, regraCrispH(dado));
			regras.add(8, regraCrispI(dado));
			regras.add(9, regraCrispJ(dado));
			regras.add(10, regraCrispK(dado));
			regras.add(11, regraCrispL(dado));
			regras.add(12, regraCrispM(dado));
			regras.add(13, regraCrispN(dado));
			regras.add(14, regraCrispO(dado));
			regras.add(15, regraCrispP(dado));
			regras.add(16, regraCrispQ(dado));
			regras.add(17, regraCrispR(dado));
			regras.add(18, regraCrispS(dado));
			regras.add(19, regraCrispT(dado));
			regras.add(20, regraCrispU(dado));
			regras.add(21, regraCrispV(dado));
			regras.add(22, regraCrispW(dado));
			regras.add(23, regraCrispX(dado));
			regras.add(24, regraCrispY(dado));
			regras.add(25, regraCrispZ(dado));
			//regras.add(26, regraCrispCedilha(dado));*/
			
			ganhadora = regras.indexOf(Collections.max(regras));
			//if(regras.get(ganhadora) == 0){
			//	dado.setLetraClassificada("erro");
			//	confusao[confusao.length-1][classes.indexOf(dado.getLetraCorreta())]++;
			//}
			//else {
				dado.setLetraClassificada(classes.get(ganhadora));			
				confusao[classes.indexOf(dado.getLetraClassificada())][classes.indexOf(dado.getLetraCorreta())]++;
			//}
			System.out.print("Nome: " + dado.getNome() + "    Classificado: " + dado.getLetraClassificada() + 
					"   Correto: " + dado.getLetraCorreta());
			if(!dado.getLetraClassificada().equals(dado.getLetraCorreta())){
				System.out.print("\t------> ERRO!");
				//System.out.println("\nConfiguração: " + dado.getClasseConfiguracao() + "    Movimento: " + 
				//		dado.getClasseMovimento()+ "    Orientação: " + dado.getClasseOrientacao());
				//detalhaErro(dado);				
			}
			else{
				//System.out.println("\nConfiguração: " + dado.getClasseConfiguracao() + "    Movimento: " + 
				//		dado.getClasseMovimento()+ "    Orientação: " + dado.getClasseOrientacao());
				certo++;				
			}	
			System.out.println("\nConfiguração: " + dado.getClasseConfiguracao() + "    Movimento: " + 
					dado.getClasseMovimento()+ "    Orientação: " + dado.getClasseOrientacao());
			detalhaErro(dado);
			total++;	
			System.out.println();
		}	
		System.out.println("Certo: " + certo + "\tTotal: " + total + "\tMedia = " + ((double)certo/(double)total)*100 + "%");
		imprimeMatrizConfusao();
	}
	
	private void detalhaErro(DadoCompleto dado){
		System.out.println("     Detalhes do Erro");
		for(String configuracao : configuracoes){
			System.out.print(configuracao + "\t");
		}
		System.out.println();
		for(Double pertinencia : dado.getPertinenciaConfiguracao()){
			System.out.printf("%.2f\t", pertinencia);
		}
		System.out.println();
		for(String movimento : movimentos){
			System.out.print(movimento + "\t");
		}
		System.out.println();
		for(Double pertinencia : dado.getPertinenciaMovimento()){
			System.out.printf("%.2f\t", pertinencia);
		}
		System.out.println();		
		for(String orientacao : orientacoes){
			System.out.print(orientacao.substring(0, 3) + "\t");
		}
		System.out.println();
		for(Double pertinencia : dado.getPertinenciaOrientacao()){
			System.out.printf("%.2f\t", pertinencia);
		}
		System.out.println();
	}
	
	private void criaMatrizConfusao(){		
		confusao = new int[classes.size()][classes.size()];
		for(int i = 0; i < classes.size(); i++)
			for(int j = 0; j < classes.size(); j++)
				confusao[i][j] = 0;
	}
	
	public void imprimeMatrizConfusao(){
		System.out.println();
		System.out.print("\t");
		for(String dado : classes){
			System.out.print(dado + "\t");								
		}
		System.out.println();
		for(int i = 0; i < confusao.length; i++){			
			//if(i < confusao.length - 1) 
				System.out.print(classes.get(i) + "\t");
			//else System.out.print("-\t");
			
			for(int j = 0; j < confusao[0].length; j++){
				System.out.print(confusao[i][j] + "\t");
			}
			
			System.out.println();
		}
		
	}
	
	protected double regraCrispA(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("A")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispB(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("B")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispC(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("C/Ç")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispD(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("D")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispE(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("E")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispF(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("F")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispG(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("G/Q/Z")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		if(dado.getClasseOrientacao().equals("baixo")) orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispH(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("H/K/P")) configuracao = 1;
		if(dado.getClasseMovimento().equals("vira")) movimento = 1;
		if(dado.getClasseOrientacao().equals("cima") || dado.getClasseOrientacao().equals("baixo")) 
			orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispI(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("I/J")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispJ(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("I/J")) configuracao = 1;
		if(dado.getClasseMovimento().equals("arco")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispK(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("H/K/P")) configuracao = 1;
		if(dado.getClasseMovimento().equals("cima")) movimento = 1;
		if(dado.getClasseOrientacao().equals("cima") || dado.getClasseOrientacao().equals("baixo")) 
			orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispL(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("L")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispM(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("M")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispN(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("N/U")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		if(dado.getClasseOrientacao().equals("cima") || dado.getClasseOrientacao().equals("direita")) 
			orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispO(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("O")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispP(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("H/K/P")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		if(dado.getClasseOrientacao().equals("esquerda") || dado.getClasseOrientacao().equals("direita")) 
			orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispQ(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("G/Q/Z")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		if(dado.getClasseOrientacao().equals("cima")) orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispR(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("R")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispS(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("S")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispT(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("T")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispU(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("N/U")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		if(dado.getClasseOrientacao().equals("baixo")) orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispV(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("V")) configuracao = 1;
		if(dado.getClasseMovimento().equals("sm")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispW(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("W")) configuracao = 1;
		if(dado.getClasseMovimento().equals("cima")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispX(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("X")) configuracao = 1;
		if(dado.getClasseMovimento().equals("lat")) movimento = 1;
		if(dado.getClasseOrientacao().equals("esquerda") || dado.getClasseOrientacao().equals("direita")) 
			orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispY(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("Y")) configuracao = 1;
		if(dado.getClasseMovimento().equals("cima")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispZ(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("G/Q/Z")) configuracao = 1;
		if(dado.getClasseMovimento().equals("zig")) movimento = 1;		
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
	protected double regraCrispCedilha(DadoCompleto dado){
		double configuracao = 0;
		double movimento = 0;
		double orientacao = 0;
		if(dado.getClasseConfiguracao().equals("C/Ç")) configuracao = 1;
		if(dado.getClasseMovimento().equals("tre")) movimento = 1;
		orientacao = 1;
		return configuracao*movimento*orientacao;
	}
		
	protected double regraA(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("A"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraB(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("B"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraC(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("C/Ç"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraD(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("D"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraE(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("E"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraF(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("F"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraG(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("G/Q/Z"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo"));
		return configuracao*movimento*orientacao;
	}
	private double regraH(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("H/K/P"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("vira"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraI(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("I/J"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraJ(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("I/J"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("arco"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraK(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("H/K/P"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("cima"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraL(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("L"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraM(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("M"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima"));
		return configuracao*movimento*orientacao;
	}
	private double regraN(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("N/U"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima"));
		return configuracao*movimento*orientacao;
	}
	private double regraO(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("O"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraP(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("H/K/P"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("esquerda")),
				 dado.getPertinenciaOrientacao().get(orientacoes.indexOf("direita")));
		return configuracao*movimento*orientacao;
	}
	private double regraQ(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("G/Q/Z"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima"));
		return configuracao*movimento*orientacao;
	}
	private double regraR(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("R"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraS(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("S"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraT(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("T"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraU(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("N/U"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo"));
		return configuracao*movimento*orientacao;
	}
	private double regraV(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("V"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("sm"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}	
	private double regraW(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("W"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("cima"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}	
	private double regraX(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("X"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("lat"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("esquerda")),
				 dado.getPertinenciaOrientacao().get(orientacoes.indexOf("direita")));
		return configuracao*movimento*orientacao;
	}	
	private double regraY(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("Y"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("cima"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	private double regraZ(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("G/Q/Z"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("zig"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("esquerda")),
				 dado.getPertinenciaOrientacao().get(orientacoes.indexOf("direita")));
		return configuracao*movimento*orientacao;
	}
	protected double regraCedilha(DadoCompleto dado){
		double configuracao = dado.getPertinenciaConfiguracao().get(configuracoes.indexOf("C/Ç"));
		double movimento = dado.getPertinenciaMovimento().get(movimentos.indexOf("tre"));
		double orientacao = max(dado.getPertinenciaOrientacao().get(orientacoes.indexOf("cima")),
				dado.getPertinenciaOrientacao().get(orientacoes.indexOf("baixo")));
		return configuracao*movimento*orientacao;
	}
	
	private double max(double a, double b){
		if(b > a) return b;
		else return a;
	}
}
