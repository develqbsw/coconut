package sk.qbsw.android.utils;

import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

public class CCamera
{
	/**
	 * To get path of picture taken by camera and saved in gallery
	 * @param context Activity from which this method is called
	 * @param pictureId Id of the picture taken by camera
	 * @return Path to picture taken by camera
	 */
	// workaround to remove taken picture from gallery. It is necessary because if EXTRA_OUTPUT is set to intent calling camera app, app will save the
	// picture to custom destination and to gallery too.
	// http://stackoverflow.com/a/8555925
	public static String getPicturePath (Context context, int pictureId)
	{
		final String[] imageColumns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID}; //get columns
		final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
		final String imageWhere = MediaStore.Images.Media._ID + "=?"; //id is same as defined in param
		final String[] imageArguments = {Integer.toString(pictureId)};
		String path = null;

		CursorLoader imageCursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, imageWhere, imageArguments, imageOrderBy);
		Cursor imageCursor = imageCursorLoader.loadInBackground();

		if (imageCursor.getCount() == 1)
		{
			imageCursor.moveToNext();

			path = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
		}

		imageCursor.close();
		return path;
	}

	/**
	 * To remove the picture taken by camera and saved in gallery
	 * @param context Activity from which this method is called
	 * @param contentResolver Content resolver of activity
	 * @param pictureId Id of the picture taken by camera
	 */
	// see above
	// http://stackoverflow.com/a/8555925
	public static void removePicture (Context context, ContentResolver contentResolver, int pictureId)
	{
		final String[] imageColumns = {MediaStore.Images.Media._ID};
		final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
		final String imageWhere = MediaStore.Images.Media._ID + "=?";
		final String[] imageArguments = {Integer.toString(pictureId)};

		CursorLoader imageCursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, imageWhere, imageArguments, imageOrderBy);
		Cursor imageCursor = imageCursorLoader.loadInBackground();

		if (imageCursor.getCount() == 1)
		{
			imageCursor.moveToNext();

			int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));

			contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media._ID + "=?", new String[] {Long.toString(id)});
		}

		imageCursor.close();
	}
	
	/**
	 * Gets the last image id from the media store
	 * @return last taken picture id
	 */
	public static int getLastTakenPictureId (Context context)
	{
		final String[] imageColumns = {MediaStore.Images.Media._ID};
		final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
		final String imageWhere = null;
		final String[] imageArguments = null;

		CursorLoader imageCursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, imageWhere, imageArguments, imageOrderBy);
		Cursor imageCursor = imageCursorLoader.loadInBackground();

		if (imageCursor.moveToFirst())
		{
			int id = imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
			imageCursor.close();
			return id;
		}
		else
		{
			imageCursor.close();
			return 0;
		}
	}
	
	/**
	 * get pictureId from intent
	 * @param data to get pictureId
	 * @param context application context - no activity context this can go to memory leak
	 * @return id of picture
	 */
	public static int getPictureId (Intent data, Context context)
	{
		int pictureId;

		if (data.getData() == null)
		{
			pictureId = CCamera.getLastTakenPictureId(context);
		}
		else
		{
			List<String> pathSegments = data.getData().getPathSegments(); //last path segment is picture id
			pictureId = Integer.parseInt(pathSegments.get(pathSegments.size() - 1));
		}

		return pictureId;
	}
}
