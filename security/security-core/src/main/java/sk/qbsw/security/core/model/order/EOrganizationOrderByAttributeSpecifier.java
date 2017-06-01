package sk.qbsw.security.core.model.order;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.model.domain.QOrganization;

/**
 * The organization order by attribute specifier.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public enum EOrganizationOrderByAttributeSpecifier implements IOrderByAttributeSpecifier
{
	/** The id. */
	ID,

	/** The email. */
	EMAIL,

	/** The phone. */
	PHONE,

	/** The fax. */
	FAX,

	/** The flag enabled. */
	FLAG_ENABLED,

	/** The name. */
	NAME,

	/** The code. */
	CODE;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.model.order.IOrderByAttributeSpecifier#getOrderSpecifier(sk.qbsw.security.core.core.model.order.EOrderSpecifier)
	 */
	public OrderSpecifier<?> getOrderSpecifier (EOrderSpecifier orderSpecifier)
	{
		QOrganization qOrganization = QOrganization.organization;
		ComparableExpressionBase<?> comparableExpression = null;

		switch (this)
		{
			case ID:
				comparableExpression = qOrganization.id;
				break;
			case EMAIL:
				comparableExpression = qOrganization.email;
				break;
			case PHONE:
				comparableExpression = qOrganization.phone;
				break;
			case FAX:
				comparableExpression = qOrganization.fax;
				break;
			case FLAG_ENABLED:
				comparableExpression = qOrganization.flagEnabled;
				break;
			case NAME:
				comparableExpression = qOrganization.name;
				break;
			case CODE:
				comparableExpression = qOrganization.code;
				break;
			default:
				throw new CSystemException("There is no such order by attribute");
		}

		switch (orderSpecifier)
		{
			case ASC:
				return comparableExpression.asc();
			case DESC:
				return comparableExpression.desc();

			default:
				throw new CSystemException("There is no such order specifier");
		}
	}
}
