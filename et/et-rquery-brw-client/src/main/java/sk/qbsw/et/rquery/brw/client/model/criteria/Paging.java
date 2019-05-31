package sk.qbsw.et.rquery.brw.client.model.criteria;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The paging - empty object has range from 0 to Integet.MAX_VALUE.
 *
 * @author podmajersky
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class Paging implements Serializable
{
	private static final long serialVersionUID = 4778551662700950445L;

	@NotNull
	private Integer offset = 0;

	@NotNull
	private Integer limit = Integer.MAX_VALUE;
}
