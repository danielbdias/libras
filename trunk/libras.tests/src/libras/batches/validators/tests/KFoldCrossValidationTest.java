package libras.batches.validators.tests;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import libras.batches.evaluators.IEvaluationAlgorithm;
import libras.batches.validators.KFoldCrossValidation;
import libras.batches.validators.IValidationAlgorithm;

import org.junit.Test;

public class KFoldCrossValidationTest {

	class TestData {
		public int timesUsedInTraining = 0;
		public int timesUsedInEvaluation = 0;
		public String label = null;
	}
	
	@Test
	public void testKFoldCrossValidationContructor() {
		IEvaluationAlgorithm<Object> eval = new IEvaluationAlgorithm<Object>() {
			@Override
			public List<String> getExpectedLabels() {
				// this is a stub, do nothing
				return null;
			}
			
			@Override
			public List<String> getComputedLabels() {
				// this is a stub, do nothing
				return null;
			}
			
			@Override
			public void evaluate(List<Object[]> trainData,
					List<String> trainDataLabels, List<Object[]> evaluationData,
					List<String> evaluationDataLabels) {
				// this is a stub, do nothing
			}
		};
		
		try {
			new KFoldCrossValidation<Object>(null, -1, -1);
			fail("Accepted null validation algorithm.");
		} catch (InvalidParameterException e) { }
		
		try {
			new KFoldCrossValidation<Object>(eval, -1, -1);
			fail("Accepted negative K.");
		} catch (InvalidParameterException e) { }
		
		try {
			new KFoldCrossValidation<Object>(eval, 10, -1);
			fail("Accepted negative fold number.");
		} catch (InvalidParameterException e) { }
		
		//This constructor must pass
		new KFoldCrossValidation<Object>(eval, 10, 30);
	}

	@Test
	public void testDoValidation() {
		IEvaluationAlgorithm<TestData> evaluationAlgorithm = new IEvaluationAlgorithm<TestData>() {
			@Override
			public List<String> getExpectedLabels() {
				// this is a stub, do nothing
				return null;
			}
			
			@Override
			public List<String> getComputedLabels() {
				// this is a stub, do nothing
				return null;
			}
			
			@Override
			public void evaluate(List<TestData[]> trainData,
					List<String> trainDataLabels, List<TestData[]> evaluationData,
					List<String> evaluationDataLabels) {
				for (TestData[] data : trainData) {
					for (TestData dataItem : data) {
						dataItem.timesUsedInTraining++;
					}
				}
				
				for (TestData[] data : evaluationData) {
					for (TestData dataItem : data) {
						dataItem.timesUsedInEvaluation++;
					}
				}
			}
		};
		
		final int K = 10;
		final int foldSize = 30;
		
		IValidationAlgorithm<TestData> validator = 
			new KFoldCrossValidation<TestData>(evaluationAlgorithm, K, foldSize);
		
		try {
			validator.doValidation(null, null);
			fail("Accepted null data.");
		} catch (InvalidParameterException e) { }
		
		try {
			validator.doValidation(new ArrayList<TestData[]>(), null);
			fail("Accepted null labels.");
		} catch (InvalidParameterException e) { }
		
		try {
			ArrayList<TestData[]> data = new ArrayList<TestData[]>();
			ArrayList<String> labels = new ArrayList<String>();
			
			data.add(new TestData[0]);
			data.add(new TestData[0]);
			
			labels.add("");
			
			validator.doValidation(data, labels);
			fail("Accepted data size greater than labels size.");
		} catch (InvalidParameterException e) { }
		
		try {
			ArrayList<TestData[]> data = new ArrayList<TestData[]>();
			ArrayList<String> labels = new ArrayList<String>();
			
			data.add(new TestData[0]);
			
			labels.add("");
			labels.add("");
			
			validator.doValidation(data, labels);
			fail("Accepted data size lesser than labels size.");
		} catch (InvalidParameterException e) { }
		
		try {
			ArrayList<TestData[]> data = new ArrayList<TestData[]>();
			ArrayList<String> labels = new ArrayList<String>();
			
			data.add(new TestData[0]);
			data.add(new TestData[0]);
			
			labels.add("");
			labels.add("");
			
			validator.doValidation(data, labels);
			fail("Accepted data size lesser than K times FoldSize.");
		} catch (InvalidParameterException e) { }
		
		try {
			ArrayList<TestData[]> data = new ArrayList<TestData[]>();
			ArrayList<String> labels = new ArrayList<String>();
			
			for (int i = 0; i < (K*foldSize)+1; i++) {
				data.add(new TestData[0]);
				labels.add("");
			}
			
			validator.doValidation(data, labels);
			fail("Accepted data size greater than K times FoldSize.");
		} catch (InvalidParameterException e) { }
		
		ArrayList<TestData[]> data = new ArrayList<TestData[]>();
		ArrayList<String> labels = new ArrayList<String>();
		
		for (int i = 0; i < (K*foldSize); i++) {
			String label = Integer.toString(i/foldSize);
			TestData testdata = new TestData();
			testdata.label = label;
			
			data.add(new TestData[] { testdata });
			labels.add(label);
		}
		
		validator.doValidation(data, labels);
		
		int totalData = 0, failures = 0;
		
		for (TestData[] testData : data) {
			totalData++;
			
			if (testData[0].timesUsedInEvaluation != 1 || testData[0].timesUsedInTraining != (K-1))
				failures++;
		}
		
		assertTrue(String.format("K-Fold Cross Validation failed. %d failures of %d tests.", failures, totalData), failures == 0);
	}
}
