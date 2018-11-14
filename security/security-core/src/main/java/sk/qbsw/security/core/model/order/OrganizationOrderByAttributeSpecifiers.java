package sk.qbsw.security.core.model.order;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.model.domain.QOrganization;

/**
 * The organization order by attribute specifier.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public enum OrganizationOrderByAttributeSpecifiers implements OrderByAttributeSpecifier
{
	/** The id. */
	ID,

	/** The email. */
	EMAIL,

	/** The flag enabled. */
	STATE,

	/** The name. */
	NAME,

	/** The code. */
	CODE;

	public OrderSpecifier<?> getOrderSpecifier (OrderSpecifiers orderSpecifier)
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
			case STATE:
				comparableExpression = qOrganization.state;
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
