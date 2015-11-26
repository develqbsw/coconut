package sk.qbsw.core.reporting.generating.concurrency.dispatcher;


public interface IReportDispatcher {
	public void dispatchExecution (String ownerSign);
	public String getQueueType();
}
