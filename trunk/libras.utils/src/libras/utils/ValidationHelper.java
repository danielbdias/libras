/**
 * Provides utility classes to multiple purposes.
 */
package libras.utils;

import java.io.File;
import java.security.InvalidParameterException;

/**
 * Provides code validation methods.
 * @author Daniel Baptista Dias
 */
public final class ValidationHelper
{
	/**
	 * This method cannot be called, because this class has only static methods.
	 */
	private ValidationHelper()
	{ }

	/**
	 * Verifies if a parameter is null, if true, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param parameter Parameter to be validated.
	 * @param parameterName Parameter name.
	 */
	public static void validateIfParameterIsNull(Object parameter, String parameterName)
	{
		if (parameter == null)
			throw new InvalidParameterException(
				String.format("The parameter \"%s\" cannot be null.", parameterName));
	}
	
	/**
	 * Verifies if a parameter is null or empty, if true, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param parameter Parameter to be validated.
	 * @param parameterName Parameter name.
	 */
	public static void validateIfParameterIsNullOrEmpty(String parameter, String parameterName)
	{
		if (parameter == null || parameter.isEmpty())
			throw new InvalidParameterException(
				String.format("The parameter \"%s\" cannot be null or empty.", parameterName));
	}
	
	/**
	 * Verifies if a parameter is null, if true, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 */
	public static void validateIfArraysHaveSameLenght(
			long firstArrayLenght, String firstArrayName, long secondArrayLenght, String secondArrayName)
	{
		if (firstArrayLenght != secondArrayLenght)
			throw new InvalidParameterException(
				String.format("The \"%s\" and the \"%s\" must have the same length.", firstArrayName, secondArrayName));

	}
	
	/**
	 * Verifies if a file exists, if false, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param file Parameter to be validated.
	 */
	public static void validateIfFileParameterExists(File file, String fileName)
	{
		if (!file.exists())
			throw new InvalidParameterException(String.format("The file [%s] not exists.", fileName));
	}
	
	/**
	 * Verifies if a file parameter is a directory, if false, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param file Parameter to be validated.
	 */
	public static void validateIfFileParameterIsDirectory(File directory)
	{
		if (!directory.isDirectory())
			throw new InvalidParameterException(String.format("The file [%s] is not a directory.", directory.getName()));
	}

	/**
	 * Verifies if a file parameter is a file, if false, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param file Parameter to be validated.
	 */
	public static void validateIfFileParameterIsFile(File file)
	{
		if (!file.isFile())
			throw new InvalidParameterException(String.format("The parameter [%s] is not a File.", file.getName()));
	}
	
	/** 
	 * Verifies if a parameter is equals or greater than 0 (zero), if false, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param parameter Parameter to be validated.
	 * @param parameterName Parameter name.
	 */
	public static void validateIfParameterIsEqualOrGreaterThanZero(long parameter, String parameterName)
	{
		if (parameter < 0)
			throw new InvalidParameterException(
					String.format("The parameter \"%s\" cannot be less than zero.", parameterName));
	}
	
	/** 
	 * Verifies if a parameter is greater than 0 (zero), if false, throws an {@link InvalidParameterException}, 
	 * otherwise do nothing.
	 * @param parameter Parameter to be validated.
	 * @param parameterName Parameter name.
	 */
	public static void validateIfParameterIsGreaterThanZero(long parameter, String parameterName)
	{
		if (parameter <= 0)
			throw new InvalidParameterException(
					String.format("The parameter \"%s\" cannot be less or equal to zero.", parameterName));
	}
}
