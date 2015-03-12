package sk.qbsw.core.persistence.dao.jpa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jdbc.ReturningWork;

import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.persistence.dao.IDao;

@SuppressWarnings("serial")
@CLogged
public abstract class AJpaDao implements IDao {

	/** The em. */
	@PersistenceContext(name = "persistenceContext")
	protected EntityManager em;

	/**
	 * Sets the entity manager.
	 *
	 * @param em the new entity manager
	 */
	public void setEntityManager(EntityManager em)
	{
		this.em = em;
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return em;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#flush()
	 */
	@Override
	public void flush()
	{
		em.flush();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IEntityDao#clear()
	 */
	@Override
	public void clear()
	{
		em.clear();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.IDao#getSequenceNextValue(java.lang.String)
	 */
	@Override
	public Long getSequenceNextValue(final String sequenceName) {
		final Session s = this.em.unwrap(Session.class);
		final Long result = s.doReturningWork(new ReturningWork<Long>() {

			@Override
			public Long execute(Connection connection) throws SQLException {
				final Dialect dialect = ((SessionFactoryImplementor) s.getSessionFactory()).getDialect();
				PreparedStatement ps = null;
				ResultSet rs = null;

				try {
					ps = connection.prepareStatement(dialect.getSequenceNextValString(sequenceName));
					rs = ps.executeQuery();
					rs.next();

					return rs.getLong(1);
				}
				finally {
					if (ps != null) {
						ps.close();
					}
					if (rs != null) {
						rs.close();
					}
				}
			}

		});

		return result;
	}

}
