package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.ButtonItem;

/**
 * Button item for starting 	
 * @author rak
 *
 */
public class CFinishButtonItem extends ButtonItem
{
	public CFinishButtonItem()
	{
		this.setTitle(ILabels.Factory.getInstance().item_finish());
		this.setEndRow(false);
		this.setStartRow(false);
		this.setAlign(Alignment.CENTER);
	}

}
