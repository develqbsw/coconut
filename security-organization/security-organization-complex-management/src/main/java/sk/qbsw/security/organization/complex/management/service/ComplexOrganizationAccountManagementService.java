package sk.qbsw.security.organization.complex.management.service;

import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountInputData;

/**
 * The complex organization account management service.
 *
 * @param <I> the complex organization account input data type
 * @param <O> the complex organization account output type
 * @author Dalibor Rak
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public interface ComplexOrganizationAccountManagementService<I extends ComplexOrganizationAccountInputData, O extends ComplexOrganizationAccountData>extends AccountManagementService<I, O>
{
}
