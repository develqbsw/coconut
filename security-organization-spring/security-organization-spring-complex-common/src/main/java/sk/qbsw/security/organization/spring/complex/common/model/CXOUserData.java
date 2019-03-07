package sk.qbsw.security.organization.spring.complex.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.qbsw.security.spring.common.model.UserData;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The complex organization user data.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CXOUserData extends UserData
{
	private static final long serialVersionUID = -6828477586096687584L;

	@NotNull
	private List<CXOOrganization> organizations = new ArrayList<>();

	/**
	 * Instantiates a new Cxo user data.
	 *
	 * @param id the id
	 * @param organizations the organizations
	 */
	public CXOUserData (Long id, List<CXOOrganization> organizations)
	{
		super(id);
		this.organizations = organizations;
	}
}
