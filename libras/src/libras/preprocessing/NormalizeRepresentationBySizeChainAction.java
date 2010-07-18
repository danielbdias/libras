package libras.preprocessing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import libras.utils.Pair;

public class NormalizeRepresentationBySizeChainAction extends
		NormalizeRepresentationChainAction {

	public NormalizeRepresentationBySizeChainAction(File representationFile,
			File normalizedFile, int normalizationSize) {
		super(representationFile, normalizedFile);
		this.normalizationSize = normalizationSize;
	}

	@Override
	protected String normalizeLine(String[] parsedLine) {
		List<Pair<Double, Double>> centroids = getCoordinatesFromParsedLine(parsedLine);
		
		this.normalizeCentroidListBySampling(centroids, this.normalizationSize);
		
		int classNumber = Integer.parseInt(parsedLine[parsedLine.length-1]);
			
		return this.codifyLine(centroids, classNumber);
	}
	
	private int normalizationSize = 0;
	
	protected void normalizeCentroidListBySampling(List<Pair<Double, Double>> centroidList, int maxCentroidsPerList)
	{
		int keepOrder = centroidList.size() / maxCentroidsPerList;
		
		if (keepOrder > 0)
		{
			List<Pair<Double, Double>> tempList = new ArrayList<Pair<Double, Double>>();
			
			//Add until fill the max number count
			for (int i = 0; i < centroidList.size() && tempList.size() < maxCentroidsPerList; i+=keepOrder)
				tempList.add(centroidList.get(i));
			
			if (tempList.size() < maxCentroidsPerList)
			{
				for (int i = maxCentroidsPerList - tempList.size(); i > 0; i--)
					tempList.add(centroidList.get(centroidList.size() - i));
			}
			
			centroidList.clear();
			centroidList.addAll(tempList);
		}
	}
}
