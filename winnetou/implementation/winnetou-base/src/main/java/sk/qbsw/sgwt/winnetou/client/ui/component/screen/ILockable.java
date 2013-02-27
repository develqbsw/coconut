package sk.qbsw.sgwt.winnetou.client.ui.component.screen;

/**
 * Interface indetifies that the screen can be locked and unlocked (works with
 * section)
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public interface ILockable
{

	/**
	 * Method called to unlock the screen
	 */
	public void unlock();

	/**
	 * Method called to lock the screen.
	 */
	public void lock();

}
