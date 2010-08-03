package libras.ui.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import libras.preprocessing.VideoProcessChainAction;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Test action used for debugging purposes.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="test",
	commandExample="-test",
	requiredArgs={},
	helpDescription="Test a functionality")
public class TestAction extends Action
{
	@Override
	public void execute() throws Exception
	{	
		//TODO: recriar videos de ziguezague
//		String[] videoDirs = { 
//				"H:\\Daniel\\Videos\\Base de dados - Movimentos Libras\\Base TCC\\Alex",
//				};
//		String frameDirectory = "D:\\Daniel Documents\\Facul\\TCC\\Base de dados\\Base nova";
//		
//		innerExecute(videoDirs, frameDirectory);
		
		String[] oldVideoDirs = { 
				"H:\\Daniel\\Videos\\Base de dados - Movimentos Libras\\Base IC\\Daniel",
				"H:\\Daniel\\Videos\\Base de dados - Movimentos Libras\\Base IC\\Roberta",
				"H:\\Daniel\\Videos\\Base de dados - Movimentos Libras\\Base IC\\Sara",
				"H:\\Daniel\\Videos\\Base de dados - Movimentos Libras\\Base IC\\Videos - Sara",
				"H:\\Daniel\\Videos\\Base de dados - Movimentos Libras\\Base IC\\Videos - todos",
				};
		String[] oldFrameDirectory = {
				"D:\\Daniel Documents\\Facul\\TCC\\Base de dados\\Base antiga\\Daniel",
				"D:\\Daniel Documents\\Facul\\TCC\\Base de dados\\Base antiga\\Roberta",
				"D:\\Daniel Documents\\Facul\\TCC\\Base de dados\\Base antiga\\Sara",
				"D:\\Daniel Documents\\Facul\\TCC\\Base de dados\\Base antiga\\Videos - Sara",
				"D:\\Daniel Documents\\Facul\\TCC\\Base de dados\\Base antiga\\Videos - todos",
		};
		
		innerExecute(oldVideoDirs, oldFrameDirectory);
	}

	private void innerExecute(final String[] videoDirs,
			final String[] frameDirs) {
		for (int k = 0; k < videoDirs.length; k++) {
			String videoDir = videoDirs[k];
			String frameDirectory = frameDirs[k];
			
			ArrayList<File> videosToProcess = this.getVideos(videoDir, ".avi");
			File[] frameDirectories = new File[videosToProcess.size()];
			for (int i = 0; i < frameDirectories.length; i++) {
				File videoToProcess = videosToProcess.get(i);

				String videoName = videoToProcess.getName();
				frameDirectories[i] = new File(frameDirectory + "\\"
						+ videoName.substring(0, videoName.lastIndexOf('.')));

				if (!frameDirectories[i].exists())
					frameDirectories[i].mkdir();
			}
			LinkedList<VideoProcessChainAction> actions = new LinkedList<VideoProcessChainAction>();
			for (int i = 0; i < videosToProcess.size(); i++) {
				File videoToProcess = videosToProcess.get(i);
				File frameDir = frameDirectories[i];

				actions.add(new VideoProcessChainAction(videoToProcess,
						frameDir));
			}
			for (int i = 1; i < actions.size(); i++)
				actions.get(i - 1).setNextAction(actions.get(i));
			actions.getFirst().executeAction();
		}
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
