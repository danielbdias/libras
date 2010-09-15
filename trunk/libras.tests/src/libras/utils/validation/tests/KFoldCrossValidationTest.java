package libras.utils.validation.tests;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import libras.utils.validation.Fold;
import libras.utils.validation.IEvaluationAlgorithm;
import libras.utils.validation.KFoldCrossValidation;

import org.junit.Test;

public class KFoldCrossValidationTest {

	class TestData {
		public int timesUsedInTraining = 0;
		public int timesUsedInEvaluation = 0;
	}
	
	@Test
	public void testKFoldCrossValidationContructor() {
		IEvaluationAlgorithm<Object> eval = new IEvaluationAlgorithm<Object>() {
			@Override
			public void evaluate(List<Fold<Object>> trainData,
					Fold<Object> evaluationData) {
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
			public void evaluate(List<Fold<TestData>> trainData,
					Fold<TestData> evaluationData) {
				for (Fold<TestData> fold : trainData) {
					for (TestData[] data : fold.getData()) {
						for (TestData dataItem : data) {
							dataItem.timesUsedInTraining++;
						}
					}
				}
				
				for (TestData[] data : evaluationData.getData()) {
					for (TestData dataItem : data) {
						dataItem.timesUsedInEvaluation++;
					}
				}
			}
		};
		
		final int K = 10;
		final int foldSize = 30;
		
		KFoldCrossValidation<TestData> validator = 
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
			data.add(new TestData[] { new TestData() });
			labels.add(Integer.toString(i/foldSize));
		}
		
		validator.doValidation(data, labels);
		
		for (TestData[] testData : data) {
			assertTrue(testData[0].timesUsedInEvaluation == 1);
			assertTrue(testData[0].timesUsedInTraining == (K-1));
		}
	}
}
