package sk.qbsw.core.base.service;

import sk.qbsw.core.base.logging.annotation.CAuditLogged;
import sk.qbsw.core.base.logging.annotation.CLogged;


/**
 * High level of service
 * Annotated with annotations which are ready for logging and audit logging
 * 
 * @author Michal Lacko
 * @version 1.0.0
 * @since 1.11.6
 * 
 */
@CLogged
@CAuditLogged(actionName = "coconutServiceAction")
public abstract class CService
{

}
