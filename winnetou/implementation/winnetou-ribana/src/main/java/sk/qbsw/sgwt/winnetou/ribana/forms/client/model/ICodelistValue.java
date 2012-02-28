package sk.qbsw.sgwt.winnetou.ribana.forms.client.model;

import java.io.Serializable;


/** 
 * Interface for list value 
 * 
 * @author Dalibor Rak
 * @version 1.1
 * 
 * */
public interface ICodelistValue extends Serializable
{
	/**
	 * Returns code of list value (probably unique)
	 * @return
	 */
	public String getCode ();

	/**
	 * Returns type used for grouping or icons
	 */
	public String getType ();

	/**
	 * Returns description of value
	 * @return
	 */
	public String getDescription ();

	/**
	 * Returns unique ID of record
	 * @return
	 */
	public Long getId ();
}
