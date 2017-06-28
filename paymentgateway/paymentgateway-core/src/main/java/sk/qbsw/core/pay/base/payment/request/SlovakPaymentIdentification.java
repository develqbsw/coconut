package sk.qbsw.core.pay.base.payment.request;

import java.awt.image.AreaAveragingScaleFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import sk.qbsw.core.pay.base.util.PaymentFormatUtils;

/**
 * Identifikacia platby na zaklade  VS,SS,KS, udajov 
 * @author martinkovic
 *
 */
public class SlovakPaymentIdentification implements PaymentIdentification
{
	private String vs;
	private String ss;
	private String ks;

	public SlovakPaymentIdentification (Long vs, Long ss, Long ks)
	{
		super();
		//must be max 10 digits
		Validate.isTrue(Long.toString(vs).length()<=10, "VS moze byt maximalne 10 znakov");
		Validate.isTrue(Long.toString(ss).length()<=10, "SS moze byt maximalne 10 znakov");		
		Validate.isTrue(Long.toString(ks).length()<=4,  "ks moze byt maximalne 4 znaky");
		
		
		this.vs = PaymentFormatUtils.formatVS(vs);
		this.ss = PaymentFormatUtils.formatSS(ss);
		this.ks = PaymentFormatUtils.formatKS(ks);

	}

	public String getVs ()
	{
		return vs;
	}

	public String getSs ()
	{
		return ss;
	}

	public String getKs ()
	{
		return ks;
	}

	public SlovakPaymentIdentification ()
	{
		super();
	}



	@Override
	public String getPaymentId ()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/VS");
		stringBuilder.append(vs);
		stringBuilder.append("/SS");
		stringBuilder.append(ss);
		stringBuilder.append("/KS");
		stringBuilder.append(ks);
		String payId = stringBuilder.toString();
		return payId;
	}



}
