package sk.qbsw.core.browser.dto;

import java.util.List;

import sk.qbsw.core.base.dto.IDTO;
import sk.qbsw.core.persistence.model.domain.IEntity;

/**
 * The Class CBRWDataDTO.
 *
 * @param <PK> the generic type
 * @param <T> the generic type
 */
@SuppressWarnings("serial")
public class CBRWDataDTO<PK, T extends IEntity<PK>> implements IDTO {

	/** The data. */
	private final List<T> data;
	
	/** The total count. */
	private final Long totalCount;

	/**
	 * Instantiates a new CBRW data dto.
	 *
	 * @param data the data
	 * @param totalCount the total count
	 */
	public CBRWDataDTO (List<T> data, Long totalCount) {
		this.data = data;
		this.totalCount = totalCount;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<T> getData () {
		return this.data;
	}

	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	public Long getTotalCount () {
		return this.totalCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return "CBRWDataDTO [totalCount=" + this.totalCount + ", data=" + this.data + "]";
	}
}
