package libras.preprocessing;

import java.io.File;

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
		libras.utils.ValidationHelper.validateIfFileParameterIsDirectory(directoryToSave);
		
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
		
		processor.processVideo(this.videoFile);
	}
	
	private void internalInterceptFrame(Image frame)
	{
		File imageToSave = new File(
			String.format(
				"%s\\frame%04d.jpg", 
				this.directoryToSave.getAbsolutePath(),
				this.framesIntercepted));
		
		libras.images.utils.ImageHelper.buildImage(frame, imageToSave);
	}
}
