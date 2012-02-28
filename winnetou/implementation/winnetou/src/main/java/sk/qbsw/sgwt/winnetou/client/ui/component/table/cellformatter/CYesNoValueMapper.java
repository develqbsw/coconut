package sk.qbsw.sgwt.winnetou.client.ui.component.table.cellformatter;

import java.util.HashMap;

import sk.qbsw.sgwt.winnetou.client.ui.localization.ISystemLabels;

/**
 * Value mapping for browser table values with return values: {true, false} and
 * associated GUI values {labelConstants.table_value_yes(),
 * labelConstants.table_value_no()}
 * 
 * @author rosenberg
 */
public final class CYesNoValueMapper extends HashMap<String, String>
{
	final static String TRUE = "t"; //$NON-NLS-0$ // do uvahy pripada este hodnota "t", ktoru treba konvertovat na "true"
	final static String FALSE = "f"; //$NON-NLS-0$ // do uvahy pripada este hodnota "f", ktoru treba konvertovat na "false"

	/**
	 * 
	 */
	private static final long serialVersionUID = -8621438515450073250L;

	public CYesNoValueMapper()
	{
		super();

		put(TRUE, ISystemLabels.Factory.getInstance().label_yes());
		put(FALSE, ISystemLabels.Factory.getInstance().label_no());
	}
}
