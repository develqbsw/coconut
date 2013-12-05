package sk.qbsw.android.ui.component.listener;


/**
 * interface to send event to parent panel when is panel submitted
 * @author Michal Lacko
 * @since 0.1.0
 * @version 0.1.0
 */
public interface COnSubmitListener<T>
{
	/**
	 * Method is called when is panel submitted(usually with submit button)
	 */
	public void onSubmit (T model);
}
