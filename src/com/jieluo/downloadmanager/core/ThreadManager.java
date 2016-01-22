package com.jieluo.downloadmanager.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import com.jieluo.downloadmanager.core.DownloadRunnable.EndDownload;

import android.util.Log;
/**
 * 下载管控类，添加下载，暂停下载。
 * 基于线程池做管理。
 * @author jieluo
 *
 */
public class ThreadManager implements DownloadManager,EndDownload
{

	private ThreadPoolExecutor mainExcutor; //主线程池
	private HashMap<String,List<DownloadRunnable>> allRunnables;//进程集合
	private HashMap<String,List<String>> allUrls;//下载链接集合
	public HashMap<String,ObjectInfo> downloadInfoList = new HashMap<String, ObjectInfo>();//保存下载记录
	public HashMap<String,MonitorProgress> progressList = new HashMap<String, MonitorProgress>();//监控下载进度
	public ThreadManager(int maxThreadNumber) 
	{
		if(mainExcutor==null||mainExcutor.isShutdown())
		{
			mainExcutor = new ThreadPoolExecutor(2, maxThreadNumber,
					1000, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>(),
					Executors.defaultThreadFactory(), new AbortPolicy());
		}
		if(allRunnables==null)
		{
			allRunnables = new HashMap<String, List<DownloadRunnable>>();
		}
		if(allUrls==null)
		{
			allUrls = new HashMap<String, List<String>>();		
		}
		
	}

	@Override
	public void addDownload(String id,List<String> urls,String localpath,MonitorProgress monitor)
	{
		if(allRunnables==null)
		{
			allRunnables = new HashMap<String, List<DownloadRunnable>>();
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
		List<DownloadRunnable> runnableList = new ArrayList<DownloadRunnable>();
		for(int i=0;i<urls.size();i++)
		{
			DownloadRunnable runnable = new DownloadRunnable(urls.get(i),localpath,downloadInfoList,id,monitor,this);
			runnableList.add(runnable);
			mainExcutor.execute(runnable);
		}
		allRunnables.put(id, runnableList);
	}

	@Override
	public void pauseDownlaod(String id) 
	{
		// TODO Auto-generated method stub
		List<DownloadRunnable> runnables = allRunnables.get(id);
		if(mainExcutor!=null&&!mainExcutor.isShutdown()&&!mainExcutor.isTerminating()&&runnables!=null&&runnables.size()>0)
		{
			Log.e("runnable", "96");
			for(int i=0;i<runnables.size();i++)
			{
				DownloadRunnable runable = runnables.get(i);
				if(mainExcutor.getQueue().contains(runable))
				{
					mainExcutor.remove(runable);
					Log.e("runnable", "remove");
				}
				if(runable!=null)
				{
					runable.setPauseState(true);
					Log.e("runnable", "state 0");
				}
			}
			synchronized (allRunnables) 
			{
				allRunnables.remove(id);
				Log.e("runnable", "remove runnables");
			}
			synchronized (allUrls) 
			{
				allUrls.remove(id);
				Log.e("runnable", "remove urls");
			}
			synchronized (downloadInfoList) 
			{
				downloadInfoList.remove(id);
			}
			synchronized (progressList) 
			{
				progressList.remove(id);
			}
		}
	}

	@Override
	public void removeAll() 
	{
		// TODO Auto-generated method stub
		if(mainExcutor!=null&&!mainExcutor.isShutdown())
		{
			mainExcutor.shutdownNow();
		}
		synchronized (allRunnables) 
		{
			if(allRunnables!=null)
			{
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

	@Override
	public void endDownload(String id) {
		// TODO Auto-generated method stub
		List<DownloadRunnable> runnables = allRunnables.get(id);
		if(mainExcutor!=null&&!mainExcutor.isShutdown()&&!mainExcutor.isTerminating()&&runnables!=null&&runnables.size()>0)
		{
			Log.e("runnable", "96");
			for(int i=0;i<runnables.size();i++)
			{
				DownloadRunnable runable = runnables.get(i);
				if(mainExcutor.getQueue().contains(runable))
				{
					mainExcutor.remove(runable);
					Log.e("runnable", "remove");
				}
				if(runable!=null)
				{
					runable.setPauseState(true);
					Log.e("runnable", "state 0");
				}
			}
			synchronized (allRunnables) 
			{
				allRunnables.remove(id);
				Log.e("runnable", "remove runnables");
			}
			synchronized (allUrls) 
			{
				allUrls.remove(id);
				Log.e("runnable", "remove urls");
			}
			synchronized (downloadInfoList) 
			{
				downloadInfoList.remove(id);
			}
			synchronized (progressList) 
			{
				progressList.remove(id);
			}
		}
	}
	
	
}
