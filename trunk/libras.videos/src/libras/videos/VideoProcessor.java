/**
 * Provides video processing functionalities.
 */
package libras.videos;

import java.io.*;
import javax.media.*;
import javax.media.control.*;
import javax.media.format.*;
import javax.media.protocol.*;

import libras.videos.DataSourceHandler;

/**
 * Process a video and get all centroids from frame data.  
 * @author Daniel Baptista Dias
 */
public final class VideoProcessor implements ControllerListener
{
	/**
	 * Video processor.
	 */
	private Processor processor = null;
	/**
	 * Syncronization object for multithread processing.
	 */
	Object waitObj = new Object();
	/**
	 * Indicate if state of the processor is ok. 
	 */
	boolean stateOK = true;
	
	private DataSourceHandler handler;

	private FrameInterceptor interceptor;
	
	/**
	 * Flag used to signalized the end of the video process.
	 */
	private boolean finished = false;
	
	/**
	* Initiates a new instance of this class.
	*/
	public VideoProcessor(FrameInterceptor interceptor)
	{
		super();
		
		this.interceptor = interceptor;
    }
	
	/**
	 * Shows a video in this frame.
	 * @param videoFile Video file to show.
	 * @return true, if the video will be displayed, otherwise false.
	 */
	public boolean processVideo(File videoFile)
	{	
		String videoFilePath = null;
		if (videoFile == null || videoFile.isDirectory()) return false;
		
		videoFilePath = "file:" + videoFile.getAbsolutePath();
		
		MediaLocator mediaLocator = null;
	
		if ((mediaLocator = new MediaLocator(videoFilePath)) != null)
		{
			if (!this.open(mediaLocator))
				return false;
			
			try
			{
				while (!finished)
					Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	* Given a MediaLocator, create a processor and start the video.
	*/
	private boolean open(MediaLocator mediaLocator)
	{	
		try
		{
			this.processor = Manager.createProcessor(mediaLocator);
		}
		catch (Exception e)
		{
			return false;
		}
		
		this.processor.addControllerListener(this);
		
		// Put the Processor into configured state.
		this.processor.configure();
		if(!waitForState(Processor.Configured)) return false;
		
		// Get the raw output from the Processor.
		this.processor.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW));
		
		TrackControl tc[] = this.processor.getTrackControls();
		if(tc == null) return false;
		
		TrackControl videoTrack = null;
		for (int i = 0; i < tc.length; i++)
		{
			if(tc[i].getFormat() instanceof VideoFormat)
			{
				tc[i].setFormat(new RGBFormat(null, -1, Format.byteArray, -1.0F, 24, 3, 2, 1));
				videoTrack = tc[i];
			}
			else
			{
				tc[i].setEnabled(false);
			}
		}		
		if (videoTrack == null) return false;

		this.processor.realize();
		if(!waitForState(Processor.Realized)) return false;
		
		// Get the output DataSource from the processor and set it to the DataSourceHandler.
		DataSource dataSource = this.processor.getDataOutput();
		
		try
		{
			handler = new DataSourceHandler(dataSource, interceptor);
		}
		catch(IncompatibleSourceException e)
		{
			return false;
		}
		
		if (handler.getFormat() == null) return false;
		
		handler.start();
		
		// Prefetch the processor.
		this.processor.prefetch();
		if(!waitForState(Processor.Prefetched)) return false;

		// Start the processor
		this.processor.start();
		
		return true;
	}

	/**
	* Tidy on finish
	*/
	private void tidyClose()
	{
		this.handler.close();
		this.processor.close();
		this.finished = true;
	}
	
	/**
	* Block until the processor has transitioned to the given state
	*/
	private boolean waitForState(int state)
	{
		synchronized(waitObj)
		{
			try
			{
				while(this.processor.getState() < state && stateOK)
				waitObj.wait();
			}
			catch (Exception e) { }
		}
		
		return stateOK;
	}

	/**
	* Controller Listener.
	*/
	public void controllerUpdate(ControllerEvent evt)
	{
		if (evt instanceof ConfigureCompleteEvent 
			|| evt instanceof RealizeCompleteEvent 
			|| evt instanceof PrefetchCompleteEvent)
		{
			synchronized(waitObj)
			{
				stateOK = true;
				waitObj.notifyAll();
			}
		}
		else if(evt instanceof ResourceUnavailableEvent)
		{
			synchronized(waitObj)
			{
				stateOK = false;
				waitObj.notifyAll();
			}
		}
		else if(evt instanceof EndOfMediaEvent || evt instanceof StopAtTimeEvent)
		{
			tidyClose();
		}
	}
}
