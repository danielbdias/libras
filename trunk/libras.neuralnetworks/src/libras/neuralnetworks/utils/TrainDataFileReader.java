/**
 * Package with utility classes to use with the neural networks.
 */
package libras.neuralnetworks.utils;

import java.io.*;
import java.util.*;
import java.security.InvalidParameterException;
import libras.utils.*;

/**
 * Utility class used to read files with data to train neural networks.
 * @author Daniel Baptista Dias
 */
public class TrainDataFileReader
{
	private static Hashtable<String, List<Pair<String, double[]>>> _dataCache = null;
	
	static {
		_dataCache = new Hashtable<String, List<Pair<String,double[]>>>();
	}
	
	/**
	 * Get class data from a text file to use in neural network training.
	 * @param fileName File where the class data will be retrieved.
	 * @return A list with all classes found in file.
	 * @throws Exception When an unexpected exception occurs.
	 */
 	public static List<String> getClassesFromFile(String fileName) throws Exception
	{
		ValidationHelper.validateIfParameterIsNullOrEmpty(fileName, "fileName");
		
		File file = new File(fileName);
		
		ValidationHelper.validateIfFileParameterExists(file, fileName);
		
		List<String> classes = new ArrayList<String>();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String temp = null;
		
		while ((temp = reader.readLine()) != null)
			classes.add(temp);
		
		return classes;
	}

	/**
	 * Get class data from a text file to use in neural network training.
	 * @param fileName File where the data will be retrieved.
	 * @return A list with all data found in file.
	 * @throws Exception When an unexpected exception occurs.
	 */
	public static synchronized List<Pair<String, double[]>> getDataFromFile(String fileName) throws Exception
	{	
		ValidationHelper.validateIfParameterIsNullOrEmpty(fileName, "fileName");
		
		if (_dataCache.containsKey(fileName)) 
			return _dataCache.get(fileName);
		
		File file = new File(fileName);
		
		ValidationHelper.validateIfFileParameterExists(file, fileName);
		
		ArrayList<Pair<String, double[]>> inputData = new ArrayList<Pair<String, double[]>>();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String temp = null;
		
		while ((temp = reader.readLine()) != null)
		{
			temp = temp.replaceAll(" ", "");
			
			String[] tokens = temp.split(",");
			
			if (tokens != null && tokens.length > 0)
			{
				double[] input = new double[tokens.length - 1];
				for (int i = 0; i < tokens.length; i++)
				{
					String token = tokens[i];

					if (i == tokens.length-1)
					{
						inputData.add(new Pair<String, double[]>(token, input));
					}
					else
					{
						if (token.matches("-{0,1}[0-9]{1,}(.[0-9]{1,}){0,1}"))
						{
							double data = Double.parseDouble(token);
							input[i] = data;
						}
					}
				}
			}
		}
		
		if (!_dataCache.containsKey(fileName))
			_dataCache.put(fileName, inputData);
		
		return inputData;
	}

	/**
	 * Get class data from a text file to use in neural network training.
	 * @param fileName File where the data will be retrieved.
	 * @return A list with all data found in file.
	 * @throws Exception When an unexpected exception occurs.
	 */
	public static List<double[]> getAnalysisDataFromFile(String fileName) throws Exception
	{
		//TODO: improve documentation
		if (fileName == null || fileName.isEmpty())
			throw new InvalidParameterException("The parameter fileName cannot be null or empty.");
		
		File file = new File(fileName);
		
		if (!file.exists())
			throw new InvalidParameterException(String.format("The file [%s] not exists.", fileName));
		
		ArrayList<double[]> inputData = new ArrayList<double[]>();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String temp = null;
		
		while ((temp = reader.readLine()) != null)
		{
			temp = temp.replaceAll(" ", "");
			
			String[] tokens = temp.split(",");
			
			if (tokens != null && tokens.length > 0)
			{
				double[] input = new double[tokens.length];
				
				for (int i = 0; i < tokens.length; i++)
				{
					String token = tokens[i];

					if (token.matches("-{0,1}[0-9]{1,}(.[0-9]{1,}){0,1}"))
					{
						double data = Double.parseDouble(token);
						input[i] = data;
					}
				}
				
				inputData.add(input);
			}
		}
		
		return inputData;
	}
}
