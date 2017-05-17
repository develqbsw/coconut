package sk.qbsw.reporting.base.generating.concurrency.dispatcher;


public interface IReportDispatcher {
	public void dispatchExecution (String ownerSign);
	public String getQueueType();
}
