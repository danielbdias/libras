package libras.preprocessing;

import java.io.File;
import java.util.List;

public class ProcessAngularVelocityRepresentationChainAction extends
		ProcessRepresentationChainAction {

	public ProcessAngularVelocityRepresentationChainAction(File representationFile,
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
		double lastAngle = 0;
		
		for (int i = 0; i < representationList.size(); i++) {
			Double[] oldRepresentation = representationList.get(i);
			
			double ordinateDelta = oldRepresentation[1] - lastRepresentation[1];
            double abscissaDelta = oldRepresentation[0] - lastRepresentation[0];
            
            double angularCoefficient = (abscissaDelta != 0 ? ordinateDelta / abscissaDelta : 0.0);

            double abcissaAngle = Math.atan(angularCoefficient);
            
            double angleDelta = 0;
            
            if (i != 0) angleDelta = abcissaAngle - lastAngle;
			
			lastRepresentation = oldRepresentation;
			lastAngle = abcissaAngle;
			
			Double[] newRepresentation = new Double[oldRepresentation.length+1];
			
			for (int j = 0; j < oldRepresentation.length; j++) {
				newRepresentation[j] = oldRepresentation[j];
			}
			
			newRepresentation[newRepresentation.length-1] = angleDelta;
			
			representationList.set(i, newRepresentation);
		}
	}

}
