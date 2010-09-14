/**
 * Package with commom image classes.
 */
package libras.images;

/**
 * Represents a point (cartesian coordinate) in a image.
 * @author Daniel Baptista Dias
 */
public class Point
{
	/**
	 * Creates a new cartesian coordinate.
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	private int x = 0;
	
	private int y = 0;
	
	/**
	 * Gets the abcissa value of the point.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Gets the ordinate value of the point.
	 */
	public int getY()
	{
		return y;
	}
	
}
