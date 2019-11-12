package sk.qbsw.et.rquery.rsql.binding.operator;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * The extended rsql operators.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class ExtendedRSQLOperators extends RSQLOperators
{
	public static final ComparisonOperator LIKE = new ComparisonOperator("=li=");
	public static final ComparisonOperator NOT_LIKE = new ComparisonOperator("=nli=");
	public static final ComparisonOperator LIKE_IGNORE_CASE = new ComparisonOperator("=lig=");

	public static Set<ComparisonOperator> defaultOperators ()
	{
		Set<ComparisonOperator> s = new HashSet<>(RSQLOperators.defaultOperators());
		s.addAll(asList(LIKE, NOT_LIKE, LIKE_IGNORE_CASE));

		return s;

	}
}
