package sk.qbsw.core.pay.vubeplatby;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sk.qbsw.core.pay.vubeplatby.model.CVubPayRequest;

public class VubEPlatbyPaymentProcessorTest
{

	@Before
	public void setUp () throws Exception
	{
	}

	private static final String VUBEPLATBY_B64_KEY = "36fmtvHtjyRAieZ9k+x9zldZIYhSx/vh22iaBG9n1aOd1ncFKrUCeR7gAE5Dg2D1";

	@Test
	public void testVUBPAY ()
	{

		CVubPayRequest request = new CVubPayRequest();
		String computeSign = request.computeSign(VUBEPLATBY_B64_KEY.getBytes(), "testHMAC31.40000016030801627513910308http://localhost:8080/dockie/confirmPayment/vubeplatby");

		assertEquals("A383B63884993C280E05F8E09F9E4B85A1946A6ACDAB7D02AAB000DC995BE0B5", computeSign.toUpperCase());
	}

}
