package libras.ui.actions;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import libras.ui.actions.annotations.ActionDescription;
import libras.utils.Pair;

/**
 * Test action used for debugging purposes.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="teste",
	commandExample="teste",
	helpDescription="Test a functionality")
public class TestAction extends Action
{
	@Override
	public void execute() throws Exception
	{
		final String fromDirectory = "D:\\Daniel documents\\Facul\\Libras\\Coordinates";
		final String toDirectory = "D:\\Daniel documents\\Facul\\Libras\\CoordinatesWithAbcissaAngle";
		
		convertFiles(fromDirectory, toDirectory);
	}

	private void convertFiles(String fromDirectoryPath, String toDirectoryPath) throws Exception
	{
		java.io.File fromDirectory = new java.io.File(fromDirectoryPath);
		java.io.File toDirectory = new java.io.File(toDirectoryPath);
		
		if (fromDirectory.exists() && fromDirectory.isDirectory())
		{
			if (!toDirectory.exists()) toDirectory.mkdir();
			
			java.io.File[] fromFiles = findDataFiles(fromDirectory);
			
			for (int i = 0; i < fromFiles.length; i++)
			{
				java.io.File fromFile = fromFiles[i];	
				
				java.io.File toFile = new java.io.File(
					String.format("%s\\%s_%s", 
							toDirectory.getAbsolutePath(), 
							toDirectory.getName(),
							fromFile.getName()));
				
				convertFile(fromFile, toFile);
				
				System.out.println(
					String.format("File %s converted to %s", fromFile.getName(), toFile.getName()));
			}
		}
	}

	private void convertFile(File fromFile, File toFile) throws Exception
	{
		java.io.BufferedReader reader = null;
		java.io.BufferedWriter writer = null;
		
		try
		{
			reader =
				new java.io.BufferedReader(
					new java.io.FileReader(fromFile));
			
			writer =
				new java.io.BufferedWriter(
					new java.io.FileWriter(toFile));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				String[] parsedLine = line.split(",");
				
				libras.utils.Pair<Double, Double>[] coordinates = getCoordinates(parsedLine);
				int classNumber = getClass(parsedLine);
				
				libras.utils.Pair<Double, Double>[] angles = getAngles(coordinates);
				
				String codifiedLine = codifyLine(coordinates, angles, classNumber);
				writer.write(codifiedLine);
			}
		}
		finally
		{
			if (reader != null) reader.close();
			if (writer != null) writer.close();
		}
	}

	private int getClass(String[] parsedLine)
	{
		return Integer.parseInt(parsedLine[parsedLine.length-1]);
	}

	private Pair<Double, Double>[] getCoordinates(String[] parsedLine)
	{		
		ArrayList<Pair<Double, Double>> list = new ArrayList<Pair<Double,Double>>((parsedLine.length - 1) / 2); 
		
		for (int i = 0; i < parsedLine.length; i++)
		{
			double firstItem = Double.parseDouble(parsedLine[i]);
			double secondItem = Double.parseDouble(parsedLine[i+1]);
			
			list.add(new Pair<Double, Double>(firstItem, secondItem));
		}
		
		Pair<Double, Double>[] coordinates = null;	
		return list.toArray(coordinates);
	}

	/**
	 * @param fromDirectory
	 */
	private java.io.File[] findDataFiles(java.io.File fromDirectory)
	{
		return fromDirectory.listFiles(
				new FileFilter() {
					@Override
					public boolean accept(File arg) {
						return arg.getName().endsWith(".data");
					}
				});
	}

	private String codifyLine(Pair<Double, Double>[] coordinates,
			Pair<Double, Double>[] angles, int classNumber)
	{
		return codifyLineWithAbcissa(coordinates, angles, classNumber);
	}

	private String codifyLineWithAbcissa(Pair<Double, Double>[] coordinates,
			Pair<Double, Double>[] angles, int classNumber)
	{
		StringBuilder line = new StringBuilder();
		
		for (int i = 0; i < angles.length; i++)
		{
			line.append(angles[i].getFirstElement());
			line.append(',');
		}
		
		line.append(classNumber);

		return line.toString();
	}
	
	protected String codifyLineWithAbcissaAndOrdinate(Pair<Double, Double>[] coordinates,
			Pair<Double, Double>[] angles, int classNumber)
	{
		StringBuilder line = new StringBuilder();
		
		for (int i = 0; i < angles.length; i++)
		{
			line.append(angles[i].getFirstElement());
			line.append(',');
			line.append(angles[i].getSecondElement());
			line.append(',');
		}
		
		line.append(classNumber);

		return line.toString();
	}
	
	protected String codifyLineWithCoordinatesAndAbcissa(Pair<Double, Double>[] coordinates,
			Pair<Double, Double>[] angles, int classNumber)
	{
		StringBuilder line = new StringBuilder();
		
		for (int i = 0; i < angles.length; i++)
		{
			line.append(coordinates[i].getFirstElement());
			line.append(',');
			line.append(coordinates[i].getSecondElement());
			line.append(',');
			line.append(angles[i].getFirstElement());
			line.append(',');
		}
		
		line.append(classNumber);

		return line.toString();
	}
	
	protected String codifyLineWithCoordinatesAndAbcissaAndOrdinate(Pair<Double, Double>[] coordinates,
			Pair<Double, Double>[] angles, int classNumber)
	{
		StringBuilder line = new StringBuilder();
		
		for (int i = 0; i < angles.length; i++)
		{
			line.append(coordinates[i].getFirstElement());
			line.append(',');
			line.append(coordinates[i].getSecondElement());
			line.append(',');
			line.append(angles[i].getFirstElement());
			line.append(',');
			line.append(angles[i].getSecondElement());
			line.append(',');
		}
		
		line.append(classNumber);

		return line.toString();
	}

	private Pair<Double, Double>[] getAngles(Pair<Double, Double>[] coordinates)
	{
		return getAnglesBetweenCoordinates(coordinates);
	}
	
	private Pair<Double, Double>[] getAnglesBetweenCoordinates(Pair<Double, Double>[] coordinates)
	{
		ArrayList<Pair<Double, Double>> list = new ArrayList<Pair<Double,Double>>(coordinates.length);
		
		list.add(new Pair<Double, Double>(0.0, 0.0));
		
		for (int i = 1; i < coordinates.length; i++)
		{
			Pair<Double, Double> firstCoordinate = coordinates[i-1];
			Pair<Double, Double> secondCoordinate = coordinates[i];
			
			double ordinateDelta = secondCoordinate.getSecondElement() - firstCoordinate.getSecondElement();
            double abscissaDelta = secondCoordinate.getFirstElement() - firstCoordinate.getFirstElement();
            
            double angularCoefficient = (abscissaDelta != 0 ? ordinateDelta / abscissaDelta : 0.0);

            double abcissaAngle = Math.atan(angularCoefficient);
            double ordinateAngle = abcissaAngle - 0.5;

            list.add(new Pair<Double, Double>(abcissaAngle, ordinateAngle));
		}
		
		Pair<Double, Double>[] angles = null;	
		return list.toArray(angles);
	}
	
	protected Pair<Double, Double>[] getAnglesFromOrigin(Pair<Double, Double>[] coordinates)
	{
		ArrayList<Pair<Double, Double>> list = new ArrayList<Pair<Double,Double>>(coordinates.length);
		
		for (int i = 0; i < coordinates.length; i++)
		{
			Pair<Double, Double> coordinate = coordinates[i];
			
			double ordinateDelta = coordinate.getSecondElement();
            double abscissaDelta = coordinate.getFirstElement();
            
            double angularCoefficient = (abscissaDelta != 0 ? ordinateDelta / abscissaDelta : 0.0);

            double abcissaAngle = Math.atan(angularCoefficient);
            double ordinateAngle = abcissaAngle - 0.5;

            list.add(new Pair<Double, Double>(abcissaAngle, ordinateAngle));
		}
		
		Pair<Double, Double>[] angles = null;	
		return list.toArray(angles);
	}
}
