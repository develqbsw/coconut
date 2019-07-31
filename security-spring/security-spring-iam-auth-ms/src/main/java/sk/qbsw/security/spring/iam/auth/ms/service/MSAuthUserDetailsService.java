package sk.qbsw.security.spring.iam.auth.ms.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.common.model.AccountDataStates;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.base.service.IAMAuthUserDetailsServiceBase;
import sk.qbsw.security.spring.iam.auth.common.configuration.InternalJwtConfiguration;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthLoggedAccount;
import sk.qbsw.security.spring.iam.auth.common.model.TokenData;

import java.util.Collection;
import java.util.List;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.0.0
 */
public class MSAuthUserDetailsService extends IAMAuthUserDetailsServiceBase<Jws<Claims>>
{
	private final InternalJwtConfiguration internalJwtConfiguration;

	private final UserDataMapper<Claims> userDataMapper;

	/**
	 * Instantiates a new Iam auth user details service.
	 *
	 * @param authorityConverter the authority converter
	 * @param userDataMapper the user data mapper
	 * @param internalJwtConfiguration the internal jwt configuration
	 */
	public MSAuthUserDetailsService (AuthorityConverter authorityConverter, UserDataMapper<Claims> userDataMapper, InternalJwtConfiguration internalJwtConfiguration)
	{
		super(authorityConverter);
		this.userDataMapper = userDataMapper;
		this.internalJwtConfiguration = internalJwtConfiguration;
	}

	@Override
	protected TokenData<Jws<Claims>> verifyToken (String token) throws PreAuthenticatedCredentialsNotFoundException
	{
		try
		{
			Jws<Claims> jws = Jwts.parser() //
				.setSigningKey(Keys.hmacShaKeyFor(internalJwtConfiguration.getHmacShaKey().getBytes())) //
				.requireIssuer(internalJwtConfiguration.getIssuer()) //
				.requireAudience(internalJwtConfiguration.getAudience()) //
				.requireSubject(internalJwtConfiguration.getSubject()) //
				.parseClaimsJws(token); //

			checkContainsClaim(jws.getBody(), internalJwtConfiguration.getClaims().getId());
			checkContainsClaim(jws.getBody(), internalJwtConfiguration.getClaims().getUid());
			checkContainsClaim(jws.getBody(), internalJwtConfiguration.getClaims().getLogin());
			checkContainsClaim(jws.getBody(), internalJwtConfiguration.getClaims().getState());
			checkContainsClaim(jws.getBody(), internalJwtConfiguration.getClaims().getRoles());

			return new TokenData<>( //
				getClaim(jws.getBody(), internalJwtConfiguration.getClaims().getUid(), String.class), //
				getClaim(jws.getBody(), internalJwtConfiguration.getClaims().getLogin(), String.class), //
				getClaim(jws.getBody(), internalJwtConfiguration.getClaims().getEmail(), String.class), //
				jws, //
				token //
			);
		}
		catch (JwtException ex)
		{
			LOGGER.error("The internal JWT parsing failed", ex);
			throw new PreAuthenticatedCredentialsNotFoundException("The internal JWT parsing failed", ex);
		}
	}

	private void checkContainsClaim (Claims claims, String claim)
	{
		if (!claims.containsKey(claim))
		{
			throw new PreAuthenticatedCredentialsNotFoundException("The internal JWT claim " + claim + " not found");
		}
	}

	private <T> T getClaim (Claims claims, String claim, Class<T> type)
	{
		if (claims.containsKey(claim))
		{
			return claims.get(claim, type);
		}
		else
		{
			return null;
		}
	}

	@Override
	protected UserDetails createUserDetails (TokenData<Jws<Claims>> tokenData) throws CBusinessException
	{
		String uid = getClaim(tokenData.getData().getBody(), internalJwtConfiguration.getClaims().getUid(), String.class);
		Long id = getClaim(tokenData.getData().getBody(), internalJwtConfiguration.getClaims().getId(), Long.class);
		String login = getClaim(tokenData.getData().getBody(), internalJwtConfiguration.getClaims().getLogin(), String.class);
		String state = getClaim(tokenData.getData().getBody(), internalJwtConfiguration.getClaims().getState(), String.class);
		Collection<GrantedAuthority> authorities = authorityConverter.convertRolesToAuthorities(getClaim(tokenData.getData().getBody(), internalJwtConfiguration.getClaims().getRoles(), List.class));

		IAMAuthData iamAuthData = new IAMAuthData(uid, tokenData.getValue());
		return new IAMAuthLoggedAccount(id, login, "N/A", state != null && AccountDataStates.valueOf(state).equals(AccountDataStates.ACTIVE), //
			userDataMapper.mapToUserData(tokenData.getData().getBody()), authorities, iamAuthData);
	}
}
