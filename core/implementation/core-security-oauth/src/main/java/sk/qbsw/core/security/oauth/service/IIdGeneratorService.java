package sk.qbsw.core.security.oauth.service;

/**
 * The id generator.
 * 
 * @author meszaros
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public interface IIdGeneratorService
{
	/**
	 * @param s vstup
	 * @return guid na zaklade casu a vstupneho stringu hashovane cez sha-256
	 */
	public String getGeneratedId (String s);

	/**
	 * @return guid na zaklade casu hashovane cez sha-256
	 */
	public String getGeneratedId ();
}
