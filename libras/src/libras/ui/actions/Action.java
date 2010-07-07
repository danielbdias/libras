/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

/**
 * Represents an action performed by this program.
 * Every action made by the {@link libras.Program} class must use a subclass of this class.
 */
public abstract class Action
{
	/**
	 * Executes an action delegated for this object.
	 */
	public abstract void execute() throws Exception;
	
	/**
	 * Provides a generic parameter recognition for a path.
	 * @param input Input data of the user for a parameter
	 * @param parameter Parameter used.
	 */
	protected String identifyPathFromUserInput(String input, String parameter)
	{
		String value = input;
		
		//Remove the parameter string from the start of the string.
		if (value.startsWith(parameter)) 
			value = value.substring(parameter.length());
		
		//Remove the "=" string from the start of the string.
		if (value.startsWith("="))
			value = value.substring(1);
		
		if (value.startsWith("\"") && value.endsWith("\""))
			value = value.substring(1).substring(0, value.length() - 2);
		
		return value;
	}
}
