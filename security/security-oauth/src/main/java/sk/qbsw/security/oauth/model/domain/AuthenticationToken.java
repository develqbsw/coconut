package sk.qbsw.security.oauth.model.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The authentication token.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
@Entity
@DiscriminatorValue ("authentication_token")
public class AuthenticationToken extends SecurityToken
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5892107129431544313L;
}
