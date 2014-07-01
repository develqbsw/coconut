package sk.qbsw.core.reporting.test.transform;

import java.math.BigDecimal;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.core.reporting.transform.exception.CNotSupportedAmountException;
import sk.qbsw.core.reporting.transform.model.CTransformedAmount;
import sk.qbsw.core.reporting.transform.service.IAmountTransformer;

/**
 * The amount transforming test.
 *
 * @autor Tomas Lauro
 *
 * @version 1.10.2
 * @since 1.10.2
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
public class CAmountTransformTest
{
	/** The amount transformers. */
	@Autowired
	@Qualifier ("transformingStrategy")
	private Map<String, IAmountTransformer> amountTransformers;

	/** The amount transformer. */
	@Autowired
	@Qualifier ("sk")
	private IAmountTransformer amountTransformer;

	/**
	 * Test billions.
	 */
	@Test
	public void testBillions () throws CNotSupportedAmountException
	{
		CTransformedAmount<String, Long> amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000000000"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000000001"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000000002"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000000050"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.FIFTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000000100"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000001"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000002"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000045"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.FOURTY.toString() + ESlovakIntegerPartTest.FIVE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000100"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000101"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000102"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000654"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.SIX.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.FOUR.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001001000"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THOUSAND.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001001001"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001001002"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001002002"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.TWO_FEMININUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001321002"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1002321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.TWO_MASKULINUM.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1003321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1004321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.FOUR.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1963321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.BILLION.toString() + " " + ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.SIXTY.toString() + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("2963321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWO_FEMININUM.toString() + " " + ESlovakIntegerPartTest.BILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.SIXTY.toString() + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("3963321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.BILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.SIXTY.toString() + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("4963321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.FOUR.toString() + " " + ESlovakIntegerPartTest.BILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.SIXTY.toString() + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("547963321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.FIVE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FOURTY.toString() + ESlovakIntegerPartTest.SEVEN.toString() + " " + ESlovakIntegerPartTest.BILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.SIXTY.toString() + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());
	}

	/**
	 * Test millions.
	 */
	@Test
	public void testMillions () throws CNotSupportedAmountException
	{
		CTransformedAmount<String, Long> amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000000"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000001"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000002"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000045"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.FOURTY.toString() + ESlovakIntegerPartTest.FIVE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000100"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000101"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000102"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000654"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.SIX.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.FOUR.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001000"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THOUSAND.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001001"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001002"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1002002"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.TWO_FEMININUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1321002"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.MILLION.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("2321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWO_MASKULINUM.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("3321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("4321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.FOUR.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("963321456"));
		Assert.assertEquals(ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.SIXTY.toString() + ESlovakIntegerPartTest.THREE.toString() + " " + ESlovakIntegerPartTest.MILLION_PLURAL_2.toString() + " " + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.FOUR.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());
	}

	/**
	 * Test thousands.
	 */
	@Test
	public void testThousands () throws CNotSupportedAmountException
	{
		CTransformedAmount<String, Long> amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1000"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1001"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1002"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1010"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1021"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1022"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1100"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.HUNDRED.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1101"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1102"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1153"));
		Assert.assertEquals(ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.FIFTY.toString() + ESlovakIntegerPartTest.THREE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("10000"));
		Assert.assertEquals(ESlovakIntegerPartTest.TEN.toString() + ESlovakIntegerPartTest.THOUSAND.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("900000"));
		Assert.assertEquals(ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.THOUSAND.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("987321"));
		Assert.assertEquals(ESlovakIntegerPartTest.NINE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.EIGHTY.toString() + ESlovakIntegerPartTest.SEVEN.toString() + ESlovakIntegerPartTest.THOUSAND.toString() + ESlovakIntegerPartTest.THREE.toString() + ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());
	}

	/**
	 * Test hundreds.
	 */
	@Test
	public void testHundreds () throws CNotSupportedAmountException
	{
		CTransformedAmount<String, Long> amount = amountTransformer.transformToStringLongFormat(new BigDecimal("100"));
		Assert.assertEquals(ESlovakIntegerPartTest.HUNDRED.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("101"));
		Assert.assertEquals(ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("102"));
		Assert.assertEquals(ESlovakIntegerPartTest.HUNDRED.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());
	}

	/**
	 * Test tens.
	 */
	@Test
	public void testTens () throws CNotSupportedAmountException
	{
		CTransformedAmount<String, Long> amount = amountTransformer.transformToStringLongFormat(new BigDecimal("0"));
		Assert.assertEquals(ESlovakIntegerPartTest.ZERO.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("1"));
		Assert.assertEquals(ESlovakIntegerPartTest.ONE_NEUTRUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("2"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWO_NEUTRUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("3"));
		Assert.assertEquals(ESlovakIntegerPartTest.THREE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("4"));
		Assert.assertEquals(ESlovakIntegerPartTest.FOUR.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("5"));
		Assert.assertEquals(ESlovakIntegerPartTest.FIVE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("6"));
		Assert.assertEquals(ESlovakIntegerPartTest.SIX.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("7"));
		Assert.assertEquals(ESlovakIntegerPartTest.SEVEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("8"));
		Assert.assertEquals(ESlovakIntegerPartTest.EIGHT.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("9"));
		Assert.assertEquals(ESlovakIntegerPartTest.NINE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("10"));
		Assert.assertEquals(ESlovakIntegerPartTest.TEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("11"));
		Assert.assertEquals(ESlovakIntegerPartTest.ELEVEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("12"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWELVE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("13"));
		Assert.assertEquals(ESlovakIntegerPartTest.THIRTEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("14"));
		Assert.assertEquals(ESlovakIntegerPartTest.FOURTEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("15"));
		Assert.assertEquals(ESlovakIntegerPartTest.FIFTEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("16"));
		Assert.assertEquals(ESlovakIntegerPartTest.SIXTEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("17"));
		Assert.assertEquals(ESlovakIntegerPartTest.SEVENTEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("18"));
		Assert.assertEquals(ESlovakIntegerPartTest.EIGHTTEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("19"));
		Assert.assertEquals(ESlovakIntegerPartTest.NINETEEN.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("20"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWENTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("21"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.ONE_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("22"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.TWO_MASKULINUM.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("23"));
		Assert.assertEquals(ESlovakIntegerPartTest.TWENTY.toString() + ESlovakIntegerPartTest.THREE.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("30"));
		Assert.assertEquals(ESlovakIntegerPartTest.THIRTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("40"));
		Assert.assertEquals(ESlovakIntegerPartTest.FOURTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("50"));
		Assert.assertEquals(ESlovakIntegerPartTest.FIFTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("60"));
		Assert.assertEquals(ESlovakIntegerPartTest.SIXTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("70"));
		Assert.assertEquals(ESlovakIntegerPartTest.SEVENTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("80"));
		Assert.assertEquals(ESlovakIntegerPartTest.EIGHTY.toString(), amount.getIntegerPart());

		amount = amountTransformer.transformToStringLongFormat(new BigDecimal("90"));
		Assert.assertEquals(ESlovakIntegerPartTest.NINETY.toString(), amount.getIntegerPart());
	}
}
