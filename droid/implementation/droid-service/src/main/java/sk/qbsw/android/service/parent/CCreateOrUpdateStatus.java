package sk.qbsw.android.service.parent;

/**
 * this class represent status which come from update method of service
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.1.0
 */
public class CCreateOrUpdateStatus
{
	/**
	 * if row is created
	 */
	private boolean created;
	/**
	 * if row is updated
	 */
	private boolean updated;
	/**
	 * count of changed lines
	 */
	private int numLinesChanged;

	public CCreateOrUpdateStatus (boolean created, boolean updated, int numberLinesChanged)
	{
		this.created = created;
		this.updated = updated;
		this.numLinesChanged = numberLinesChanged;
	}

	public boolean isCreated ()
	{
		return created;
	}

	public boolean isUpdated ()
	{
		return updated;
	}

	public int getNumLinesChanged ()
	{
		return numLinesChanged;
	}
}
