package sk.qbsw.et.rquery.brw.client.model.request;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.criteria.SortingCriteria;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The data request body.
 *
 * @param <F> the filterable type
 * @author Peter Bozik
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class DataRequestBody<F extends Filterable>extends CountDataRequestBody<F>
{
	private static final long serialVersionUID = 7751636869920682118L;

	@Valid
	@NotNull
	private SortingCriteria<F> sortingCriteria = new SortingCriteria<>();
}
