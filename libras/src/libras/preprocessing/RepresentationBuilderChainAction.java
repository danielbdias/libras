package libras.preprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import libras.preprocessing.representation.Coordinate;
import libras.preprocessing.representation.DimensionBuilder;

public class RepresentationBuilderChainAction extends ChainAction
{
	public RepresentationBuilderChainAction(File centroidFile, File representationFile, DimensionBuilder[] representation)
	{
		super();
		
		initializeAttributes(centroidFile, representationFile, representation);
	}

	public RepresentationBuilderChainAction(File centroidFile, File representationFile, DimensionBuilder[] representation, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(centroidFile, representationFile, representation);
	}
	
	private File centroidFile = null;
	
	private File representationFile = null;
	
	private DimensionBuilder[] representation = null;
	
	private void initializeAttributes(File centroidFile, File representationFile, DimensionBuilder[] representation)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(centroidFile, "centroidFile");
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(centroidFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(representationFile, "representationFile");
		
		if (representationFile.exists())
			libras.utils.ValidationHelper.validateIfFileParameterIsFile(representationFile);
		
		libras.utils.ValidationHelper.validateIfParameterIsNull(representation, "representation");
		
		for (DimensionBuilder dimensionBuilder : representation) {
			libras.utils.ValidationHelper.validateIfParameterIsNull(dimensionBuilder, "dimensionBuilder");	
		}
		
		this.centroidFile = centroidFile;
		this.representationFile = representationFile;
		this.representation = representation;
	}
	
	@Override
	protected void doExecuteAction()
	{
		java.io.BufferedReader reader = null;
		java.io.BufferedWriter writer = null;
		
		try
		{
			reader =
				new java.io.BufferedReader(
					new java.io.FileReader(this.centroidFile));
			
			writer =
				new java.io.BufferedWriter(
					new java.io.FileWriter(this.representationFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				String codifiedLine = codifyLine(parsedLine);
				writer.write(codifiedLine);
				writer.newLine();
			}
			
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace(System.err);
		}
	}

	protected String codifyLine(String[] parsedLine)
	{
		Coordinate[] coordinates = this.getCoordinatesFromParsedLine(parsedLine);
		int classNumber = this.getClassNumberFromParsedLine(parsedLine);
		
		Double[] instance = this.buildInstance(coordinates); 
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < instance.length; i++) {
			if (instance[i] != null)
				builder.append(instance[i]);
			else
				builder.append('?');
			builder.append(',');
		}
		
		builder.append(classNumber);
		
		return builder.toString();
	}

	private Coordinate[] getCoordinatesFromParsedLine(String[] parsedLine) {
		final int coordinateSize = 2;
		
		Coordinate[] coordinates = new Coordinate[parsedLine.length / coordinateSize];
		
		for (int i = 0; i < coordinates.length; i++) {
			String abcissaAsString = parsedLine[coordinateSize*i]; 
			String ordinateAsString = parsedLine[coordinateSize*i+1];
			
			if (abcissaAsString != null && ordinateAsString != null &&
				!abcissaAsString.contains("?") && !ordinateAsString.contains("?")) {
				double abcissa = Double.parseDouble(parsedLine[coordinateSize*i]);
				double ordinate = Double.parseDouble(parsedLine[coordinateSize*i+1]);
				
				coordinates[i] = new Coordinate(abcissa, ordinate);
			}
		}
		
		return coordinates;
	}	

	private int getClassNumberFromParsedLine(String[] parsedLine) {
		return Integer.parseInt(parsedLine[parsedLine.length-1]);
	}

	private Double[] buildInstance(Coordinate[] coordinates) {
		ArrayList<Double[]> dimensions = this.getDimensions(coordinates);
		
		ArrayList<Double> instance = new ArrayList<Double>();
		
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j < dimensions.size(); j++) {
				instance.add(dimensions.get(j)[i]);
			}
		}
		
		return instance.toArray(new Double[] {});
	}

	private ArrayList<Double[]> getDimensions(Coordinate[] coordinates) {
		ArrayList<Double[]> dimensions = new ArrayList<Double[]>();
		
		for (DimensionBuilder builder : this.representation) {
			Double[] dimension = builder.buildDimension(coordinates);
			
			//TODO: improve validation
			if (dimension.length != coordinates.length)
				throw new InvalidParameterException("The parameter \"representation\" must build dimensions with the same size of the coordinates");
			
			dimensions.add(dimension);
		}
		
		return dimensions;
	}
}
