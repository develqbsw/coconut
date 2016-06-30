/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IAddressDao;
import sk.qbsw.core.security.model.domain.CAddress;

/**
 * The Class CUnitJpaDao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.7.1
 * @since 1.6.0
 */
@Repository (value = "addressDao")
public class CAddressJpaDao extends AEntityJpaDao<Long, CAddress> implements IAddressDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public CAddressJpaDao ()
	{
		super(CAddress.class);
	}
}
