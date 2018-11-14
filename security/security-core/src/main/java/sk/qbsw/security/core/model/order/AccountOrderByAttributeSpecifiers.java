package sk.qbsw.security.core.model.order;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.model.domain.QAccount;

/**
 * The account order by attribute specifier.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public enum AccountOrderByAttributeSpecifiers implements OrderByAttributeSpecifier
{
	/** The id. */
	ID,

	/** The flag enabled. */
	STATE,

	/** The login. */
	LOGIN,

	/** The email. */
	EMAIL,

	/** The type. */
	TYPE;

	public OrderSpecifier<?> getOrderSpecifier (OrderSpecifiers orderSpecifier)
	{
		QAccount qAccount = QAccount.account;
		ComparableExpressionBase<?> comparableExpression = null;

		switch (this)
		{
			case ID:
				comparableExpression = qAccount.id;
				break;
			case STATE:
				comparableExpression = qAccount.state;
				break;
			case LOGIN:
				comparableExpression = qAccount.login;
				break;
			case EMAIL:
				comparableExpression = qAccount.email;
				break;
			case TYPE:
				comparableExpression = qAccount.type;
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
