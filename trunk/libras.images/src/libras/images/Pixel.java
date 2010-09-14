/**
 * Package with commom image classes.
 */
package libras.images;

import java.security.InvalidParameterException;

import libras.utils.ValidationHelper;

/**
 * Represents a pixel of an image.
 * @author Daniel Baptista Dias
 */
public class Pixel
{
	/**
	 * Red index in a array representation of a pixel.
	 */
	final static int RED_INDEX = 0;
	/**
	 * Green index in a array representation of a pixel.
	 */
	final static int GREEN_INDEX = 1;
	/**
	 * Blue index in a array representation of a pixel.
	 */
	final static int BLUE_INDEX = 2;
	/**
	 * Represents the number of colors in a pixel.
	 */
	public final static int PIXEL_COLORS = 3;
	/**
	 * Represents a white pixel.
	 */
	public static final Pixel WHITE = PixelFactory.getPixel(255, 255, 255);
	/**
	 * Represents a black pixel.
	 */
	public static final Pixel BLACK = PixelFactory.getPixel(0, 0, 0);
	/**
	 * Represents a red pixel.
	 */
	public static final Pixel RED = PixelFactory.getPixel(255, 0, 0);
	/**
	 * Represents a green pixel.
	 */
	public static final Pixel GREEN = PixelFactory.getPixel(0, 255, 0);
	/**
	 * Represents a blue pixel.
	 */
	public static final Pixel BLUE = PixelFactory.getPixel(0, 0, 255);
	/**
	 * Represents a yellow pixel.
	 */
	public static final Pixel YELLOW = PixelFactory.getPixel(255, 255, 0);
	/**
	 * Represents a magenta pixel.
	 */
	public static final Pixel MAGENTA = PixelFactory.getPixel(255, 0, 255);
	/**
	 * Represents a cyan pixel.
	 */
	public static final Pixel CYAN = PixelFactory.getPixel(0, 255, 255);
	
	/**
	 * Instantiate a new pixel with determined values.
	 * This constructor must me used only in the PixelFactory class. 
	 * To get an instance uses the {@link libras.images.PixelFactory} instead.
	 * @param red Red component of the pixel.
	 * @param green Green component of the pixel.
	 * @param blue Blue component of the pixel.
	 */
	Pixel(int red, int green, int blue)
	{
		this(new int[] { red, green, blue });
	}
	
	/**
	 * Instantiate a new pixel with determined values in a array.
	 * This constructor must me used only in the PixelFactory class. 
	 * To get an instance uses the {@link libras.images.PixelFactory} instead.
	 * @param colors Array with the 3 color components (Red, Green, Blue) of the pixel
	 */
	Pixel(int[] colors)
	{
		ValidationHelper.validateIfParameterIsNull(colors, "colors");
		
		if (colors.length < PIXEL_COLORS)
			throw new InvalidParameterException(
				String.format("Invalid pixel. The pixel dimension must be equal or greater than %d.", 
						PIXEL_COLORS));
		
		this.pixel = getSingleIntRepresentation(colors[RED_INDEX], colors[GREEN_INDEX], colors[BLUE_INDEX]);
	}
	
	/**
	 * Representation of a pixel.
	 */
	private int pixel;

	/**
	 * Red color of the pixel.
	 */
	public int getRed()
	{
		return ((pixel >> 16) & 0xff);
	}
	/**
	 * Green color of the pixel.
	 */
	public int getGreen()
	{
		return ((pixel >> 8) & 0xff);
	}
	/**
	 * Blue color of the pixel.
	 */
	public int getBlue()
	{
		return ((pixel >> 0) & 0xff);
	}
	
	/**
	 * Returns a representation of this pixel as array.
	 */
	public int[] asArray()
	{
		int[] pixelAsArray = new int[PIXEL_COLORS];
		pixelAsArray[RED_INDEX] = this.getRed();
		pixelAsArray[GREEN_INDEX] = this.getGreen();
		pixelAsArray[BLUE_INDEX] = this.getBlue();
		
		return pixelAsArray;
	}

	/**
	 * Returns a representation of this pixel as number.
	 */
	public int asNumber()
	{
		return this.pixel;
	}

	static int getSingleIntRepresentation(int red, int green, int blue)
	{
		return (red << 16) | (green << 8) | (blue << 0);
	}
}
