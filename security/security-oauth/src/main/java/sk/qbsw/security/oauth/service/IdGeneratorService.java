package sk.qbsw.security.oauth.service;

/**
* Service interface for generating ID PD.
*
* @author Csaba Meszaros
* @author Dalibor Rak
* 
* @version 1.13.3
* @since 1.13.3
*/
public interface IdGeneratorService
{
	/**
	* Gets the generated id.
	*
	* @param s vstup
	* @return guid na zaklade casu a vstupneho stringu hashovane cez sha-256
	*/
	public String getGeneratedId (String s);

	/**
	* Gets the generated id.
	*
	* @return guid na zaklade casu hashovane cez sha-256
	*/
	public String getGeneratedId ();
}
