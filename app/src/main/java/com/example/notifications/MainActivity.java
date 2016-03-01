package com.example.notifications;


import java.io.File;

import com.example.notifications.base.BaseActivity;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnClickListener{
	private Button btn_show;
	private Button btn_bigstyle_show;
	private Button btn_show_progress;
	private Button btn_show_cz;
	private Button btn_clear;
	private Button btn_clear_all;
	private Button btn_show_custom;
	private Button btn_show_intent_act;
	private Button btn_show_intent_apk;
	NotificationCompat.Builder mBuilder;
	int notifyId = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		initNotify();
	}

	private void initView() {
		btn_show = (Button)findViewById(R.id.btn_show);
		btn_bigstyle_show = (Button)findViewById(R.id.btn_bigstyle_show);
		btn_show_progress = (Button)findViewById(R.id.btn_show_progress);
		btn_show_cz = (Button)findViewById(R.id.btn_show_cz);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		btn_clear_all = (Button)findViewById(R.id.btn_clear_all);
		btn_show_custom = (Button)findViewById(R.id.btn_show_custom);
		btn_show_intent_act = (Button)findViewById(R.id.btn_show_intent_act);
		btn_show_intent_apk = (Button)findViewById(R.id.btn_show_intent_apk);
		btn_show.setOnClickListener(this);
		btn_bigstyle_show.setOnClickListener(this);
		btn_show_progress.setOnClickListener(this);
		btn_show_cz.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_clear_all.setOnClickListener(this);
		btn_show_custom.setOnClickListener(this);
		btn_show_intent_act.setOnClickListener(this);
		btn_show_intent_apk.setOnClickListener(this);
	}
	
	private void initNotify(){
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setContentTitle("setContentTitle")
				.setContentText("setContentText")
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
//				.setNumber(number)
				.setTicker("setTicker")
				.setWhen(System.currentTimeMillis())
				.setPriority(Notification.PRIORITY_DEFAULT)
//				.setAutoCancel(true)
				.setOngoing(false)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND ������� // requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
	}
	
	public void showNotify(){
		mBuilder.setContentTitle("setContentTitle")
				.setContentText("setContentText")
//				.setNumber(number)
				.setTicker("setTicker");
		mNotificationManager.notify(notifyId, mBuilder.build());
//		mNotification.notify(getResources().getString(R.string.app_name), notiId, mBuilder.build());
	}
	
	public void showV4BigStyleNotify() {
		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("setBigContentTitle");
		// Moves events into the big view
		for (int i=0; i < 5; i++) {
		    inboxStyle.addLine("This is showBigStyleNotify");
		}
		mBuilder.setContentTitle("setContentTitle")
				.setContentText("setContentText")
//				.setNumber(number)
				.setStyle(inboxStyle)
				.setTicker("setTicker");
		mNotificationManager.notify(notifyId, mBuilder.build());
		// mNotification.notify(getResources().getString(R.string.app_name),
		// notiId, mBuilder.build());
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void showBigStyleNotify() {
		Notification.Builder builder = new Notification.Builder(this);

		Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("setBigContentTitle");
		// Moves events into the big view
		for (int i=0; i < 5; i++) {
			inboxStyle.addLine("This is showBigStyleNotify");
		}
		builder.setContentTitle("setContentTitle")
				.setContentText("setContentText")
//				.setNumber(number)
				.setStyle(inboxStyle)
				.setTicker("setTicker");
		mNotificationManager.notify(notifyId, builder.build());
		// mNotification.notify(getResources().getString(R.string.app_name),
		// notiId, mBuilder.build());
	}

	public void showCzNotify(){
//		Notification mNotification = new Notification();
//		Notification mNotification  = new Notification.Builder(this).getNotification();
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, getIntent(), 0);
		mBuilder.setSmallIcon(R.drawable.ic_launcher)
				.setTicker("setTicker")
				.setContentTitle("setContentTitle")
				.setContentText("setContentText")
				.setContentIntent(pendingIntent);
		Notification mNotification = mBuilder.build();
		mNotification.icon = R.drawable.ic_launcher;
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		mNotification.defaults = Notification.DEFAULT_VIBRATE;
		mNotification.tickerText = "tickerText";
		mNotification.when=System.currentTimeMillis();
//		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//		mNotification.setLatestEventInfo(this, "setLatestEventInfo", "setLatestEventInfo", null);
		mNotificationManager.notify(notifyId, mNotification);
	}
	
	public void showIntentActivityNotify(){
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
		mBuilder.setAutoCancel(true)
				.setContentTitle("setContentTitle")
				.setContentText("setContentText")
				.setTicker("setTicker");
		Intent resultIntent = new Intent(this, MainActivity.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	public void showIntentApkNotify(){
//		notification.flags = Notification.FLAG_AUTO_CANCEL;
		mBuilder.setAutoCancel(true)
				.setContentTitle("setContentTitle")
				.setContentText("setContentText")
				.setTicker("setTicker");
		Intent apkIntent = new Intent();
		apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		apkIntent.setAction(android.content.Intent.ACTION_VIEW);
		String apk_path = "file:///android_asset/cs.apk";
//		Uri uri = Uri.parse(apk_path);
		Uri uri = Uri.fromFile(new File(apk_path));
		apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
		// context.startActivity(intent);
		PendingIntent contextIntent = PendingIntent.getActivity(this, 0,apkIntent, 0);
		mBuilder.setContentIntent(contextIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show:
			showNotify();
			break;
		case R.id.btn_bigstyle_show:
			showV4BigStyleNotify();
			break;
		case R.id.btn_show_cz:
			showCzNotify();
			break;
		case R.id.btn_clear:
			clearNotify(notifyId);
			break;
		case R.id.btn_clear_all:
			clearAllNotify();
			break;
		case R.id.btn_show_intent_act:
			showIntentActivityNotify();
			break;
		case R.id.btn_show_intent_apk:
			showIntentApkNotify();
			break;
		case R.id.btn_show_progress:
			startActivity(new Intent(getApplicationContext(), ProgressAcitivty.class));
			break;
		case R.id.btn_show_custom:
			startActivity(new Intent(getApplicationContext(), CustomActivity.class));
			break;
		default:
			break;
		}
	}

}
