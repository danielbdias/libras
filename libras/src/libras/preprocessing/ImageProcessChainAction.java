package libras.preprocessing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

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
	
	public ImageProcessChainAction(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser, boolean saveSegmentedImage)
	{
		super();
		
		initializeAttributes(frameDirectories, centroidFile, analyser, saveSegmentedImage);
	}
	
	public ImageProcessChainAction(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser, boolean saveSegmentedImage, File[] segmentedImageDirecories)
	{
		super();
		
		initializeAttributes(frameDirectories, centroidFile, analyser, saveSegmentedImage, segmentedImageDirecories);
	}

	public ImageProcessChainAction(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser, ChainAction nextAction)
	{
		super(nextAction);
		
		initializeAttributes(frameDirectories, centroidFile, analyser);
	}
	
	private File[] frameDirectories = null;
	
	private boolean saveSegmentedImage = false;
	
	private File centroidFile = null;
	
	private File[] segmentedFrameDirectories = null;
	
	private ColorSegmentationImageAnalyser analyser = null;
	
	private void initializeAttributes(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser)
	{
		this.initializeAttributes(frameDirectories, centroidFile, analyser, false);
	}
	
	private void initializeAttributes(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser, boolean saveSegmentedImage)
	{
		this.initializeAttributes(frameDirectories, centroidFile, analyser, saveSegmentedImage, null);
	}
	
	private void initializeAttributes(File[] frameDirectories, File centroidFile, ColorSegmentationImageAnalyser analyser, boolean saveSegmentedImage, File[] segmentedFrameDirectories)
	{
		libras.utils.ValidationHelper.validateIfParameterIsNull(frameDirectories, "frameDirectories");
		libras.utils.ValidationHelper.validateIfParameterIsNull(centroidFile, "centroidFile");
		libras.utils.ValidationHelper.validateIfParameterIsNull(analyser, "analyser");
		
		for (File frameDirectory : frameDirectories)
		{
			libras.utils.ValidationHelper.validateIfFileParameterIsDirectory(frameDirectory);
			libras.utils.ValidationHelper.validateIfFileParameterExists(frameDirectory, frameDirectory.getName());	
		}
		
		if (centroidFile.exists())
			libras.utils.ValidationHelper.validateIfFileParameterIsFile(centroidFile);
		
		this.frameDirectories = frameDirectories;
		this.centroidFile = centroidFile;
		this.analyser = analyser;
		this.saveSegmentedImage = saveSegmentedImage;
		this.segmentedFrameDirectories = segmentedFrameDirectories;
	}
	
	@Override
	protected void doExecuteAction()
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.centroidFile));
			
			for (int i = 0; i < this.frameDirectories.length; i++)
			{
				File frameDirectory = this.frameDirectories[i];
				File segmentedFrameDirectory = (this.segmentedFrameDirectories != null ? this.segmentedFrameDirectories[i] : null);
				
				this.log("Processing frame directory [%s]...", frameDirectory);
				Date processStart = new Date(System.currentTimeMillis());
				
				File[] images = frameDirectory.listFiles(new FileFilter(){
					@Override
					public boolean accept(File arg) { return arg.getName().endsWith("jpg"); }
				});
				
				ArrayList<libras.images.Point> centroidList = new ArrayList<libras.images.Point>(); 
				
				for (File image : images)
				{
					libras.images.Point centroid = getCentroid(image, segmentedFrameDirectory);
					centroidList.add(centroid);
				}
				
				String centroidFileLine = this.codifyLine(centroidList, frameDirectory.getName());
				writer.write(centroidFileLine);
				
				this.log("[%d] frames processed.", images.length);
				
				Date processEnd = new Date(System.currentTimeMillis());
				long processDuration = processEnd.getTime() - processStart.getTime();
				double processDurationInSeconds = processDuration / 1000.0; //convert in seconds
				this.log("Frame directory [%s] processed in [%f] seconds.", frameDirectory, processDurationInSeconds);
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
		if (frameDirectoryName.startsWith("arcos_antihorario")) return 1; 
		else if (frameDirectoryName.startsWith("arcos_horario")) return 2;
		else if (frameDirectoryName.startsWith("balancar_curva")) return 3;
		else if (frameDirectoryName.startsWith("balancar_horizontal")) return 4;
		else if (frameDirectoryName.startsWith("balancar_vertical")) return 5;
		else if (frameDirectoryName.startsWith("circulos")) return 6;
		else if (frameDirectoryName.startsWith("curvas_inferior")) return 7;
		else if (frameDirectoryName.startsWith("curvas_superior")) return 8;
		else if (frameDirectoryName.startsWith("ondulatorio_horizontal")) return 9;
		else if (frameDirectoryName.startsWith("ondulatorio_vertical")) return 10;
		else if (frameDirectoryName.startsWith("reta_horizontal")) return 11;
		else if (frameDirectoryName.startsWith("reta_vertical")) return 12;
		else if (frameDirectoryName.startsWith("tremular")) return 13;
		else if (frameDirectoryName.startsWith("ziguezague_horizontal")) return 14;
		else if (frameDirectoryName.startsWith("ziguezague_vertical")) return 15;
		
		return -1;
	}
	
	private String codifyLine(ArrayList<Point> centroidList, String frameDirectoryName)
	{
		StringBuilder line = new StringBuilder();
		
		for (Point centroid : centroidList)
		{
			if (centroid != null) {
				line.append(centroid.getX());
				line.append(',');
				line.append(centroid.getY());
				line.append(',');
			}
			else {
				line.append("?,?,");
			}
		}
		
		line.append(this.getFrameClass(frameDirectoryName));
		line.append("\r\n");
		
		return line.toString();
	}

	private Point getCentroid(File imageFile, File segmentedFrameDirectory)
	{
		Image image = null;
		
		try {
			image = libras.images.utils.ImageHelper.getImage(imageFile);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		ImageIndexer indexer = analyser.analyse(image);
		
		if (this.saveSegmentedImage)
		{
			File segmentedDirectory = null;
			
			if (segmentedFrameDirectory != null)
				segmentedDirectory = segmentedFrameDirectory;
			else
				segmentedDirectory = new File(imageFile.getParent() + "_segmented");
			
			if (!segmentedDirectory.exists()) 
				segmentedDirectory.mkdirs();
			
			File segmentedImage = new File(segmentedDirectory + "\\" + imageFile.getName());
			
			libras.images.utils.ImageHelper.buildImage(indexer, segmentedImage);
		}
		
		return analyser.findCentroid(indexer);
	}
}
