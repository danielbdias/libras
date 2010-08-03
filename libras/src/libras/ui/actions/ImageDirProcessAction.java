/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import libras.images.Pixel;
import libras.images.analysers.ColorSegmentationImageAnalyser;
import libras.preprocessing.*;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Action which process all videos in a directory.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="imagedir",
	commandExample="-imagedir -dir=[image_dir_path] -centroidFile=[centroid_file_path] -saveSegmentedFrames",
	helpDescription="Process a directory which contains videos with a libras gesture.",
	requiredArgs= { "dir", "centroidFile" },
	needUserInput=true)
public class ImageDirProcessAction extends Action
{
	/**
	 * Creates a new instance of this action passing the directory
	 * where the video files are located
	 * @param videoDir Directory with videos to be processed.
	 */
	public ImageDirProcessAction(Hashtable<String, String> arguments)
	{
		this.imageDir = arguments.get("dir");
		this.centroidFile = arguments.get("centroidFile");
		
		if (arguments.containsKey("saveSegmentedFrames"))
			this.saveSegmentedFrames = true;
	}
	
	private String imageDir = null;
	
	private String centroidFile = null;
	
	private boolean saveSegmentedFrames = false;
	
	/**
	 * Show help to the user.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute()
	{	
		File centroidFile = new File(this.centroidFile);
		
		File[] frameDirectories = this.getImagesDirectories(this.imageDir);
		
		ColorSegmentationImageAnalyser analyser = new ColorSegmentationImageAnalyser(Pixel.RED, 175);
		
		ImageProcessChainAction imageProcess = 
			new ImageProcessChainAction(
				frameDirectories, 
				centroidFile, 
				analyser, 
				this.saveSegmentedFrames);
		
		imageProcess.executeAction();
	}

	private File[] getImagesDirectories(String imageDirPath) {
		File imageDir = new File(imageDirPath);
		
		ArrayList<File> directories = new ArrayList<File>();
		
		File[] dirs = imageDir.listFiles();
	
		for (File dir : dirs) {
			if (dir.isDirectory())
				directories.add(dir);
		}
		
		return directories.toArray(new File[0]);
	}
}
