package sk.qbsw.core.reporting.transform.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import sk.qbsw.core.reporting.transform.exception.CNotSupportedAmountException;
import sk.qbsw.core.reporting.transform.model.CTransformedAmount;

/**
* The transformer transforms the amount to {@link CTransformedAmount} object with string in Slovak language.
*
* @author Tomas Lauro
* 
* @version 1.10.2
* @since 1.10.2
*/
@Qualifier ("transformingStrategy")
@Component ("sk")
public class CSkAmountTransformer implements IAmountTransformer
{
	/* (non-Javadoc)
	 * @see sk.qbsw.core.reporting.transform.service.IAmountTransformer#transformToStringLongFormat(java.math.BigDecimal)
	 */
	@Override
	public CTransformedAmount<String, Long> transformToStringLongFormat (BigDecimal amount) throws CNotSupportedAmountException
	{
		CTransformedAmount<String, Long> transformedAmount = new CTransformedAmount<String, Long>();
		transformedAmount.setIntegerPart(transformIntegerPart(amount.longValue()));
		transformedAmount.setFractionalPart(amount.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).round(new MathContext(2, RoundingMode.HALF_UP)).longValue());

		return transformedAmount;
	}

	/**
	 * Transform integer part of the amount.
	 *
	 * @param amount the amount
	 * @return the transformed amount
	 * @throws CNotSupportedAmountException 
	 */
	private String transformIntegerPart (Long amount) throws CNotSupportedAmountException
	{
		String word = null;

		//process hundreds
		Long hundredPartOfNumber = (amount % 1000);
		if ( (amount / 1000) >= 1)
		{
			word = transformHundreds(hundredPartOfNumber.intValue(), ESlovakGender.MASKULINUM, false);
		}
		else
		{
			word = transformHundreds(hundredPartOfNumber.intValue(), ESlovakGender.NEUTRUM, true);
		}

		//process thousands
		if ( (amount / 1000) >= 1)
		{
			Long thousandPartOfNumber = ( (amount % 1000000) / 1000);
			word = transformThousands(thousandPartOfNumber.intValue()) + word;
		}
		else
		{
			return word;
		}

		//process millions
		if ( (amount / 1000000) >= 1)
		{
			Long millionPartOfNumber = ( (amount % 1000000000) / 1000000);
			word = (transformMillions(millionPartOfNumber.intValue()) + " " + word).trim();
		}
		else
		{
			return word;
		}

		//process billions
		if ( (amount / 1000000000) >= 1)
		{
			Long billionPartOfNumber = ( (amount % 1000000000000L) / 1000000000);
			word = (transformBillions(billionPartOfNumber.intValue()) + " " + word).trim();
		}
		else
		{
			return word;
		}

		//process higher number than million or result
		if ( (amount / 1000000000000L) >= 1)
		{
			throw new CNotSupportedAmountException("The given amount is not supported");
		}
		else
		{
			return word;
		}
	}

	/**
	 * Transform billions.
	 *
	 * @param billionPartOfNumber the billion part of number
	 * @return the billions part of number in words
	 */
	private String transformBillions (Integer billionPartOfNumber)
	{
		String word = null;

		if (billionPartOfNumber == 0)
		{
			word = "";
		}
		else if (billionPartOfNumber == 1)
		{
			word = ESlovakIntegerPart.BILLION.toString();
		}
		else if (billionPartOfNumber == 2 || billionPartOfNumber == 3 || billionPartOfNumber == 4)
		{
			word = transformHundreds(billionPartOfNumber, ESlovakGender.FEMININUM, false) + " " + ESlovakIntegerPart.BILLION_PLURAL.toString();
		}
		else
		{
			word = transformHundreds(billionPartOfNumber, ESlovakGender.FEMININUM, false) + " " + ESlovakIntegerPart.BILLION_PLURAL_2.toString();
		}

		return word.trim();
	}

	/**
	 * Transform millions of the amount.
	 *
	 * @param millionPartOfNumber the million part of number
	 * @return the millions part of number in words
	 */
	private String transformMillions (Integer millionPartOfNumber)
	{
		String word = null;

		if (millionPartOfNumber == 0)
		{
			word = "";
		}
		else if (millionPartOfNumber == 1)
		{
			word = ESlovakIntegerPart.MILLION.toString();
		}
		else if (millionPartOfNumber == 2 || millionPartOfNumber == 3 || millionPartOfNumber == 4)
		{
			word = transformHundreds(millionPartOfNumber, ESlovakGender.MASKULINUM, false) + " " + ESlovakIntegerPart.MILLION_PLURAL.toString();
		}
		else
		{
			word = transformHundreds(millionPartOfNumber, ESlovakGender.MASKULINUM, false) + " " + ESlovakIntegerPart.MILLION_PLURAL_2.toString();
		}

		return word.trim();
	}

	/**
	 * Transform thousands of the amount.
	 *
	 * @param thousandPartOfNumber the thousand part of number
	 * @return the thousands part of number in words
	 */
	private String transformThousands (Integer thousandPartOfNumber)
	{
		String word = null;

		if (thousandPartOfNumber == 0)
		{
			word = "";
		}
		else if (thousandPartOfNumber == 1)
		{
			word = ESlovakIntegerPart.THOUSAND.toString();
		}
		else
		{
			word = transformHundreds(thousandPartOfNumber, ESlovakGender.FEMININUM, false) + ESlovakIntegerPart.THOUSAND.toString();
		}

		return word;
	}

	/**
	 * Transform hundreds of the amount.
	 *
	 * @param hundredPartOfNumber the hundred part of number
	 * @param gender the gender
	 * @param returnZeroWord the flag indicates if the string "zero" should return or not.
	 * @return the hundred part of number in words
	 */
	private String transformHundreds (Integer hundredPartOfNumber, ESlovakGender gender, boolean returnZeroWord)
	{
		Integer hundreds = hundredPartOfNumber / 100; //gets the number of hundreds
		Integer tenPartOfNumber = hundredPartOfNumber % 100;
		String word = null;

		//the final number is two digit
		if (hundreds == 0)
		{
			word = transformTens(tenPartOfNumber, gender, returnZeroWord);
		}
		//the final number is one hundred and something
		else if (hundreds == 1)
		{
			word = ESlovakIntegerPart.HUNDRED.toString() + transformTens(tenPartOfNumber, ESlovakGender.MASKULINUM, false);
		}
		//the final number is more than one in hundreds part
		else
		{
			word = transformTens(hundreds, ESlovakGender.NEUTRUM, false) + ESlovakIntegerPart.HUNDRED.toString() + transformTens(tenPartOfNumber, ESlovakGender.MASKULINUM, false);
		}

		return word;
	}

	/**
	 * Transform tens of the amount.
	 *
	 * @param tenPartOfNumber the ten part of number
	 * @param gender the gender
	 * @param returnZeroWord the flag indicates if the string "zero" should return or not.
	 * @return the ten part of number in words
	 */
	private String transformTens (Integer tenPartOfNumber, ESlovakGender gender, boolean returnZeroWord)
	{
		String tensWord = ESlovakIntegerPart.getTensWord(tenPartOfNumber);
		String unitsWord = null; //the unit word depends on gender (dve miliardy, dva miliony)
		if (gender.equals(ESlovakGender.MASKULINUM) == true)
		{
			unitsWord = ESlovakIntegerPart.getMaskulinumGenderUnitsWord(tenPartOfNumber);
		}
		else if (gender.equals(ESlovakGender.FEMININUM) == true)
		{
			unitsWord = ESlovakIntegerPart.getFemininumUnitsWord(tenPartOfNumber);
		}
		else
		{
			unitsWord = ESlovakIntegerPart.getNeutrumUnitsWord(tenPartOfNumber);
		}

		//if there are unit in the number, which are defined in enum
		if (unitsWord != null)
		{
			//if we should not return the zero, return empty string
			if (tenPartOfNumber == 0 && returnZeroWord == false)
			{
				return "";
			}
			else
			{
				return unitsWord;
			}
		}
		//if there are ten in the number, which are defined in enum
		else if (tensWord != null)
		{
			return tensWord;
		}
		//if the word must be composed
		else
		{
			StringBuilder wordBuilder = new StringBuilder();
			wordBuilder.append(ESlovakIntegerPart.getTensWord( (tenPartOfNumber / 10) * 10));
			wordBuilder.append(ESlovakIntegerPart.getMaskulinumGenderUnitsWord(tenPartOfNumber % 10));
			return wordBuilder.toString();
		}
	}
}
