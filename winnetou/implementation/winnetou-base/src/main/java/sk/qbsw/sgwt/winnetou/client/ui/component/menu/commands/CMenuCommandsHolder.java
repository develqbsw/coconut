package sk.qbsw.sgwt.winnetou.client.ui.component.menu.commands;

import java.util.HashMap;

import sk.qbsw.sgwt.winnetou.client.exception.CSystemFailureException;

/**
 * Singleton, which holds all menu commands available in the system. Command is
 * identified by ID...
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CMenuCommandsHolder
{
	// references to all available commands
	private HashMap<String, IMenuCommand> commands;

	/**
	 * Hides constructor
	 */
	private CMenuCommandsHolder()
	{
		commands = new HashMap<String, IMenuCommand>();
	}

	private static CMenuCommandsHolder instance;

	/**
	 * Singleton reference
	 * 
	 * @return
	 */
	public static CMenuCommandsHolder getInstance()
	{
		if (instance == null)
		{
			instance = new CMenuCommandsHolder();
		}

		return instance;
	}

	/**
	 * Adds command
	 * 
	 * @param command
	 */
	public void addCommand(IMenuCommand command)
	{
		commands.put(command.getId(), command);
	}

	/**
	 * Retrieves command
	 * 
	 * @param id
	 *            id of searched command
	 * @return IMenuCommand
	 */
	public IMenuCommand getCommand(String id)
	{

		if (!commands.containsKey(id))
		{
			throw new CSystemFailureException("No menu command for id=" + id, null);
		}
		return commands.get(id);
	}
}
