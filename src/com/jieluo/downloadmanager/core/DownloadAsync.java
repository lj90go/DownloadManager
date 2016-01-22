package com.jieluo.downloadmanager.core;

import java.util.HashMap;

import com.jieluo.downloadmanager.http.HttpControl;
import com.jieluo.downloadmanager.http.HttpDownload;
import com.jieluo.downloadmanager.http.HttpMonitor;

import android.os.AsyncTask;

public class DownloadAsync extends AsyncTask<String, Integer, String> implements HttpMonitor
{

	private HashMap<String,ObjectInfo> downloadInfoList = new HashMap<String, ObjectInfo>();
	private MonitorProgress monitor;
	private ZyfdInfo info;
	private String id;
	private HttpDownload httpDownload;
	public DownloadAsync(MonitorProgress monitor,HashMap<String,ObjectInfo> downloadInfoList) 
	{
		this.monitor = monitor;
		this.downloadInfoList = downloadInfoList;
		httpDownload = new HttpDownload();
	}


	@Override
	protected String doInBackground(String... arg0) 
	{
		// TODO Auto-generated method stub
		id = arg0[0];
		String localpath = arg0[1];
		String url = arg0[2];
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

	
}
