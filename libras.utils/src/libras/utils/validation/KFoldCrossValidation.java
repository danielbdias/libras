package libras.utils.validation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class KFoldCrossValidation<T> {
	public KFoldCrossValidation(IEvaluationAlgorithm<T> evaluationAlgorithm, int K, int foldSize) {
		this.evaluationAlgorithm = evaluationAlgorithm;
		this.K = K;
		this.foldSize = foldSize;
	}
	
	private IEvaluationAlgorithm<T> evaluationAlgorithm = null;
	
	private int foldSize = 0;
	
	private int K = 0;
	
	public void doValidation(ArrayList<T[]> data, ArrayList<String> labels) {
		if (data.size() < this.K * this.foldSize)
			throw new InvalidParameterException(
				String.format(
					"The parameter \"data\" must have at least lenght equal to %d",
					this.K * this.foldSize));
		
		if (data.size() != labels.size())
			throw new InvalidParameterException("The data size must correspond to the labels size.");
		
		if (this.foldSize % labels.size() != 0)
			throw new InvalidParameterException("The foldSize must a multiple of label size.");
		
		Hashtable<String, ArrayList<T[]>> dataDividedByLabels = this.divideDataByLabel(data, labels);
		
		Hashtable<String, ArrayList<T[]>> selectedData = this.selectDataToFolds(dataDividedByLabels);
		
		ArrayList<Fold<T>> folds = this.divideInFolds(selectedData);
		
		for (int i = 0; i < folds.size(); i++) {
			ArrayList<Fold<T>> trainData = new ArrayList<Fold<T>>(folds);
			
			Fold<T> evaluationData = trainData.remove(i);
			this.evaluationAlgorithm.evaluate(trainData, evaluationData);
		}
	}

	private Hashtable<String, ArrayList<T[]>> divideDataByLabel(ArrayList<T[]> data, ArrayList<String> labels) {
		Hashtable<String,  ArrayList<T[]>> dataDividedByLabels = new Hashtable<String, ArrayList<T[]>>();
		
		for (int i = 0; i < data.size(); i++) {
			ArrayList<T[]> list = null;
			
			if (dataDividedByLabels.containsKey(labels.get(i)))
			{
				list = dataDividedByLabels.get(i);
			}
			else
			{
				list = new ArrayList<T[]>();
				dataDividedByLabels.put(labels.get(i), list);
			}
					
			list.add(data.get(i));
		}
		
		return dataDividedByLabels;
	}

	private Hashtable<String, ArrayList<T[]>> selectDataToFolds(Hashtable<String, ArrayList<T[]>> data) {
		Hashtable<String, ArrayList<T[]>> selectedData = data;
		
		Random rnd = new Random();
		
		for (String label : selectedData.keySet()) {
			ArrayList<T[]> labelData = selectedData.get(label);
			
			int maxItemsInSelectedData = (this.foldSize * this.K) / selectedData.keySet().size();
			
			while (labelData.size() > maxItemsInSelectedData) {
				int indexToRemove = rnd.nextInt(labelData.size()-1);
				labelData.remove(indexToRemove);
			}
		}
		
		return selectedData;
	}
	
	private ArrayList<Fold<T>> divideInFolds(Hashtable<String, ArrayList<T[]>> selectedData) {
		ArrayList<Fold<T>> folds = new ArrayList<Fold<T>>();
		
		Random rnd = new Random();
		
		for (int i = 0; i < this.K; i++) {
			ArrayList<T[]> foldData = new ArrayList<T[]>();
			
			for (String label : selectedData.keySet()) {
				ArrayList<T[]> dataToChoose = selectedData.get(label);
				
				int maxLabelsPerFold = this.foldSize / selectedData.keySet().size();
				
				for (int j = 0; j < maxLabelsPerFold; j++) {
					int indexToRemove = rnd.nextInt(dataToChoose.size()-1);
					foldData.add(dataToChoose.remove(indexToRemove));
				}
			}
			
			folds.add(new Fold<T>(foldData));
		}
		
		return folds;
	}

	
}
