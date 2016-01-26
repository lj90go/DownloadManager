package com.jieluo.downloadmanager.core;

/**
 * Monitor the progress the files which are downloading.
 * @author jieluo
 *
 */
public interface MonitorProgress {

	/**
	 * The files in download,return the objectInfo information.
	 * @param info the objectinfo information.which contains the all size,downloaded size, download url and so on.
	 */
	public void loadingProgress(ObjectInfo info);
	
	/**
	 * The objectInfo is paused.
	 * @param info the objectinfo information.which contains the all size,downloaded size, download url and so on.
	 */
	public void pauseProgress(ObjectInfo info);
	
	/**
	 * The objectInfo is removed.
	 * @param info the objectinfo information.which contains the all size,downloaded size, download url and so on.
	 */
	public void removeProgress(ObjectInfo info);
}
