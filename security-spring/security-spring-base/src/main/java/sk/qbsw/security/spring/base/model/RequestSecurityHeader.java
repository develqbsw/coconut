package sk.qbsw.security.spring.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * The request security base.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
@Data
@AllArgsConstructor
@ToString
public class RequestSecurityHeader implements Serializable
{
	private final String token;
}
