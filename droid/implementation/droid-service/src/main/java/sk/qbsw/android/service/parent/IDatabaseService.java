package sk.qbsw.android.service.parent;

/**
 * This class is iterface for {@link CDatabaseService}
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 */
public interface IDatabaseService
{
	/**
	 * remove helper and close connection for access to database
	 */
	public abstract void removeHelper();
}
