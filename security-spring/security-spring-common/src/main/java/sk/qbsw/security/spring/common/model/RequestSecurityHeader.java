package sk.qbsw.security.spring.common.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * The request security base.
 *
 * @author Tomas Lauro
 * @version 2.4.0
 * @since 2.0.0
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RequestSecurityHeader implements Serializable
{
	private static final long serialVersionUID = 5604776261180131055L;

	private final String token;
}
