package sk.qbsw.et.rquery.core.configuration.expression;

import com.querydsl.core.types.dsl.SimpleExpression;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The querydsl expression wrapper.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@AllArgsConstructor
@Getter
public class EntityPropertyExpression
{
	private final SimpleExpression<?> expression;
}
