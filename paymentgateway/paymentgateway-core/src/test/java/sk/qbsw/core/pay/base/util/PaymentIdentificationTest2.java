package sk.qbsw.core.pay.base.util;

import org.junit.Assert;
import org.junit.Test;

import sk.qbsw.core.pay.base.payment.request.PaymentIdentification;
import sk.qbsw.core.pay.base.payment.request.SlovakPaymentIdentification;

/**
 * 
 * @author martinkovic
 *
 */
public class PaymentIdentificationTest2
{

	@Test
	public void testFormat()
	{
		PaymentIdentification identification = new SlovakPaymentIdentification(10000L,2000L,366L);
		String id = identification.getPaymentId();
		Assert.assertEquals("/VS0000010000/SS0000002000/KS0366", id);

	}
	@Test
	public void testFormat2()
	{
		PaymentIdentification identification = new SlovakPaymentIdentification(10000L,2000L,366L);
		String id = identification.getPaymentId();
		Assert.assertEquals("/VS0000010000/SS0000002000/KS0366", id);
	}

}
