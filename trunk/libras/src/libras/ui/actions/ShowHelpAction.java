/**
 * Package with all actions performed by the program.
 */
package libras.ui.actions;

import java.util.ArrayList;
import java.util.List;

import libras.ui.actions.annotations.ActionDescription;
import libras.utils.ReflectionHelper;

/**
 * Show help to the user.
 * @author Daniel Baptista Dias
 */
@ActionDescription(
	command="help",
	commandExample="help",
	requiredArgs = {},
	helpDescription="Show help options")
public class ShowHelpAction extends Action
{
	/**
	 * Show help to the user.
	 * @see libras.ui.actions.Action#execute()
	 */
	public void execute()
	{	
		
		System.out.println("The follow command arguments can be used in the program:");
		
		List<ActionDescription> descriptions = this.getActionDescriptions();
		
		int maxExampleLenght = this.getMaxExampleLenght(descriptions);
		
		for (int i = 0; i < descriptions.size(); i++)
		{
			ActionDescription item = descriptions.get(i);
			
			System.out.println(
				String.format("-%s%s %s", 
						item.commandExample(),
						this.getEmptySpaces(maxExampleLenght - item.commandExample().length()),
						item.helpDescription()));
		}
		
		System.out.println();
	}

	/**
	 * Generates a string with a determined number of empty spaces.
	 * @param totalSpaces Number of spaces used in the string.
	 * @return A string with a determined number of empty spaces.
	 */
	private String getEmptySpaces(int totalSpaces)
	{
		String result = "";
		
		for (int i = 0; i < totalSpaces; i++)
			result += " ";
		
		return result;
	}

	/**
	 * Finds the max example lenght of the descriptions.
	 * @param descriptions Descriptions of all actions in this package.
	 * @return Max size of a command example.
	 */
	private int getMaxExampleLenght(List<ActionDescription> descriptions)
	{
		int maxExampleLenght = 0;
		
		for (int i = 0; i < descriptions.size(); i++)
		{
			ActionDescription item = descriptions.get(i);
			
			if (item.commandExample() != null && item.commandExample().length() > maxExampleLenght)
				maxExampleLenght = item.commandExample().length();
		}
		
		return maxExampleLenght;
	}

	/**
	 * Gets the descriptions of all actions in this package.
	 */
	private List<ActionDescription> getActionDescriptions()
	{
		List<ActionDescription> descriptions = new ArrayList<ActionDescription>();
		
		List<Class<?>> actionClasses = 
			ReflectionHelper.getSubclasses(Action.class, Action.class.getPackage());
		
		if (actionClasses != null)
		{
			for (int i = 0; i < actionClasses.size(); i++)
			{
				Class<?> clazz = actionClasses.get(i);

				ActionDescription actionDescription = clazz.getAnnotation(ActionDescription.class);

				if (actionDescription != null)
					descriptions.add(actionDescription);
			}
		}
		
		return descriptions;
	}	 
}
