package sk.qbsw.reporting.base.generating.concurrency;

import java.util.concurrent.CountDownLatch;

import sk.qbsw.reporting.base.generating.model.IReportRequest;

public interface IReportCreatorWorkerTask extends Runnable {
	public void configure(IReportRequest reportRequest,CountDownLatch latch);
}
