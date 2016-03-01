package com.jieluo.downloadmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.jieluo.downloadmanager.core.AsyncTaskManager;
import com.jieluo.downloadmanager.core.DownloadManager;
import com.jieluo.downloadmanager.core.MonitorProgress;
import com.jieluo.downloadmanager.core.ThreadManager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class DownMangerService extends Service {

	public DownloadManager dm = null;
	public DownloadReceiver addDownloadReceiver = null;
	public static String ADDDOWNLOAD_ACTION = "cn.jl.addurls";
	public static String URLS = "urls";
	public static String ID = "id";
	public static String LOCALPATH = "localpath";
	private MonitorProgress monitor = null;
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

	private void addDownload(String[] urls,String id,String localpath,int type)
	{
		if(urls==null||id==null||id.equals("")||localpath==null||localpath.equals("")||monitor==null)
		{
			return;
		}
		if(type==1)
		{
			dm = new AsyncTaskManager();
		}else if(type==2)
		{
			dm = new ThreadManager(20);
		}
		if(dm==null)
		{
			return;
		}
		List<String> urlList = new ArrayList<String>();
		for(int i=0;i<urls.length;i++)
		{
			urlList.add(urls[i]);
		}
		dm.addDownload(id, urlList, localpath, monitor);
	}
	
	class DownloadReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context arg0, Intent arg1) 
		{
			// TODO Auto-generated method stub
			String action = arg1.getAction();
			if(action.equals(ADDDOWNLOAD_ACTION))
			{
				Bundle bundle = arg1.getExtras();
				if(bundle!=null)
				{
					String[] urls = bundle.getStringArray(URLS);
					String id = bundle.getString(ID);
					String localPath = bundle.getString(LOCALPATH)
;				}
			}
		}
		
	}
}
