/**
 * Package with commom image classes.
 */
package libras.images;

import java.security.InvalidParameterException;

import libras.utils.ValidationHelper;

/**
 * Provides a simpler representation of an Image.
 * @author Daniel Baptista Dias
 */
public class Image
{
	/**
	 * Creates a new representation of a image, where all pixels are white.
	 * @param height Height of the image.
	 * @param width Weight of the image.
	 */
	public Image(int width, int height)
	{
		this.initializePixels(width, height);
	}
	/**
	 * Creates a new representation of a image with determined pixels.
	 * @param pixels Pixels used to build the image.
	 * @throws Exception When the parameter <b>pixel</b> is null or have a dimension with lenght 0 (zero).
	 */
	public Image(Pixel[][] pixels) throws Exception
	{
		this.initializePixels(pixels);	
	}
	
	private Pixel[][] pixels = null;
	
	/**
	 * Initializes all pixels in the image.
	 * @param height Height of the image.
	 * @param width Weight of the image.
	 */
	private void initializePixels(int width, int height)
	{
		this.pixels = new Pixel[width][height];
		
		for (int i = 0; i < this.pixels.length; i++)
		{
			for (int j = 0; j < this.pixels[i].length; j++)
			{
				this.pixels[i][j] = Pixel.WHITE;
			}
		}
	}
	
	/**
	 * Initializes all pixels in the image using an array as example.
	 * @param pixels Array with the pixels of the image.
	 * @throws InvalidParameterException When a invalid position is passed.
	 */
	private void initializePixels(Pixel[][] pixels)
	{
		ValidationHelper.validateIfParameterIsNull(pixels, "pixels");
		
		if (pixels.length <= 0 || pixels[0].length <= 0)
			throw new InvalidParameterException("A dimension of the image cannot be 0 (zero).");
		
		this.pixels = new Pixel[pixels.length][pixels[0].length];
		
		for (int i = 0; i < this.pixels.length; i++)
		{
			for (int j = 0; j < this.pixels[i].length; j++)
			{
				//Copy each pixel in array
				Pixel pixel = pixels[i][j];
				this.pixels[i][j] = PixelFactory.getPixel(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
			}
		}
	}
	
	/**
	 * Gets a pixel in a determined position.
	 * @param i The abcissa coordinate.
	 * @param j The ordinate coordinate.
	 * @return A pixel.
	 * @throws InvalidParameterException When a invalid position is passed.
	 */
	public Pixel getPixel(int i, int j)
	{
		if (i < 0 || i >= this.pixels.length ||
			j < 0 || j >= this.pixels[0].length)
			throw new InvalidParameterException(
				String.format("The coordinate indexes (%d, %d) does not exists in the image.", i, j));
		
		return this.pixels[i][j];
	}
	
	/**
	 * Sets the value of a pixel in the image.
	 * @param i The abcissa coordinate.
	 * @param j The ordinate coordinate.
	 * @param pixel Pixel to be setted in the specified coordinate.
	 */
	public void setPixel(int i, int j, Pixel pixel)
	{
		if (i < 0 || i >= this.pixels.length ||
				j < 0 || j >= this.pixels[0].length)
			throw new InvalidParameterException(
				String.format("The coordinate indexes (%d, %d) does not exists in the image.", i, j));
		
		this.pixels[i][j] = pixel;
	}
	
	/**
	 * Gets the image height.
	 */
	public int getHeight()
	{
		return this.pixels[0].length;
	}
	/**
	 * Gets the image weight.
	 */
	public int getWidth()
	{
		return this.pixels.length;
	}

	/**
	 * Returns a representation of this image as an array.
	 * @return Representation of this image as an array 
	 */
	public int[] asArray()
	{
		int[] array = new int[this.getWidth() * this.getHeight()];
		
		int arrayIndex = 0;
		
		for (int i = 0; i < this.getHeight(); i++)
		{
			for (int j = 0; j < this.getWidth(); j++)
			{
				array[arrayIndex] = pixels[j][i].asNumber();
				arrayIndex++;
			}
		}
		
		return array;
	}
}
