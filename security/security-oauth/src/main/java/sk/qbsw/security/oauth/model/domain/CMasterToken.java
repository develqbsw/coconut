package sk.qbsw.security.oauth.model.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The master token.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 */
@Entity
@DiscriminatorValue ("master_token")
public class CMasterToken extends CSecurityToken
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -375148075346298238L;
}
