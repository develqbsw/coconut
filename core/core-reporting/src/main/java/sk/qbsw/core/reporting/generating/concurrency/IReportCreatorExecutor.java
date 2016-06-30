package sk.qbsw.core.reporting.generating.concurrency;


/**
 * Report creator executor. In every execution new reports are created
 * @author Peter Bozik
 *
 */
public interface IReportCreatorExecutor extends IExecutor{
	/**
	 * Configuration of the executor
	 * @param reportTypes
	 * @param poolSize how many threads can be alocated
	 * @param queueType 
	 */
	public void configure(String[] reportTypes, Integer poolSize, String queueType);
}
