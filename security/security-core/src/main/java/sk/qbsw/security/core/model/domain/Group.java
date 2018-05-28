package sk.qbsw.security.core.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.base.configuration.DatabaseSchemas;
import sk.qbsw.core.persistence.model.domain.AEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * The group.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
@Entity
@Table (name = "t_group", schema = DatabaseSchemas.SECURITY, //
	uniqueConstraints = @UniqueConstraint (name = "uc_group_code", columnNames = {"c_code"}))
@Getter
@Setter
public class Group extends AEntity<Long>
{
	private static final long serialVersionUID = 7160613084349492550L;

	private static final String CATEGORIES_SEPARATOR = ";";

	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "groupSequenceGenerator")
	@SequenceGenerator (name = "groupSequenceGenerator", sequenceName = DatabaseSchemas.SECURITY + ".s_group")
	@Column (name = "pk_id")
	private Long id;

	@NotNull
	@Column (name = "c_code")
	private String code;

	@NotNull
	@Column (name = "c_type")
	@Enumerated (EnumType.STRING)
	private GroupTypes type = GroupTypes.STANDARD;

	@Column (name = "c_category")
	private String category;

	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<Role> roles;

	@OneToMany (mappedBy = "group", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<AccountUnitGroup> accountUnitGroups = new HashSet<>();

	@ManyToMany (mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<Unit> units;

	// this association excludes group in other words if user have one group then can't have groups which are associated with this group through this association
	@ManyToMany (fetch = FetchType.LAZY)
	@JoinTable (schema = DatabaseSchemas.SECURITY, name = "t_x_group_group", joinColumns = {@JoinColumn (name = "fk_group")}, inverseJoinColumns = {@JoinColumn (name = "fk_excluded_group")})
	private Set<Group> excludedGroups;

	/**
	 * Has role boolean.
	 *
	 * @param roleToCheck the role to check
	 * @return the boolean
	 */
	public boolean hasRole (Role roleToCheck)
	{
		if (roleToCheck == null)
		{
			return true;
		}

		if (roles == null)
		{
			return false;
		}

		return roles.stream().anyMatch(r -> r.getCode().equals(roleToCheck.getCode()));
	}

	/**
	 * Has unit boolean.
	 *
	 * @param unitToCheck the unit to check
	 * @return the boolean
	 */
	public boolean hasUnit (Unit unitToCheck)
	{
		if (unitToCheck == null)
		{
			return true;
		}

		if (units == null)
		{
			return false;
		}

		return units.stream().anyMatch(u -> u.getName().equals(unitToCheck.getName()));
	}

	/**
	 * Has category boolean.
	 *
	 * @param category the category
	 * @param role the role
	 * @return the boolean
	 */
	public boolean hasCategory (String category, Role role)
	{
		if (this.category != null && hasRole(role))
		{
			String[] categories = this.category.split(CATEGORIES_SEPARATOR);
			for (String cat : categories)
			{
				if (cat.equals(category))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Gets accounts.
	 *
	 * @return the accounts
	 */
	public Set<Account> getAccounts ()
	{
		HashSet<Account> accounts = new HashSet<>();

		for (AccountUnitGroup xuug : accountUnitGroups)
		{
			accounts.add(xuug.getAccount());
		}
		return accounts;
	}

	/**
	 * Sets accounts.
	 *
	 * @param accounts the accounts
	 */
	public void setAccounts (Set<Account> accounts)
	{
		for (Account account : accounts)
		{
			addAccount(account);
		}
	}

	/**
	 * Add account.
	 *
	 * @param account the account
	 */
	public void addAccount (Account account)
	{
		AccountUnitGroup xuug = new AccountUnitGroup();
		xuug.setGroup(this);
		xuug.setAccount(account);
		accountUnitGroups.add(xuug);
	}
}
