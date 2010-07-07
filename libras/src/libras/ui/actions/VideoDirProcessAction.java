/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import libras.images.Pixel;
import libras.images.analysers.ColorSegmentationImageAnalyser;
import libras.preprocessing.*;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Action which process all videos in a directory.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="videodir",
	commandExample="-videodir -dir=[video_dir_path]",
	helpDescription="Process a directory with videos with a libras gesture.",
	requiredArgs= { "dir" },
	needUserInput=true)
public class VideoDirProcessAction extends Action
{
	/**
	 * Creates a new instance of this action passing the directory
	 * where the video files are located
	 * @param videoDir Directory with videos to be processed.
	 */
	public VideoDirProcessAction(Hashtable<String, String> arguments)
	{
		this.videoDir = arguments.get("dir");
	}
	
	private String videoDir = null;
	
	/**
	 * Show help to the user.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute()
	{	
		File centroidFile = new File(this.videoDir + "\\centroids.txt");
		
		ArrayList<File> videosToProcess = this.getVideos(this.videoDir, ".avi");
		File[] directoriesToSave = new File[videosToProcess.size()];
		
		ColorSegmentationImageAnalyser analyser = new ColorSegmentationImageAnalyser(Pixel.RED, 175);
		
		ImageProcessChainAction imageProcess = new ImageProcessChainAction(directoriesToSave, centroidFile, analyser);
		
		LinkedList<VideoProcessChainAction> actions = new LinkedList<VideoProcessChainAction>();
		
		for (int i = 0; i < videosToProcess.size(); i++)
		{
			File videoToProcess = videosToProcess.get(i);
			File directoryToSave = new File(videoToProcess.getPath() + "\\" + videoToProcess.getName().substring(0, videoToProcess.getName().lastIndexOf('.')));
			
			actions.add(new VideoProcessChainAction(videoToProcess, directoryToSave));
		}
		
		for (int i = 1; i < actions.size(); i++)
			actions.get(i-1).setNextAction(actions.get(i));
		
		actions.getLast().setNextAction(imageProcess);
		
		actions.getFirst().executeAction();
	}
	
	private ArrayList<File> getVideos(String videoDir, String videoExtension)
	{
		ArrayList<File> videos = new ArrayList<File>();
		
		this.getVideos(videoDir, videoExtension, videos);
		
		return videos;
	}

	private void getVideos(String videoDir, String videoExtension, ArrayList<File> videos)
	{
		File directory = new File(videoDir);
		
		if (directory.exists())
		{
			String[] dirItems = directory.list();
			
			for (String item : dirItems)
			{
				File fileSystemEntry = new File(videoDir + "\\" + item);
				
				if (fileSystemEntry.isFile() && item.endsWith(videoExtension))					
					videos.add(fileSystemEntry);
				else if (fileSystemEntry.isDirectory())
					this.getVideos(videoDir + "\\" + item, videoExtension, videos);
			}
		}
	}
}
