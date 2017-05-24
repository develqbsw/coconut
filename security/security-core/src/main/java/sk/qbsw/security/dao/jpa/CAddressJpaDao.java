/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.dao.jpa;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.dao.IAddressDao;
import sk.qbsw.security.model.domain.CAddress;
import sk.qbsw.security.model.domain.QCAddress;

/**
 * The address dao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.6.0
 */
@Repository (value = "addressDao")
public class CAddressJpaDao extends AEntityQDslDao<Long, CAddress> implements IAddressDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public CAddressJpaDao ()
	{
		super(QCAddress.cAddress, Long.class);
	}
}
