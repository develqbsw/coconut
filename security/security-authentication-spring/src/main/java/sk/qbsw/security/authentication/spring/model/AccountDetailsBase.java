package sk.qbsw.security.authentication.spring.model;

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
 * @version 1.19.0
 * @since 1.18.6
 */
@Getter
public abstract class AccountDetailsBase implements AccountDetails
{
	private static final long serialVersionUID = -105300727899469649L;

	@NotNull
	private final Long id;

	@NotNull
	private final Organization organization;

	@NotNull
	private final Map<String, Object> additionalInformation;

	@NotNull
	private final Set<GrantedAuthority> authorities;

	/**
	 * Instantiates a new Security account details base.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param authorities the authorities
	 */
	public AccountDetailsBase (Long id, Organization organization, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.organization = organization;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.additionalInformation = new HashMap<>();
	}

	/**
	 * Instantiates a new Security account details base.
	 *
	 * @param id the id
	 * @param organization the organization
	 * @param additionalInformation the additional information
	 * @param authorities the authorities
	 */
	public AccountDetailsBase (Long id, Organization organization, Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.organization = organization;
		this.additionalInformation = additionalInformation;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	private static SortedSet<GrantedAuthority> sortAuthorities (Collection<? extends GrantedAuthority> authorities)
	{
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AccountDetailsBase.AuthorityComparator());

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
