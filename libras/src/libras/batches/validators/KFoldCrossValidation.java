package libras.batches.validators;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.utils.ValidationHelper;

public class KFoldCrossValidation<T> implements IValidationAlgorithm<T> {
	public KFoldCrossValidation(IEvaluationAlgorithm<T> evaluationAlgorithm, int K, int foldSize) {
		ValidationHelper.validateIfParameterIsNull(evaluationAlgorithm, "evaluationAlgorithm");
		ValidationHelper.validateIfParameterIsGreaterThanZero(K, "K");
		ValidationHelper.validateIfParameterIsGreaterThanZero(foldSize, "foldSize");
		
		this.evaluationAlgorithm = evaluationAlgorithm;
		this.K = K;
		this.foldSize = foldSize;
	}
	
	private IEvaluationAlgorithm<T> evaluationAlgorithm = null;
	
	private int foldSize = 0;
	
	private int K = 0;
	
	/* (non-Javadoc)
	 * @see libras.batches.validators.ValidationAlgorithm#doValidation(java.util.ArrayList, java.util.ArrayList)
	 */
	public void doValidation(ArrayList<T[]> data, ArrayList<String> labels) throws Exception {
		ValidationHelper.validateIfParameterIsNull(data, "data");
		ValidationHelper.validateIfParameterIsNull(labels, "labels");
		ValidationHelper.validateIfArraysHaveSameLenght(data.size(), "data", labels.size(), "labels");
		
		if (data.size() < this.K * this.foldSize)
			throw new InvalidParameterException(
				String.format(
					"The parameter \"data\" must have at least lenght equal to %d",
					this.K * this.foldSize));
		
		if ((this.foldSize * this.K) % labels.size() != 0)
			throw new InvalidParameterException("The foldSize times K must a multiple of label size.");
		
		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i) == null)
				throw new InvalidParameterException("Null label found at index " + i);	
		}
		
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) == null)
				throw new InvalidParameterException("Null data found at index " + i);	
		}
		
		Hashtable<String, ArrayList<T[]>> dataDividedByLabels = this.divideDataByLabel(data, labels);
		
		Hashtable<String, ArrayList<T[]>> selectedData = this.selectDataToFolds(dataDividedByLabels);
		
		ArrayList<Fold<T>> folds = this.divideInFolds(selectedData);
		
		for (int i = 0; i < folds.size(); i++) {
			ArrayList<Fold<T>> trainData = new ArrayList<Fold<T>>(folds);
			
			Fold<T> evaluationData = trainData.remove(i);
			
			ArrayList<T[]> trainDataAsList = new ArrayList<T[]>(); 
			ArrayList<String> trainDataLabelsAsList = new ArrayList<String>();
			
			this.convertTrainDataToList(trainData, trainDataAsList, trainDataLabelsAsList);
			
			this.evaluationAlgorithm.evaluate(
				trainDataAsList, trainDataLabelsAsList, 
				evaluationData.getData(), evaluationData.getLabels());
		}
	}

	private Hashtable<String, ArrayList<T[]>> divideDataByLabel(ArrayList<T[]> data, ArrayList<String> labels) {
		Hashtable<String,  ArrayList<T[]>> dataDividedByLabels = new Hashtable<String, ArrayList<T[]>>();
		
		for (int i = 0; i < data.size(); i++) {
			ArrayList<T[]> list = null;
			
			String label = labels.get(i);
			
			if (dataDividedByLabels.containsKey(label))
			{
				list = dataDividedByLabels.get(label);
			}
			else
			{
				list = new ArrayList<T[]>();
				dataDividedByLabels.put(label, list);
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
			ArrayList<String> labels = new ArrayList<String>();
			folds.add(new Fold<T>(foldData, labels));
		}
		
		for (String label : selectedData.keySet()) {
			ArrayList<T[]> dataToChoose = selectedData.get(label);
			
			int maxLabelsPerFold = this.foldSize / selectedData.keySet().size();
			
			for (int i = 0; i < this.K; i++) {
				List<T[]> foldData = folds.get(i).getData();
				List<String> labels = folds.get(i).getLabels();
				
				for (int j = 0; j < maxLabelsPerFold; j++) {
					int indexToRemove = rnd.nextInt(dataToChoose.size());
					if (indexToRemove > 0) indexToRemove--;
					
					foldData.add(dataToChoose.remove(indexToRemove));
					labels.add(label);
				}	
			}
		}
		
		return folds;
	}

	private void convertTrainDataToList(ArrayList<Fold<T>> trainData, ArrayList<T[]> list, ArrayList<String> labels) {
		for (Fold<T> fold : trainData) {
			list.addAll(fold.getData());
			labels.addAll(fold.getLabels());
		}
	}
}
