package sk.qbsw.android.utils;

import java.io.File;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;

/**
 * this class must be called to show file in windows manager
 * @author Michal Lacko
 * @since 7.0
 * @version 0.1
 */
public class CSingleMediaScanner implements MediaScannerConnectionClient
{

	private MediaScannerConnection mMs;
	private File mFile;

	public CSingleMediaScanner (Context context, File f)
	{
		mFile = f;
		mMs = new MediaScannerConnection(context, this);
		mMs.connect();
	}

	@Override
	public void onMediaScannerConnected ()
	{
		mMs.scanFile(mFile.getAbsolutePath(), null);
	}

	@Override
	public void onScanCompleted (String path, Uri uri)
	{
		mMs.disconnect();
	}

}