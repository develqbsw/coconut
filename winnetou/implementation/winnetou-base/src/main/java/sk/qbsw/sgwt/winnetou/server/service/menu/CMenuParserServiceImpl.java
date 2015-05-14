package sk.qbsw.sgwt.winnetou.server.service.menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sk.qbsw.sgwt.winnetou.client.model.menu.CMenu;
import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabItem;
import sk.qbsw.sgwt.winnetou.client.model.menu.CMenuTabSubItem;
import sk.qbsw.sgwt.winnetou.client.service.menu.IMenuParserService;
import sk.qbsw.sgwt.winnetou.server.exception.CServerExceptionPublisher;

/**
 * Menu parser service - ported from Ondrej
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
@Service (value = "menuParserService")
public class CMenuParserServiceImpl implements IMenuParserService
{
	final static String MENU_XML = "menu.xml";
	/**
	 * ROLE_DELIMITER
	 */
	final static String ROLE_DELIMITER = ",";

	/**
	 * XML_ATTRIBUTE_HINT = "hint"
	 */
	final static String XML_ATTRIBUTE_HINT = "hint";

	/**
	 * XML_ATTRIBUTE_HINT = "hint-key"
	 */
	final static String XML_ATTRIBUTE_HINT_KEY = "hint-key";

	/**
	 * XML_ATTRIBUTE_COMMAND = "command"
	 */
	final static String XML_ATTRIBUTE_COMMAND = "command";


	/**
	 * XML_ATTRIBUTE_ICON = "icon"
	 */
	final static String XML_ATTRIBUTE_ICON = "icon";

	/**
	 * XML_ATTRIBUTE_ID = "id"
	 */
	final static String XML_ATTRIBUTE_ID = "id";

	/**
	 * XML_ATTRIBUTE_LABEL = "id"
	 */
	final static String XML_ATTRIBUTE_LABEL = "label";

	/**
	 * XML_ATTRIBUTE_LABEL_KEY = "label-key"
	 */
	final static String XML_ATTRIBUTE_LABEL_KEY = "label-key";

	/**
	 * XML_ATTRIBUTE_LABEL = "valid"
	 */
	final static String XML_ATTRIBUTE_ENABLED = "enabled";

	/**
	 * XML_ATTRIBUTE_SEPARATOR = "separator"
	 */
	final static String XML_ATTRIBUTE_SEPARATOR = "separator";

	/**
	 * XML_ELEMENT_SUBTABITEM_ROLE = "tabSubItemRole"
	 */
	final static String XML_ELEMENT_SUBTABITEM_ROLE = "tabSubItemRole";

	/**
	 * XML_ELEMENT_TABITEM = "tabItem"
	 */
	final static String XML_ELEMENT_TABITEM = "tabItem";

	/**
	 * XML_ELEMENT_TABITEM_ROLE = "tabItemRole"
	 */
	final static String XML_ELEMENT_TABITEM_ROLE = "tabItemRole";

	/**
	 * XML_ELEMENT_TABS = "tabs"
	 */
	final static String XML_ELEMENT_TABS = "tabs";

	/**
	 * XML_ELEMENT_TABSUBITEM = "tabSubItem"
	 */
	final static String XML_ELEMENT_TABSUBITEM = "tabSubItem";

	/**
	 * XML_ELEMENT_TABSUBITEMS = "tabSubItems"
	 */
	final static String XML_ELEMENT_TABSUBITEMS = "tabSubItems";

	/**
	 * Gets file stream
	 * 
	 * @param filePath
	 * @return
	 */
	private static InputStream getStream (String filePath)
	{
		try
		{
			ClassPathResource res = new ClassPathResource(filePath);
			return res.getInputStream();
		}
		catch (IOException ex)
		{
			CServerExceptionPublisher.pass(ex);
		}
		return null;
	}

	/**
	 * 
	 */
	private InputStream xmlSource;

	/**
	 * load XML file that contains menu definition
	 * 
	 * @param filePath
	 *            XML relative filename
	 */
	private void loadXML (String filePath)
	{
		this.xmlSource = getStream(filePath);
	}

	/**
	 * Main service method parses XML file with menu definition and returns
	 * CMenuItems objects.
	 */
	public CMenu parse ()
	{
		Logger.getLogger(this.getClass()).info("Parsing menu xml");
		loadXML(MENU_XML);

		ArrayList<CMenuTabItem> menuTabs = new ArrayList<CMenuTabItem>();
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc;

			doc = parser.parse(this.xmlSource);
			Node menuNode = doc.getFirstChild();
			NodeList menuNodeChildren = menuNode.getChildNodes();

			for (int i = 0; i < menuNodeChildren.getLength(); i++)
			{
				Node tempNode = menuNodeChildren.item(i);
				if (tempNode.getNodeName().equals(XML_ELEMENT_TABS))
				{
					parseTabsNode(menuTabs, tempNode);
				}
			}
		}
		catch (Throwable e)
		{
			CServerExceptionPublisher.pass("XML PARSER ERROR", e);
		}

		CMenu menuItems = new CMenu();
		menuItems.setMenuTabItems(menuTabs);

		Logger.getLogger(this.getClass()).info("Menu constructed");
		return menuItems;
	}

	private CMenuTabItem parseTabItemNode (Node tabsItemNode)
	{
		CMenuTabItem cMenuTabItem = new CMenuTabItem();
		NamedNodeMap attributes = tabsItemNode.getAttributes();

		// id
		cMenuTabItem.setId(attributes.getNamedItem(XML_ATTRIBUTE_ID).getNodeValue());

		// hint
		Node hintAtt = attributes.getNamedItem(XML_ATTRIBUTE_HINT);
		if (hintAtt != null)
		{
			cMenuTabItem.setHint(hintAtt.getNodeValue());
		}

		// hint key
		Node hintKeyAtt = attributes.getNamedItem(XML_ATTRIBUTE_HINT_KEY);
		if (hintKeyAtt != null)
		{
			cMenuTabItem.setHintKey(hintKeyAtt.getNodeValue());
		}

		// label
		Node labelAtt = attributes.getNamedItem(XML_ATTRIBUTE_LABEL);
		if (labelAtt != null)
		{
			cMenuTabItem.setLabel(labelAtt.getNodeValue());
		}

		// label key
		Node labelKeyAtt = attributes.getNamedItem(XML_ATTRIBUTE_LABEL_KEY);
		if (labelKeyAtt != null)
		{
			cMenuTabItem.setLabelKey(labelKeyAtt.getNodeValue());
		}


		// label
		Node enabled = attributes.getNamedItem(XML_ATTRIBUTE_ENABLED);
		if (enabled != null)
		{
			cMenuTabItem.setEnabled(Boolean.getBoolean(enabled.getNodeValue()));
		}


		// icon
		Node iconAtt = attributes.getNamedItem(XML_ATTRIBUTE_ICON);
		if (iconAtt != null)
		{
			cMenuTabItem.setIconPath(iconAtt.getNodeValue());
		}

		cMenuTabItem.setLabel(cMenuTabItem.getId());
		NodeList nodeList = tabsItemNode.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);

			if (node.getNodeName().equals(XML_ELEMENT_TABITEM_ROLE))
			{
				cMenuTabItem.setRole(node.getTextContent());
			}
			if ( (node.getNodeName().equals(XML_ELEMENT_TABSUBITEMS)))
			{
				cMenuTabItem.setMenuTabSubItems(parseTabSubItems(node.getChildNodes()));
			}
		}
		return cMenuTabItem;
	}

	private void parseTabsNode (ArrayList<CMenuTabItem> result, Node tabsNode)
	{
		NodeList tabItems = tabsNode.getChildNodes();

		for (int i = 0; i < tabItems.getLength(); i++)
		{
			Node tabItemNode = tabItems.item(i);

			if (tabItemNode.getNodeName().equals(XML_ELEMENT_TABITEM))
			{
				result.add(parseTabItemNode(tabItemNode));
			}
		}
	}

	private ArrayList<CMenuTabSubItem> parseTabSubItems (NodeList nodeList)
	{
		ArrayList<CMenuTabSubItem> result = new ArrayList<CMenuTabSubItem>();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node tabSubItem = nodeList.item(i);
			if (tabSubItem.getNodeName().equals(XML_ELEMENT_TABSUBITEM))
			{
				CMenuTabSubItem menuTabSubItem = new CMenuTabSubItem();
				NamedNodeMap attributes = tabSubItem.getAttributes();
				menuTabSubItem.setId(attributes.getNamedItem(XML_ATTRIBUTE_ID).getNodeValue());

				Node label = attributes.getNamedItem(XML_ATTRIBUTE_LABEL);
				if (label != null)
				{
					menuTabSubItem.setLabel(label.getNodeValue());
				}

				Node labelKey = attributes.getNamedItem(XML_ATTRIBUTE_LABEL_KEY);
				if (labelKey != null)
				{
					menuTabSubItem.setLabelKey(labelKey.getNodeValue());
				}


				Node command = attributes.getNamedItem(XML_ATTRIBUTE_COMMAND);
				if (command != null)
				{
					menuTabSubItem.setCommand(command.getNodeValue());
				}

				Node valid = attributes.getNamedItem(XML_ATTRIBUTE_ENABLED);
				if (valid != null)
				{
					menuTabSubItem.setEnabled(Boolean.getBoolean(valid.getNodeValue()));
				}

				Node hint = attributes.getNamedItem(XML_ATTRIBUTE_HINT);
				if (hint != null)
				{
					menuTabSubItem.setHint(hint.getNodeValue());
				}

				Node hintKey = attributes.getNamedItem(XML_ATTRIBUTE_HINT_KEY);
				if (hintKey != null)
				{
					menuTabSubItem.setHintKey(hintKey.getNodeValue());
				}


				Node icoAtt = attributes.getNamedItem(XML_ATTRIBUTE_ICON);
				if (icoAtt != null)
				{
					menuTabSubItem.setIconPath(icoAtt.getNodeValue());
				}

				Node separatorAtt = attributes.getNamedItem(XML_ATTRIBUTE_SEPARATOR);
				if (separatorAtt != null)
				{
					menuTabSubItem.setSeparator(Boolean.parseBoolean(separatorAtt.getNodeValue()));
				}

				NodeList list = tabSubItem.getChildNodes();
				for (int j = 0; j < list.getLength(); j++)
				{
					Node n = list.item(j);
					if (n.getNodeName().equals(XML_ELEMENT_SUBTABITEM_ROLE))
					{

						menuTabSubItem.setRoles(n.getTextContent().split(ROLE_DELIMITER));
					}
				}
				result.add(menuTabSubItem);
			}
		}
		return result;
	}

}
