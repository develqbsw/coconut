/**
 * 
 */
package sk.qbsw.core.pay.base;

import java.awt.image.AreaAveragingScaleFilter;
import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;

import sk.qbsw.core.pay.base.payment.request.PaymentAmount;
import sk.qbsw.core.pay.base.payment.request.PaymentIdentification;
import sk.qbsw.core.pay.base.payment.request.PaymentInfo;

/** 
 * base interface for payment information, witch are used to realize payment 
 * toto sluzi ako 
 *  -predpis pri vytvarani platby
 *  -reprezentacia platby v systeme. 
 * @author martinkovic
 * @version 1.15.0
 * @since 1.15.0 
 *
 */
public class PaymentRequestImpl implements PaymentRequest
{
	PaymentIdentification identification;
	PaymentAmount amount;
	PaymentInfo info;

	@Override
	public PaymentIdentification getIdentification ()
	{
		return identification;
	}

	public void setIdentification (PaymentIdentification identification)
	{
		this.identification = identification;
	}

	@Override
	public PaymentAmount getAmount ()
	{
		return amount;
	}

	public void setAmount (PaymentAmount amount)
	{
		this.amount = amount;
	}

	@Override
	public PaymentInfo getInfo ()
	{
		return info;
	}

	public void setInfo (PaymentInfo info)
	{
		this.info = info;
	}



}
