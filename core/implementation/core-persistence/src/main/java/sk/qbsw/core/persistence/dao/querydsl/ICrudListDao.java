package sk.qbsw.core.persistence.dao.querydsl;

import java.util.List;

import sk.qbsw.core.persistence.model.CFilterParameter;
import sk.qbsw.core.persistence.model.COrderParameter;
import sk.qbsw.core.persistence.model.domain.IEntity;

public interface ICrudListDao<PK, T extends IEntity<PK>> {

	public List<T> getCRUDList(final List<? extends CFilterParameter> wheres, List<COrderParameter> orderSpecifiers, Long from, Long count);

	public long getCRUDCount(final List<? extends CFilterParameter> wheres);
}
