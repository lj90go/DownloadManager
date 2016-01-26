package com.jieluo.downloadmanager.http;

/**
 * Monitor downloading file progress .
 * @author edutech
 *
 */
public interface HttpMonitor
{
	/**
	 * Count the total size(single objectInfo).
	 * Save url infomation.
	 * @param url           url for downlad.
	 * @param downloadsize  downloaded size.
	 * @param extrasize     extrasize to be downloaded.
	 */
	public void initTotal(String url,long downloadsize,long extrasize);
	
	/**
	 * Update the progress.
	 * Save the file infomation.
	 * @param url
	 * @param downloadsize
	 * @param byteslength
	 */
	public void updateDownload(String url,long downloadsize,long byteslength);
	
	/**
	 * If one file has downloaded,this method will be avoked.
	 * If all urls in the objectInfo has be downloaded,it will add another objectInfo to the download pool.
	 * @param url  the url has be downloaded.
	 */
	public void overDownload(String url);
}
