package sk.qbsw.security.oauth.service;

/**
 * Service interface for generating ID PD.
 *
 * @author Csaba Meszaros
 * @author Dalibor Rak
 * @version 1.18.2
 * @since 1.13.3
 */
public interface IdGeneratorService
{
	/**
	 * Gets generated id.
	 *
	 * @param s the s
	 * @return the generated id
	 */
	String getGeneratedId (String s);

	/**
	 * Gets generated id.
	 *
	 * @return the generated id
	 */
	String getGeneratedId ();
}
