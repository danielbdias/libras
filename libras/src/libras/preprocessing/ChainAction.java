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
}
