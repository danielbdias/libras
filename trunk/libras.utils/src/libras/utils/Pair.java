/**
 * Provides utility classes to multiple purposes.
 */
package libras.utils;

/**
 * Represents a pair of objects.
 * @author Daniel Baptista Dias
 *
 * @param <TFirstElement> Type of the first element of the pair.
 * @param <TSecondElement> Type of the second element of the pair.
 */
public class Pair<TFirstElement, TSecondElement>
{
	/**
	 * Creates a new instace of a pair, setting the values of the two attributes.
	 * @param firstElement First element value.
	 * @param secondElement Second element value.
	 */
	public Pair(TFirstElement firstElement, TSecondElement secondElement)
	{
		this.firstElement = firstElement;
		this.secondElement = secondElement;
	}
	
	private TFirstElement firstElement = null;
	private TSecondElement secondElement = null;
	
	/**
	 * Set the first element value.
	 * @param firstElement First element value.
	 */
	public void setFirstElement(TFirstElement firstElement)
	{
		this.firstElement = firstElement;
	}
	
	/**
	 * Set the second element value.
	 * @param secondElement Second element value.
	 */
	public void setSecondElement(TSecondElement secondElement)
	{
		this.secondElement = secondElement;
	}
	
	/**
	 * Get the first element value.
	 * @return First element value.
	 */
	public TFirstElement getFirstElement()
	{
		return firstElement;
	}
	
	/**
	 * Get the second element value.
	 * @return Second element value.
	 */
	public TSecondElement getSecondElement()
	{
		return secondElement;
	}
}
