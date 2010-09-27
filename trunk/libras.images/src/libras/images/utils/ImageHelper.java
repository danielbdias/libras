/**
 * Package with utilities classes.
 */
package libras.images.utils;

import java.awt.Point;
import java.awt.image.*;
import java.io.*;
import java.security.InvalidParameterException;

import javax.media.jai.*;
import libras.images.*;
import libras.utils.ValidationHelper;

/**
 * Helper class created used to deal with JAI API.
 * @author Daniel Baptista Dias
 */
public final class ImageHelper
{
	private static final int RED_MASK = 0x00ff0000;
	private static final int GREEN_MASK = 0x0000ff00;
	private static final int BLUE_MASK = 0x000000ff;
	
	/**
	 * Gets an image from file.
	 * @param imageFile File with an image.
	 * @throws Exception When the image is corrupted (JAI exceptions).
	 * @throws InvalidParameterException When the image file does not exists.
	 */
	public static Image getImage(File imageFile) throws Exception
	{
		ValidationHelper.validateIfFileParameterExists(imageFile, imageFile.getName());
		
		RenderedOp renderedOp = JAI.create("fileload", imageFile.getAbsolutePath());
		
		Raster raster = renderedOp.getData();
		
		PixelColorDepth colorDepth = PixelColorDepth.getColorDepth(		
				raster.getSampleModel().getDataType(), raster.getSampleModel().getNumBands());
		
		Image image = new Image(raster.getWidth(), raster.getHeight());
		
		for (int i = 0; i < image.getWidth(); i++) 
		{
			for (int j = 0; j < image.getHeight(); j++) 
			{
				int[] pixelData = raster.getPixel(i, j, (int[]) null);
				
				image.setPixel(i, j, PixelFactory.getPixel(pixelData, colorDepth));
			}
		}
		
		//renderedOp.dispose();
		
		return image;
	}
	/**
	 * Build an image file with image data.
	 * @param image Image to be saved.
	 * @param imageFile File where the image will be saved.
	 */
	public static void buildImage(Image image, File imageFile)
	{	
		 buildImage(image, null, imageFile);
	}
	/**
	 * Build an image file with indexer data.
	 * @param indexer Indexer telling which portions of the image will be saved.
	 * @param imageFile File where the image will be saved.
	 */
	public static void buildImage(ImageIndexer indexer,  File imageFile)
	{
		buildImage(indexer.getIndexedImage(), indexer, imageFile);
	}
	/**
	 * Build an image file from an image using an indexer.
	 * @param image Image to be saved.
	 * @param indexer Indexer telling which parts of the image will be saved. If is null, all image will be saved.
	 * @param imageFile File where whe image will be saved.
	 */
	private static void buildImage(Image image, ImageIndexer indexer,  File imageFile)
	{
		if (image == null) return;
		
		SampleModel sampleModel = 
			new SinglePixelPackedSampleModel(
					DataBuffer.TYPE_INT, 
					image.getWidth(),
					image.getHeight(),
					new int[] {
						RED_MASK, //Mask of red color
						GREEN_MASK, //Mask of green color
						BLUE_MASK //Mask of blue color
					});
		
		WritableRaster raster = Raster.createWritableRaster(sampleModel, new Point(0, 0));
		
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				int[] pixelData = null;
				
				if (indexer != null)
				{
					boolean copyPixel = indexer.getValue(i, j);
					
					if (copyPixel)
						pixelData = image.getPixel(i, j).asArray();
					else
						pixelData = Pixel.WHITE.asArray();
				}
				else
				{
					pixelData = image.getPixel(i, j).asArray();
				}
				
				raster.setPixel(i, j, pixelData);
			}
		}
		
		DirectColorModel colorModel = getColorModel();
		
		BufferedImage bufferedImage = new BufferedImage(colorModel, raster, true, null);
		
		JAI.create("filestore", bufferedImage, imageFile.getAbsolutePath(), "JPEG");
	}
	/**
	 * Gets the color model used to save an image.
	 * @return The color model used to save an image.
	 */
	public static DirectColorModel getColorModel()
	{
		return new DirectColorModel(
				8 * Pixel.PIXEL_COLORS, //Total bits representing an pixel
				RED_MASK, //Mask of red color
				GREEN_MASK, //Mask of green color
				BLUE_MASK); //Mask of blue color
	}
}
