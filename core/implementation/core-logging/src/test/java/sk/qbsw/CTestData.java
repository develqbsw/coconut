package sk.qbsw;

import org.joda.time.DateTime;

/**
 * Test data to store in the log
 * @author Michal Lacko
 *
 */
class CTestData
{
	private String attributeString;
	private DateTime attributeDateTime;
	private double attributeSmallDouble;
	private Double atributeObjectDouble;
	private byte[] attributeBytes;

	/**
	 * @return the attributeString
	 */
	public String getAttributeString ()
	{
		return attributeString;
	}

	/**
	 * @param attributeString the attributeString to set
	 */
	public void setAttributeString (String attributeString)
	{
		this.attributeString = attributeString;
	}

	/**
	 * @return the attributeDateTime
	 */
	public DateTime getAttributeDateTime ()
	{
		return attributeDateTime;
	}

	/**
	 * @param attributeDateTime the attributeDateTime to set
	 */
	public void setAttributeDateTime (DateTime attributeDateTime)
	{
		this.attributeDateTime = attributeDateTime;
	}

	/**
	 * @return the attributeSmallDouble
	 */
	public double getAttributeSmallDouble ()
	{
		return attributeSmallDouble;
	}

	/**
	 * @param attributeSmallDouble the attributeSmallDouble to set
	 */
	public void setAttributeSmallDouble (double attributeSmallDouble)
	{
		this.attributeSmallDouble = attributeSmallDouble;
	}

	/**
	 * @return the atributeObjectDouble
	 */
	public Double getAtributeObjectDouble ()
	{
		return atributeObjectDouble;
	}

	/**
	 * @param atributeObjectDouble the atributeObjectDouble to set
	 */
	public void setAtributeObjectDouble (Double atributeObjectDouble)
	{
		this.atributeObjectDouble = atributeObjectDouble;
	}

	/**
	 * @return the attributeBytes
	 */
	public byte[] getAttributeBytes ()
	{
		return attributeBytes;
	}

	/**
	 * @param attributeBytes the attributeBytes to set
	 */
	public void setAttributeBytes (byte[] attributeBytes)
	{
		this.attributeBytes = attributeBytes;
	}


}