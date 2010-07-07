package libras.preprocessing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.ArrayList;

import libras.images.Image;
import libras.images.ImageIndexer;
import libras.images.Point;
import libras.images.analysers.ColorSegmentationImageAnalyser;

public class ImageProcessChainAction extends ChainAction
{
	public ImageProcessChainAction(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser)
	{
		super();
		
		initializeAttributes(frameDirectories, centroidFile, analyser);
	}

	public ImageProcessChainAction(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(frameDirectories, centroidFile, analyser);
	}
	
	private File[] frameDirectories = null;
	
	private File centroidFile = null;
	
	private ColorSegmentationImageAnalyser analyser = null;
	
	private void initializeAttributes(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(frameDirectories, "frameDirectories");
		libras.utils.ValidationHelper.validateIfParameterIsNull(centroidFile, "centroidFile");
		libras.utils.ValidationHelper.validateIfParameterIsNull(analyser, "analyser");
		
		for (File frameDirectory : frameDirectories)
		{
			libras.utils.ValidationHelper.validateIfFileParameterIsDirectory(frameDirectory);
			libras.utils.ValidationHelper.validateIfFileParameterExists(frameDirectory, frameDirectory.getName());	
		}
		
		libras.utils.ValidationHelper.validateIfFileParameterIsFile(centroidFile);
		
		this.frameDirectories = frameDirectories;
		this.centroidFile = centroidFile;
		this.analyser = analyser;
	}
	
	@Override
	protected void doExecuteAction()
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.centroidFile));
			
			for (File frameDirectory : this.frameDirectories)
			{
				File[] images = frameDirectory.listFiles(new FileFilter(){
					@Override
					public boolean accept(File arg) { return arg.getName().endsWith("jpg"); }
				});
				
				ArrayList<libras.images.Point> centroidList = new ArrayList<libras.images.Point>(); 
				
				for (File image : images)
				{
					libras.images.Point centroid = getCentroid(image);
					centroidList.add(centroid);
				}
				
				String centroidFileLine = this.codifyLine(centroidList, frameDirectory.getName());
				writer.write(centroidFileLine);
			}
			
			if (writer != null) writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}
	}

	private int getFrameClass(String frameDirectoryName)
	{
		if (frameDirectoryName.endsWith("arcos_antihorario")) return 0; 
		else if (frameDirectoryName.endsWith("arcos_horario")) return 1;
		else if (frameDirectoryName.endsWith("balancar_curva")) return 2;
		else if (frameDirectoryName.endsWith("balancar_vertical")) return 3;
		else if (frameDirectoryName.endsWith("balancar_horizontal")) return 4;
		else if (frameDirectoryName.endsWith("circulos")) return 5;
		else if (frameDirectoryName.endsWith("curvas_superior")) return 6;
		else if (frameDirectoryName.endsWith("curvas_inferior")) return 7;
		else if (frameDirectoryName.endsWith("ondulatorio_horizontal")) return 8;
		else if (frameDirectoryName.endsWith("ondulatorio_vertical")) return 9;
		else if (frameDirectoryName.endsWith("reta_horizontal")) return 10;
		else if (frameDirectoryName.endsWith("reta_vertical")) return 11;
		else if (frameDirectoryName.endsWith("tremular")) return 12;
		else if (frameDirectoryName.endsWith("ziguezague_horizontal")) return 13;
		else if (frameDirectoryName.endsWith("ziguezague_vertical")) return 14;
		
		return -1;
	}
	
	private String codifyLine(ArrayList<Point> centroidList, String frameDirectoryName)
	{
		StringBuilder line = new StringBuilder();
		
		for (Point centroid : centroidList)
		{
			line.append(centroid.getX());
			line.append(',');
			line.append(centroid.getY());
			line.append(',');
		}
		
		line.append(this.getFrameClass(frameDirectoryName));
		line.append("\r\n");
		
		return line.toString();
	}

	private Point getCentroid(File imageFile)
	{
		Image image = null;
		
		try {
			image = libras.images.utils.ImageHelper.getImage(imageFile);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		ImageIndexer indexer = analyser.analyse(image);
		
		return analyser.findCentroid(indexer);
	}
}
