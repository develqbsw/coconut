package sk.qbsw.et.browser.core.dao.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * The fetch capable querydsl repository factory.
 *
 * @author Adrian Lopez (http://stackoverflow.com/a/21630123)
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CFetchCapableQueryDslJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>extends JpaRepositoryFactoryBean<R, T, I>
{
	/* (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean#createRepositoryFactory(javax.persistence.EntityManager)
	 */
	protected RepositoryFactorySupport createRepositoryFactory (EntityManager entityManager)
	{
		return new CFetchCapableQueryDslJpaRepositoryFactory<T, I>(entityManager);
	}

	/**
	 * A factory for creating CFetchCapableQueryDslJpaRepository objects.
	 */
	private static class CFetchCapableQueryDslJpaRepositoryFactory<T, I extends Serializable>extends JpaRepositoryFactory
	{
		/** The entity manager. */
		private EntityManager entityManager;

		/**
		 * Instantiates a new c fetch capable query dsl jpa repository factory.
		 *
		 * @param entityManager the entity manager
		 */
		public CFetchCapableQueryDslJpaRepositoryFactory (EntityManager entityManager)
		{
			super(entityManager);
			this.entityManager = entityManager;
		}

		/* (non-Javadoc)
		 * @see org.springframework.data.jpa.repository.support.JpaRepositoryFactory#getTargetRepository(org.springframework.data.repository.core.RepositoryInformation)
		 */
		@Override
		protected Object getTargetRepository (RepositoryInformation information)
		{
			return new CFetchCapableQueryDslJpaRepository<>(getEntityInformation(information.getDomainType()), entityManager);
		}

		/* (non-Javadoc)
		 * @see org.springframework.data.jpa.repository.support.JpaRepositoryFactory#getRepositoryBaseClass(org.springframework.data.repository.core.RepositoryMetadata)
		 */
		@Override
		protected Class<?> getRepositoryBaseClass (RepositoryMetadata metadata)
		{
			return CFetchCapableQueryDslJpaRepository.class;
		}
	}
}
