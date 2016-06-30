package sk.qbsw.core.persistence.dao.jpa.qdsl;

/**
 * operator pre filtrovanie.
 *
 * @author podmajersky
 */
public enum EOperator
{
	/** The eq. */
	EQ,

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
