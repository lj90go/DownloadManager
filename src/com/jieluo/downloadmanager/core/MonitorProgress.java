package com.jieluo.downloadmanager.core;

public interface MonitorProgress {

	public void loadingProgress(ObjectInfo info);
	public void pauseProgress(ObjectInfo info);
	public void removeProgress(ObjectInfo info);
}
