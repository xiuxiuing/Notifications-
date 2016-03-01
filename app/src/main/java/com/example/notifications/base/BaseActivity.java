package com.example.notifications.base;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public NotificationManager mNotificationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initService();
	}


	private void initService() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	

	public void clearNotify(int notifyId){
		mNotificationManager.cancel(notifyId);
//		mNotification.cancel(getResources().getString(R.string.app_name));
	}
	

	public void clearAllNotify() {
		mNotificationManager.cancelAll();
	}
	

	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
}
