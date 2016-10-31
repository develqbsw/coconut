package sk.qbsw.paypal.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Model for paypal payment.
 * @author rosenberg
 * @since 1.0.0
 * @version 1.0.0 
 */
public class CPayPalPaymentData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Amount 
	 */
	private BigDecimal amount;
	
	/**
	 * Additional tax amount 
	 */
	private BigDecimal taxAmount;
	
	/**
	 * payment notes
	 */
	private String notes;
	
	/**
	 * payment item identifier
	 */
	private String itemNumber;
	
	
	public BigDecimal getAmount ()
	{
		return amount;
	}

	public void setAmount (BigDecimal amount)
	{
		this.amount = amount;
	}

	public BigDecimal getTaxAmount ()
	{
		return taxAmount;
	}

	public void setTaxAmount (BigDecimal taxAmount)
	{
		this.taxAmount = taxAmount;
	}

	public String getNotes ()
	{
		return notes;
	}

	public void setNotes (String notes)
	{
		this.notes = notes;
	}

	public String getItemNumber ()
	{
		return itemNumber;
	}

	public void setItemNumber (String itemNumber)
	{
		this.itemNumber = itemNumber;
	}


}
