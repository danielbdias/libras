/**
 * Provides various image analysers.
 */
package libras.images.analysers;

import libras.images.Pixel;
import libras.utils.ValidationHelper;

/**
 * Analyses an image using color segmentation.
 * @author Daniel Baptista Dias 
 */
public class ColorSegmentationImageAnalyser extends ImageAnalyser
{
	/**
	 * Creates a new instance of this analyser, setting the pixel used in comparison 
	 * and the threshold for the comparison.
	 * @param pixelToBeCompared Pixel used as reference to make the segmentation.
	 * @param threshold Threshold value used in the algorithm.
	 */
	public ColorSegmentationImageAnalyser(Pixel pixelToBeCompared, int threshold)
	{
		ValidationHelper.validateIfParameterIsNull(pixelToBeCompared, "pixelToBeCompared");
		ValidationHelper.validateIfParameterIsEqualOrGreaterThanZero(threshold, "threshold");
		
		this.pixelToBeCompared = pixelToBeCompared;
		this.threshold = threshold;
	}
	
	private Pixel pixelToBeCompared = null;
	
	private int threshold = 0;
	
	/**
	 * Make an analysis of a pixel using color segmentation
	 * @param pixel Pixel to be validated as similar to the base pixel used in the algorithm.
	 * @return True, if the pixel is similar to the base pixel. Otherwise, false.
	 */
	protected boolean analysePixel(Pixel pixel)
	{
		int distance = 
			libras.utils.MathHelper.euclideanDistance(pixel.asArray(), this.pixelToBeCompared.asArray());
		return (this.threshold >= distance);
	}
	
	/**
	 * Get the threshold value used in color segmentation.
	 * @return Threshold value
	 */
	protected int getThreshold()
	{
		return threshold;
	}
}
