package com.jieluo.downloadmanager.http;

public interface HttpMonitor
{
	public void initTotal(String url,long downloadsize,long extrasize);
	public void updateDownload(String url,long downloadsize,long byteslength);
}
