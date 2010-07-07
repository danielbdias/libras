/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.io.File;

import libras.images.Pixel;
import libras.images.analysers.ColorSegmentationImageAnalyser;
import libras.preprocessing.*;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Process a video.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="video", 
	commandExample="video=[video_path]",
	helpDescription="Process a video with a libras gesture.",
	needUserInput=true)
public class VideoProcessAction extends Action
{
	/**
	 * Creates a new instance of an video process action.
	 */
	public VideoProcessAction(String videoPath)
	{
		this.videoPath = videoPath;
	}
	
	private String videoPath = null;
	
	/**
	 * Process the video assigned to this action.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute()
	{
		File videoFile = new File(this.videoPath);
		File directoryToSave = new File(videoFile.getPath() + "\\" + videoFile.getName().substring(0, videoFile.getName().lastIndexOf('.')));
		
		File centroidFile = new File(videoFile.getPath() + "\\" + videoFile.getName().substring(0, videoFile.getName().lastIndexOf('.')) + ".txt");
		
		ColorSegmentationImageAnalyser analyser = new ColorSegmentationImageAnalyser(Pixel.RED, 175);
		
		VideoProcessChainAction videoProcess = new VideoProcessChainAction(videoFile, directoryToSave);
		ImageProcessChainAction imageProcess = new ImageProcessChainAction(new File[] { directoryToSave }, centroidFile, analyser);

		videoProcess.setNextAction(imageProcess);
		
		System.out.println(String.format("Processing video: [%s] ...", videoFile.getAbsolutePath()));
		
		videoProcess.executeAction();
		
		System.out.println("Video processed.");
	}
}
