package sk.qbsw.integration.message.email.model.domain.type;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.beans.BeanUtils;
import sk.qbsw.integration.message.email.model.domain.Recipient;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

/**
 * The type for recipient.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.9.0
 */
public class RecipientType implements UserType
{
	private static final char SEPARATOR = ';';

	private static final int SQL_TYPE = Types.VARCHAR;

	@Override
	public int[] sqlTypes ()
	{
		return new int[] {SQL_TYPE};
	}

	@Override

	public Class returnedClass ()
	{
		return Recipient.class;
	}

	@Override
	public boolean equals (Object x, Object y) throws HibernateException
	{
		return EqualsBuilder.reflectionEquals(x, y);
	}

	@Override
	public int hashCode (Object value) throws HibernateException
	{
		if (value != null)
		{
			return value.hashCode();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public Object nullSafeGet (ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException
	{
		// assume that we only map to one column, so there's only one column name
		String value = rs.getString(names[0]);
		if (value == null)
		{
			return null;
		}

		try
		{
			return new Recipient(Arrays.asList(StringUtils.split(value, SEPARATOR)));
		}
		catch (Exception e)
		{
			throw new HibernateException(e);
		}
	}

	@Override
	public void nullSafeSet (PreparedStatement stmt, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException
	{
		if (value == null)
		{
			stmt.setNull(index, SQL_TYPE);
			return;
		}

		if (! (value instanceof Recipient))
		{
			throw new UnsupportedOperationException("can't convert " + value.getClass());

		}

		try
		{
			String recipientString = StringUtils.join( ((Recipient) value).getAddressList().toArray(), SEPARATOR);
			stmt.setString(index, recipientString);
		}
		catch (Exception e)
		{
			throw new HibernateException(e);
		}
	}

	@Override
	public Object deepCopy (Object value)
	{
		if (value == null)
		{
			return null;
		}

		if (! (value instanceof Recipient))
		{
			throw new UnsupportedOperationException("can't convert " + value.getClass());
		}
		else
		{
			Recipient copiedValue = (Recipient) BeanUtils.instantiate(value.getClass());
			BeanUtils.copyProperties(value, copiedValue);
			return copiedValue;
		}
	}

	@Override
	public boolean isMutable ()
	{
		return true;
	}

	@Override
	public Object assemble (Serializable cached, Object owner)
	{
		return deepCopy(cached);
	}

	@Override
	public Serializable disassemble (Object value) throws HibernateException
	{
		Object copy = deepCopy(value);

		if (copy instanceof Recipient)
		{
			return (Recipient) copy;
		}
		else
		{
			return null;
		}
	}

	@Override
	public Object replace (Object original, Object target, Object owner)
	{
		return deepCopy(original);
	}
}
