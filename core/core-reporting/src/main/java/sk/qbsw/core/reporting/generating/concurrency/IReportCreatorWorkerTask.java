package sk.qbsw.core.reporting.generating.concurrency;

import java.util.concurrent.CountDownLatch;

import sk.qbsw.core.reporting.generating.model.IReportRequest;

public interface IReportCreatorWorkerTask extends Runnable {
	public void configure(IReportRequest reportRequest,CountDownLatch latch);
}
