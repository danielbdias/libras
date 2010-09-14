/**
 * Provides an user interface helpers to the application.
 */
package libras.videos.ui;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
* Extends JPanel to show images.
* @author Daniel Baptista Dias
*/
public class ImagePanel extends JPanel
{
	/**
	 * Dimension of this panel
	 */
	private Dimension size = null;
	/**
	 * Current image used in this panel 
	 */
	private Image image = null;

	/**
	 * Creates a new instance of this class with a specified dimension.
	 * @param size Size of this panel.
	 */
	public ImagePanel(Dimension size)
	{
		super();
		this.size = size;
	}

	/**
	 * Preferred size of this panel.
	 */
	public Dimension getPreferredSize()
	{
		return size;
	}

	/**
	 * Update image in this frame.
	 */
	public void update(Graphics g)
	{
		paint(g);
	}

	/**
	 * Paint the current image using the Graphics visitor.
	 */
	public void paint(Graphics g)
	{
		if (this.image != null)
			g.drawImage(this.image, 0, 0, this);
	}

	/**
	* Draw image to AWT frame
	*/
	public void setImage(int[] outpix)
	{
		MemoryImageSource sourceImage = 
			new MemoryImageSource(
					this.size.width, 
					this.size.height, 
					libras.images.utils.ImageHelper.getColorModel(), 
					outpix, 
					0, 
					this.size.width);
		
		Image outputImage = this.createImage(sourceImage);
		
		this.setImage(outputImage);	
	}
	
	/**
	 * Sets a new image for this panel.
	 * @param image New image to be setted.
	 */
	public void setImage(Image image)
	{
		if (image != null)
		{
			this.image = image;
			this.update(this.getGraphics());
		}
	}
	
	/**
	 * Get an image associated with this control.
	 * @return Image associated with this control.
	 */
	public Image getImage()
	{
		return this.image;
	}
}

