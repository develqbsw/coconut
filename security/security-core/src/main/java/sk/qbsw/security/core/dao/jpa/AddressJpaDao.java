/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.dao.AddressDao;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.QAddress;

/**
 * The address dao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.6.0
 */
@Repository (value = "addressDao")
public class AddressJpaDao extends AEntityQDslDao<Long, Address> implements AddressDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public AddressJpaDao ()
	{
		super(QAddress.address, Long.class);
	}
}
