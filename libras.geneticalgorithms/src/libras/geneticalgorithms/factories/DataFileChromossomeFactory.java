package libras.geneticalgorithms.factories;

import java.io.*;
import java.util.*;

import libras.geneticalgorithms.chromossomes.*;
import libras.utils.*;


public class DataFileChromossomeFactory extends ChromossomeFactory {
	public DataFileChromossomeFactory(String dataFilePath, int numberOfClusters) {
		this.dataFilePath = dataFilePath;
		this.numberOfClusters = numberOfClusters;
	}
	
	private String dataFilePath = null;
	private int numberOfClusters = 0;
	
	@Override
	public Chromossome create() {
		List<Pair<String, Double[]>> data = this.getDataFromFile(this.dataFilePath);
		
		ClusteringChromossome newChromossome = 
			new ClusteringChromossome(data.size(), this.numberOfClusters);
		
		for (int i = 0; i < data.size(); i++) {
			Pair<String, Double[]> pair = data.get(i);
			Double[] allelData = pair.getSecondItem();
			
			Allel allel = new Allel(allelData.length);
			
			allel.setType(pair.getFirstItem());
			
			for (int j = 0; j < allelData.length; j++) {
				allel.setData(j, allelData[j]);
			}
			
			newChromossome.setAllel(i, allel);
		}
		
		return newChromossome;
	}

	private List<Pair<String, Double[]>> getDataFromFile(String dataFilePath) {
		try {
			ArrayList<Pair<String, Double[]>> result = new ArrayList<Pair<String, Double[]>>();
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(dataFilePath)));
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				String[] temp = line.split(",");
				
				Double[] data = new Double[temp.length-1];
				
				for (int i = 0; i < data.length; i++) {
					data[i] = Double.parseDouble(temp[i].trim());
				}
				
				result.add(
					new Pair<String, Double[]>(
							temp[temp.length-1].trim(), 
							data));
			}
			
			reader.close();
			
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
