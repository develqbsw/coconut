package sk.qbsw.core.reporting.test.transform;

/**
* The enum of slovak translations of basic number of which the composed numbers are composed for test purposes.
*
* @author Tomas Lauro
* 
* @version 1.10.2
* @since 1.10.2
*/
enum ESlovakIntegerPartTest
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
	ESlovakIntegerPartTest (String word)
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
}
