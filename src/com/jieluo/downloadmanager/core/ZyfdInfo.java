package com.jieluo.downloadmanager.core;

import java.util.HashMap;
import java.util.List;

import android.util.Log;

public class ZyfdInfo extends ObjectInfo 
{
	private List<String> urls;
	private String type;
	private HashMap<String, Long> loadingInfo;
	public List<String> getUrls() {
		
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public HashMap<String, Long> getLoadingInfo() {
		return loadingInfo;
	}
	public void setLoadingInfo(String url,long size) {
		if(loadingInfo==null)
		{
			loadingInfo = new HashMap<String, Long>();
		}
		loadingInfo.put(url, size);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Log.e("zyfdinfo", downloadid+","+downloadsize+","+loadingInfo.toString());
		return super.toString();
	}
	
	

}
