/**
 * Provides various image analysers.
 */
package libras.images.analysers;

import java.util.*;

import libras.images.*;
import libras.utils.ValidationHelper;

/**
 * Provides a common image analysis.
 * @author Daniel Baptista Dias
 */
public abstract class ImageAnalyser
{
	/**
	 * Analyse an image.
	 * @param image Image to be analysed.
	 */
	public ImageIndexer analyse(Image image)
	{
		ValidationHelper.validateIfParameterIsNull(image, "image");
		
		ImageIndexer indexer = this.analyseImage(image);
		
		this.treatNoise(indexer);
		
		return indexer;
	}

	/**
	 * Make analysis of the image.
	 * @param image Image to be analysed.
	 * @return An image indexer corresponding with the analysis criteria.
	 */
	protected ImageIndexer analyseImage(Image image)
	{		
		ImageIndexer indexer = new ImageIndexer(image);
		
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				boolean validPixel = this.analysePixel(image.getPixel(i, j));
				indexer.setValue(i, j, validPixel);
			}
		}
		
		return indexer;
	}
	
	/**
	 * Analyse a pixel telling if it is valid.
	 * @param pixel
	 * @return True, if is a valid pixel, otherwise false.
	 */
	protected abstract boolean analysePixel(Pixel pixel);
	
	/**
	 * Treat noise on the image.
	 * @param indexer Indexer of the image to be treated.
	 */
	protected void treatNoise(ImageIndexer indexer)
	{
		//Makes a copy which will receive the changes
		ImageIndexer indexerWithoutNoise = indexer.clone();
		
		for (int i = 0; i < indexer.getIndexedImage().getWidth(); i++)
		{
			for (int j = 0; j < indexer.getIndexedImage().getHeight(); j++)
			{
				if (indexer.getValue(i, j))
				{
					boolean validPixel = this.validPixel(indexer, i, j);
					indexerWithoutNoise.setValue(i, j, validPixel);
				}
			}
		}
		
		//Copy data for the indexer
		for (int i = 0; i < indexer.getIndexedImage().getWidth(); i++)
		{
			for (int j = 0; j < indexer.getIndexedImage().getHeight(); j++)
			{
				indexer.setValue(i, j, indexerWithoutNoise.getValue(i, j));
			}
		}
	}

	/**
	 * Determines if a pixel is valid.
	 * @param indexer Indexer of the image used in the validation.
	 * @param i The abcissa coordinate of the pixel.
	 * @param j The ordinate coordinate of the pixel.
	 */
	protected boolean validPixel(ImageIndexer indexer, int i, int j)
	{
		//TODO: improve the documentation of this method.
		
		final int bound = 20;
		
		int startWidthToAnalyse = i - bound, endWidthToAnalyse = i + bound;
		int startHeightToAnalyse = j - bound, endHeightToAnalyse = j + bound;
		
		//Fix the bounds
		if (startWidthToAnalyse < 0) startWidthToAnalyse = 0;
		if (startHeightToAnalyse < 0) startHeightToAnalyse = 0;
		
		if (endWidthToAnalyse > indexer.getIndexedImage().getWidth() - 1) 
			endWidthToAnalyse = indexer.getIndexedImage().getWidth() - 1;

		if (endHeightToAnalyse > indexer.getIndexedImage().getHeight() - 1) 
			endHeightToAnalyse = indexer.getIndexedImage().getHeight() - 1;
		
		int sumOfRelevantPixelsInAnalysedSpace = 0;
		
		for (int x = startWidthToAnalyse; x < endWidthToAnalyse; x++)
		{
			for (int y = startHeightToAnalyse; y < endHeightToAnalyse; y++)
			{
				if (indexer.getValue(x, y)) sumOfRelevantPixelsInAnalysedSpace++;
			}
		}
		
		int totalPixelsInAnalysedSpace = (endWidthToAnalyse - startWidthToAnalyse) * (endHeightToAnalyse - startHeightToAnalyse);
		
		return ((sumOfRelevantPixelsInAnalysedSpace / (double) totalPixelsInAnalysedSpace) > 0.35);
	}

	/**
	 * Find the centroid of an image using an index.
	 * @param indexer Indexer of an image.
	 * @return The centroid of the valid pixels in the image.
	 */
	public Point findCentroid(ImageIndexer indexer)
	{
		ArrayList<Integer> widthPoints = new ArrayList<Integer>();
		ArrayList<Integer> heightPoints = new ArrayList<Integer>();
	
		//Insert the coordinates in collections
		for (int i = 0; i < indexer.getIndexedImage().getWidth(); i++)
		{
			for (int j = 0; j < indexer.getIndexedImage().getHeight(); j++)
			{
				if (indexer.getValue(i, j))
				{	
					widthPoints.add(new Integer(i));
					heightPoints.add(new Integer(j));
				}
			}
		}
		
		int[] orderedWidthPoints = new int[widthPoints.size()];
		
		for (int i = 0; i < orderedWidthPoints.length; i++)
			orderedWidthPoints[i] = widthPoints.get(i);
		
		int[] orderedHeightPoints = new int[heightPoints.size()];
		
		for (int i = 0; i < orderedHeightPoints.length; i++)
			orderedHeightPoints[i] = heightPoints.get(i);
		
		Arrays.sort(orderedWidthPoints);
		Arrays.sort(orderedHeightPoints);
		
		if (orderedHeightPoints.length > 0 && orderedWidthPoints.length > 0)
			return new Point(
					orderedWidthPoints[orderedWidthPoints.length/2], 
					orderedHeightPoints[orderedHeightPoints.length/2]);
		else
			return null; //Indicates that no segmented image was found
	}
}
