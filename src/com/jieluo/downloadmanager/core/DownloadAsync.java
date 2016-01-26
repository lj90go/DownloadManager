package com.jieluo.downloadmanager.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jieluo.downloadmanager.core.DownloadRunnable.EndDownload;
import com.jieluo.downloadmanager.http.HttpControl;
import com.jieluo.downloadmanager.http.HttpDownload;
import com.jieluo.downloadmanager.http.HttpMonitor;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadAsync extends AsyncTask<String, Integer, String> implements HttpMonitor
{

	private HashMap<String,ObjectInfo> downloadInfoList = new HashMap<String, ObjectInfo>();
	private MonitorProgress monitor;
	private ZyfdInfo info;
	private String id;
	private HttpDownload httpDownload;
	private EndDownload endDownload;
	private String url;
	private String localpath;
	public DownloadAsync(MonitorProgress monitor,HashMap<String,ObjectInfo> downloadInfoList,EndDownload enddownload,String id,String url,String localPath) 
	{
		this.monitor = monitor;
		this.downloadInfoList = downloadInfoList;
		httpDownload = new HttpDownload();
		this.endDownload = enddownload;
		this.id = id;
		this.url = url;
		this.localpath = localPath;
	}


	@Override
	protected String doInBackground(String... arg0) 
	{
		// TODO Auto-generated method stub
//		Log.e("download", id+","+url);
		boolean isRange = false;
	    info = (ZyfdInfo) downloadInfoList.get(id);
		if(info==null)
		{
			info = new ZyfdInfo();
			synchronized (downloadInfoList) {
				downloadInfoList.put(id, info);
			}
		}
		info.setLoadingInfo(url, 0);
		isRange = HttpControl.JudgeUrlloadType(url);
		String filepath = HttpControl.getLocalPath(url,localpath);
		if(httpDownload!=null)
		{
			httpDownload.Httpdown_single(url, this, isRange, filepath);
		}
		return "";
	}

	

	@Override
	protected void onPostExecute(String result) 
	{
		// TODO Auto-generated method stub
	}


	@Override
	protected void onProgressUpdate(Integer... values) 
	{
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}


	public boolean isPaused()
	{
		return httpDownload!=null?httpDownload.isPaused():false;

	}


	public void setPaused(boolean paused) 
	{
		if(httpDownload!=null)
		{
			httpDownload.setPaused(paused);
		}
	}


	@Override
	public void initTotal(String url, long downloadsize, long extrasize) {
		// TODO Auto-generated method stub
		info.setLoadingInfo(url, downloadsize);
		info.setDownloadsize(info.getDownloadsize()+downloadsize);
		info.setAllSize(info.getAllSize()+downloadsize+extrasize);
		synchronized (downloadInfoList) {
			downloadInfoList.put(id, info);
		}
		monitor.loadingProgress(info);
	}

	@Override
	public void updateDownload(String url, long downloadsize, long byteslength) {
		// TODO Auto-generated method stub
		info.setLoadingInfo(url, downloadsize);
		info.setDownloadsize(info.getDownloadsize()+byteslength);
		synchronized (downloadInfoList) {
			downloadInfoList.put(id, info);
		}
		monitor.loadingProgress(info);
	}


	@Override
	public void overDownload(String url) {

		// TODO Auto-generated method stub
		info.setLoadingInfo(url, 1);
		List<String> urls = info.getUrls();
		HashMap<String,Long> maps = info.getLoadingInfo();
//		Log.e("download", maps.toString() );
		boolean alldownloaded = true;
		if(urls!=null&&urls.size()>0)
		{
			for(int i=0;i<urls.size();i++)
			{
				String u = urls.get(i);
				if(maps==null&&maps.size()<0)
				{
					alldownloaded = false;
				}else if(maps.containsKey(u))
				{
					long download = maps.get(u);
//					Log.e("download", u+","+download+","+i+","+urls.size());
					if(download!=1)
					{
						alldownloaded = false;
						break;
					}
				}else
				{
					alldownloaded = false;
					break;
				}
			}
		}
		Log.e("download", alldownloaded+"");
		if(alldownloaded)
		{
			info.setHasDownloaded(true);
			endDownload.endDownload(id);
		}
		synchronized (downloadInfoList) {
			downloadInfoList.put(id, info);
		}
	
	}

	
}
