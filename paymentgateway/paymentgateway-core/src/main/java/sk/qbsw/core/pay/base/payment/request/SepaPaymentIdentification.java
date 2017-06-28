package sk.qbsw.core.pay.base.payment.request;

/**
 * identifikacia platby na zaklade 35 znakoveho retazca. 
 * @author martinkovic
 *
 */
public class SepaPaymentIdentification implements PaymentIdentification
{


	//max 35 ASCII characters
	//povolené znaky: 0-9 A-Z
	//a-z . - / a medzera
	private String reference;

	public SepaPaymentIdentification ()
	{
		super();
	}

	public SepaPaymentIdentification (String reference)
	{
		super();
		this.reference = reference;
	}

	public String getReference ()
	{
		return reference;
	}

	public void setReference (String reference)
	{
		if (reference.length() > 35)
		{
			throw new IllegalArgumentException("only 35 character string is allowed");
		}
		this.reference = reference;
	}

	@Override
	public String getPaymentId ()
	{
		return getReference();
	}

}
