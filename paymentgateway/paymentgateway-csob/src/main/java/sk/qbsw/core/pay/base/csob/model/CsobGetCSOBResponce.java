/**
 * 
 */
package sk.qbsw.core.pay.base.csob.model;

/**
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class CsobGetCSOBResponce
{
	public static final String STATUS_OK="OK";

	private String status;
	private String splatnost;
	private String vs;
	private String of;
	private String castka;

	/**
	 * @return the status
	 */
	public String getStatus ()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus (String status)
	{
		this.status = status;
	}

	/**
	 * @return the splatnost
	 */
	public String getSplatnost ()
	{
		return splatnost;
	}

	/**
	 * @param splatnost the splatnost to set
	 */
	public void setSplatnost (String splatnost)
	{
		this.splatnost = splatnost;
	}

	/**
	 * @return the vs
	 */
	public String getVs ()
	{
		return vs;
	}

	/**
	 * @param vs the vs to set
	 */
	public void setVs (String vs)
	{
		this.vs = vs;
	}

	/**
	 * @return the of
	 */
	public String getOf ()
	{
		return of;
	}

	/**
	 * @param of the of to set
	 */
	public void setOf (String of)
	{
		this.of = of;
	}

	/**
	 * @return the castka
	 */
	public String getCastka ()
	{
		return castka;
	}

	/**
	 * @param castka the castka to set
	 */
	public void setCastka (String castka)
	{
		this.castka = castka;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{
		return "CsobGetCSOBResponce [status=" + status + ", splatnost=" + splatnost + ", vs=" + vs + ", of=" + of + ", castka=" + castka + "]";
	}

}
