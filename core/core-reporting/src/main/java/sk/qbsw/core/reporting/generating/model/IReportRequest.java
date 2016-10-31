package sk.qbsw.core.reporting.generating.model;

public interface IReportRequest
{

	public String getIdentificator();
	/**
	 * Returns class path of the report creator. 
	 * @return String
	 */
	public String getReportCreatorClassName();
}
