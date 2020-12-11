package sk.qbsw.core.persistence.dao.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * The fetch capable querydsl repository factory.
 *
 * @param <R> the type parameter
 * @param <T> the type parameter
 * @param <I> the type parameter
 * @author Adrian Lopez (http://stackoverflow.com/a/21630123)
 * @author Tomas Lauro
 * @version 2.6.0
 * @since 1.16.0
 */
public class CFetchCapableQueryDslJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>extends JpaRepositoryFactoryBean<R, T, I>
{
	/**
	 * Instantiates a new C fetch capable query dsl jpa repository factory bean.
	 *
	 * @param repositoryInterface the repository interface
	 */
	public CFetchCapableQueryDslJpaRepositoryFactoryBean (Class<? extends R> repositoryInterface)
	{
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory (EntityManager entityManager)
	{
		return new CFetchCapableQueryDslJpaRepositoryFactory<T, I>(entityManager);
	}

	/**
	 * A factory for creating CFetchCapableQueryDslJpaRepository objects.
	 */
	private static class CFetchCapableQueryDslJpaRepositoryFactory<T, I extends Serializable>extends JpaRepositoryFactory
	{
		/**
		 * Instantiates a new c fetch capable query dsl jpa repository factory.
		 *
		 * @param entityManager the entity manager
		 */
		public CFetchCapableQueryDslJpaRepositoryFactory (EntityManager entityManager)
		{
			super(entityManager);
		}

		@Override
		@SuppressWarnings ({"unchecked", "rawtypes"})
		protected JpaRepositoryImplementation<T, I> getTargetRepository (RepositoryInformation information, EntityManager entityManager)
		{
			return new CFetchCapableQueryDslJpaRepository(getEntityInformation(information.getDomainType()), entityManager);
		}

		@Override
		protected Class<?> getRepositoryBaseClass (RepositoryMetadata metadata)
		{
			return CFetchCapableQueryDslJpaRepository.class;
		}
	}
}
