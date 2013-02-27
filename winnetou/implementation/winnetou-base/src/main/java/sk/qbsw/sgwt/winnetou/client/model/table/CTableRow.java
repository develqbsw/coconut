package sk.qbsw.sgwt.winnetou.client.model.table;

import java.io.Serializable;
import java.util.HashMap;

import sk.qbsw.sgwt.winnetou.client.model.IFetchable;

/**
 * Object represents data transfered from server to client
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * @gwt.typeArgs <java.util.HashMap<java.lang.String, java.lang.oBJECT>>
 */
@SuppressWarnings("serial")
public class CTableRow extends HashMap<String, Serializable> implements IFetchable
{
}
