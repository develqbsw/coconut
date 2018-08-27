package sk.qbsw.integration.message.email.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The recipient of the email.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.9.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipient implements Serializable
{
	private static final long serialVersionUID = -102539044118358283L;

	private List<String> addressList;

	/**
	 * To array string [ ].
	 *
	 * @return the string [ ]
	 */
	public String[] toArray ()
	{
		return addressList.toArray(new String[0]);
	}

	@Override
	public boolean equals (Object obj)
	{
		if (addressList != null)
		{
			return addressList.equals(obj);
		}
		else
		{
			// if the list of address is empty, the objects are equals
			return true;
		}
	}

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
