/**
 * Package with annotations used by the actions performed by the commandline program
 */
package libras.ui.actions.annotations;

import java.lang.annotation.*;

/**
 * Represents an action description.
 * @author Daniel Baptista Dias
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionDescription
{
	/**
	 * Command passed to the program to invoke the action.
	 */
	String command();
	/**
	 * Command example used in the help explaining the action.
	 */
	String commandExample();
	/**
	 * Description used in the help explaining the action.
	 */
	String helpDescription();
	
	/**
	 * Verifies if the action need an user input.
	 */
	boolean needUserInput() default false;
	
	String[] requiredArgs();
}
