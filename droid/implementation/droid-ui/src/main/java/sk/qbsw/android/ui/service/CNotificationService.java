package sk.qbsw.android.ui.service;

import java.io.Serializable;

import sk.qbsw.android.ui.activity.broardcast.EBroadcastAction;
import sk.qbsw.android.ui.activity.broardcast.EBroadcastActionType;
import android.app.Service;
import android.content.Intent;

public abstract class CNotificationService extends Service
{
	protected void sendNotification (EBroadcastActionType type, Serializable content)
	{
		Intent intent = new Intent(EBroadcastAction.OUTSIDE_RESPONSE.getValue());
		intent.putExtra("content", content);
		intent.putExtra("type", type);
		sendBroadcast(intent, null);
	}
}
