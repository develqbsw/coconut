package sk.qbsw.security.spring.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * The request security base.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.0.0
 */
@Data
@AllArgsConstructor
@ToString
public class RequestSecurityHeader implements Serializable
{
	private static final long serialVersionUID = 5604776261180131055L;

	private final String token;
}
