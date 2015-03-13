package sk.qbsw.core.persistence.model.domain;

import javax.persistence.Column;
import javax.persistence.Version;

/**
 * Versioned entity
 *
 * @param <PK> the primary key type
 */
public abstract class AVersionedEntity<PK> extends AEntity<PK> {
	/** The version. */
	@Version
	@Column(name = "c_version", nullable = false)
	private Long version;

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
}