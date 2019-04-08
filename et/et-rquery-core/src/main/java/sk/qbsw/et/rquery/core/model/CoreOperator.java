package sk.qbsw.et.rquery.core.model;

/**
 * The filtering operator.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public enum CoreOperator
{
	/**
	 * The eq.
	 */
	EQ,

	/**
	 * The ne.
	 */
	NE,

	/**
	 * The gt.
	 */
	GT,

	/**
	 * The goe.
	 */
	GOE,

	/**
	 * The lt.
	 */
	LT,

	/**
	 * The loe.
	 */
	LOE,

	/**
	 * In core operator.
	 */
	IN,

	/**
	 * Not in core operator.
	 */
	NOT_IN,

	/**
	 * The like ignore case.
	 */
	LIKE_IGNORE_CASE
}
