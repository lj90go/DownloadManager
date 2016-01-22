package com.jieluo.downloadmanager.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.jieluo.downloadmanager.http.HttpControl;
import com.jieluo.downloadmanager.http.HttpDownload;
import com.jieluo.downloadmanager.http.HttpMonitor;

import android.util.Log;
 
public class DownloadRunnable implements Runnable,HttpMonitor {

	private String url;
	private String localPath;
	private String id;
	private MonitorProgress monitor;//进度监控
	private EndDownload endDownload;
	public HashMap<String,ObjectInfo> downloadInfoList = new HashMap<String, ObjectInfo>();
	private HttpDownload httpDownload;
	private ZyfdInfo info;
	public DownloadRunnable(String url,String localpath,HashMap<String,ObjectInfo> downloadInfoList,String id,MonitorProgress monitor,EndDownload endDownload) 
	{
		this.url = url;
		this.localPath = localpath;
		this.downloadInfoList = downloadInfoList;
		this.id = id;
		this.monitor = monitor;
		this.endDownload = endDownload;
		httpDownload = new HttpDownload();
	}
	
	public boolean getPauseState() {
		return httpDownload!=null?httpDownload.isPaused():false;
	}

	public void setPauseState(boolean downState) {
		if(httpDownload!=null)
		{
			httpDownload.setPaused(downState);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
		String filepath = HttpControl.getLocalPath(url,localPath);
		if(httpDownload!=null)
		{
			httpDownload.Httpdown_single(url, this, isRange, filepath);
		}

//		if(info.getAllSize()==info.getDownloadsize())
//		{
//			endDownload.endDownload(id);
//		}
	}

	
	public interface EndDownload
	{
		public void endDownload(String id);
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
