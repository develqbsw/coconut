package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * Phase of timesheet
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 *
 */
public class CTimesheetPhaseItem extends TextItem
{
	public CTimesheetPhaseItem()
	{
		setTitle(ILabels.Factory.getInstance().item_phase());
		setShowTitle(true);
		
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setLength(1000);
		setTextAlign(Alignment.LEFT);
	}
}
