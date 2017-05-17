package sk.qbsw.integration.mail.model.domain;

import java.io.Serializable;
import java.util.List;

/**
 * The recipient of the mail.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.9.0
 */
public class CRecipient implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The addresses. */
	private List<String> addressList;

	/**
	 * Instantiates a new c recipient.
	 */
	public CRecipient ()
	{
	}

	/**
	 * Instantiates a new c recipient.
	 *
	 * @param addressList the address list
	 */
	public CRecipient (List<String> addressList)
	{
		this.addressList = addressList;
	}

	/**
	 * Gets the addresses.
	 *
	 * @return the addresses
	 */
	public List<String> getAddressList ()
	{
		return addressList;
	}

	/**
	 * Sets the address list.
	 *
	 * @param addressList the new address list
	 */
	public void setAddressList (List<String> addressList)
	{
		this.addressList = addressList;
	}

	/**
	 * Convert address list to array.
	 *
	 * @return the string[]
	 */
	public String[] toArray ()
	{
		return addressList.toArray(new String[addressList.size()]);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object obj)
	{
		if (addressList != null)
		{
			return addressList.equals(obj);
		}
		else
		{
			//if the list of address is empty, the objects are equals
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		if (addressList != null)
		{
			return addressList.hashCode();
		}
		else
		{
			return 0;
		}
	}
}
