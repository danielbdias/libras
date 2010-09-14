/**
 * Package with commom image classes.
 */
package libras.images;

import java.security.InvalidParameterException;
import java.util.Hashtable;

import libras.utils.ValidationHelper;

/**
 * Handles all instances of Pixel objects, using the Flyweight desing pattern.
 * @author Daniel Baptista Dias
 */
public class PixelFactory
{
	private static Hashtable<Integer, Pixel> cache = null;
	
	static 
	{
		cache = new Hashtable<Integer, Pixel>();
	}
	
	/**
	 * Gets a pixel with the specified parameter values.
	 * @param red Red component of the pixel.
	 * @param green Green component of the pixel.
	 * @param blue Blue component of the pixel.
	 * @return Pixel with the specified parameter values.
	 */
	public static Pixel getPixel(int red, int green, int blue)
	{
		return getPixel(new int[] { red, green, blue });
	}
	
	/**
	 * Gets a pixel with the specified parameter values.
	 * @param colors Array with the components of the pixel.
	 * @return Pixel with the specified parameter values.
	 */
	public static Pixel getPixel(int[] colors) 
	{
		ValidationHelper.validateIfParameterIsNull(colors, "colors");
		
		if (colors.length < Pixel.PIXEL_COLORS)
			throw new InvalidParameterException(
				String.format("Invalid pixel. The pixel dimension must be equal or greater than %d.", 
						Pixel.PIXEL_COLORS));
		
		Integer sigleIntRepresentation = 
			Integer.valueOf(Pixel.getSingleIntRepresentation(colors[Pixel.RED_INDEX], colors[Pixel.GREEN_INDEX], colors[Pixel.BLUE_INDEX]));
		
		Pixel pix = cache.get(sigleIntRepresentation);
		
		if (pix == null)
		{
			pix = new Pixel(colors[Pixel.RED_INDEX], colors[Pixel.GREEN_INDEX], colors[Pixel.BLUE_INDEX]);
			cache.put(sigleIntRepresentation, pix);
		}
		
		return pix;
	}
}
