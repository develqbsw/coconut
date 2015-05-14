package sk.qbsw.android.ui.component.fragment;

import java.io.Serializable;
import java.sql.SQLException;

import sk.qbsw.android.CQBSWAndroidLoggerTag;
import sk.qbsw.android.exception.CExceptionHandler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * parent for all fragments used in application
 * @author Michal Lacko
 * @param <L> parameter to define which type of layout is inflated into fragment
 * @param <M> parameter to define which model is used in this fragment
 * @since 0.1.0
 * @version 0.2.0
 */
public abstract class CDefaultFragment<L extends ViewGroup, M extends Serializable> extends Fragment
{
	/**
	 * Indices when is fragment added into activity
	 */
	protected Boolean viewCreated;

	/**
	 * model which is used in this fragment
	 */
	protected M model;

	/**
	 * layout which is inflated into this fragment
	 */
	protected L layout;

	public CDefaultFragment ()
	{
		super();
		//when is class instantiated then isn't initialized view attributes
		this.viewCreated = Boolean.FALSE;

		initData();
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
//		super.onCreateView(inflater, container, savedInstanceState);
		this.viewCreated = Boolean.TRUE;
		inflate(inflater, container);
		init(savedInstanceState);
		try
		{
			loadData(savedInstanceState);
		}
		catch (SQLException e)
		{
			CExceptionHandler.logException(CDefaultFragment.class, e);
		}


		refresh();

		return this.layout;
	}

	/**
	 * method to inflate xml_layout to views
	 * this in unchecked because we expect than xml view is same type like <L> parameter which is getted as parameter to class
	 */
	@SuppressWarnings ("unchecked")
	private void inflate (LayoutInflater inflater, ViewGroup container)
	{
		// We have different layouts, and in one of them this
		// fragment's containing frame doesn't exist.  The fragment
		// may still be created from its saved state, but there is
		// no reason to try to create its view hierarchy because it
		// won't be displayed.  Note this is not needed -- we could
		// just run the code below, where we would create and return
		// the view hierarchy; it would just never be used.

		//when fragment go directly into xml then container is filled every time
//		if (this.layout == null)
//		{
			this.layout = (L) inflater.inflate(getViewToInflate(), container, false);
//		}
	}

	/**
	 * this method must be override to get XML view to inflate this fragment
	 * @return id of layout to inflate this fragment
	 */
	protected abstract int getViewToInflate ();

	public M getModel ()
	{
		return model;
	}

	public void setModel (M model)
	{
		this.model = model;
	}

	public L getLayout ()
	{
		return layout;
	}

	/**
	 * refresh panel from model
	 */
	public void refresh ()
	{
		//refresh only if is are view attributed created
		if (this.viewCreated && this.model != null)
		{
			updateFields();
		}
	}

	/**
	 * refresh panel from model
	 * method is call is is called refresh 
	 * and onCreate method was called 
	 * and this. model isn't null
	 */
	protected void updateFields ()
	{

	}

	/**
	 * 
	 */
	protected void init (Bundle savedInstanceState)
	{

	}

	/**
	 * init data to default values
	 */
	private void initData ()
	{
		this.model = null;
	}

	/**
	 * method where fragment can load data from service
	 * @param savedInstanceState - bundle from which are data loaded after is screen rotated
	 */
	protected void loadData (Bundle savedInstanceState) throws SQLException
	{
		if (savedInstanceState != null)
		{
			this.model = (M) savedInstanceState.getSerializable("model");
			
			Log.d(CQBSWAndroidLoggerTag.TAG, "model"+this.model);
		}
	}


	@Override
	public void onSaveInstanceState (Bundle outState)
	{
		outState.putSerializable("model", this.model);
		super.onSaveInstanceState(outState);
	}

}
