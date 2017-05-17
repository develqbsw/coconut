package sk.qbsw.reporting.base.transform.service;

/**
* The enum of slovak translations of basic number of which the composed numbers are composed.
*
* @author Tomas Lauro
* 
* @version 1.10.2
* @since 1.10.2
*/
enum ESlovakIntegerPart
{
	/** The zero. */
	ZERO ("nula"),

	/** The one maskulinum. */
	ONE_MASKULINUM ("jeden"),

	/** The one femininum. */
	ONE_FEMININUM ("jeden"),

	/** The one neutrum. */
	ONE_NEUTRUM ("jedno"),

	/** The two maskulinum. */
	TWO_MASKULINUM ("dva"),

	/** The two femininum. */
	TWO_FEMININUM ("dve"),

	/** The two neutrum. */
	TWO_NEUTRUM ("dve"),

	/** The three. */
	THREE ("tri"),

	/** The four. */
	FOUR ("štyri"),

	/** The five. */
	FIVE ("päť"),

	/** The six. */
	SIX ("šesť"),

	/** The seven. */
	SEVEN ("sedem"),

	/** The eight. */
	EIGHT ("osem"),

	/** The nine. */
	NINE ("deväť"),

	/** The ten. */
	TEN ("desať"),

	/** The eleven. */
	ELEVEN ("jedenásť"),

	/** The twelve. */
	TWELVE ("dvanásť"),

	/** The thirteen. */
	THIRTEEN ("trinásť"),

	/** The fourteen. */
	FOURTEEN ("štrnásť"),

	/** The fifteen. */
	FIFTEEN ("pätnásť"),

	/** The sixteen. */
	SIXTEEN ("šestnásť"),

	/** The seventeen. */
	SEVENTEEN ("sedemnásť"),

	/** The eightteen. */
	EIGHTTEEN ("osemnásť"),

	/** The nineteen. */
	NINETEEN ("devätnásť"),

	/** The twenty. */
	TWENTY ("dvadsať"),

	/** The thirty. */
	THIRTY ("tridsať"),

	/** The fourty. */
	FOURTY ("štyridsať"),

	/** The fifty. */
	FIFTY ("päťdesiat"),

	/** The sixty. */
	SIXTY ("šesťdesiat"),

	/** The seventy. */
	SEVENTY ("sedemdesiat"),

	/** The eighty. */
	EIGHTY ("osemdesiat"),

	/** The ninety. */
	NINETY ("deväťdesiat"),

	/** The hundred. */
	HUNDRED ("sto"),

	/** The thousand. */
	THOUSAND ("tisíc"),

	/** The million. */
	MILLION ("milión"),

	/** The million plural. */
	MILLION_PLURAL ("milióny"),

	/** The MILLIO n_ plura l_2. */
	MILLION_PLURAL_2 ("miliónov"),

	/** The billion. */
	BILLION ("miliarda"),

	/** The billion plural. */
	BILLION_PLURAL ("miliardy"),

	/** The BILLIO n_ plura l_2. */
	BILLION_PLURAL_2 ("miliárd");

	/** The word. */
	private String word;

	/**
	 * Instantiates a new slovak integer part.
	 *
	 * @param word the word
	 */
	ESlovakIntegerPart (String word)
	{
		this.word = word;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString ()
	{
		return word;
	}

	/**
	 * Gets the maskulinum gender units word.
	 *
	 * @param number the number
	 * @return the maskulinum gender units word
	 */
	public static String getMaskulinumGenderUnitsWord (Integer number)
	{
		switch (number)
		{
			case 0:
				return ESlovakIntegerPart.ZERO.toString();
			case 1:
				return ESlovakIntegerPart.ONE_MASKULINUM.toString();
			case 2:
				return ESlovakIntegerPart.TWO_MASKULINUM.toString();
			case 3:
				return ESlovakIntegerPart.THREE.toString();
			case 4:
				return ESlovakIntegerPart.FOUR.toString();
			case 5:
				return ESlovakIntegerPart.FIVE.toString();
			case 6:
				return ESlovakIntegerPart.SIX.toString();
			case 7:
				return ESlovakIntegerPart.SEVEN.toString();
			case 8:
				return ESlovakIntegerPart.EIGHT.toString();
			case 9:
				return ESlovakIntegerPart.NINE.toString();
			default:
				return null;
		}
	}

	/**
	 * Gets the femininum units word.
	 *
	 * @param number the number
	 * @return the femininum units word
	 */
	public static String getFemininumUnitsWord (Integer number)
	{
		switch (number)
		{
			case 1:
				return ESlovakIntegerPart.ONE_FEMININUM.toString();
			case 2:
				return ESlovakIntegerPart.TWO_FEMININUM.toString();
			default:
				return getMaskulinumGenderUnitsWord(number);
		}
	}

	/**
	 * Gets the neutrum units word.
	 *
	 * @param number the number
	 * @return the neutrum units word
	 */
	public static String getNeutrumUnitsWord (Integer number)
	{
		switch (number)
		{
			case 1:
				return ESlovakIntegerPart.ONE_NEUTRUM.toString();
			case 2:
				return ESlovakIntegerPart.TWO_NEUTRUM.toString();
			default:
				return getMaskulinumGenderUnitsWord(number);
		}
	}

	/**
	 * Gets the tens word.
	 *
	 * @param number the number
	 * @return the tens word
	 */
	public static String getTensWord (Integer number)
	{
		switch (number)
		{
			case 10:
				return ESlovakIntegerPart.TEN.toString();
			case 11:
				return ESlovakIntegerPart.ELEVEN.toString();
			case 12:
				return ESlovakIntegerPart.TWELVE.toString();
			case 13:
				return ESlovakIntegerPart.THIRTEEN.toString();
			case 14:
				return ESlovakIntegerPart.FOURTEEN.toString();
			case 15:
				return ESlovakIntegerPart.FIFTEEN.toString();
			case 16:
				return ESlovakIntegerPart.SIXTEEN.toString();
			case 17:
				return ESlovakIntegerPart.SEVENTEEN.toString();
			case 18:
				return ESlovakIntegerPart.EIGHTTEEN.toString();
			case 19:
				return ESlovakIntegerPart.NINETEEN.toString();
			case 20:
				return ESlovakIntegerPart.TWENTY.toString();
			case 30:
				return ESlovakIntegerPart.THIRTY.toString();
			case 40:
				return ESlovakIntegerPart.FOURTY.toString();
			case 50:
				return ESlovakIntegerPart.FIFTY.toString();
			case 60:
				return ESlovakIntegerPart.SIXTY.toString();
			case 70:
				return ESlovakIntegerPart.SEVENTY.toString();
			case 80:
				return ESlovakIntegerPart.EIGHTY.toString();
			case 90:
				return ESlovakIntegerPart.NINETY.toString();
			default:
				return null;
		}
	}
}
