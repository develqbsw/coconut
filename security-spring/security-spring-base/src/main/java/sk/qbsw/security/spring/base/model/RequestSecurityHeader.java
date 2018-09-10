package sk.qbsw.security.spring.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * The request security base.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@AllArgsConstructor
@ToString
public class RequestSecurityHeader implements Serializable
{
	private final String token;
}
