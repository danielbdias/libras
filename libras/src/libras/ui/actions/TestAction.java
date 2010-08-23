package libras.ui.actions;

import java.io.File;
import java.util.LinkedList;

import libras.preprocessing.NormalizeRepresentationByDataChainAction;
import libras.preprocessing.NormalizeRepresentationByDimensionChainAction;
import libras.preprocessing.NormalizeRepresentationByPositionChainAction;
import libras.preprocessing.NormalizeRepresentationBySamplingChainAction;
import libras.preprocessing.RepresentationBuilderChainAction;
import libras.preprocessing.representation.*;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Test action used for debugging purposes.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="test",
	commandExample="-test",
	requiredArgs={},
	helpDescription="Test a functionality")
public class TestAction extends Action
{
	@Override
	public void execute() throws Exception
	{
		//extractRepresentation();
		//normalizeRepresentationBySampling();
		//normalizeRepresentationByDimension();
		//normalizeRepresentationByData();
		normalizeRepresentationByPosition();
	}

	private void generateActionsForNormalizationByPosition(
			String[] representationDirs, String[] normalizationDirs, String[] files,
			LinkedList<NormalizeRepresentationByPositionChainAction> list) {
		
		for (int i = 0; i < representationDirs.length; i++) {
			File[] representationFiles = new File[files.length];
			File[] normalizedFiles = new File[files.length];	
			
			for (int j = 0; j < files.length; j++) {
				representationFiles[j] = new File(representationDirs[i] + files[j]);
				normalizedFiles[j] = new File(normalizationDirs[i] + files[j]);
				
				if (!normalizedFiles[j].getParentFile().exists())
					normalizedFiles[j].getParentFile().mkdirs();
				
				list.add(new NormalizeRepresentationByPositionChainAction(representationFiles[j], normalizedFiles[j]));
			}
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).setNextAction(list.get(i+1));
		}
	}
	
	protected void normalizeRepresentationByPosition() {
		
		String[] representationDirs = {
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] normalizationDirs = {
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por posição)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] files = {
			"\\IC\\Daniel.txt",
			"\\IC\\Roberta.txt",
			"\\IC\\Sara.txt",
			"\\IC\\Videos - Sara.txt",
			"\\IC\\Videos - todos.txt",
			"\\TCC\\Alex.txt",
			"\\TCC\\Daniel.txt",
			"\\TCC\\Lucas.txt",
			"\\TCC\\Márcia.txt",
			"\\TCC\\Mirian.txt"
		};
		
		LinkedList<NormalizeRepresentationByPositionChainAction> list = new LinkedList<NormalizeRepresentationByPositionChainAction>();
		
		generateActionsForNormalizationByPosition(representationDirs, normalizationDirs, files, list);
		
		list.getFirst().executeAction();
		
	}
	
	private void generateActionsForNormalizationByData(
			String[] representationDirs, String[] normalizationDirs, String[] files,
			LinkedList<NormalizeRepresentationByDataChainAction> list) {
		
		for (int i = 0; i < representationDirs.length; i++) {
			File[] representationFiles = new File[files.length];
			File[] normalizedFiles = new File[files.length];	
			
			for (int j = 0; j < files.length; j++) {
				representationFiles[j] = new File(representationDirs[i] + files[j]);
				normalizedFiles[j] = new File(normalizationDirs[i] + files[j]);
				
				if (!normalizedFiles[j].getParentFile().exists())
					normalizedFiles[j].getParentFile().mkdirs();
				
				list.add(new NormalizeRepresentationByDataChainAction(representationFiles[j], normalizedFiles[j]));
			}
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).setNextAction(list.get(i+1));
		}
	}
	
	protected void normalizeRepresentationByData() {
		
		String[] representationDirs = {
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] normalizationDirs = {
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por dados)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] files = {
			"\\IC\\Daniel.txt",
			"\\IC\\Roberta.txt",
			"\\IC\\Sara.txt",
			"\\IC\\Videos - Sara.txt",
			"\\IC\\Videos - todos.txt",
			"\\TCC\\Alex.txt",
			"\\TCC\\Daniel.txt",
			"\\TCC\\Lucas.txt",
			"\\TCC\\Márcia.txt",
			"\\TCC\\Mirian.txt"
		};
		
		LinkedList<NormalizeRepresentationByDataChainAction> list = new LinkedList<NormalizeRepresentationByDataChainAction>();
		
		generateActionsForNormalizationByData(representationDirs, normalizationDirs, files, list);
		
		list.getFirst().executeAction();
		
	}
	
	private void generateActionsForNormalizationByDimension(
			String[] representationDirs, String[] normalizationDirs, String[] files,
			LinkedList<NormalizeRepresentationByDimensionChainAction> list) {
		
		for (int i = 0; i < representationDirs.length; i++) {
			File[] representationFiles = new File[files.length];
			File[] normalizedFiles = new File[files.length];
			
			for (int j = 0; j < files.length; j++) {
				representationFiles[j] = new File(representationDirs[i] + files[j]);
				normalizedFiles[j] = new File(normalizationDirs[i] + files[j]);
				
				if (!normalizedFiles[j].getParentFile().exists())
					normalizedFiles[j].getParentFile().mkdirs();
			}
			
			list.add(new NormalizeRepresentationByDimensionChainAction(representationFiles, normalizedFiles));
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).setNextAction(list.get(i+1));
		}
	}
	
	protected void normalizeRepresentationByDimension() {
		
		String[] representationDirs = {
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] normalizationDirs = {
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por dimensão)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] files = {
			"\\IC\\Daniel.txt",
			"\\IC\\Roberta.txt",
			"\\IC\\Sara.txt",
			"\\IC\\Videos - Sara.txt",
			"\\IC\\Videos - todos.txt",
			"\\TCC\\Alex.txt",
			"\\TCC\\Daniel.txt",
			"\\TCC\\Lucas.txt",
			"\\TCC\\Márcia.txt",
			"\\TCC\\Mirian.txt"
		};
		
		LinkedList<NormalizeRepresentationByDimensionChainAction> list = new LinkedList<NormalizeRepresentationByDimensionChainAction>();
		
		generateActionsForNormalizationByDimension(representationDirs, normalizationDirs, files, list);
		
		list.getFirst().executeAction();
		
	}

	private void generateActionsForNormalizationBySampling(
			String representationDir, String normalizationDir, String[] files,
			int representationSize, int normalizationSize,
			LinkedList<NormalizeRepresentationBySamplingChainAction> list) {
		
		for (int i = 0; i < files.length; i++) {
			File representationFile = new File(representationDir + files[i]);
			File normalizationFile = new File(normalizationDir + files[i]);
			
			if (!normalizationFile.getParentFile().exists())
				normalizationFile.getParentFile().mkdirs();
			
			list.add(new NormalizeRepresentationBySamplingChainAction(
				representationFile, normalizationFile, representationSize, normalizationSize));
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).setNextAction(list.get(i+1));
		}
		
	}
	
	protected void normalizeRepresentationBySampling() {
		final int normalizationSize = 35;
		
		String[] representationDirs = {
				"H:\\TCC\\Base de dados\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		String[] normalizationDirs = {
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + ângulo abcissa)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade instantânea)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + velocidade angular)",
				"H:\\TCC\\Base de dados normalizada (por tempo)\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
			};
		
		int[] representationSizes = { 2, 1, 2, 3, 4, 3, 3, 4, 2, 3 };
		
		String[] files = {
			"\\IC\\Daniel.txt",
			"\\IC\\Roberta.txt",
			"\\IC\\Sara.txt",
			"\\IC\\Videos - Sara.txt",
			"\\IC\\Videos - todos.txt",
			"\\TCC\\Alex.txt",
			"\\TCC\\Daniel.txt",
			"\\TCC\\Lucas.txt",
			"\\TCC\\Márcia.txt",
			"\\TCC\\Mirian.txt"
		};
		
		LinkedList<NormalizeRepresentationBySamplingChainAction> list = new LinkedList<NormalizeRepresentationBySamplingChainAction>();
		
		for (int i = 0; i < representationDirs.length; i++) {
			String representationDir = representationDirs[i];
			String normalizationDir = normalizationDirs[i];
			int representationSize = representationSizes[i];
			generateActionsForNormalizationBySampling(
				representationDir, normalizationDir, files, representationSize, normalizationSize, list);
		}
		
		list.getFirst().executeAction();
	}

	protected void extractRepresentation() {
		String centroidDir = "H:\\TCC\\Base de dados (representação - coordenadas)";
		String[] representationDirs = {
			"H:\\TCC\\Base de dados (representação - ângulo abcissa)",
			"H:\\TCC\\Base de dados (representação - ângulo abcissa + ordenada)",
			"H:\\TCC\\Base de dados (representação - coordenadas + ângulo abcissa)",
			"H:\\TCC\\Base de dados (representação - coordenadas + ângulo abcissa + ordenada)",
			"H:\\TCC\\Base de dados (representação - coordenadas + velocidade instantânea)",
			"H:\\TCC\\Base de dados (representação - coordenadas + velocidade angular)",
			"H:\\TCC\\Base de dados (representação - coordenadas + velocidade instantânea + angular)",
			"H:\\TCC\\Base de dados (representação - ângulo abcissa + velocidade angular)",
			"H:\\TCC\\Base de dados (representação - ângulo abcissa + ordenada + velocidade angular)",
		};
		
		String[] files = {
			"\\IC\\Daniel.txt",
			"\\IC\\Roberta.txt",
			"\\IC\\Sara.txt",
			"\\IC\\Videos - Sara.txt",
			"\\IC\\Videos - todos.txt",
			"\\TCC\\Alex.txt",
			"\\TCC\\Daniel.txt",
			"\\TCC\\Lucas.txt",
			"\\TCC\\Márcia.txt",
			"\\TCC\\Mirian.txt"
		};
		
		DimensionBuilder[][] representations = {
			{ AbcissaAngleDimensionBuilder.getInstance() },
			{ AbcissaAngleDimensionBuilder.getInstance(), OrdinateAngleDimensionBuilder.getInstance() },
			{ AbcissaCoordinateDimensionBuilder.getInstance(), OrdinateCoordinateDimensionBuilder.getInstance(), 
				AbcissaAngleDimensionBuilder.getInstance() },
			{ AbcissaCoordinateDimensionBuilder.getInstance(), OrdinateCoordinateDimensionBuilder.getInstance(), 
				AbcissaAngleDimensionBuilder.getInstance(), OrdinateAngleDimensionBuilder.getInstance() },
			{ AbcissaCoordinateDimensionBuilder.getInstance(), OrdinateCoordinateDimensionBuilder.getInstance(), 
				InstantVelocityDimensionBuilder.getInstance() },
			{ AbcissaCoordinateDimensionBuilder.getInstance(), OrdinateCoordinateDimensionBuilder.getInstance(), 
				AngularVelocityDimensionBuilder.getInstance() },
			{ AbcissaCoordinateDimensionBuilder.getInstance(), OrdinateCoordinateDimensionBuilder.getInstance(), 
				InstantVelocityDimensionBuilder.getInstance(), AngularVelocityDimensionBuilder.getInstance() },
			{ AbcissaAngleDimensionBuilder.getInstance(), AngularVelocityDimensionBuilder.getInstance() },
			{ AbcissaAngleDimensionBuilder.getInstance(), OrdinateAngleDimensionBuilder.getInstance(),
				AngularVelocityDimensionBuilder.getInstance() }
		};
		
		LinkedList<RepresentationBuilderChainAction> list = new LinkedList<RepresentationBuilderChainAction>();
		
		for (int i = 0; i < representationDirs.length; i++) {
			String representationDir = representationDirs[i];
			DimensionBuilder[] representation = representations[i];
			generateActionsForRepresentation(centroidDir, representationDir, files, representation, list);
		}
		
		list.getFirst().executeAction();
	}
	
	private void generateActionsForRepresentation(
		String centroidDir, String representationDir, String[] files, 
		DimensionBuilder[] representation, LinkedList<RepresentationBuilderChainAction> list) {
		
		for (int i = 0; i < files.length; i++) {
			File centroidFile = new File(centroidDir + files[i]);
			File representationFile = new File(representationDir + files[i]);
			
			if (!representationFile.getParentFile().exists())
				representationFile.getParentFile().mkdirs();
			
			list.add(new RepresentationBuilderChainAction(centroidFile, representationFile, representation));
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).setNextAction(list.get(i+1));
		}
	}
}
