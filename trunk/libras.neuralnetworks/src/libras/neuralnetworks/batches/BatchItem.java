/**
 * Package that provide classes to automatize batch process.
 */
package libras.neuralnetworks.batches;

import java.util.*;

/**
 * Represents an item in the batch file.
 * @author Daniel Baptista Dias
 */
public class BatchItem
{
	/**
	 * Creates a new instance. 
	 */
	BatchItem()
	{
		
	}
	
	private String batchName = null;
	private Integer epochsToExecute = null;
	private Double initialLearningRate = null;
	private Double learningRateDecreasingRate = null;
	private Integer inputNeuronsCount = null;
	private Integer outputNeuronsCount = null;
	private libras.neuralnetworks.utils.LabelCollection labels = null;
	private Integer crossValidationFoldNumber = null;
	private List<java.io.File> validationFiles = null;
	private List<java.io.File> inputFiles = null;
	private java.io.File reportDataFile = null;
	private java.io.File networkDataFile = null;
	private java.io.File networkSetupFile = null;
	private String networkSetupFileFormat = null;
	
	/**
	 * Get the item name.
	 * @return Item name.
	 */
	public String getBatchName()
	{
		return this.batchName;
	}
	
	/**
	 * Get the number of epochs of the training.
	 * @return Number of epochs of the training.
	 */
	public Integer getEpochsToExecute()
	{
		return epochsToExecute;
	}
	
	/**
	 * Get the decreasing rate of the learning rate in the training.
	 * @return Decreasing rate of the learning rate in the training.
	 */
	public Double getLearningRateDecreasingRate()
	{
		return learningRateDecreasingRate;
	}
	
	/**
	 * Get the initial learning rate of the training.
	 * @return The initial learning rate of the training.
	 */
	public Double getInitialLearningRate()
	{
		return initialLearningRate;
	}
	
	/**
	 * Get the number of the input neurons of the network to be trained.
	 * @return The number of the input neurons of the network to be trained.
	 */
	public Integer getInputNeuronsCount()
	{
		return inputNeuronsCount;
	}
	
	/**
	 * Get the number of the output neurons of the network to be trained.
	 * @return The number of the output neurons of the network to be trained.
	 */
	public Integer getOutputNeuronsCount()
	{
		return outputNeuronsCount;
	}
	
	/**
	 * Get the labels used by the network.
	 * @return Labels used by the network.
	 */
	public libras.neuralnetworks.utils.LabelCollection getLabels()
	{
		return labels;
	}
	
	/**
	 * Get the cross validation fold number.
	 * @return Cross validation fold number.
	 */
	public Integer getCrossValidationFoldNumber()
	{
		return this.crossValidationFoldNumber;
	}
	
	/**
	 * Get the files with data to do the validation of the training.
	 * @return Files with data to do the validation of the training.
	 */
	public List<java.io.File> getValidationFiles()
	{
		return validationFiles;
	}
	
	/**
	 * Get the input files with data to do the training.
	 * @return Input files with data to do the training.
	 */
	public List<java.io.File> getInputFiles()
	{
		return inputFiles;
	}
	
	/**
	 * Get the location of the report file generated after the execution of the item.
	 * @return Location of the report file generated after the execution of the item.
	 */
	public java.io.File getReportDataFile()
	{
		return reportDataFile;
	}
	
	/**
	 * Get the location of the network data file generated after the execution of the item.
	 * @return Location of the network data file generated after the execution of the item.
	 */
	public java.io.File getNetworkDataFile()
	{
		return networkDataFile;
	}
	
	/**
	 * Get the file used to setup the network before the training.
	 * @return File used to setup the network before the training.
	 */
	public java.io.File getNetworkSetupFile()
	{
		return networkSetupFile;
	}
	
	/**
	 * Get the format of the file used to setup the network before the training.
	 * This item value must be "text" or "xml".
	 * @return Format of the file used to setup the network before the training.
	 */
	public String getNetworkSetupFileFormat()
	{
		return networkSetupFileFormat;
	}
	
	/**
	 * Set the item name.
	 * @param batchName Item name.
	 */
	void setBatchName(String batchName)
	{
		this.batchName = batchName;
	}
	
	/**
	 * Set the number of epochs of the training.
	 * @param epochsToExecute Number of epochs of the training.
	 */
	void setEpochsToExecute(Integer epochsToExecute)
	{
		this.epochsToExecute = epochsToExecute;
	}
	
	/**
	 * Set the initial learning rate of the training.
	 * @param initialLearningRate The initial learning rate of the training.
	 */
	void setInitialLearningRate(Double initialLearningRate)
	{
		this.initialLearningRate = initialLearningRate;
	}
	
	/**
	 * Set the decreasing rate of the learning rate in the training.
	 * @param learningRateDecreasingRate Decreasing rate of the learning rate in the training.
	 */
	void setLearningRateDecreasingRate(Double learningRateDecreasingRate)
	{
		this.learningRateDecreasingRate = learningRateDecreasingRate;
	}
	
	/** 
	 * Set the number of the input neurons of the network to be trained.
	 * @param inputNeuronsCount The number of the input neurons of the network to be trained.
	 */
	void setInputNeuronsCount(Integer inputNeuronsCount)
	{
		this.inputNeuronsCount = inputNeuronsCount;
	}
	
	/**
	 * Set the number of the output neurons of the network to be trained.
	 * @param outputNeuronsCount The number of the output neurons of the network to be trained.
	 */
	void setOutputNeuronsCount(Integer outputNeuronsCount)
	{
		this.outputNeuronsCount = outputNeuronsCount;
	}
	
	/**
	 * Set the labels used by the network.
	 * @param labels Labels used by the network.
	 */
	void setLabels(libras.neuralnetworks.utils.LabelCollection labels)
	{
		this.labels = labels;
	}
	
	/**
	 * Set the cross validation fold number.
	 * @param crossValidationFoldNumber Cross validation fold number.
	 */
	void setCrossValidationFoldNumber(Integer crossValidationFoldNumber)
	{
		this.crossValidationFoldNumber = crossValidationFoldNumber;
	}
	
	/**
	 * Set the files with data to do the validation of the training.
	 * @param validationFiles Files with data to do the validation of the training.
	 */
	void setValidationFiles(List<java.io.File> validationFiles)
	{
		this.validationFiles = validationFiles;
	}
	
	/**
	 * Set the input files with data to do the training.
	 * @param inputFiles Input files with data to do the training.
	 */
	void setInputFiles(List<java.io.File> inputFiles)
	{
		this.inputFiles = inputFiles;
	}
	
	/**
	 * Set the location of the report file generated after the execution of the item.
	 * @param reportDataFile Location of the report file generated after the execution of the item.
	 */
	void setReportDataFile(java.io.File reportDataFile)
	{
		this.reportDataFile = reportDataFile;
	}
	
	/**
	 * Get the location of the network data file generated after the execution of the item.
	 * @param networkDataFile Location of the network data file generated after the execution of the item.
	 */
	void setNetworkDataFile(java.io.File networkDataFile)
	{
		this.networkDataFile = networkDataFile;
	}
	
	/**
	 * Set the file used to setup the network before the training.
	 * @param networkSetupFile File used to setup the network before the training.
	 */
	void setNetworkSetupFile(java.io.File networkSetupFile)
	{
		this.networkSetupFile = networkSetupFile;
	}
	
	/**
	 * Set the format of the file used to setup the network before the training.
	 * This item value must be "text" or "xml".
	 * @param networkSetupFileFormat Format of the file used to setup the network before the training.
	 */
	void setNetworkSetupFileFormat(String networkSetupFileFormat)
	{
		this.networkSetupFileFormat = networkSetupFileFormat;
	}
}
