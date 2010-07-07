/**
 * Provides an user interface helpers to the application.
 */
package libras.videos.ui;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * Provides an filter to be used in UI.
 * @author Daniel Dias
 */
public class VideoFilter extends FileFilter 
{
	private final static String[] supportedExtensions =
		new String[] { ".avi", ".mpeg" };
	
    /**
     * Accept only video extensions and directories.
     */
    public boolean accept(File file) 
    {
        if (file.isDirectory()) return true;

        String extension = file.getName();
        int lastPointIndex = extension.lastIndexOf((int) '.'); 
        if (lastPointIndex > 0)
        {
        	extension = extension.substring(lastPointIndex);
        	if (extension != null) extension = extension.toLowerCase();
        }
        
        
        if (extension != null) 
        {
        	boolean validExtension = false;
        	
        	for (String supportedExtension : supportedExtensions)
			{
				validExtension = supportedExtension.equals(extension);
				if (validExtension) break;
			}
        	
        	return validExtension;
        }

        return false;
    }

    /**
     * Description of this filter.
     */
    public String getDescription() 
    {
    	String description = "Video Files";
    	
    	if (supportedExtensions != null && supportedExtensions.length > 0)
    	{
    		int itemsToShow = (supportedExtensions.length > 3 ? 3 : supportedExtensions.length);
    		
    		description += " ( ";
    		
    		for (int i = 0; i < itemsToShow; i++)
			{
				description += supportedExtensions[i];
				if (i < itemsToShow-1) description += ", ";
			}
    		
    		description += " )";
    	}
    	
        return description;
    }
}
