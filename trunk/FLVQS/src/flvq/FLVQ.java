package flvq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FLVQ {
	private Vetor dados;
	private ArrayList<ArrayList<Double>> U;
	private Vetor M;
	private Vetor MAntigo;
	private double m;
	private double mInicial;
	private double mFinal;
	private double erro;
	private int numeroDeClusters;
	private String nomeArquivo;
	private double epoca;
	private double epocas;
	
	public FLVQ(String nomeArquivo, int numeroDeClusters, double parametroDeFuzificacaoInicial, double parametroDeFuzificacaoFinal, double epocas, double erro) throws IOException{
		dados = new Vetor(nomeArquivo, true);
		M = new Vetor(numeroDeClusters, dados.dimensao(), dados.min(), dados.max());
		m = parametroDeFuzificacaoInicial;
		mInicial = parametroDeFuzificacaoInicial;
		mFinal = parametroDeFuzificacaoFinal;
		this.erro = erro;
		this.numeroDeClusters = numeroDeClusters;
		if(mInicial < mFinal) this.nomeArquivo = nomeArquivo.replace(".", "-flc.");
		else 
			if(mInicial == mFinal) this.nomeArquivo = nomeArquivo.replace(".", "-flm.");
			else this.nomeArquivo = nomeArquivo.replace(".", "-fld.");
		epoca = 1;
		this.epocas = epocas;
	}
	
	public FLVQ(String nomeArquivo, int numeroDeClusters, double parametroDeFuzificacaoInicial, double parametroDeFuzificacaoFinal, double erro) throws IOException{
		dados = new Vetor(nomeArquivo, true);
		M = new Vetor(numeroDeClusters, dados.dimensao(), dados.min(), dados.max());
		m = parametroDeFuzificacaoInicial;
		mInicial = parametroDeFuzificacaoInicial;
		mFinal = parametroDeFuzificacaoFinal;
		this.erro = erro;
		this.numeroDeClusters = numeroDeClusters;
		if(mInicial < mFinal) this.nomeArquivo = nomeArquivo.replace(".", "-flc.");
		else 
			if(mInicial == mFinal) this.nomeArquivo = nomeArquivo.replace(".", "-flm.");
			else this.nomeArquivo = nomeArquivo.replace(".", "-fld.");
		epoca = 1;
		this.epocas = Integer.MAX_VALUE;
	}
	
	public FLVQ(String nomeArquivo, int numeroDeClusters, double parametroDeFuzificacaoInicial, 
			double parametroDeFuzificacaoFinal, double erro, Vetor clustersIniciais) throws IOException{
		dados = new Vetor(nomeArquivo, true);
		M = clustersIniciais;
		m = parametroDeFuzificacaoInicial;
		mInicial = parametroDeFuzificacaoInicial;
		mFinal = parametroDeFuzificacaoFinal;
		this.erro = erro;
		this.numeroDeClusters = numeroDeClusters;
		if(mInicial < mFinal) this.nomeArquivo = nomeArquivo.replace(".", "-flc.");
		else 
			if(mInicial == mFinal) this.nomeArquivo = nomeArquivo.replace(".", "-flm.");
			else this.nomeArquivo = nomeArquivo.replace(".", "-fld.");
		epoca = 1;
		this.epocas = Integer.MAX_VALUE;
	}
	
	public FLVQ(String nomeDados, String nomeM) throws IOException{
		dados = new Vetor(nomeDados, true);
		M = new Vetor(nomeM, false);
		numeroDeClusters = M.tamanho();
	}
	
	public FLVQ(String nomeM) throws IOException{		
		M = new Vetor(nomeM, false);
		numeroDeClusters = M.tamanho();
	}
	
	public void imprimeM() {
		for(Dado d : M){
			System.out.println(d.getV());
		}
	}
	
	public void clusteriza(){
		criaMatrizPertinencia();
		do{
			atualizaParametroFuzificacao();
			atualizaMatrizPertinencia();
			atualizaMatrizPrototipos();	
			if(epoca == 8) M.salvaArquivo(nomeArquivo + "_8", false);
			epoca++;
		}while(erroInsatisfatorio() && epoca < epocas);
		System.out.println("Epocas: " + epoca);
		M.salvaArquivo(nomeArquivo, false);
	}
	
	public void clusteriza(String nome){
		criaMatrizPertinencia();
		do{
			atualizaParametroFuzificacao();
			atualizaMatrizPertinencia();
			atualizaMatrizPrototipos();			
			epoca++;
		}while(erroInsatisfatorio() && epoca < epocas);
		M.salvaArquivo(nome, false);
	}
	
	private void atualizaParametroFuzificacao(){
		m = mInicial + epoca * (mFinal - mInicial)/epocas;
	}
	
	private boolean erroInsatisfatorio(){
		ArrayList<Double> subtracao;
		for(int i = 0; i < M.tamanho(); i++){
			try {
				subtracao = OpVetor.subtrai(M.get(i).getV(), MAntigo.get(i).getV());
				for(int j = 0; j < M.dimensao(); j++)
					if(Math.abs(subtracao.get(j)) > erro) return true;
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return false;
	}
	
	private void criaMatrizPertinencia(){		
		U = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> auxiliar;
		for(int i = 0; i < numeroDeClusters; i++){
			auxiliar = new ArrayList<Double>();
			for(int j = 0; j < dados.tamanho(); j++)
				auxiliar.add(0.0);
			U.add(auxiliar);
		}		
	}
	
	private void atualizaMatrizPertinencia(){
		double u_ij;
		double d_lj;
		double d_ij;
		double d;
		for(int i = 0; i < numeroDeClusters; i++){
			for(int j = 0; j < dados.tamanho(); j++){		
				u_ij = 0;
				d_ij = OpVetor.distancia(M.get(i).getV(), dados.get(j).getV());
				for(int l = 0; l < numeroDeClusters; l++){
					d_lj = OpVetor.distancia(M.get(l).getV(), dados.get(j).getV());
					d = d_lj/d_ij;
					u_ij = u_ij + Math.pow(d, (1/(1 - m)));
				}
				u_ij = 1/u_ij;
				U.get(i).set(j, u_ij);
			}
		}
	}
	
	private void atualizaMatrizPrototipos(){
		ArrayList<Double> numerador;
		ArrayList<Double> aux;
		double denominador;
		double u_ij;
		double exp;
		MAntigo = M.copia();
		for(int i = 0; i < numeroDeClusters; i++){
			numerador = new ArrayList<Double>();			
			for(int j = 0; j < M.dimensao(); j++)
				numerador.add(0.0);
			aux= new ArrayList<Double>();
			denominador = 0;
			for(int j = 0; j < dados.tamanho(); j++){
				u_ij = U.get(i).get(j);
				exp = Math.pow(u_ij, m);
				try {
					aux = OpVetor.subtrai(dados.get(j).getV(), M.get(i).getV());
					aux = OpVetor.multiplica(aux, exp);
					numerador = OpVetor.soma(numerador, aux);
				} catch (Exception e) {
					e.printStackTrace();
				}
				denominador = denominador + u_ij;
			}
			numerador = OpVetor.multiplica(numerador, 1/denominador);
			try {
				numerador = OpVetor.soma(numerador, M.get(i).getV());
			} catch (Exception e) {
				e.printStackTrace();
			}
			M.get(i).setV(numerador);			
		}
	}
	
	@SuppressWarnings("unchecked")
	public void testa(){
		int escolhido;
		ArrayList<Integer> classesPorCluster[] = new ArrayList[numeroDeClusters];
		for(int i = 0; i < numeroDeClusters; i++){
			classesPorCluster[i] = new ArrayList<Integer>();
		}
		System.out.println(dados.tamanho());
		for(Dado d : dados){
			escolhido = OpVetor.minimo(d.getV(), M);
			if(!classesPorCluster[escolhido].contains(d.getCl())) 
				classesPorCluster[escolhido].add(Integer.parseInt(d.getCl()));
		}
		
		for(int i = 0; i < numeroDeClusters; i++){
			Collections.sort(classesPorCluster[i]);
			System.out.println(classesPorCluster[i]);
		}
				
	}
	
	@SuppressWarnings("unchecked")
	public void testaSemImpressao(){
		int escolhido;
		ArrayList<String> classesPorCluster[] = new ArrayList[numeroDeClusters];
		for(int i = 0; i < numeroDeClusters; i++){
			classesPorCluster[i] = new ArrayList<String>();
		}
		for(Dado d : dados){
			escolhido = OpVetor.minimo(d.getV(), M);
			if(!classesPorCluster[escolhido].contains(d.getCl())) 
				classesPorCluster[escolhido].add(d.getCl());
		}				
	}
	
	public Vetor[] separaDados(){
		int numCluster = numeroDeClusters;
		int escolhido;
		Vetor clustersSeparados[] = new Vetor[numCluster];
		for(int i = 0; i < numCluster; i++)
			clustersSeparados[i] = new Vetor();
		for(Dado d : dados){
			escolhido = OpVetor.minimo(d.getV(), M);
			clustersSeparados[escolhido].add(d);
		}
		return clustersSeparados;
	}
	
	public int defineCluster(Dado dado){
		int escolhido = OpVetor.minimo(dado.getV(), M);
		return escolhido;
	}
	
	public void escreveArquivos(String nomeArquivos, Vetor[] clustersSeparados, String tipo){
		int tamanho;
		Vetor separado;
		for(int i = 0; i < clustersSeparados.length; i++){
			separado = new Vetor();
			clustersSeparados[i].embaralha();
			tamanho = clustersSeparados[i].tamanho();
			for(int j = 0; j < tamanho; j++){
				
				separado.add(clustersSeparados[i].get(j));
			}
			separado.salvaArquivo(nomeArquivos.replace(".", tipo + "_" + i + "."), true);
		}
	}
	
	public double DBIndex(Vetor[] c){
		double s[] = new double[c.length];
		ArrayList<Double> dist = new ArrayList<Double>();
		for(int i = 0; i < c.length; i++){
			dist = OpVetor.calculaDistancia(c[i], M.get(i));
			s[i] = OpVetor.variancia(dist, OpVetor.media(dist));
		}
		double soma = 0;
		double parcial;
		for(int i = 0; i < c.length; i++){
			parcial = 0;
			if(Double.isNaN(s[i])) continue;
			for(int j = 0; j < c.length; j++){
				if(Double.isNaN(s[j])) continue;
				if(parcial < (s[i] + s[j])/OpVetor.distancia(M.get(i).getV(), M.get(j).getV()))
					if(OpVetor.distancia(M.get(i).getV(), M.get(j).getV()) != 0)
							parcial = (s[i] + s[j])/OpVetor.distancia(M.get(i).getV(), M.get(j).getV());
			}
			soma = soma + parcial;
		}
		return (1/(double)c.length)*soma;
	}

	
	public static void main(String args[]){
		try{
			Vetor.pasta = "RITA//";
			String arquivo = "Data22";
			
			boolean terceiro = true;
			int clusters = 3;
			
			if(arquivo.equals("Data22")){ 
				terceiro = false;
				clusters = 2;
			}
			
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
			if(terceiro) pesosIniciais.add(new Dado(dado, "3"));
			
			double m1 = 1.1;
			double m2 = 2.0;
			
			for(int i = 0; i < 1; i++){
				FLVQ treinamento = new FLVQ(arquivo + ".txt", clusters, m1, m2, 0.001, pesosIniciais);
				treinamento.clusteriza();
				System.out.println(arquivo);
				System.out.println("Inicialização Padrão");
				System.out.println("Parâmetro de Fuzzificação Inicial: " + m1);
				System.out.println("Parâmetro de Fuzzificação Final: " + m2);
				/*Teste treino = new Teste(arquivo + "-flvq.txt", arquivo + ".txt");
				treino.testaEQ();	*/	
			}
		}catch(Exception e){
			System.out.println("Erro");
		}
	}
	
}
