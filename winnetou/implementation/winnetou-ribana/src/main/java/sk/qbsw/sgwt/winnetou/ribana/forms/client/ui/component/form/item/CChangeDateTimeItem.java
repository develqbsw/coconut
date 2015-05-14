package sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.component.form.item;

import java.util.Date;

import sk.qbsw.sgwt.winnetou.client.ui.component.form.item.CTextItem;
import sk.qbsw.sgwt.winnetou.ribana.forms.client.ui.localization.ILabels;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.Alignment;

/**
 * City form item
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public class CChangeDateTimeItem extends CTextItem
{
	public CChangeDateTimeItem()
	{
		super();

		setTitle(ILabels.Factory.getInstance().item_changedatetime());
		setWrapTitle(false);
		setRequired(false);
		setAlign(Alignment.LEFT);
		setWidth("*");

		setLength(19);
		setDisabled(true);
		setTextAlign(Alignment.LEFT);
	}

	@Override
	public void setValue(Date date)
	{
		final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm:ss");
		String toShow = dateFormatter.format(date);
		setValue(toShow);
	}
}
