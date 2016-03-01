package com.example.notifications;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.notifications.base.BaseActivity;

public class ProgressAcitivty extends BaseActivity implements OnClickListener {
	private Button btn_show_progress;
	private Button btn_show_un_progress;
	private Button btn_show_custom_progress;
	int notifyId = 102;
	int progress = 0;
	NotificationCompat.Builder mBuilder;
	Button btn_download_start;
	Button btn_download_pause;
	Button btn_download_cancel;
	public boolean isPause = false;
	public boolean isCustom = false;
	DownloadThread downloadThread;
	public Boolean indeterminate = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		initView();
		initNotify();
	}

	private void initView() {
		btn_show_progress = (Button) findViewById(R.id.btn_show_progress);
		btn_show_un_progress = (Button) findViewById(R.id.btn_show_un_progress);
		btn_show_custom_progress = (Button) findViewById(R.id.btn_show_custom_progress);
		btn_download_start = (Button) findViewById(R.id.btn_download_start);
		btn_download_pause = (Button) findViewById(R.id.btn_download_pause);
		btn_download_cancel = (Button) findViewById(R.id.btn_download_cancel);
		btn_show_progress.setOnClickListener(this);
		btn_show_un_progress.setOnClickListener(this);
		btn_show_custom_progress.setOnClickListener(this);
		btn_download_start.setOnClickListener(this);
		btn_download_pause.setOnClickListener(this);
		btn_download_cancel.setOnClickListener(this);
	}

	private void initNotify() {
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setWhen(System.currentTimeMillis())
				.setContentIntent(getDefalutIntent(0))
				// .setNumber(number)
				.setPriority(Notification.PRIORITY_DEFAULT)
				// .setAutoCancel(true)
				.setOngoing(false)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND
				// requires VIBRATE permission
				.setSmallIcon(R.drawable.icon);
	}

	public void showProgressNotify() {
		mBuilder.setContentTitle("setContentTitle")
				.setContentText("setContentText")
				.setTicker("setTicker");
		if(indeterminate){
			mBuilder.setProgress(0, 0, true);
		}else{
			mBuilder.setProgress(100, progress, false);
		}
		mNotificationManager.notify(notifyId, mBuilder.build());
	}
	
	private void showCustomProgressNotify(String status) {
		RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_custom_progress);
		mRemoteViews.setImageViewResource(R.id.custom_progress_icon, R.drawable.icon);
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "setTextViewText");
		mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, status);
		if(progress >= 100 || downloadThread == null){
			mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
			mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
		}else{
			mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress, false);
			mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.VISIBLE);
		}
		mBuilder.setContent(mRemoteViews)
				.setContentIntent(getDefalutIntent(0))
				.setTicker("setTicker");
		Notification nitify = mBuilder.build();
		nitify.contentView = mRemoteViews;
		mNotificationManager.notify(notifyId, nitify);
	}
	
	public void startDownloadNotify() {
		isPause = false;
		if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
		}else{
			downloadThread = new DownloadThread();
			downloadThread.start();
		}
	}

	public void pauseDownloadNotify() {
		isPause = true;
		if(!isCustom){
			mBuilder.setContentTitle("setContentTitle");
			setNotify(progress);
		}else{
			showCustomProgressNotify("showCustomProgressNotify");
		}
	}

	public void stopDownloadNotify() {
		if (downloadThread != null) {
			downloadThread.interrupt();
		}
		downloadThread = null;
		if(!isCustom){
			mBuilder.setContentTitle("setContentTitle").setProgress(0, 0, false);
			mNotificationManager.notify(notifyId, mBuilder.build());
		}else{
			showCustomProgressNotify("showCustomProgressNotify");
		}
	}

	public void setNotify(int progress) {
		mBuilder.setProgress(100, progress, false);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}


	class DownloadThread extends Thread {

		@Override
		public void run() {
			int now_progress = 0;
			// Do the "lengthy" operation 20 times
			while (now_progress <= 100) {
				// Sets the progress indicator to a max value, the
				// current completion percentage, and "determinate"
				if(downloadThread == null){
					break;
				}
				if (!isPause) {
					progress = now_progress;
					if(!isCustom){
						mBuilder.setContentTitle("setContentTitle");
						if(!indeterminate){
							setNotify(progress);
						}
					}else{
						showCustomProgressNotify("showCustomProgressNotify");
					}
					now_progress += 10;
				}
				try {
					// Sleep for 1 seconds
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
				}
			}
			// When the loop is finished, updates the notification
			if(downloadThread != null){
				if(!isCustom){
					mBuilder.setContentText("setContentText")
					// Removes the progress bar
					.setProgress(0, 0, false);
					mNotificationManager.notify(notifyId, mBuilder.build());
				}else{
					showCustomProgressNotify("showCustomProgressNotify");
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show_progress:
			downloadThread = null;
			isCustom = false;
			indeterminate = false;
			showProgressNotify();
			break;
		case R.id.btn_show_un_progress:
			downloadThread = null;
			isCustom = false;
			indeterminate = true;
			showProgressNotify();
			break;
		case R.id.btn_show_custom_progress:
			downloadThread = null;
			isCustom = true;
			indeterminate = false;
			showCustomProgressNotify("showCustomProgressNotify");
			break;
		case R.id.btn_download_start:
			startDownloadNotify();
			break;
		case R.id.btn_download_pause:
			pauseDownloadNotify();
			break;
		case R.id.btn_download_cancel:
			stopDownloadNotify();
			break;
		default:
			break;
		}
	}
}
