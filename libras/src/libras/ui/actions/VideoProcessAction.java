/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.io.File;
import java.util.Hashtable;

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
	commandExample="-video -videoPath=[video_path] -frameDirectory=[frame_direcory] -centroidFile=[centroid_file] -saveSegmentedFrames",
	helpDescription="Process a video with a libras gesture.",
	requiredArgs= { "videoPath", "frameDirectory", "centroidFile" },
	needUserInput=true)
public class VideoProcessAction extends Action
{
	/**
	 * Creates a new instance of an video process action.
	 */
	public VideoProcessAction(Hashtable<String, String> arguments)
	{
		this.videoPath = this.identifyPathInput(arguments.get("videoPath"));
		this.frameDirectory = this.identifyPathInput(arguments.get("frameDirectory"));
		this.centroidFile = this.identifyPathInput(arguments.get("centroidFile"));
		
		if (arguments.containsKey("saveSegmentedFrames"))
			this.saveSegmentedFrames = true;
	}
	
	private String videoPath = null;
	
	private String frameDirectory = null;
	
	private String centroidFile = null;
	
	private boolean saveSegmentedFrames = false;
	
	/**
	 * Process the video assigned to this action.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute()
	{
		File videoFile = new File(this.videoPath);
		File directoryToSave = new File(this.frameDirectory);
		
		File centroidFile = new File(this.centroidFile);
		
		ColorSegmentationImageAnalyser analyser = new ColorSegmentationImageAnalyser(Pixel.RED, 175);
		
		VideoProcessChainAction videoProcess = new VideoProcessChainAction(videoFile, directoryToSave);
		ImageProcessChainAction imageProcess = new ImageProcessChainAction(new File[] { directoryToSave }, centroidFile, analyser, this.saveSegmentedFrames);

		videoProcess.setNextAction(imageProcess);
		
		videoProcess.executeAction();
	}
}
