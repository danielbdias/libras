/**
 * Package with classes to process a LIBRAS sign.
 */
package libras;

import java.lang.reflect.Constructor;
import java.security.InvalidParameterException;
import java.util.*;

import libras.ui.actions.Action;
import libras.ui.actions.annotations.ActionDescription;
import libras.utils.ReflectionHelper;
import libras.utils.ValidationHelper;

/**
 * Manage the program.
 * @author Daniel Dias
 */
public final class Program
{
	/**
	 * This method cannot be called, because this class has only static methods.
	 */
	private Program()
	{ }

	/**
	 * Entry point of the program.
	 * @param args Command-line arguments for this program.
	 */
	public static void main(String[] args)
	{	
		try
		{
			System.out.println("Welcome to LIBRAS !");
			
			executeActions(args);
			
			//processImageTest();
			//testNeuralNetwork();
			//processImageMultipleColorSegmentationTest();
		}
		catch (Exception ex)
		{
			treatException(ex);
		}
	}
	
	/**
	 * Treat an exception thrown by this program.
	 * @param ex Exception to be treated.
	 */
	private static void treatException(Exception ex)
	{
		System.out.println("An unknown error has occurred in this program...");
		System.out.println("Error: " + ex.toString());
		
		ex.printStackTrace(System.out);
		System.out.println();
	}
	
	/**
	 * Execute actions based in the program arguments.
	 * @param args Program arguments.
	 */
	protected static void executeActions(String[] args) throws Exception
	{
		ValidationHelper.validateIfParameterIsNull(args, "args");
		
		if (args.length != 1)
			throw new InvalidParameterException("Only 1 (one) parameter must be passed to the program.");
		
		String argument = args[0];
		argument = argument.toLowerCase();
		
		if (!argument.startsWith("-"))
			throw new InvalidParameterException("The parameter does not start with \"-\".");
		
		//Removes the "-"
		argument = argument.substring(1);
		
		List<Class<?>> actionClasses = ReflectionHelper.getSubclasses(Action.class, Action.class.getPackage());
		
		if (actionClasses != null)
		{
			for (int j = 0; j < actionClasses.size(); j++)
			{
				Class<?> actionClass = actionClasses.get(j);
				
				ActionDescription description = actionClass.getAnnotation(ActionDescription.class);
				
				//Ignores all actions with description or with empty commands.
				if (description != null 
						&& description.command() != null
						&& !description.command().isEmpty())
				{
					if (argument.startsWith(description.command()))
					{
						Action action = null;
						
						if (description.needUserInput())
						{
							String userInput = identifyPathFromUserInput(argument, description.command());
						
							Constructor<?> constructor = actionClass.getConstructor(String.class);
							action = (Action) constructor.newInstance(userInput);
						}
						else
						{
							action = (Action) actionClass.newInstance();
						}
						
						if (action != null)
						{
							java.text.DateFormat dfm = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
							
							System.out.println(String.format("Executing process at [%s]...", dfm.format(new Date(System.currentTimeMillis()))));
							action.execute();
							System.out.println(String.format("Process executed at [%s].", dfm.format(new Date(System.currentTimeMillis()))));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Provides a generic parameter recognition for a path.
	 * @param input Input data of the user for a parameter
	 * @param parameter Parameter used.
	 */
	private static String identifyPathFromUserInput(String input, String parameter)
	{
		String value = input;
		
		//Remove the parameter string from the start of the string.
		if (value.startsWith(parameter)) 
			value = value.substring(parameter.length());
		
		//Remove the "=" string from the start of the string.
		if (value.startsWith("="))
			value = value.substring(1);
		
		if (value.startsWith("\"") && value.endsWith("\""))
			value = value.substring(1).substring(0, value.length() - 2);
		
		return value;
	}
	
	/**
	 * Development tests of image processing project.
	 */
	protected static void processImageTest()
	{
		try
		{
			libras.images.Image image = 
				libras.images.utils.ImageHelper.getImage(
						new java.io.File("D:\\Daniel Pictures\\IC\\IMG00001.jpg"));
			
			libras.images.analysers.ColorSegmentationImageAnalyser analyser =
				new libras.images.analysers.ColorSegmentationImageAnalyser(
						libras.images.Pixel.RED, 175);
			
			libras.images.ImageIndexer indexer = analyser.analyse(image);
			
			libras.images.Point centroid = analyser.findCentroid(indexer);
			
			int i = centroid.getX(), j = centroid.getY();
			
			int startWidthToAnalyse = i - 5, endWidthToAnalyse = i + 5;
			int startHeightToAnalyse = j - 5, endHeightToAnalyse = j + 5;
			
			//Fix the bounds
			if (startWidthToAnalyse < 0) startWidthToAnalyse = 0;
			if (startHeightToAnalyse < 0) startHeightToAnalyse = 0;
			
			if (endWidthToAnalyse > indexer.getIndexedImage().getWidth() - 1) 
				endWidthToAnalyse = indexer.getIndexedImage().getWidth() - 1;

			if (endHeightToAnalyse > indexer.getIndexedImage().getHeight() - 1) 
				endHeightToAnalyse = indexer.getIndexedImage().getHeight() - 1;
			
			for (int x = startWidthToAnalyse; x < endWidthToAnalyse; x++)
				for (int y = startHeightToAnalyse; y < endHeightToAnalyse; y++)
					image.setPixel(x, y, libras.images.Pixel.BLACK);
			
			libras.images.utils.ImageHelper.buildImage(indexer, 
					new java.io.File("D:\\Daniel Pictures\\IC\\bla.jpg"));
			
			System.out.print("end");
			System.in.read();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Development tests of multiple color segmentation image processing.
	 */
	protected static void processImageMultipleColorSegmentationTest()
	{
		try
		{
			libras.images.Image image = 
				libras.images.utils.ImageHelper.getImage(
						new java.io.File("D:\\Daniel Pictures\\IC\\Segmentation\\sample.jpg"));
			
			libras.images.analysers.ColorSegmentationImageAnalyser firstAnalyser =
				new libras.images.analysers.ColorSegmentationImageAnalyser(
						libras.images.Pixel.RED, 50);
			
			libras.images.analysers.ColorSegmentationImageAnalyser secondAnalyser =
				new libras.images.analysers.ColorSegmentationImageAnalyser(
						libras.images.Pixel.GREEN, 50);
			
			libras.images.analysers.ColorSegmentationImageAnalyser thirdAnalyser =
				new libras.images.analysers.ColorSegmentationImageAnalyser(
						libras.images.Pixel.BLUE, 50);
			
			libras.images.analysers.ColorSegmentationImageAnalyser forthAnalyser =
				new libras.images.analysers.ColorSegmentationImageAnalyser(
						libras.images.Pixel.BLACK, 50);
			
			libras.images.ImageIndexer firstIndexer = firstAnalyser.analyse(image);
			libras.images.ImageIndexer secondIndexer = secondAnalyser.analyse(image);
			libras.images.ImageIndexer thirdIndexer = thirdAnalyser.analyse(image);
			libras.images.ImageIndexer fourthIndexer = forthAnalyser.analyse(image);
			
			libras.images.ImageIndexer[] indexers = 
				new libras.images.ImageIndexer[] { firstIndexer, secondIndexer, thirdIndexer, fourthIndexer };
			
			libras.images.ImageIndexer finalIndexer = indexers[0].clone();
			
			for (int i = 1; i < indexers.length - 1; i++)
			{
				for (int x = 0; x < indexers[i].getIndexedImage().getHeight(); x++)
				{
					for (int y = 0; y < indexers[i].getIndexedImage().getHeight(); y++)
					{
						if (indexers[i].getValue(x, y))
							finalIndexer.setValue(x, y, indexers[i].getValue(x, y));
					}
				}
			}
			
			libras.images.utils.ImageHelper.buildImage(firstIndexer, 
					new java.io.File("D:\\Daniel Pictures\\IC\\Segmentation\\first.jpg"));
			
			libras.images.utils.ImageHelper.buildImage(secondIndexer, 
					new java.io.File("D:\\Daniel Pictures\\IC\\Segmentation\\second.jpg"));
			
			libras.images.utils.ImageHelper.buildImage(thirdIndexer, 
					new java.io.File("D:\\Daniel Pictures\\IC\\Segmentation\\third.jpg"));
			
			libras.images.utils.ImageHelper.buildImage(fourthIndexer, 
					new java.io.File("D:\\Daniel Pictures\\IC\\Segmentation\\fourth.jpg"));
			
			libras.images.utils.ImageHelper.buildImage(finalIndexer, 
					new java.io.File("D:\\Daniel Pictures\\IC\\Segmentation\\sum.jpg"));
			
			System.out.print("end");
			System.in.read();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Development tests of neural network project.
	 */
	protected static void testNeuralNetwork()
	{
		/*final String classesFile = "D:\\Daniel Documents\\Facul\\_classes2.txt";
		final String dataFile = "D:\\Daniel Documents\\Facul\\_dados2.txt";
		final String analysisFile = "D:\\Daniel Documents\\Facul\\_reconhecimento2.txt";
		
		try
		{
			List<String> classes = 
				libras.neuralnetworks.utils.DataFileReader.getClassesFromFile(classesFile);
			
			List<Pair<String, double[]>> trainData = 
				libras.neuralnetworks.utils.DataFileReader.getInputDataFromFile(dataFile);
			
			List<double[]> analysisData =
				libras.neuralnetworks.utils.DataFileReader.getAnalysisDataFromFile(analysisFile);
			
			if (classes != null && !classes.isEmpty() &&
				trainData != null && !trainData.isEmpty())
			{
				int inputLength = trainData.get(0).getSecondElement().length, outputLength = classes.size();
				
				int[] expectedOutput = new int[trainData.size()];
				double[][] input = new double[trainData.size()][];
				
				for (int i = 0; i < trainData.size(); i++)
				{
					Pair<String, double[]> pair = trainData.get(i);
					
					expectedOutput[i] = classes.indexOf(pair.getFirstElement());
					input[i] = pair.getSecondElement();
				}
				
				libras.neuralnetworks.distancenetworks.DistanceNeuralNetwork network =
					new libras.neuralnetworks.distancenetworks.DistanceNeuralNetwork(new int[] { inputLength, outputLength });
			
				libras.neuralnetworks.learning.LearningVectorQuantization teacher =
					new libras.neuralnetworks.learning.LearningVectorQuantization(network, 1.0, null)*/;
				
				/*for (int i = 0; i < 25; i++)
					teacher.trainNetwork(input, expectedOutput, LVQLearningStrategy.Randomized);
				
				teacher.setLearningRate(0.1);
				teacher.setLearningTaxDecreaseRate(0.1);
				
				for (int i = 0; i < 100; i++)
					teacher.trainNetwork(input, expectedOutput, LVQLearningStrategy.Randomized);
				
				for (int i = 0; i < analysisData.size(); i++)
				{
					double[] vectorToAnalyse = analysisData.get(i);
					
					int output = network.compute(vectorToAnalyse);
					
					//for (int j = 0; j < vectorToAnalyse.length; j++)
						//System.out.print(String.format("%f, ", vectorToAnalyse[j]));
					
					System.out.println(classes.get(output));
				}*/
//			}
//		}
//		catch (Exception e)
//		{
//			System.err.println(e.toString());
//			e.printStackTrace(System.err);
//		}
	}
}
