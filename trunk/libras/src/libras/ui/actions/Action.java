/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

/**
 * Represents an action performed by this program.
 * Every action made by the {@link libras.geneticalgorithms.Program} class must use a subclass of this class.
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
	protected String identifyPathInput(String input)
	{
		String value = input;
		
		if (value.startsWith("\"") && value.endsWith("\""))
			value = value.substring(1).substring(0, value.length() - 2);
		
		return value;
	}
}
