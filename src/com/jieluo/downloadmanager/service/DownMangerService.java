package com.jieluo.downloadmanager.service;

import com.jieluo.downloadmanager.core.DownloadManager;
import com.jieluo.downloadmanager.core.ThreadManager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownMangerService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	
}
