package sk.qbsw.core.security.oauth.dao.impl;

import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.impl.JPAQuery;

import sk.qbsw.core.persistence.dao.querydsl.AEntityQueryDslDao;
import sk.qbsw.core.security.oauth.dao.ISecurityTokenDao;
import sk.qbsw.core.security.oauth.model.CSecurityToken;
import sk.qbsw.core.security.oauth.model.QCSecurityToken;

/**
 * 
 * @author podmajersky
 * @version 1.3.0
 * @since 1.3.0
 *
 */
@SuppressWarnings("serial")
@Repository
public class CSecurityTokenDao extends AEntityQueryDslDao<Long, CSecurityToken> implements ISecurityTokenDao {

	public CSecurityTokenDao () {
		super(QCSecurityToken.cSecurityToken);
	}

	@Override
	public CSecurityToken findByUserId (long userId) {
		return createQuery(QCSecurityToken.cSecurityToken).leftJoin(QCSecurityToken.cSecurityToken.user).fetch().where(QCSecurityToken.cSecurityToken.user.id.eq(userId)).singleResult(QCSecurityToken.cSecurityToken);
	}

	@Override
	public CSecurityToken findByToken (String token, String ip) {
		QCSecurityToken st = new QCSecurityToken("st");
		JPAQuery q = createQuery(st).leftJoin(st.user).fetch().where(st.token.eq(token));
		if (ip == null) {
			q.where(st.ip.isNull());
		}
		else {
			q.where(st.ip.eq(ip));
		}
		return q.singleResult(st);
	}


}
