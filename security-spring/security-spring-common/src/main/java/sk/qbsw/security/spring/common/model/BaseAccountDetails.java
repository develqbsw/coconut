package sk.qbsw.security.spring.common.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * The account details base.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.18.6
 */
@Getter
public abstract class BaseAccountDetails implements AccountDetails
{
	private static final long serialVersionUID = -105300727899469649L;

	@NotNull
	private final Long id;

	private final UserData user;

	@NotNull
	private final Map<String, Object> additionalInformation;

	@NotNull
	private final Set<GrantedAuthority> authorities;

	/**
	 * Instantiates a new Base account details.
	 *
	 * @param id the id
	 * @param authorities the authorities
	 */
	public BaseAccountDetails (Long id, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.user = null;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.additionalInformation = new HashMap<>();
	}

	/**
	 * Instantiates a new Base account details.
	 *
	 * @param id the id
	 * @param user the user
	 * @param authorities the authorities
	 */
	public BaseAccountDetails (Long id, UserData user, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.user = user;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.additionalInformation = new HashMap<>();
	}

	/**
	 * Instantiates a new Base account details.
	 *
	 * @param id the id
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public BaseAccountDetails (Long id, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.user = null;
		this.additionalInformation = additionalInformation;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	/**
	 * Instantiates a new Base account details.
	 *
	 * @param id the id
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public BaseAccountDetails (Long id, UserData user, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.user = user;
		this.additionalInformation = additionalInformation;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	private static SortedSet<GrantedAuthority> sortAuthorities (Collection<? extends GrantedAuthority> authorities)
	{
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new BaseAccountDetails.AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities)
		{
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable
	{
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare (GrantedAuthority g1, GrantedAuthority g2)
		{
			// Neither should ever be null as each entry is checked before adding it to
			// the set.
			// If the authority is null, it is a custom authority and should precede
			// others.
			if (g2.getAuthority() == null)
			{
				return -1;
			}

			if (g1.getAuthority() == null)
			{
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	/**
	 * Compares objects based on login
	 * 
	 * @param rhs object to compare
	 * @return true / false
	 */
	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	/**
	 * Generates hashcode from login
	 * 
	 * @return hashcode from login
	 */
	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}
}
