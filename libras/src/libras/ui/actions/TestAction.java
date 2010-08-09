package libras.ui.actions;

import java.io.File;
import java.util.LinkedList;

import libras.preprocessing.RepresentationBuilderChainAction;
import libras.preprocessing.representation.AbcissaAngleDimensionBuilder;
import libras.preprocessing.representation.AbcissaCoordinateDimensionBuilder;
import libras.preprocessing.representation.DimensionBuilder;
import libras.preprocessing.representation.InstantVelocityDimensionBuilder;
import libras.preprocessing.representation.OrdinateAngleDimensionBuilder;
import libras.preprocessing.representation.OrdinateCoordinateDimensionBuilder;
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
		String[] centroidFiles = {
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\IC\\Daniel.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\IC\\Roberta.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\IC\\Sara.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\IC\\Videos - Sara.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\IC\\Videos - todos.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\TCC\\Alex.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\TCC\\Daniel.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\TCC\\Lucas.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\TCC\\M�rcia.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas)\\TCC\\Mirian.txt"
		};
		
		String[] representationFiles = {
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\IC\\Daniel.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\IC\\Roberta.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\IC\\Sara.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\IC\\Videos - Sara.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\IC\\Videos - todos.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\TCC\\Alex.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\TCC\\Daniel.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\TCC\\Lucas.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\TCC\\M�rcia.txt",
			"H:\\TCC\\Base de dados (representa��o - coordenadas + velocidade instant�nea)\\TCC\\Mirian.txt"
		};
		
		DimensionBuilder[] representation = {
			AbcissaCoordinateDimensionBuilder.getInstance(),
			OrdinateCoordinateDimensionBuilder.getInstance(),
			InstantVelocityDimensionBuilder.getInstance()
		};
		
		LinkedList<RepresentationBuilderChainAction> list = new LinkedList<RepresentationBuilderChainAction>();
		
		generateActionsForRepresentation(centroidFiles, representationFiles, representation, list);
		
		list.getFirst().executeAction();
	}

	private void generateActionsForRepresentation(
		String[] centroidFiles, String[] representationFiles, 
		DimensionBuilder[] representation, LinkedList<RepresentationBuilderChainAction> list) {
		
		for (int i = 0; i < centroidFiles.length; i++) {
			File centroidFile = new File(centroidFiles[i]);
			File representationFile = new File(representationFiles[i]);
			
			if (!representationFile.getParentFile().exists())
				representationFile.getParentFile().mkdirs();
			
			list.add(new RepresentationBuilderChainAction(centroidFile, representationFile, representation));
		}
		
		for (int i = 0; i < list.size() - 1; i++) {
			list.get(i).setNextAction(list.get(i+1));
		}
	}
}
