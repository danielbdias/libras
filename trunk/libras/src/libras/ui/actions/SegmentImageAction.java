/**
 * 
 */
package libras.ui.actions;

import java.io.*;
import java.util.Hashtable;

import libras.images.Image;
import libras.images.ImageIndexer;
import libras.images.Pixel;
import libras.images.analysers.ColorSegmentationImageAnalyser;
import libras.ui.actions.annotations.ActionDescription;

/**
 * @author Daniel Baptista Dias
 *
 */
@ActionDescription(
		command="segmentimage", 
		commandExample="segmentimage -image=[image_to_be_segmented]",
		helpDescription="Process an image identifying a object.",
		requiredArgs= { "image" },
		needUserInput=true)
public class SegmentImageAction extends Action
{
	public SegmentImageAction(Hashtable<String, String> arguments)
	{
		this.imagePath = arguments.get("image");
	}
	
	private String imagePath = null;
	
	/**
	 * @see libras.ui.actions.Action#execute()
	 */
	@Override
	public void execute() throws Exception
	{
		final int threshold = 150;
		
		File file = new File(this.imagePath); 
		
		if (file.exists() && file.isFile())
		{
			Image image = libras.images.utils.ImageHelper.getImage(file);
			
			libras.images.analysers.ColorSegmentationImageAnalyser analyser =
				new ColorSegmentationImageAnalyser(Pixel.RED, threshold);
			
			ImageIndexer indexer = analyser.analyse(image);
			
			int pointIndex = this.imagePath.lastIndexOf('.');
			String firstSegment = this.imagePath.substring(0, pointIndex);
			String secondSegment = this.imagePath.substring(pointIndex);
			
			File newImage = new File(firstSegment + "_segmented" + secondSegment);
			
			libras.images.utils.ImageHelper.buildImage(indexer, newImage);
		}
	}
}
