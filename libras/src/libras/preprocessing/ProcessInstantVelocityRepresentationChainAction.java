package libras.preprocessing;

import java.io.File;
import java.util.List;

public class ProcessInstantVelocityRepresentationChainAction extends
		ProcessRepresentationChainAction {

	public ProcessInstantVelocityRepresentationChainAction(File representationFile,
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
			
			double instantVelocity = libras.utils.MathHelper.euclideanDistance(lastRepresentation, oldRepresentation);
			
			lastRepresentation = oldRepresentation;
			
			Double[] newRepresentation = new Double[oldRepresentation.length+1];
			
			for (int j = 0; j < oldRepresentation.length; j++) {
				newRepresentation[j] = oldRepresentation[j];
			}
			
			newRepresentation[newRepresentation.length-1] = instantVelocity;
			
			representationList.set(i, newRepresentation);
		}
	}

}
