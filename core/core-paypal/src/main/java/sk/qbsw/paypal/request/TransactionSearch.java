/*
 * Copyright (C) 2010 Pete Reisinger <p.reisinger@gmail.com>. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package sk.qbsw.paypal.request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sk.qbsw.paypal.core.NVPResponse;
import sk.qbsw.paypal.fields.Currency;
import sk.qbsw.paypal.fields.PayerName;
import sk.qbsw.paypal.util.FormatFields;
import sk.qbsw.paypal.util.Validator;

/**
 * Instance is used for TransactionSearch request. It searches transaction
 * history for transactions that meet the specified criteria.
 *
 * @author Pete Reisinger <p.reisinger@gmail.com>.
 * @author Dalibor Rak
 */
public final class TransactionSearch implements Request
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Classification of transaction.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum TranscationClass
	{

		/** all transaction classifications. */
		ALL,

		/** only payments sent. */
		SENT,

		/** only payments received. */
		RECEIVED,

		/** only mass payments. */
		MASSPAY,

		/** only money requests. */
		MONEYREQUEST,

		/** only funds added to balance. */
		FUNDSADDED,

		/** only funds withdrawn from balance. */
		FUNDSWITHDRAWN,

		/** only transactions involving referrals. */
		REFERRAL,

		/** only transactions involving fees. */
		FEE,

		/** only transactions involving subscriptions. */
		SUBSCRIPTION,

		/** only transactions involving dividends. */
		DIVIDEND,

		/** only transactions involving BillPay Transactions. */
		BILLPAY,

		/** only transactions involving funds. */
		REFUND,

		/** only transactions involving currency conversions. */
		CURRENCYCONVERSIONS,

		/** only transactions involving balance transfers. */
		BALANCETRANSFER,

		/** only transactions involving BillPay reversals. */
		REVERSAL,

		/** only transactions involving UPS shipping fees. */
		SHIPPING,

		/** only transactions that affect the account balance. */
		BALANCEAFFECTING,

		/** only transactions involving eCheck. */
		ECHECK;
	}

	/**
	 * Transaction status.
	 *
	 * @author Dalibor Rak
	 * @version 1.8
	 * @since 1.8
	 */
	public enum TransactionStatus
	{

		/** 
		 * The payment is pending. The specific reason the payment is pending 
		 * is returned by the GetTransactionDetails API PendingReason field.
		 */
		PENDING,

		/** The payment is being processed. */
		Processing,

		/** 
		 * The payment has been completed and the funds have been added 
		 * successfully to your account balance. 
		 */
		Success,

		/** 
		 * You denied the payment. This happens only if the payment was 
		 * previously pending.
		 */
		Denied,

		/** 
		 * A payment was reversed due to a chargeback or other type of 
		 * reversal. The funds have been removed from your account balance and 
		 * returned to the buyer. 
		 */
		Reversed;
	}

	/** Method value of this request. */
	private static final String METHOD_NAME = "TransactionSearch";

	/** map that holds name value pair request values. */
	private final Map<String, String> nvpRequest;

	/** map that holds name value pair response values. */
	private NVPResponse nvpResponse;

	/**
	 * Instantiates a new transaction search.
	 *
	 * @param startDate the start date
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public TransactionSearch (Date startDate) throws IllegalArgumentException
	{

		/*  paypal needs  Coordinated Universal Time (UTC/GMT), using ISO 8601
		    format, and of type ns:dateTime for Date/Time formats */
		String date = FormatFields.getDateTimeField(startDate);

		nvpResponse = new NVPResponse();
		nvpRequest = new HashMap<String, String>();

		nvpRequest.put("METHOD", METHOD_NAME);
		nvpRequest.put("STARTDATE", date);
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate The latest transaction date to be included in the search
	 */
	public void setEndDate (Date endDate)
	{

		String date = FormatFields.getDateTimeField(endDate);
		nvpRequest.put("ENDDATE", date);
	}

	/**
	 * Search by the buyer's email address. Email argument has to be valid
	 * email and less than 127 characters, otherwise exception is thrown.
	 *
	 * @param email maximum 127 single-byte alphanumeric characters.
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setEmail (String email) throws IllegalArgumentException
	{

		if (!Validator.isValidEmail(email))
		{
			throw new IllegalArgumentException("Email is not valid");
		}

		if (email.length() > 127)
		{
			throw new IllegalArgumentException("Email cannot be longer than " + "127 characters.");
		}
		nvpRequest.put("EMAIL", email);
	}

	/**
	 * Search by the receiver's email address. If the merchant account has only
	 * one email, this is the primary email. Can also be a non-primary email.
	 * Email has to be valid, otherwise exception is thrown.
	 *
	 * @param email the new receiver email
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setReceiverEmail (String email) throws IllegalArgumentException
	{

		if (!Validator.isValidEmail(email))
		{
			throw new IllegalArgumentException("Email is not valid");
		}
		nvpRequest.put("RECEIVER", email);
	}

	/**
	 * Search by the PayPal Account Optional receipt ID.
	 *
	 * @param id the new receipt id
	 */
	public void setReceiptId (String id)
	{
		nvpRequest.put("RECEIPTID", id);
	}

	/**
	 * Search by the transaction ID. The returned results are from the
	 * merchant's transaction records.
	 * 
	 * Character length and limitations: 19 single-byte characters maximum.
	 *
	 * @param id the new transaction id
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setTransactionId (String id) throws IllegalArgumentException
	{

		if (id != null && id.length() < 19)
		{
			throw new IllegalArgumentException("Id can be max 19 characters " + "long");
		}
		nvpRequest.put("TRANSACTIONID", id);
	}

	/**
	 * Search by invoice identification key, as set by you for the original
	 * transaction. This field searches the records for items sold by the
	 * merchant, not the items purchased.
	 * 
	 * Note: No wildcards are allowed.
	 * 
	 * Character length and limitations: 127 single-byte characters maximum.
	 *
	 * @param id the new invoice id
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setInvoiceId (String id) throws IllegalArgumentException
	{

		if (id != null && id.length() < 127)
		{
			throw new IllegalArgumentException("Id can be max 127 characters " + "long");
		}
		nvpRequest.put("INVNUM", id);
	}

	/**
	 * Search by credit card number, as set by you for the original
	 * transaction. This field searches the records for items sold by the
	 * merchant, not the items purchased.
	 * 
	 * Note: No wildcards are allowed.
	 * 
	 * Character length and limitations: Must be at least 11 and no more than
	 * 25 single-byte numeric characters maximum. Special punctuation, such as
	 * dashes or spaces, is ignored.
	 *
	 * @param acct the new credit card number
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setCreditCardNumber (String acct) throws IllegalArgumentException
	{

		if (acct != null && (acct.length() > 10 && acct.length() < 25))
		{
			throw new IllegalArgumentException("Acct can be min 11 characters " + " and max 25 characters long");
		}
		nvpRequest.put("ACCT", acct);
	}

	/**
	 * Search by auction item number of the purchased goods.
	 *
	 * @param item the new auction item number
	 */
	public void setAuctionItemNumber (String item)
	{
		nvpRequest.put("AUCTIONITEMNUMBER", item);
	}

	/**
	 * Search by classification of transaction. Some kinds of possible classes
	 * of transactions are not searchable with this field. You cannot search
	 * for bank transfer withdrawals, for example.
	 *
	 * @param transactionClass the new transaction
	 */
	public void setTransaction (TranscationClass transactionClass)
	{
		nvpRequest.put("TRANSACTIONCLASS", transactionClass.toString());
	}

	/**
	 * Search by transaction amount.
	 *
	 * @param amount the new amount
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void setAmount (String amount) throws IllegalArgumentException
	{

		if (!Validator.isValidAmount(amount))
		{
			throw new IllegalArgumentException("Amount " + amount + " is not valid. Amount has to have exactly two decimal " + "places seaprated by \".\" - example: \"50.00\"");
		}
		nvpRequest.put("AMT", amount);
	}

	/**
	 * Search by currency code.
	 *
	 * @param currency the new currency
	 */
	public void setCurrency (Currency currency)
	{
		nvpRequest.put("CURRENCYCODE", currency.toString());
	}

	/**
	 * Search by transaction status.
	 *
	 * @param status the new status
	 */
	public void setStatus (TransactionStatus status)
	{
		nvpRequest.put("STATUS", status.toString());
	}

	/**
	 * Set payer names fields.
	 *
	 * @param payerName the new payer name
	 */
	public void setPayerName (PayerName payerName)
	{
		nvpRequest.putAll(payerName.getNVPRequest());
	}

	/** 
	 * @see sk.qbsw.paypal.request.Request#getNVPRequest()
	 */
	public Map<String, String> getNVPRequest ()
	{
		return new HashMap<String, String>(nvpRequest);
	}

	/** 
	 * @see sk.qbsw.paypal.request.Request#setNVPResponse(sk.qbsw.paypal.core.NVPResponse)
	 */
	public void setNVPResponse (NVPResponse nvpResponse)
	{
		this.nvpResponse = new NVPResponse(nvpResponse);
	}

	/** 
	 * @see sk.qbsw.paypal.request.Request#getNVPResponse()
	 */
	public NVPResponse getNVPResponse ()
	{
		return new NVPResponse(nvpResponse);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString ()
	{

		StringBuffer str = new StringBuffer("instance of TransactionSearch ");
		str.append("class with the vlues: nvpRequest - ");
		str.append(nvpRequest.toString());
		str.append("; nvpResponse - ");
		str.append(nvpResponse.toString());

		return str.toString();
	}
}
