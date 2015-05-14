package sk.qbsw.core.persistence.model.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * Base entity implementation
 *
 * @param <PK> the primary key type
 */
@Access(AccessType.FIELD)
@MappedSuperclass
public abstract class AEntity<PK> implements IEntity<PK> {
}
