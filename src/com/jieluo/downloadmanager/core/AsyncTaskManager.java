package com.jieluo.downloadmanager.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import android.os.AsyncTask;
import android.util.Log;


public class AsyncTaskManager implements DownloadManager 
{

	private HashMap<String,List<DownloadAsync>> allRunnables;
	private HashMap<String,List<String>> allUrls;
	public HashMap<String,ObjectInfo> downloadInfoList = new HashMap<String, ObjectInfo>();
	public HashMap<String,MonitorProgress> progressList = new HashMap<String, MonitorProgress>();
	
	
	public AsyncTaskManager() 
	{
		if(allRunnables==null)
		{
			allRunnables = new HashMap<String, List<DownloadAsync>>();
		}
		if(allUrls==null)
		{
			allUrls = new HashMap<String, List<String>>();
		}
	}

	@Override
	public void addDownload(String id, List<String> urls, String localpath,
			MonitorProgress monitor) {
		// TODO Auto-generated method stub
		if(allRunnables==null)
		{
			allRunnables = new HashMap<String, List<DownloadAsync>>();
		}
		if(allUrls==null)
		{
			allUrls = new HashMap<String, List<String>>();
		}
		if(downloadInfoList==null)
		{
			downloadInfoList = new HashMap<String, ObjectInfo>();
		}
		if(progressList==null)
		{
			progressList = new HashMap<String, MonitorProgress>();
		}
		synchronized (allUrls) 
		{
			allUrls.put(id, urls);
		}
		synchronized (downloadInfoList) 
		{
			ZyfdInfo info = new ZyfdInfo();
			info.setUrls(urls);
			info.setDownloadid(id);
			downloadInfoList.put(id, info);
		}
		synchronized (progressList) {
			progressList.put(id, monitor);
		};
		List<DownloadAsync> runnableList = new ArrayList<DownloadAsync>();
		for(int i=0;i<urls.size();i++)
		{
			String url = urls.get(i);
			DownloadAsync async = new DownloadAsync(monitor,downloadInfoList);
			async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id,localpath,url);
			runnableList.add(async);
		}
		allRunnables.put(id, runnableList);
	}

	@Override
	public void pauseDownlaod(String id) {
		// TODO Auto-generated method stub
		synchronized (allRunnables) {
			if(allRunnables!=null)
			{
				if(allRunnables.containsKey(id))
				{
					Log.e("runnable", "86");
					List<DownloadAsync> runables = allRunnables.get(id);
					if(runables!=null)
					{
						Log.e("runnable", "runnable:"+runables.size());
						for(int i=0;i<runables.size();i++)
						{
							DownloadAsync async = runables.get(i);
							async.setPaused(true);
							async.cancel(true);
							Log.e("asyncpause", "paused");
						}
					}
					allRunnables.remove(id);
				}
			}
		}
		synchronized (allUrls) {
			if(allUrls.containsKey(id))
			{
				Log.e("runnable", "104");
				allUrls.remove(id);
			}
		}
		synchronized (downloadInfoList) {
			if(downloadInfoList.containsKey(id))
			{
				Log.e("runnable", "111");
				downloadInfoList.remove(id);
			}
		}
		synchronized (progressList) {
			if(progressList.containsKey(id))
			{
				Log.e("runnable", "118");
				progressList.remove(id);
			}
		}
		
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		synchronized (allRunnables) {
			if(allRunnables!=null)
			{
				Set<String> keysets = allRunnables.keySet();
				if(keysets!=null)
				{
					Iterator<String> itor = keysets.iterator();
					while(itor.hasNext())
					{
						String id = itor.next();
						List<DownloadAsync> runables = allRunnables.get(id);
						if(runables!=null)
						{
							for(int i=0;i<runables.size();i++)
							{
								DownloadAsync async = runables.get(i);
								async.setPaused(true);
							}
						}
						allRunnables.remove(id);
					}
				}
				allRunnables.clear();
				allRunnables = null;
			}
		}
		synchronized (allUrls) {
			if(allUrls!=null)
			{
				allUrls.clear();
				allUrls = null;
			}
		}
		synchronized (downloadInfoList) {
			if(downloadInfoList!=null)
			{
				downloadInfoList.clear();
				downloadInfoList = null;
			}
		}
		synchronized (progressList) {
			if(progressList!=null)
			{
				progressList.clear();
				progressList = null;
			}
		}
	}


}
