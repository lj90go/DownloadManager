package com.jieluo.downloadmanager.core;

import java.util.List;

/**
 * 
 * @author jieluo
 *
 */
public interface DownloadManager 
{

	/**
	 * add download ObjectInfo.
	 * @param id          objetctinfo id.
	 * @param urls        url list.
	 * @param localpath   the localpath(File directory)
	 * @param monitor     The monitor for Update progress.
	 */
	public void addDownload (String id,List<String> urls,String localpath,MonitorProgress monitor);
	
	/**
	 * Pausedown some objectinfo
	 * @param id the objectinfo id
	 */
	public void pauseDownlaod(String id);
	
	/**
	 * Remove all objectinfos which are downloading.
	 */
	public void removeAll();
}
