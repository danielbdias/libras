package libras.batches.outputfiles.models;

public class ReportData {
	
	
	public ReportData(double confidenceRate, double hitRateMean,
			double hitRateMedian, double hitRateVariance, double hitRateStandardDeviation,
			String[] labels, int[][] confusionMatrix) {
		this.confidenceRate = confidenceRate;
		this.hitRateMean = hitRateMean;
		this.hitRateMedian = hitRateMedian;
		this.hitRateVariance = hitRateVariance; 
		this.hitRateStandardDeviation = hitRateStandardDeviation;
		this.labels = labels;
		this.confusionMatrix = confusionMatrix;
	}
	private double confidenceRate = 0.0;
	private double hitRateMean = 0.0;
	private double hitRateMedian = 0.0;
	private double hitRateVariance = 0.0;
	private double hitRateStandardDeviation = 0.0;
	private String[] labels = null;
	private int[][] confusionMatrix = null;
	public double getConfidenceRate() {
		return confidenceRate;
	}
	public double getHitRateMean() {
		return hitRateMean;
	}
	public double getHitRateMedian() {
		return hitRateMedian;
	}
	public double getHitRateVariance() {
		return hitRateVariance;
	}
	public double getHitRateStandardDeviation() {
		return hitRateStandardDeviation;
	}
	public String[] getLabels() {
		return labels;
	}
	public int[][] getConfusionMatrix() {
		return confusionMatrix;
	}
	
	
}
