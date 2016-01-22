package com.jieluo.downloadmanager.core;

import java.util.List;

public interface DownloadManager 
{

	public void addDownload (String id,List<String> urls,String localpath,MonitorProgress monitor);
	public void pauseDownlaod(String id);
	public void removeAll();
}
