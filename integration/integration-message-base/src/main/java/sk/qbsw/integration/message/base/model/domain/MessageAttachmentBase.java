package sk.qbsw.integration.message.base.model.domain;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.persistence.model.domain.AEntity;
import sk.qbsw.core.persistence.model.domain.IEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * The attachment base.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.0.0
 * @since 2.0.0
 */
@MappedSuperclass
@Getter
@Setter
public abstract class MessageAttachmentBase extends AEntity<Long> implements IEntity<Long>
{
	private static final long serialVersionUID = 2750157552458667371L;

	@NotNull
	@Column (name = "c_file_name", length = 2000)
	private String fileName;

	@NotNull
	@Column (name = "c_original_file_name", length = 2000)
	private String originalFileName;
}
