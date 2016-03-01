package com.example.notifications;


import com.example.notifications.base.BaseActivity;
import com.example.notifications.tools.BaseTools;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class CustomActivity extends BaseActivity implements OnClickListener{
	/** TAG */
	private final static String TAG = "CustomActivity";
	private Button btn_show_custom;
	private Button btn_show_custom_button;
	int notifyId = 101;
	NotificationCompat.Builder mBuilder;
	public boolean isPlay = false;
	public ButtonBroadcastReceiver bReceiver;
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom);
		initView();
		initButtonReceiver();
	}

	private void initView() {
		btn_show_custom = (Button) findViewById(R.id.btn_show_custom);
		btn_show_custom.setOnClickListener(this);
		btn_show_custom_button = (Button) findViewById(R.id.btn_show_custom_button);
		btn_show_custom_button.setOnClickListener(this);
	}

	public void shwoNotify(){
		RemoteViews view_custom = new RemoteViews(getPackageName(), R.layout.view_custom);
		view_custom.setImageViewResource(R.id.custom_icon, R.drawable.icon);
//		view_custom.setInt(R.id.custom_icon,"setBackgroundResource",R.drawable.icon);
		view_custom.setTextViewText(R.id.tv_custom_title, "setTextViewText");
		view_custom.setTextViewText(R.id.tv_custom_content, "setTextViewText");
//		view_custom.setTextViewText(R.id.tv_custom_time, String.valueOf(System.currentTimeMillis()));
//		view_custom.setViewVisibility(R.id.tv_custom_time, View.VISIBLE);
//		view_custom.setLong(R.id.tv_custom_time,"setTime", System.currentTimeMillis());
//		NumberFormat num = NumberFormat.getIntegerInstance();
//		view_custom.setTextViewText(R.id.tv_custom_num, num.format(this.number));
		mBuilder = new Builder(this);
		mBuilder.setContent(view_custom)
				.setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				.setWhen(System.currentTimeMillis())
				.setTicker("setTicker")
				.setPriority(Notification.PRIORITY_DEFAULT)
				.setOngoing(false)
				.setSmallIcon(R.drawable.icon);
//		mNotificationManager.notify(notifyId, mBuilder.build());
		Notification notify = mBuilder.build();
		notify.contentView = view_custom;
//		Notification notify = new Notification();
//		notify.icon = R.drawable.icon;
//		notify.contentView = view_custom;
//		notify.contentIntent = getDefalutIntent(Notification.FLAG_AUTO_CANCEL);
		mNotificationManager.notify(notifyId, notify);
	}
	

	public void showButtonNotify(){
		NotificationCompat.Builder mBuilder = new Builder(this);
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_button);
		mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.drawable.sing_icon);
		mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "setTextViewText");
		mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "setTextViewText");
		if(BaseTools.getSystemVersion() <= 9){
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
		}else{
			mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
			//
			if(isPlay){
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_pause);
			}else{
				mRemoteViews.setImageViewResource(R.id.btn_custom_play, R.drawable.btn_play);
			}
		}

		Intent buttonIntent = new Intent(ACTION_BUTTON);
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
		PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
		PendingIntent intent_next = PendingIntent.getBroadcast(this, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);
		
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
				.setWhen(System.currentTimeMillis())
				.setTicker("setTicker")
				.setPriority(Notification.PRIORITY_DEFAULT)
				.setOngoing(true)
				.setSmallIcon(R.drawable.sing_icon);
		Notification notify = mBuilder.build();
		notify.flags = Notification.FLAG_ONGOING_EVENT;
//		notify.contentView = mRemoteViews;
//		notify.contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
		mNotificationManager.notify(200, notify);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_custom:
			shwoNotify();
			break;
		case R.id.btn_show_custom_button:
			showButtonNotify();
			break;
		default:
			break;
		}
	}
	
	public void initButtonReceiver(){
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		registerReceiver(bReceiver, intentFilter);
	}
	
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	public final static int BUTTON_PREV_ID = 1;
	public final static int BUTTON_PALY_ID = 2;
	public final static int BUTTON_NEXT_ID = 3;

	public class ButtonBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if(action.equals(ACTION_BUTTON)){
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				switch (buttonId) {
				case BUTTON_PREV_ID:
					Log.d(TAG , "BUTTON_PREV_ID");
					Toast.makeText(getApplicationContext(), "BUTTON_PREV_ID", Toast.LENGTH_SHORT).show();
					break;
				case BUTTON_PALY_ID:
					String play_status = "";
					isPlay = !isPlay;
					if(isPlay){
						play_status = "BUTTON_PALY_ID";
					}else{
						play_status = "BUTTON_PALY_ID";
					}
					showButtonNotify();
					Log.d(TAG , play_status);
					Toast.makeText(getApplicationContext(), play_status, Toast.LENGTH_SHORT).show();
					break;
				case BUTTON_NEXT_ID:
					Log.d(TAG , "BUTTON_NEXT_ID");
					Toast.makeText(getApplicationContext(), "BUTTON_NEXT_ID", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(bReceiver != null){
			unregisterReceiver(bReceiver);
		}
		super.onDestroy();
	}
}
