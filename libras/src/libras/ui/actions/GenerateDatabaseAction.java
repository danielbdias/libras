package libras.ui.actions;

import java.io.File;
import java.io.FileFilter;
import java.util.Hashtable;

import libras.preprocessing.NormalizeRepresentationBySizeChainAction;
import libras.preprocessing.NormalizeRepresentationBySpaceChainAction;
import libras.preprocessing.ProcessAngleRepresentationChainAction;
import libras.ui.actions.annotations.ActionDescription;

/**
 * Action which process all videos in a directory.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="generatedatabase",
	commandExample="-generatedatabase -inputDir=[centroid_dir_path] -outputDir=[database_path]",
	helpDescription="Process a directory which contains files with centroids of a libras gesture.",
	requiredArgs= { "inputDir", "outputDir" },
	needUserInput=true)
public class GenerateDatabaseAction extends Action
{
	/**
	 * Creates a new instance of this action passing the directory
	 * where the video files are located
	 * @param videoDir Directory with videos to be processed.
	 */
	public GenerateDatabaseAction(Hashtable<String, String> arguments)
	{
		this.inputDir = arguments.get("inputDir");
		this.outputDir = arguments.get("outputDir");
	}

	private String inputDir = null;
	
	private String outputDir = null;
	
	@Override
	public void execute() throws Exception {
		final int representationSize = 2;
		final int normalizationSize = 45;
		
		File inputDir = new File(this.inputDir);
		File outputDir = new File(this.outputDir);
		
		if (!outputDir.exists()) outputDir.mkdirs();
		
		File[] inputFiles = this.getRepresentationFiles(inputDir);
		
		for (int i = 0; i < inputFiles.length; i++) {
			File inputFile = inputFiles[i];
			File outputFile = new File(outputDir.getAbsolutePath() + "\\" + inputFile.getName());
			
			File bufferAngleFile = new File(this.outputDir + "\\buffAng_" + inputFile.getName());
			File bufferNormalizeFile = new File(this.outputDir + "\\buffNorm_" + inputFile.getName());
			
			ProcessAngleRepresentationChainAction angleRepresentationAction = 
				new ProcessAngleRepresentationChainAction(inputFile, bufferAngleFile, representationSize);
			
			NormalizeRepresentationBySpaceChainAction normalizationBySpaceAction =
				new NormalizeRepresentationBySpaceChainAction(bufferAngleFile, bufferNormalizeFile, representationSize);
			
			NormalizeRepresentationBySizeChainAction normalizationBySizeAction =
				new NormalizeRepresentationBySizeChainAction(bufferNormalizeFile, outputFile, representationSize, normalizationSize);
	
			angleRepresentationAction.setNextAction(normalizationBySpaceAction);
			normalizationBySpaceAction.setNextAction(normalizationBySizeAction);
			
			angleRepresentationAction.executeAction();
			
			bufferAngleFile.delete();
			bufferNormalizeFile.delete();
		}
	}

	private File[] getRepresentationFiles(File inputDir) {
		File[] result = inputDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt");
			}
		});
		
		return result;
	}

}
