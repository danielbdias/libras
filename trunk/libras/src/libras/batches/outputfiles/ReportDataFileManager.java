package libras.batches.outputfiles;

import java.io.File;
import java.io.FileWriter;

import libras.batches.outputfiles.models.ReportData;

public class ReportDataFileManager {
	public static void createFile(ReportData report, File outputFile) throws Exception {
		File directory = outputFile.getParentFile();
		if (!directory.exists()) directory.mkdirs();
		
		FileWriter writer = new FileWriter(outputFile);
		
		writer.write("<report>\r\n");
		
		report.getConfusionMatrix();
		
		writer.write(String.format("\t<mean>%f</mean>\r\n", report.getHitRateMean()));
		writer.write(String.format("\t<median>%f</median>\r\n", report.getHitRateMedian()));
		writer.write(String.format("\t<variance>%f</variance>\r\n", report.getHitRateVariance()));
		writer.write(String.format("\t<standardDeviation>%f</standardDeviation>\r\n", report.getHitRateStandardDeviation()));
		writer.write(String.format("\t<confidence>%f</confidence>\r\n", report.getConfidenceRate()));
		
		writer.write("\t<confusionMatrix>\r\n");
		writer.write("\t\t<labels>\r\n");
		
		for (int i = 0; i < report.getLabels().length; i++) {
			writer.write("\t\t\t<label name=\"" + report.getLabels()[i] + "\" index=\"" + i + "\">\r\n");	
		}
		
		writer.write("\t\t</labels>\r\n");
		
		writer.write("\t\t<data>\r\n");
		
		for (int i = 0; i < report.getConfusionMatrix().length; i++)
		{
			writer.write("\t\t\t");
			
			for (int j = 0; j < report.getConfusionMatrix()[i].length; j++)
				writer.write(String.format("%02d;", report.getConfusionMatrix()[i][j]));	
			
			writer.write("\r\n");
		}
		
		writer.write("\t\t</data>\r\n");
		
		writer.write("\t</confusionMatrix>\r\n");
		
		writer.write("</report>\r\n");
		
		writer.close();
	}
}
