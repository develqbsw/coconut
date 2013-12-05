package sk.qbsw.android.ui.component.model;

import java.io.Serializable;

/**
 * Universal model to store one value of Type T
 * @author Michal Lacko
 * @since 0.4.0
 * @version 0.1.0
 */
public class CModel<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * content of model
	 */
	private T content;

	public T getContent ()
	{
		return content;
	}

	public void setContent (T content)
	{
		this.content = content;
	}
}
