package sk.qbsw.reporting.base.generating.concurrency.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;

/**
 * Abstract common report creator task
 * @author Peter Bozik
 *
 */
@CNotAuditLogged
public abstract class AReportCreatorTask{
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
}
