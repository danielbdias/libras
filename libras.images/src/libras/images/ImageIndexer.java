/**
 * Package with commom image classes.
 */
package libras.images;

import java.security.InvalidParameterException;

import libras.utils.ValidationHelper;

/**
 * Indexes an image, telling which pixels are valid.
 * This class is used in image processes.
 * @author Daniel Baptista Dias
 */
public class ImageIndexer implements Cloneable
{
	/**
	 * Creates a new index for an image.
	 * @param image Image to be indexed.
	 */
	public ImageIndexer(Image image)
	{
		ValidationHelper.validateIfParameterIsNull(image, "image");
		
		this.imageToIndex = image;
		this.initializeIndex();
	}
	
	private boolean[][] index = null;

	private Image imageToIndex = null;
	
	/**
	 * Initializes the internal index.
	 */
	private void initializeIndex()
	{
		this.index = new boolean[imageToIndex.getWidth()][imageToIndex.getHeight()];
		
		for (int i = 0; i < imageToIndex.getWidth(); i++)
		{
			for (int j = 0; j < imageToIndex.getHeight(); j++)
				this.index[i][j] = true;
		}
	}

	/**
	 * Gets a index data in a determined position
	 * @return A index data.
	 * @throws InvalidParameterException When a invalid position is passed.
	 */
	public boolean getValue(int i, int j)
	{
		if (i < 0 || i >= this.index.length ||
			j < 0 || j >= this.index[0].length)
			throw new InvalidParameterException(
				String.format("The coordinate indexes (%d, %d) does not exists in the image indexer.", i, j));
			
		return this.index[i][j];
	}
	/**
	 * Sets index data for a pixel.
	 * @param i The abcissa coordinate.
	 * @param j The ordinate coordinate.
	 * @param indexData Value to be setted in the indexer.
	 */
	public void setValue(int i, int j, boolean indexData)
	{
		if (i < 0 || i >= this.index.length ||
				j < 0 || j >= this.index[0].length)
			return;
		
		this.index[i][j] = indexData;
	}
	/**
	 * Returns the image indexed by this object.
	 * @return Image indexed by this object.
	 */
	public Image getIndexedImage()
	{
		return this.imageToIndex;
	}
	/**
	 * Returns a copy of image index.
	 * @return Copy of this object.
	 */
	public ImageIndexer clone()
	{
		ImageIndexer indexer = new ImageIndexer(this.imageToIndex);
		
		for (int i = 0; i < this.index.length; i++)
		{
			for (int j = 0; j < this.index[i].length; j++)
			{
				indexer.setValue(i, j, this.index[i][j]);
			}
		}
		
		return indexer;
	}
}
