package sk.qbsw.sgwt.winnetou.ribana.forms.client.model;


/**
 * Model used for transfering codelists to client side
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
@SuppressWarnings ("serial")
public class CCodeListRecord implements ICodelistValue
{
	/**
	 * ID of the record
	 */
	private Long id;

	/**
	 * Code
	 */
	private String code;

	/**
	 * Description
	 */
	private String description;

	/**
	 * Type used to identify grouping eg. icon
	 */
	private String type;

	public Long getId ()
	{
		return id;
	}

	public void setId (Long id)
	{
		this.id = id;
	}

	public String getCode ()
	{
		return code;
	}

	public void setCode (String code)
	{
		this.code = code;
	}

	public String getDescription ()
	{
		return description;
	}

	public void setDescription (String description)
	{
		this.description = description;
	}

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
	}

}
