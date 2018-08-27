package sk.qbsw.integration.message.email.repository;

import com.querydsl.core.BooleanBuilder;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AQDslDao;
import sk.qbsw.integration.message.base.model.domain.MessageStates;
import sk.qbsw.integration.message.email.model.domain.Email;
import sk.qbsw.integration.message.email.model.domain.QEmail;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * The email repository implementation.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class EmailRepositoryImpl extends AQDslDao<Long, Email> implements EmailRepositoryExtension
{
	@Override
	public List<Email> findByCountAndMaxAttemptCount (long count, int maxAttemptCount)
	{
		QEmail qEmail = QEmail.email;

		BooleanBuilder predicate = new BooleanBuilder();
		predicate.and(qEmail.currentState.state.in(Arrays.asList(MessageStates.NEW, MessageStates.NETWORK_ERROR)));
		predicate.and(qEmail.created.before(OffsetDateTime.now()));
		predicate.and(qEmail.sendAttemptCount.lt(maxAttemptCount));

		return queryFactory.selectFrom(qEmail) //
			.leftJoin(qEmail.attachments).fetchJoin() //
			.leftJoin(qEmail.states).fetchJoin() //
			.leftJoin(qEmail.currentState).fetchJoin() //
			.where(predicate) //
			.orderBy(qEmail.created.asc()) //
			.limit(count). //
			fetch();
	}
}
