package sk.qbsw.core.security.oauth.service;

/**
 * @author meszaros
 *
 */
public interface IIdGeneratorService {

	/**
	 * @param s vstup
	 * @return guid na zaklade casu a vstupneho stringu hashovane cez sha-256
	 */
	public String getGeneratedId(String s);

	/**
	 * @return guid na zaklade casu hashovane cez sha-256
	 */
	public String getGeneratedId();

}
