package sk.qbsw.dockie.core.gopay.model;

/**
 * The Class CGopayContact.
 *
 * @author martinkovic
 * @version 1.7.0
 * @since  1.7.0
 */
public class CGopayContact
{

	private String first_name;
	private String last_name;
	private String email;
	private String phone_number;
	private String city;
	private String street;
	private String postal_code;
	private String country_code;

	public String getFirst_name ()
	{
		return first_name;
	}

	public void setFirst_name (String first_name)
	{
		this.first_name = first_name;
	}

	public String getLast_name ()
	{
		return last_name;
	}

	public void setLast_name (String last_name)
	{
		this.last_name = last_name;
	}

	public String getEmail ()
	{
		return email;
	}

	public void setEmail (String email)
	{
		this.email = email;
	}

	public String getPhone_number ()
	{
		return phone_number;
	}

	public void setPhone_number (String phone_number)
	{
		this.phone_number = phone_number;
	}

	public String getCity ()
	{
		return city;
	}

	public void setCity (String city)
	{
		this.city = city;
	}

	public String getStreet ()
	{
		return street;
	}

	public void setStreet (String street)
	{
		this.street = street;
	}

	public String getPostal_code ()
	{
		return postal_code;
	}

	public void setPostal_code (String postal_code)
	{
		this.postal_code = postal_code;
	}

	public String getCountry_code ()
	{
		return country_code;
	}

	public void setCountry_code (String country_code)
	{
		this.country_code = country_code;
	}

}
