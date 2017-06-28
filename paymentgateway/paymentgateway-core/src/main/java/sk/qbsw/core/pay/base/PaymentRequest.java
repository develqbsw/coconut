/**
 * 
 */
package sk.qbsw.core.pay.base;

import java.math.BigDecimal;

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
public interface PaymentRequest
{
	PaymentIdentification getIdentification();
	PaymentAmount getAmount();
	PaymentInfo getInfo();

}
