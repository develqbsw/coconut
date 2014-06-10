package sk.qbsw.core.communication.mail.model.domain.type;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.beans.BeanUtils;

import sk.qbsw.core.communication.mail.model.domain.CRecipient;

/**
 * The type for recipient.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.10.0
 * @since 1.9.0
 */
public class CRecipientType implements UserType
{
	/** The separator. */
	private final char SEPARATOR = ';';

	/** The sql type. */
	private final int SQL_TYPE = Types.VARCHAR;

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	@Override
	public int[] sqlTypes ()
	{
		return new int[] {SQL_TYPE};
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	@Override
	@SuppressWarnings ("rawtypes")
	public Class returnedClass ()
	{
		return CRecipient.class;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean equals (Object x, Object y) throws HibernateException
	{
		return EqualsBuilder.reflectionEquals(x, y);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	@Override
	public Object nullSafeGet (ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException
	{
		// assume that we only map to one column, so there's only one column name
		String value = rs.getString(names[0]);
		if (value == null)
		{
			return null;
		}

		try
		{
			return new CRecipient(Arrays.asList(StringUtils.split(value, SEPARATOR)));
		}
		catch (Throwable e)
		{
			throw new HibernateException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int, org.hibernate.engine.spi.SessionImplementor)
	 */
	@Override
	public void nullSafeSet (PreparedStatement stmt, Object value, int index, SessionImplementor session) throws HibernateException, SQLException
	{
		if (value == null)
		{
			stmt.setNull(index, SQL_TYPE);
			return;
		}

		if ( (value instanceof CRecipient) == false)
		{
			throw new UnsupportedOperationException("can't convert " + value.getClass());

		}

		try
		{
			String recipientString = StringUtils.join( ((CRecipient) value).getAddressList().toArray(), SEPARATOR);
			stmt.setString(index, recipientString);
		}
		catch (Throwable e)
		{
			throw new HibernateException(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	@Override
	public Object deepCopy (Object value)
	{
		if (value == null)
		{
			return null;
		}

		if ( (value instanceof CRecipient) == false)
		{
			throw new UnsupportedOperationException("can't convert " + value.getClass());
		}
		else
		{
			CRecipient copiedValue = (CRecipient) BeanUtils.instantiate(value.getClass());
			BeanUtils.copyProperties(value, copiedValue);
			return copiedValue;
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	@Override
	public boolean isMutable ()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	@Override
	public Object assemble (Serializable cached, Object owner)
	{
		return deepCopy(cached);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	@Override
	public Serializable disassemble (Object value) throws HibernateException
	{
		Object copy = deepCopy(value);

		if (copy instanceof CRecipient)
		{
			return (CRecipient) copy;
		}
		else
		{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object replace (Object original, Object target, Object owner)
	{
		return deepCopy(original);
	}
}
