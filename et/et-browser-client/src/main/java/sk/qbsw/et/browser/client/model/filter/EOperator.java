package sk.qbsw.et.browser.client.model.filter;

/**
 * The filtering operator.
 *
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.16.0
 */
public enum EOperator
{
	/** The eq. */
	EQ,

	/** The ne. */
	NE,

	/** The gt. */
	GT,

	/** The goe. */
	GOE,

	/** The lt. */
	LT,

	/** The loe. */
	LOE,

	/** The between date time. */
	BETWEEN_DATE_TIME,

	/** The like ignore case. */
	LIKE_IGNORE_CASE;
}
