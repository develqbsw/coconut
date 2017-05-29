package sk.qbsw.core.pay.base.csob.model;

import java.util.HashMap;

import sk.qbsw.core.pay.base.response.BankResponse;

/**
 * The Class SporoPayRequest.
 *
 * @author martinkovic
 * @version 0.6.0
 * @since 0.6.0
 */
public class CsobBankResponse extends BankResponse
{

	/**
	 * @param params
	 */
	public CsobBankResponse ()
	{
		super(new HashMap<String, String>());
	}

	public CsobMessageToVOD getMessageToVOD ()
	{
		return messageToVOD;
	}

	public void setMessageToVOD (CsobMessageToVOD messageToVOD)
	{
		this.messageToVOD = messageToVOD;
	}

	private CsobMessageToVOD messageToVOD;


}
