package libras;

import libras.geneticalgorithms.*;
import libras.geneticalgorithms.chromossomes.*;
import libras.geneticalgorithms.factories.*;
import libras.geneticalgorithms.operators.crossover.*;
import libras.geneticalgorithms.operators.mutation.*;
import libras.geneticalgorithms.operators.selection.*;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Testando GA...");
		
		try {
			squareTest();
			//sineTest();
			//clusterizationTest();

			System.out.println();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		System.out.println("Teste terminado.");
	}

	protected static void clusterizationTest() throws Exception {
		System.out.println("Iniciando os testes de clusterização...");
		
        int sizePopulation = 1000;
        double mutationRate = 0.2;
        int generations = 500;

		GeneticAlgorithm ga =
			new GeneticAlgorithm(
				sizePopulation, //tamanho da população
				new DataFileChromossomeFactory(
					"D:\\Daniel Documents\\Facul\\6º Semestre\\IA\\EP\\dados\\clustering_umadimensao.txt",
					3), //Número de clusters
				new RandomMutationOperator(mutationRate),
				new OnePointCrossoverOperator(),
				new RoulleteSelectionOperator());
				//new TournmentSelectionOperator());

		ClusteringChromossome bestMatch = (ClusteringChromossome) ga.execute(generations);

        System.out.println("Parameters: size population = "+sizePopulation+" AND mutation rate = "+mutationRate+" AND generations = "+generations+";");

        System.out.println("Cromossomo vencedor:");
		
		for (int i = 0; i < bestMatch.allelesCount(); i++) {
			Allel allel = bestMatch.getAllel(i);
			
			System.out.printf(" Alelo [%d]:\r\n", i);
			System.out.printf("  Grupo real: [%s]\r\n", allel.getType());
			System.out.printf("  Grupo atribuido: [%d]\r\n", allel.getGroup());
			System.out.println();
		}
		
		System.out.println("Testes de clusterização terminados.");
	}

	protected static void squareTest() throws Exception {
		System.out.println("Iniciando os testes de x^2...");
		
		System.out.println("Domínio Máximo, População, Geração, Mutação, Seleção, Resultado");
		
		squareParametrizedTest(100, 50, 50,
				new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 1000, 50, 
				new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 50, 200,
				new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
			
		squareParametrizedTest(100, 1000, 200, 
				new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 50, 1000,
				new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 1000, 1000, 
				new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		//////////////////////////////////////////////////////////////////////
		
		squareParametrizedTest(100, 50, 50,
				new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 1000, 50, 
				new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 50, 200,
				new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 1000, 200, 
				new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 50, 1000,
				new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		squareParametrizedTest(100, 1000, 1000, 
				new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		
		squareParametrizedTest(100, 50, 50,
				new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 1000, 50, 
				new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 50, 200,
				new RandomMutationOperator(0.1), new TournmentSelectionOperator());
			
		squareParametrizedTest(100, 1000, 200, 
				new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 50, 1000,
				new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 1000, 1000, 
				new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		//////////////////////////////////////////////////////////////////////
		
		squareParametrizedTest(100, 50, 50,
				new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 1000, 50, 
				new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 50, 200,
				new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 1000, 200, 
				new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 50, 1000,
				new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		squareParametrizedTest(100, 1000, 1000, 
				new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		System.out.println("Testes de x^2 terminados.");
	}

	private static void squareParametrizedTest(
		double maximumValue, int populationSize, int generations, 
		MutationOperator mutation, SelectionOperator selection) throws Exception {
		
		//System.out.println("Domínio Máximo, População, Geração, Mutação, Seleção, Resultado, Fitness");
		
		System.out.printf("%s, ", maximumValue);
		System.out.printf("%s, ", populationSize);
		System.out.printf("%s, ", generations);
		
		if (mutation instanceof RandomMutationOperator) {
			System.out.printf("Aleatória (%s", 
					((RandomMutationOperator) mutation).getMutationChance());
			System.out.print("%), ");
		}
		else {
			System.out.printf("Dirigida a domínio, ");
		}
		
		if (selection instanceof RoulleteSelectionOperator)
			System.out.printf("Roleta, ");
		else 
			System.out.printf("Torneio, ");
		
		GeneticAlgorithm ga = 
			new GeneticAlgorithm(
				populationSize, 
				new SquareChromossomeFactory(maximumValue), 
				mutation,
				new OnePointCrossoverOperator(),
				selection);
		
		SquareChromossome bestMatch = (SquareChromossome) ga.execute(generations);
		
		System.out.printf("%s, %s\r\n", bestMatch.getX(), bestMatch.fitness());
	}

	protected static void sineTest() throws Exception {
		System.out.println("Iniciando os testes de sen(x*y)...");
		
		System.out.println("População, Geração, Mutação, Seleção, Resultado (X, Y), Fitness");
		
		sineParametrizedTest(50, 50, new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		sineParametrizedTest(1000, 50, new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		sineParametrizedTest(50, 200, new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
			
		sineParametrizedTest(1000, 200, new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		sineParametrizedTest(50, 1000, new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		sineParametrizedTest(1000, 1000, new RandomMutationOperator(0.1), new RoulleteSelectionOperator());
		
		//////////////////////////////////////////////////////////////////////
		
		sineParametrizedTest(50, 50, new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		sineParametrizedTest(1000, 50, new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		sineParametrizedTest(50, 200, new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		sineParametrizedTest(1000, 200, new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		sineParametrizedTest(50, 1000, new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		sineParametrizedTest(1000, 1000, new RandomMutationOperator(0.8), new RoulleteSelectionOperator());
		
		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		
		sineParametrizedTest(50, 50, new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		sineParametrizedTest(1000, 50, new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		sineParametrizedTest(50, 200, new RandomMutationOperator(0.1), new TournmentSelectionOperator());
			
		sineParametrizedTest(1000, 200, new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		sineParametrizedTest(50, 1000, new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		sineParametrizedTest(1000, 1000, new RandomMutationOperator(0.1), new TournmentSelectionOperator());
		
		//////////////////////////////////////////////////////////////////////
		
		sineParametrizedTest(50, 50, new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		sineParametrizedTest(1000, 50, new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		sineParametrizedTest(50, 200, new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		sineParametrizedTest(1000, 200, new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		sineParametrizedTest(50, 1000, new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		sineParametrizedTest(1000, 1000, new RandomMutationOperator(0.8), new TournmentSelectionOperator());
		
		System.out.println("Testes de sen(x*y) terminados.");
	}

	private static void sineParametrizedTest(
			int populationSize, int generations, 
			MutationOperator mutation, SelectionOperator selection) throws Exception {
			
			//System.out.println("População, Geração, Mutação, Seleção, Resultado (X, Y)");
			
			System.out.printf("%s, ", populationSize);
			System.out.printf("%s, ", generations);
			
			if (mutation instanceof RandomMutationOperator) {
				System.out.printf("Aleatória (%s", 
						((RandomMutationOperator) mutation).getMutationChance());
				System.out.print("%), ");
			}
			else {
				System.out.printf("Dirigida a domínio, ");
			}
			
			if (selection instanceof RoulleteSelectionOperator)
				System.out.printf("Roleta, ");
			else 
				System.out.printf("Torneio, ");
			
			GeneticAlgorithm ga = 
				new GeneticAlgorithm(
					populationSize, 
					new SineChromossomeFactory(), 
					mutation,
					new OnePointCrossoverOperator(),
					selection);
			
			SineChromossome bestMatch = (SineChromossome) ga.execute(generations);
			
			System.out.printf("%s, %s, %s\r\n", bestMatch.getX(), bestMatch.getY(), bestMatch.fitness());
		}}
