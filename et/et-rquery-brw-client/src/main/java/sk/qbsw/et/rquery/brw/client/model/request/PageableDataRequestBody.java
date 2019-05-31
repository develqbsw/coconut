package sk.qbsw.et.rquery.brw.client.model.request;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.criteria.Paging;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The pageable data request body.
 *
 * @param <F> the filterable type
 * @author Peter Bozik
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class PageableDataRequestBody<F extends Filterable>extends DataRequestBody<F>
{
	private static final long serialVersionUID = -8804636840815516951L;

	@Valid
	@NotNull
	private Paging paging = new Paging();
}
