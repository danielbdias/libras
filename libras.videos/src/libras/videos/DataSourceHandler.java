/**
 * Provides video processing functionalities.
 */
package libras.videos;

import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;

import libras.images.*;

/**
* A DataSourceHandler class to read from a DataSource and displays
* information of each frame of data received.
* @author Daniel Baptista Dias
*/
public class DataSourceHandler implements BufferTransferHandler
{
	/**
	 * Data source where the frames will be get.
	 */
	private DataSource source = null;
	/**
	 * Format used in data source.
	 */
	private VideoFormat format = null;
	
	private FrameInterceptor interceptor = null;
	
	/**
	 * Creates a new instance of this class passing the DataSource to handle.
	 */
	public DataSourceHandler(DataSource source, FrameInterceptor interceptor) throws IncompatibleSourceException
	{
		this.setSource(source);
		this.interceptor = interceptor;
	}

	/**
	* Sets the media source this MediaHandler should use to obtain content.
	*/
	private void setSource(DataSource source) throws IncompatibleSourceException
	{
		// Different types of DataSources need to handled differently.
		if(source instanceof PushBufferDataSource) 
		{
			PushBufferStream pushStrms[] = ((PushBufferDataSource) source).getStreams();
			
			// Set the transfer handler to receive pushed data from the push DataSource.
			pushStrms[0].setTransferHandler(this);

			// Set format
			this.format = (VideoFormat)pushStrms[0].getFormat();
		}
		else if(source instanceof PullBufferDataSource)
		{		
			// This handler only handles push buffer datasource.
			throw new IncompatibleSourceException();
		}
		
		this.source = source;
	}
	
	/**
	* This will get called when there's data pushed from the PushBufferDataSource.
	*/
	public void transferData(PushBufferStream stream)
	{
		Buffer readBuffer = new Buffer();
		
		try
		{
			stream.read(readBuffer);
		}
		catch(Exception e)
		{
			return;
		}

		// Check for end of stream
		if(readBuffer.isEOM()) return;

		// Do useful stuff or wait
		useFrameData(readBuffer);
	}

	/**
	 * Start to handle output from data source.
	 */
	public void start()
	{
		try
		{
			source.start();
		}
		catch(Exception e) { }
	}
	
	/**
	 * Stop to handle output from data source.
	 */
	public void stop()
	{
		try
		{
			source.stop();
		}
		catch(Exception e) { }
	}	
	
	/**
	 * Stop to handle output from data source. 
	 */
	public void close()
	{
		this.stop();
	}
	
	/**
	 * Get any control associated with this handler.
	 * @return Controls associated with this handler.
	 */
	public Object[] getControls()
	{
		return new Object[0];
	}
	
	/**
	 * Get a control associated with this handler.
	 * @param name Name of control.
	 * @return Control associated with this handler.
	 */
	public Object getControl(String name)
	{
		return null;
	}
	
	/**
	 * Get format used by the data source.
	 * @return Format used by the data source.
	 */
	public VideoFormat getFormat()
	{
		return format;
	}

	/**
	* Called on each new frame buffer
	*/
	private void useFrameData(Buffer inBuffer)
	{
		try
		{
			if (inBuffer.getData() != null)
			{
				byte[] inData = (byte[])inBuffer.getData();
				
				//Converts the buffer to an format used by the program
				Image image = this.convertBufferToImage(inData);
				
				this.interceptor.interceptFrame(image);
			}
		}
		catch(Exception e) { }	
	}

	/**
	* Converts buffer data to pixel data for display in the image panel
	*/
	protected Image convertBufferToImage(byte[] inData)
	{
		int imageWidth = this.format.getSize().width;
		int imageHeight = this.format.getSize().height; 
		
		Image image = new Image(imageWidth, imageHeight);
		
		int srcPtr = 0;
		
		for (int y = 0; y < imageHeight; y++)
		{
			for (int x = 0; x < imageWidth; x++)
			{
				int red = (inData[srcPtr + 2] & 0xff);
				int green = (inData[srcPtr + 1] & 0xff);
				int blue = (inData[srcPtr] & 0xff);
				
				Pixel pixel = PixelFactory.getPixel(red, green, blue);
				image.setPixel(x, y, pixel);
				
				srcPtr += 3;
			}
		}
		
		Thread.yield();
		
		return image;
	}
}