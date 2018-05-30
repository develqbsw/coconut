package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The account.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @author Michal Lacko
 * @version 1.19.0
 * @since 1.0.0
 */
@Entity
@Table (name = "t_account", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_account_login", columnNames = {"c_login"}))
@FilterDef (name = "accountDefaultUnitFilter")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue ("account")
@DiscriminatorColumn (name = "d_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Account extends AEntity<Long>
{
	private static final long serialVersionUID = 425742764286573960L;

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "accountSequenceGenerator")
	@SequenceGenerator (name = "accountSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_account")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_login")
	private String login;

	@Column (name = "c_email")
	private String email;

	@NotNull
	@Column (name = "c_state")
	@Enumerated (EnumType.STRING)
	private ActivityStates state = ActivityStates.ACTIVE;

	@NotNull
	@Column (name = "c_type")
	@Enumerated (EnumType.STRING)
	private AccountTypes type = AccountTypes.PERSONAL;

	@NotNull
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_organization")
	private Organization organization;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "fk_default_unit")
	private Unit defaultUnit;

	@OneToMany (mappedBy = "account", fetch = FetchType.LAZY)
	private Set<AuthenticationParams> authenticationParams;

	@OneToMany (mappedBy = "account", fetch = FetchType.LAZY)
	@Filter (name = "accountDefaultUnitFilter", condition = "( (fk_unit = (select us.fk_default_unit from sec.t_account us where us.pk_id = fk_account)) or ( (select us.fk_default_unit from sec.t_account us where us.pk_id = fk_account) is null and fk_unit is null) )")
	private Set<AccountUnitGroup> accountUnitGroups = new HashSet<>();

	public String getPassword ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getPassword();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	public String getPasswordDigest ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getPasswordDigest();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	public Set<Group> getGroups ()
	{
		return accountUnitGroups.stream().map(AccountUnitGroup::getGroup).collect(Collectors.toSet());
	}

	private AuthenticationParams getAuthenticationParams ()
	{
		if (authenticationParams.stream().findFirst().isPresent())
		{
			return authenticationParams.stream().findFirst().get();
		}
		else
		{
			return null;
		}
	}

	public AuthenticationTypes getAuthenticationType ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getAuthenticationType();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	public PasswordTypes getPasswordType ()
	{
		if (getAuthenticationParams() != null)
		{
			return getAuthenticationParams().getPasswordType();
		}
		else
		{
			throw new CSystemException("The user has not a authentication params");
		}
	}

	public boolean hasRole (Role role)
	{
		for (Group group : getGroups())
		{
			if (group.hasRole(role))
			{
				return true;
			}
		}

		return false;
	}

	public List<String> exportRoles ()
	{
		List<String> retVal = new ArrayList<>();

		for (Group group : getGroups())
		{
			Set<Role> roles = group.getRoles();
			for (Role role : roles)
			{
				retVal.add(role.getCode());
			}
		}

		return retVal;
	}

	public boolean isInUnit (Unit unit)
	{
		for (Group group : getGroups())
		{
			if (group.hasUnit(unit))
			{
				return true;
			}
		}

		return false;
	}

	public boolean hasCategory (String category, Role role)
	{
		for (Group group : getGroups())
		{
			if (group.hasCategory(category, role))
			{
				return true;
			}
		}

		return false;
	}
}