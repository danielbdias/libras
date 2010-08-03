package libras.preprocessing;

import java.io.File;
import java.util.List;

public class ProcessAngleRepresentationChainAction extends
		ProcessRepresentationChainAction {

	public ProcessAngleRepresentationChainAction(File representationFile,
			File processedRepresentationFile, int representationSize) {
		super(representationFile, processedRepresentationFile, representationSize);
	}
	
	@Override
	protected String processLine(String[] parsedLine) {
		List<Double[]> representationList = this.getRepresentationFromParsedLine(parsedLine);
		
		this.convertRepresentation(representationList);
		
		int classNumber = Integer.parseInt(parsedLine[parsedLine.length-1]);
			
		return this.codifyLine(representationList, classNumber);
	}

	private void convertRepresentation(List<Double[]> representationList) {
		
		Double[] lastRepresentation = representationList.get(0);
		
		for (int i = 0; i < representationList.size(); i++) {
			Double[] oldRepresentation = representationList.get(i);
			
			double ordinateDelta = oldRepresentation[1] - lastRepresentation[1];
            double abscissaDelta = oldRepresentation[0] - lastRepresentation[0];
            
            double angularCoefficient = (abscissaDelta != 0 ? ordinateDelta / abscissaDelta : 0.0);

            double abcissaAngle = Math.atan(angularCoefficient);
            double ordinateAngle = abcissaAngle - 0.5;
			
			lastRepresentation = oldRepresentation;
						
			representationList.set(i, new Double[] { abcissaAngle, ordinateAngle });
		}
	}

}
