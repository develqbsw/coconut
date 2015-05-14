package sk.qbsw.sgwt.winnetou.client.ui.component.table;

import sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter.CCountCellFormater;
import sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter.CDateCellFormater;
import sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter.CDurationCellFormater;
import sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter.CYesNoValueMapper;
import sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer.CCountGroupTitleRenderer;
import sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer.CDateGroupTitleRenderer;
import sk.qbsw.sgwt.winnetou.client.ui.component.table.titlerenderer.CYesNoGroupTitleRenderer;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * Class used for formatting table columns.
 *
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CTableCellFormater
{
	/**
	 * Formats column as Date. Formats as CDateCellFormater.DMYYYY
	 * @see CDateCellFormater
	 * @see CDateGroupTitleRenderer
	 * @param column column to reconfigure
	 */
	public static void formatAsDate(ListGridField column)
	{
		column.setType(ListGridFieldType.DATE);
		column.setWidth(IColumnSizes.DATE);
		column.setCellAlign(Alignment.CENTER);
		column.setCellFormatter(new CDateCellFormater(CDateCellFormater.DMYYYY));
		column.setGroupTitleRenderer(new CDateGroupTitleRenderer(CDateCellFormater.DMYYYY));
	}

	/**
	 * Formats column as Date. Formats as CDateCellFormater.HHMM
	 * @see CDateCellFormater
	 * @see CDateGroupTitleRenderer
	 * @param column column to reconfigure
	 */
	public static void formatAsTime(ListGridField column)
	{
		column.setType(ListGridFieldType.TIME);
		column.setWidth(IColumnSizes.TIME);
		column.setCellAlign(Alignment.CENTER);
//		column.setCellFormatter(new CDateCellFormater(CDateCellFormater.HHMM));
//		column.setGroupTitleRenderer(new CDateGroupTitleRenderer(CDateCellFormater.HHMM));
	}

	/**
	 * Formats column as Date. Formats as CDateCellFormater.DDMMYYYYHHMM
	 * @see CDateCellFormater
	 * @see CDateGroupTitleRenderer
	 * @param column column to reconfigure
	 */
	public static void formatAsDateTime(ListGridField column)
	{
		column.setType(ListGridFieldType.DATE);
		column.setWidth(IColumnSizes.DATE_TIME);
		column.setCellAlign(Alignment.CENTER);
//		column.setCellFormatter(new CDateCellFormater(CDateCellFormater.DDMMYYYYHHMM));
//		column.setGroupTitleRenderer(new CDateGroupTitleRenderer(CDateCellFormater.DDMMYYYYHHMM));
	}

	/**
	 * Formats column as Date. Formats as CDateCellFormater.HHMM
	 * @see CDurationCellFormater
	 * @see CDateGroupTitleRenderer
	 * @param column column to reconfigure
	 */
	public static void formatAsDuration(ListGridField column)
	{
		column.setType(ListGridFieldType.TIME);
		column.setWidth(IColumnSizes.TIME);
		column.setCellAlign(Alignment.CENTER);
		column.setCellFormatter(new CDurationCellFormater(CDateCellFormater.HHMM));
		column.setGroupTitleRenderer(new CDateGroupTitleRenderer(CDateCellFormater.HHMM));
	}

	/**
	 * Default formatting of header, provides CENTER alignment
	 *
	 * @param column column to reconfigure
	 */
	public static void formatHeader(ListGridField column)
	{
		column.setType(ListGridFieldType.TEXT);
		column.setAlign(Alignment.CENTER);
	}

	/**
	 * Default formatting of column (just aligns to left)
	 * 
	 * @param column column to reconfigure
	 */
	public static void formatAsText(ListGridField column)
	{
		column.setType(ListGridFieldType.TEXT);
		column.setCellAlign(Alignment.LEFT);
	}

	/**
	 * Formats boolean column as checkbox
	 * @param column column to reconfigure
	 */
	public static void formatAsCheckBox(ListGridField column)
	{
		column.setType(ListGridFieldType.BOOLEAN);
		column.setCellAlign(Alignment.CENTER);
		column.setWidth(IColumnSizes.BOOLEAN);
	}
	
	/**
	 * Formats boolean column as yes no
	 * @param column column to reconfigure
	 */
	public static void formatAsYesNo(ListGridField column)
	{
		column.setType(ListGridFieldType.BOOLEAN);
		column.setCellAlign(Alignment.CENTER);
		column.setWidth(IColumnSizes.BOOLEAN);
		column.setGroupTitleRenderer(new CYesNoGroupTitleRenderer());
		column.setValueMap(new CYesNoValueMapper());
	}

	/**
	 * Formats integer column as count
	 * @see CCountCellFormater
	 * @see CCountGroupTitleRenderer
	 * @param column column to reconfigure
	 */
	public static void formatAsCount(ListGridField column)
	{
		column.setType(ListGridFieldType.INTEGER);
		column.setCellAlign(Alignment.CENTER);
		column.setCellFormatter(new CCountCellFormater());
		column.setGroupTitleRenderer(new CCountGroupTitleRenderer());
	}	
	
	/**
	 * Formats integer column as count
	 * @see CCountCellFormater
	 * @see CCountGroupTitleRenderer
	 * @param column column to reconfigure
	 */
	public static void formatAsId(ListGridField column)
	{
		column.setType(ListGridFieldType.TEXT);
		column.setCellAlign(Alignment.CENTER);
	}	
	
}
