//package libras.preprocessing;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//public class NormalizeRepresentationBySizeChainAction extends
//		ProcessRepresentationChainAction {
//
//	public NormalizeRepresentationBySizeChainAction(File representationFile,
//			File processedRepresentationFile, int representationSize, int normalizationSize) {
//		super(representationFile, processedRepresentationFile, representationSize);
//		this.normalizationSize = normalizationSize;
//	}
//
//	@Override
//	protected String processLine(String[] parsedLine) {
//		List<Double[]> representationList = this.getRepresentationFromParsedLine(parsedLine);
//		
//		this.normalizeRepresentationListBySampling(representationList, this.normalizationSize);
//		
//		int classNumber = Integer.parseInt(parsedLine[parsedLine.length-1]);
//			
//		return this.codifyLine(representationList, classNumber);
//	}
//	
//	private int normalizationSize = 0;
//	
//	protected void normalizeRepresentationListBySampling(List<Double[]> representationList, int maxItemsPerList)
//	{
//		int keepOrder = representationList.size() / maxItemsPerList;
//		
//		if (keepOrder > 0)
//		{
//			List<Double[]> tempList = new ArrayList<Double[]>();
//			
//			//Add until fill the max number count
//			for (int i = 0; i < representationList.size() && tempList.size() < maxItemsPerList; i+=keepOrder)
//				tempList.add(representationList.get(i));
//			
//			if (tempList.size() < maxItemsPerList)
//			{
//				for (int i = maxItemsPerList - tempList.size(); i > 0; i--)
//					tempList.add(representationList.get(representationList.size() - i));
//			}
//			
//			representationList.clear();
//			representationList.addAll(tempList);
//		}
//	}
//}
