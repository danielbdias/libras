package libras.preprocessing;

public abstract class ChainAction
{
	public ChainAction()
	{
		this(null);
	}
	
	public ChainAction(ChainAction nextAction)
	{
		this.nextAction = nextAction;
	}
	
	private ChainAction nextAction = null; 
	
	public void executeAction()
	{
		this.doExecuteAction();
		
		if (this.nextAction != null) nextAction.executeAction();
	}
	
	protected abstract void doExecuteAction();

	public ChainAction getNextAction()
	{
		return nextAction;
	}

	public void setNextAction(ChainAction nextAction)
	{
		this.nextAction = nextAction;
	}
	
	protected void log(String message)
	{
		this.log(message, (String) null);
	}
	
	protected void log(String message, Object... args)
	{
		if (args == null || args.length == 0 || args[0] == null)
			System.out.print(message);
		else
			System.out.format(message, args);
		
		System.out.println();
	}
}
