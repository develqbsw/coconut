package sk.qbsw.security.model.order;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.model.domain.QCUser;

/**
 * The user order by attribute specifier.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public enum EUserOrderByAttributeSpecifier implements IOrderByAttributeSpecifier
{
	/** The id. */
	ID,

	/** The flag enabled. */
	FLAG_ENABLED,

	/** The login. */
	LOGIN,

	/** The name. */
	NAME,

	/** The surname. */
	SURNAME,

	/** The email. */
	EMAIL,

	/** The degree. */
	DEGREE,

	/** The working position. */
	WORKING_POSITION,

	/** The user type. */
	USER_TYPE;

	public OrderSpecifier<?> getOrderSpecifier (EOrderSpecifier orderSpecifier)
	{
		QCUser qUser = QCUser.cUser;
		ComparableExpressionBase<?> comparableExpression = null;

		switch (this)
		{
			case ID:
				comparableExpression = qUser.id;
				break;
			case FLAG_ENABLED:
				comparableExpression = qUser.flagEnabled;
				break;
			case LOGIN:
				comparableExpression = qUser.login;
				break;
			case NAME:
				comparableExpression = qUser.name;
				break;
			case SURNAME:
				comparableExpression = qUser.surname;
				break;
			case EMAIL:
				comparableExpression = qUser.email;
				break;
			case DEGREE:
				comparableExpression = qUser.degree;
				break;
			case WORKING_POSITION:
				comparableExpression = qUser.workingPosition;
				break;
			case USER_TYPE:
				comparableExpression = qUser.userType;
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
