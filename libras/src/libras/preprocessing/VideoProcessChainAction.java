package libras.preprocessing;

import java.io.File;
import java.util.Date;

import libras.images.Image;
import libras.videos.FrameInterceptor;

public class VideoProcessChainAction extends ChainAction
{
	public VideoProcessChainAction(File videoFile, File directoryToSave)
	{
		super();
		
		initializeAttributes(videoFile, directoryToSave);
	}

	public VideoProcessChainAction(File videoFile, File directoryToSave, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(videoFile, directoryToSave);
	}
	
	private File videoFile = null;
	
	private File directoryToSave = null;
	
	private int framesIntercepted = 0;
	
	/**
	 * Initialize all class attributes.
	 * @param videoFile
	 * @param directoryToSave
	 */
	private void initializeAttributes(File videoFile, File directoryToSave)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(videoFile, "videoFile");
		libras.utils.ValidationHelper.validateIfParameterIsNull(directoryToSave, "directoryToSave");
		libras.utils.ValidationHelper.validateIfFileParameterExists(videoFile, videoFile.getName());
		
		if (!directoryToSave.exists()) directoryToSave.mkdirs();
		
		this.videoFile = videoFile;
		this.directoryToSave = directoryToSave;
	}
	
	@Override
	protected void doExecuteAction()
	{
		libras.videos.VideoProcessor processor = 
			new libras.videos.VideoProcessor(new FrameInterceptor() {
				public void interceptFrame(Image frame) {
					internalInterceptFrame(frame);	
				}
			});
		
		this.log("Processing video [%s]...", this.videoFile);
		Date processStart = new Date(System.currentTimeMillis());
		
		processor.processVideo(this.videoFile);
		
		Date processEnd = new Date(System.currentTimeMillis());
		long processDuration = processEnd.getTime() - processStart.getTime();
		double processDurationInSeconds = processDuration / 1000.0; //convert in seconds
		this.log("Video [%s] processed in [%f] seconds.", this.videoFile, processDurationInSeconds);
	}
	
	private void internalInterceptFrame(Image frame)
	{
		File imageToSave = new File(
			String.format(
				"%s\\frame%04d.jpg", 
				this.directoryToSave.getAbsolutePath(),
				this.framesIntercepted));
		
		libras.images.utils.ImageHelper.buildImage(frame, imageToSave);
		
		this.framesIntercepted++;
	}
}
