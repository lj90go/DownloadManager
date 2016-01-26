package com.jieluo.downloadmanager.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import com.jieluo.downloadmanager.core.DownloadRunnable.EndDownload;

import android.os.AsyncTask;
import android.util.Log;


public class AsyncTaskManager implements DownloadManager ,EndDownload
{

	private HashMap<String,List<DownloadAsync>> allRunnables; //管理所有下载
	private HashMap<String,List<String>> waitingUrls;
	public HashMap<String,ObjectInfo> downloadInfoList = new HashMap<String, ObjectInfo>();
	public HashMap<String,MonitorProgress> progressList = new HashMap<String, MonitorProgress>();
	
	public AsyncTaskManager() 
	{
		if(allRunnables==null)
		{
			allRunnables = new HashMap<String, List<DownloadAsync>>();
		}
		if(waitingUrls==null)
		{
			waitingUrls = new HashMap<String, List<String>>();
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
		if(waitingUrls==null)
		{
			waitingUrls = new HashMap<String, List<String>>();
		}
		if(downloadInfoList==null)
		{
			downloadInfoList = new HashMap<String, ObjectInfo>();
		}
		if(progressList==null)
		{
			progressList = new HashMap<String, MonitorProgress>();
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
		synchronized (allRunnables)
		{

			List<DownloadAsync> runnableList = new ArrayList<DownloadAsync>();
			for(int i=0;i<urls.size();i++)
			{
				String url = urls.get(i);
				DownloadAsync async = new DownloadAsync(monitor,downloadInfoList,this,id,url,localpath);
				runnableList.add(async);
				if(allRunnables.size()<=5)
				{
					async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
				}else
				{
					synchronized (waitingUrls) 
					{
						waitingUrls.put(id, urls);
					}
				}
			}
			allRunnables.put(id, runnableList);		
		}
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
		synchronized (waitingUrls)
		{
			if(waitingUrls!=null&&waitingUrls.size()>0)
			{
				Set<String> keys = waitingUrls.keySet();
				if(keys!=null&&keys.size()>0)
				{
					String key = keys.iterator().next();
					if(key!=null)
					{
						synchronized (allRunnables) 
						{
							
								List<DownloadAsync> runnableList = allRunnables.get(key);
								if(runnableList!=null&&runnableList.size()>0)
								{
									for(int i=0;i<runnableList.size();i++)
									{
										DownloadAsync async = runnableList.get(i);
										async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
									}
								}
								waitingUrls.remove(key);
							
						}
					}
				}
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
				if(keysets!=null&&keysets.size()>0)
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
					}
				}
				allRunnables.clear();
				allRunnables = null;
			}
		}
		synchronized (waitingUrls) {
			if(waitingUrls!=null)
			{
				waitingUrls.clear();
				waitingUrls = null;
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

	@Override
	public void endDownload(String id) {
		// TODO Auto-generated method stub

		Log.e("download", "enddownload:"+id+","+waitingUrls.size()+","+allRunnables.size());
		synchronized (allRunnables) {
			if(allRunnables!=null)
			{
				if(allRunnables.containsKey(id))
				{
					List<DownloadAsync> runables = allRunnables.get(id);
					if(runables!=null)
					{
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
		synchronized (downloadInfoList) {
			if(downloadInfoList.containsKey(id))
			{
				downloadInfoList.remove(id);
			}
		}
		synchronized (progressList) {
			if(progressList.containsKey(id))
			{
				progressList.remove(id);
			}
		}
		
		synchronized (waitingUrls)
		{
			if(waitingUrls!=null&&waitingUrls.size()>0)
			{
				Set<String> keys = waitingUrls.keySet();
				if(keys!=null&&keys.size()>0)
				{
					String key = keys.iterator().next();
					if(key!=null)
					{
						synchronized (allRunnables) 
						{
							List<DownloadAsync> runnableList = allRunnables.get(key);
							if(runnableList!=null&&runnableList.size()>0)
							{
								for(int i=0;i<runnableList.size();i++)
								{
									DownloadAsync async = runnableList.get(i);
									async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
								}
							}
							waitingUrls.remove(key);					
						}
					}
				}
			}
		}
	}


}
