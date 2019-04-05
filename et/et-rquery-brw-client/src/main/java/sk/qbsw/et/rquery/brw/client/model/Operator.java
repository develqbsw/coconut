package sk.qbsw.et.rquery.brw.client.model;

/**
 * The filtering operator.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public enum Operator
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

	/** The like ignore case. */
	LIKE_IGNORE_CASE,

	/** The is null. */
	IS_NULL,

	/** The is not null. */
	IS_NOT_NULL
}
