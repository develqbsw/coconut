package sk.qbsw.core.pay.tatrapay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import sk.qbsw.core.pay.tatrapay.model.CTBPayRequest;
import sk.qbsw.core.pay.tatrapay.model.CTBPayResponse;

public class TatrapayPaymentProcessorTest
{

	@Before
	public void setUp () throws Exception
	{
	}

	@Test
	//	@Ignore
	public void testComputeHmac () throws DecoderException
	{

		//		podla https://moja.tatrabanka.sk/cgi-bin/e-commerce/start/example
		CTBPayRequest pay = new CTBPayRequest();
		pay.currency().guiLang("sk");
		pay.redirectTimeout(false);
		pay.timestamp("01092014125505");
		pay.redirectURL("https://moja.tatrabanka.sk/cgi-bin/e-commerce/start/example.jsp");
		pay.merchantId("9999");
		pay.vs("1111");
		pay.amount(new BigDecimal("1234.50"));
		pay.ks("0308");
		//compute hmac

		byte[] key = Hex.decodeHex("31323334353637383930313233343536373839303132333435363738393031323132333435363738393031323334353637383930313233343536373839303132".toCharArray());
		String hmac = pay.computeHMAC(key);
		System.out.println("hmac = " + hmac);

		assertEquals("8a91ea4e87c3db6c695051c5ac72498c54099114fc83554fdfabb784b485eea4", hmac);

	}

	@Test
	public void testVerifyECdsa () throws DecoderException
	{

		//		podla https://moja.tatrabanka.sk/cgi-bin/e-commerce/start/example
		HashMap<String, String> map = new HashMap<>();
		map.put("RES", "OK");
		map.put("TID", "1");
		map.put("HMAC", "ff1780ef346419d8460dd7f9dec48506524effdb6d2c9739ac44bab07a28b80f");
		map.put("ECDSA_KEY", "1");
		map.put("ECDSA", "304502210095eec9c4af68c04e2b2942b58725563ad237e47f36640abce4b511fcf971c15d022015fa889db23fe89074428dda573f3599b3f0f94fcb56119c3a631585ab9f2499");
		CTBPayResponse pay = new CTBPayResponse(map);
		pay.currency().guiLang("sk");
		pay.redirectTimeout(false);
		pay.timestamp("01092014125505");
		pay.redirectURL("https://moja.tatrabanka.sk/cgi-bin/e-commerce/start/example.jsp");
		pay.merchantId("9999");
		pay.vs("1111");
		pay.amount(new BigDecimal("1234.50"));
		pay.ks("0308");

		assertEquals("1234.5097811110308OK101092014125505", pay.getHmacString());
		//compute hmac
		byte[] key = Hex.decodeHex("31323334353637383930313233343536373839303132333435363738393031323132333435363738393031323334353637383930313233343536373839303132".toCharArray());
		String hmac = pay.computeHMAC(key);
		assertEquals("ff1780ef346419d8460dd7f9dec48506524effdb6d2c9739ac44bab07a28b80f", hmac);
		assertEquals("1234.5097811110308OK101092014125505ff1780ef346419d8460dd7f9dec48506524effdb6d2c9739ac44bab07a28b80f", pay.getEcsdaString());
		assertEquals("1", pay.ecdsaKey());
		assertEquals("304502210095eec9c4af68c04e2b2942b58725563ad237e47f36640abce4b511fcf971c15d022015fa889db23fe89074428dda573f3599b3f0f94fcb56119c3a631585ab9f2499", pay.ecdsa());

		boolean result = pay.verifyECDSA("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEozvFM1FJP4igUQ6kP8ofnY7ydIWksMDk1IKXyr/TRDoX4sTMmmdiIrpmCZD4CLDtP0j2LfD7saSIc8kZUwfILg==");

		assertTrue(result);

	}

	@Test
	public void testComputeECDSA () throws DecoderException
	{
		String request = certData();

		assertEquals("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEaq6djyzkpHdX7kt8DsSt6IuSoXjpWVlLfnZPoLaGKc/2BSfYQuFIO2hfgueQINJN3ZdujYXfUJ7Who+XkcJqHQ==", extractCert(1, request));
		assertEquals("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE+Y5mYZL/EEY9zGji+hrgGkeoyccKD0/oBoSDALHc9+LXHKsxXiEV7/h6d6+fKRDb6Wtx5cMzXT9HyY+TjPeuTg==", extractCert(2, request));
		assertEquals("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEkvgJ6sc2MM0AAFUJbVOD/i34YJJ8ineqTN+DMjpI5q7fQNPEv9y2z/ecPl8qPus8flS4iLOOxdwGoF1mU9lwfA==", extractCert(3, request));
		assertNull(extractCert(5, request));

	}

	private String certData ()
	{
		String request = "<html><head><link title=\"Zalomiť dlhé riadky\" href=\"resource://gre-resources/plaintext.css\" type=\"text/css\" rel=\"alternate stylesheet\"></head><body><pre>KEY_ID: 1\n" + "STATUS: VALID\n" + "-----BEGIN PUBLIC KEY-----\n" + "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEaq6djyzkpHdX7kt8DsSt6IuSoXjp\n" + "WVlLfnZPoLaGKc/2BSfYQuFIO2hfgueQINJN3ZdujYXfUJ7Who+XkcJqHQ==\n" + "-----END PUBLIC KEY-----\n" + "\n" + "KEY_ID: 2\n" + "STATUS: VALID\n" + "-----BEGIN PUBLIC KEY-----\n" + "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE+Y5mYZL/EEY9zGji+hrgGkeoyccK\n" + "D0/oBoSDALHc9+LXHKsxXiEV7/h6d6+fKRDb6Wtx5cMzXT9HyY+TjPeuTg==\n" + "-----END PUBLIC KEY-----\n" + "\n" + "KEY_ID: 3\n" + "STATUS: VALID\n" + "-----BEGIN PUBLIC KEY-----\n" + "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEkvgJ6sc2MM0AAFUJbVOD/i34YJJ8\n" + "ineqTN+DMjpI5q7fQNPEv9y2z/ecPl8qPus8flS4iLOOxdwGoF1mU9lwfA==\n" + "-----END PUBLIC KEY-----\n" + "</pre></body></html>";
		return request;
	}

	//sk.qbsw.dockie.core.payment.flow.CFlowTatraPayService.extractCert(int, String)
	private static String extractCert (int i, String entity)
	{
		String[] split = entity.split("KEY_ID: ");
		for (String rawBody : split)
		{
			if (rawBody.startsWith(Integer.toString(i)))
			{
				String[] parts = rawBody.split("-----");
				if (parts.length > 2)
				{
					return parts[2].replaceAll("\n", "");
				}

			}
		}
		return null;
	}


}
