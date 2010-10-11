package libras.ui.actions;

import java.io.*;
import java.util.*;

import libras.batches.outputfiles.OutputDataFileManager;
import libras.batches.outputfiles.ReportDataFileManager;
import libras.batches.outputfiles.models.OutputData;
import libras.batches.outputfiles.models.ReportData;
import libras.ui.actions.annotations.ActionDescription;

@ActionDescription(
		command="batchreport",
		commandExample="batchreport -dir=[batchexec_outputdir_path] -report=[report_path] ",
		helpDescription="Generate a report with the output data of a batch execution.",
		requiredArgs={ "dir", "report" },
		needUserInput=true)
public class BatchReportingAction extends Action {
	
	/**
	 * Creates a new instance of this action passing the batch file
	 * with the configuration of the training
	 * @param arguments Arguments of this action
	 */
	public BatchReportingAction(Hashtable<String, String> arguments) 
	{
		this(arguments.get("dir"), arguments.get("report"));
	}
	
	/**
	 * Creates a new instance of this action passing the batch file
	 * with the configuration of the training
	 * @param batchFile Batch file with the training parameters
	 */
	public BatchReportingAction(String batchexecOutputDir, String reportPath) 
	{
		this.dir = batchexecOutputDir;
		this.reportPath = reportPath;
	}
	
	private String dir = null;
	private String reportPath = null;
	
	@Override
	public void execute() throws Exception {
		System.out.printf("Creating report for directory [%s] ...\r\n", this.dir);

		File[] outputFiles = this.getOutputFiles(new File(this.dir));
		
		List<OutputData> data = new ArrayList<OutputData>();
		
		for (File outputFile : outputFiles) {
			try {
				data.add(OutputDataFileManager.parseFile(outputFile));
			} catch (Exception e) {
				System.out.printf("An exception occurred parsing file [%s].\r\n", outputFile.getAbsolutePath());
				e.printStackTrace(System.err);
			}
		}
		
		ReportData reportData = this.getReportData(data);
		
		ReportDataFileManager.createFile(reportData, new File(this.reportPath));
		
		System.out.printf("Report created.\r\n", this.dir);
	}

	private ReportData getReportData(List<OutputData> data) {
		double[] hitRates = this.calculateHitRates(data);
		
		double hitRateMean = this.calculateHitRateMean(hitRates);
		double hitRateMedian = this.calculateHitRateMedian(hitRates);
		double confidenceRate = this.calculateConfidenceRate(data);
		double hitRateVariance = this.calculateHitRateVariance(hitRates, hitRateMean);
		double hitRateStandardDeviation = this.calculateHitRateStandardDeviation(hitRateVariance);
		String[] labels = this.findLabelsUsed(data); 
		int[][] confusionMatrix = this.buildConfusionMatrix(data, labels);	
		
		return new ReportData(confidenceRate, hitRateMean, hitRateMedian, 
				hitRateVariance, hitRateStandardDeviation, labels, confusionMatrix);
	}

	private double calculateConfidenceRate(List<OutputData> data) {
		// TODO Calcule the confidence Rate
		return 0;
	}

	private int[][] buildConfusionMatrix(List<OutputData> data, String[] labels) {
		Hashtable<String, Integer> labelIndex = new Hashtable<String, Integer>(); 
		
		for (int i = 0; i < labels.length; i++)
			labelIndex.put(labels[i], i);
		
		int[][] confusionMatrix = new int[labels.length][labels.length];
		
		for (int i = 0; i < data.size(); i++) {
			OutputData outputData = data.get(i);
			
			for (int j = 0; j < outputData.getComputedLabels().size(); j++) {
				String computedLabel = outputData.getComputedLabels().get(j);
				String expectedLabel = outputData.getExpectedLabels().get(j);
				
				int preditedIndex = labelIndex.get(computedLabel);
				int actualIndex = labelIndex.get(expectedLabel);
				
				confusionMatrix[preditedIndex][actualIndex]++;
			}
		}
		
		
		return confusionMatrix;
	}

	private String[] findLabelsUsed(List<OutputData> data) {
		Set<String> labels = new HashSet<String>();
		
		for (int i = 0; i < data.size(); i++) {
			OutputData outputData = data.get(i);
			
			for (int j = 0; j < outputData.getExpectedLabels().size(); j++) {
				String expectedLabel = outputData.getExpectedLabels().get(j);
				labels.add(expectedLabel);
			}
		}
		
		return labels.toArray(new String[0]);
	}

	private double calculateHitRateStandardDeviation(double hitRateVariance) {
		return Math.sqrt(hitRateVariance);
	}

	private double calculateHitRateVariance(double[] hitRates, double hitRateMean) {
		double variance = 0.0;
		
		for (int i = 0; i < hitRates.length; i++)
			variance = Math.pow(hitRates[i] - hitRateMean, 2.0);
		
		return variance;
	}

	private double calculateHitRateMedian(double[] hitRates) {
		return hitRates[hitRates.length / 2];
	}

	private double calculateHitRateMean(double[] hitRates) {
		double total = 0.0;
		
		for (int i = 0; i < hitRates.length; i++)
			total += hitRates[i];
		
		return total / hitRates.length;
	}

	private double[] calculateHitRates(List<OutputData> data) {
		double[] hitRates = new double[data.size()];
		
		for (int i = 0; i < data.size(); i++) {
			OutputData outputData = data.get(i);
		
			int hits = 0, misses = 0;
			
			for (int j = 0; j < outputData.getComputedLabels().size(); j++) {
				String computedLabel = outputData.getComputedLabels().get(j);
				String expectedLabel = outputData.getExpectedLabels().get(j);
				
				if (computedLabel.equals(expectedLabel))
					hits++;
				else
					misses++;
			}
			
			hitRates[i] = (double) hits / (double) hits + misses;
		}
		
		Arrays.sort(hitRates);
		
		return hitRates;
	}
	
	private File[] getOutputFiles(File dir) {
		
		List<File> files = new ArrayList<File>();
		
		this.getOutputFilesRecursive(dir, files);
		
		return files.toArray(new File[0]);
	}
	
	private void getOutputFilesRecursive(File dir, List<File> files) {
		File[] dirs = dir.listFiles(
				new FileFilter() {
					@Override
					public boolean accept(File arg) {
						return arg.isDirectory();
					}
				});
		
		for (File subDir : dirs)
			this.getOutputFilesRecursive(subDir, files);
		
		File[] outputFiles = dir.listFiles(
				new FileFilter() {
					@Override
					public boolean accept(File arg) {
						return arg.getName().endsWith(".xml");
					}
				});
		
		for (File file : outputFiles)
			files.add(file);
	}
}
