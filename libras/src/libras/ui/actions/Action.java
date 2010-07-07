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
}
