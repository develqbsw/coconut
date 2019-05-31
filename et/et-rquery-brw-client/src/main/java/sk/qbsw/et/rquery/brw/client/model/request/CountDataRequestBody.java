package sk.qbsw.et.rquery.brw.client.model.request;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.core.client.model.request.BaseRequestBody;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.criteria.FilterCriteria;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The count data request body.
 *
 * @param <F> the filterable type
 * @author Peter Bozik
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class CountDataRequestBody<F extends Filterable>extends BaseRequestBody
{
	private static final long serialVersionUID = 2184850074033502510L;

	@Valid
	@NotNull
	private FilterCriteria<F> filterCriteria = new FilterCriteria<>();
}
