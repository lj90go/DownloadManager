package com.jieluo.downloadmanager.core;

import java.util.HashMap;
import java.util.List;

import android.util.Log;

/**
 * To save the download infomation.
 * Every ZyfdInfo maybe have serveral urls.
 * @author jieluo
 *
 */
public class ZyfdInfo extends ObjectInfo 
{
	private List<String> urls; //url list .
	private HashMap<String, Long> loadingInfo; //Url as key,downloaded size as value.
	private boolean hasDownloaded = false; //if all urls has be downloaded,the value will be true;
	
	public boolean isHasDownloaded() 
	{
		return hasDownloaded;
	}
	public void setHasDownloaded(boolean hasDownloaded)
	{
		this.hasDownloaded = hasDownloaded;
	}
	public List<String> getUrls() 
	{
		
		return urls;
	}
	public void setUrls(List<String> urls) 
	{
		this.urls = urls;
	}
	public HashMap<String, Long> getLoadingInfo() 
	{
		return loadingInfo;
	}
	public void setLoadingInfo(String url,long size) 
	{
		if(loadingInfo==null)
		{
			loadingInfo = new HashMap<String, Long>();
		}
		loadingInfo.put(url, size);
	}
	
	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		Log.e("zyfdinfo", downloadid+","+downloadsize+","+loadingInfo.toString());
		return super.toString();
	}
	
	

}
