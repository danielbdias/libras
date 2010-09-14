/**
 *  Provides utility classes to multiple purposes.
 */
package libras.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Helps to deal with reflection classes in Java.
 * @author Daniel Baptista Dias
 */
public final class ReflectionHelper
{
	/**
	 * This method cannot be called, because this class has only static methods.
	 */
	private ReflectionHelper()
	{ }
	
	/**
	 * Gets the subclasses of a class located in a package.
	 * Method based in Java Tip 113 (http://www.javaworld.com/javaworld/javatips/jw-javatip113.html)
	 * @param clazz Class that the subclasses must inherit.
	 * @param pack Package where the subclasses will be searched.
	 * @return A list with the subclasses found.
	 */
	public static List<Class<?>> getSubclasses(Class<?> clazz, Package pack)
	{
		ValidationHelper.validateIfParameterIsNull(clazz, "clazz");
		ValidationHelper.validateIfParameterIsNull(pack, "pack");
		
		List<Class<?>> subClasses = new ArrayList<Class<?>>();
		
		// Translate the package name into an absolute path
		String name = new String(pack.getName());
		 
		if (!name.startsWith("/")) name = "/" + name;
		name = name.replace('.','/');
		
		//Get a File object for the package
		URL url = clazz.getResource(name);
		
		File directory = null;
		
		try
		{
			directory = new File(URLDecoder.decode(url.getFile(), "utf-8"));
		}
		catch (UnsupportedEncodingException ue) { }	
		
		if (directory != null && directory.exists()) 
		{
			// Get the list of the files contained in the package
			String [] files = directory.list();
   
			for (int i=0; i < files.length; i++) 
			{
				if (files[i].endsWith(".class")) 
				{
					// removes the .class extension
					String className = files[i].substring(0, files[i].length() - 6);

					try
					{
						Class<?> subClass = Class.forName(pack.getName() + "." + className);
						subClasses.add(subClass);
					}
					catch (ClassNotFoundException e) { }
				}
			}
		}
		
		return subClasses;
	}
}
