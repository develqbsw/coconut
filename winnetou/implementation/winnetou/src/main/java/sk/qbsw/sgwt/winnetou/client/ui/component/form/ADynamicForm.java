package sk.qbsw.sgwt.winnetou.client.ui.component.form;

import sk.qbsw.sgwt.winnetou.client.ui.component.IUIComponent;
import sk.qbsw.sgwt.winnetou.client.ui.event.CApplicationEventListenerCollection;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEvent;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventListener;
import sk.qbsw.sgwt.winnetou.client.ui.event.IApplicationEventSource;

import com.smartgwt.client.widgets.form.DynamicForm;

/**
 * Base form class
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public abstract class ADynamicForm extends DynamicForm implements IUIComponent, IApplicationEventSource
{

	/**
	 * reference to event source
	 */
	protected IApplicationEventSource eventSource = this;
	/**
	 * Event listeners
	 */
	protected CApplicationEventListenerCollection applicationEventsListeners;

	/**
	 * Method used for initialization of components
	 */
	public void initComponents()
	{

	}

	/**
	 * Creates listeners collection
	 */
	public ADynamicForm()
	{
		applicationEventsListeners = new CApplicationEventListenerCollection();
	}

	/**
	 * removes all listeners
	 */
	public void clearListeners()
	{
		applicationEventsListeners.clear();
	}

	/**
	 * method for removing new listener
	 * 
	 * @param listener
	 *            listener to be removed
	 */
	@SuppressWarnings("unchecked")
	public void removeListener(IApplicationEventListener listener)
	{
		applicationEventsListeners.remove(listener);
	}

	/**
	 * method for adding new listener
	 * 
	 * @param listener
	 *            listener to be added
	 */
	@SuppressWarnings("unchecked")
	public void addListener(IApplicationEventListener listener)
	{
		applicationEventsListeners.add(listener);
	}

	/**
	 * Initializes component (initLayout and initComponents)
	 */
	public final void initialize()
	{
		initLayout();
		initComponents();
	}

	/**
	 * Method used for initialization of layout
	 */
	public void initLayout()
	{
		this.setStyleName("formBase");
		setMargin(5);
		setPadding(5);

		setCellPadding(0);
		setCellSpacing(2);
		setWidth100();
		setHeight100();
	}

	/**
	 * Fires event
	 * 
	 * @param event
	 */
	public void fireEvent(IApplicationEvent event)
	{
		applicationEventsListeners.fireEvent(event);
	}
}
