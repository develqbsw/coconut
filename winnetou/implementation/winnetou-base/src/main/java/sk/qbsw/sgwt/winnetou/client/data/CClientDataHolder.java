package sk.qbsw.sgwt.winnetou.client.data;

import java.util.HashMap;

import sk.qbsw.sgwt.winnetou.client.model.security.CLoggedUserRecord;

/**
 * Data Stored on the client side.
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public class CClientDataHolder 
{
	public static final String ERROR_CODES = "ERROR_CODES";
	
	/**
	 * Singleton instance
	 */
	private static CClientDataHolder instance;
	
	/**
	 * Info about logged user
	 */
	private CLoggedUserRecord loggedUserRecord;
	
	/**
	 * factory method
	 * @return instance
	 */
	public static CClientDataHolder getInstance()
	{
		if (instance == null)
		{
			instance = new CClientDataHolder();
		}
		
		return instance;
	}

	public CLoggedUserRecord getLoggedUserRecord()
	{
		return loggedUserRecord;
	}
	
	public void setLoggedUserRecord(CLoggedUserRecord loggedUserRecord)
	{
		this.loggedUserRecord = loggedUserRecord;
	}
	
	private HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String,String>>();
	
	/**
	 * Hidden constructor
	 */
	private CClientDataHolder()
	{
		
	}

	/**
	 * Adds value to the data
	 * @param area area, where the value will be stored
	 * @param key key to the HashMap
 	 * @param value value to store
	 */
	public void addData(String area, String key, String value)
	{
		if (!data.containsKey(area))
		{
			data.put(area, new HashMap<String, String>());
		}
		
		HashMap<String, String> toAdd = data.get(area);
		toAdd.put(key, value);
	}

	/**
	 * Gets value from the area with the key 
	 * @param area area to search
	 * @param key key to read
	 * @return String value
	 */
	public String getData(String area, String key)
	{
		if (data.containsKey(area))
		{
			return data.get(area).get(key);
		}
		return null;
	}
	

}
