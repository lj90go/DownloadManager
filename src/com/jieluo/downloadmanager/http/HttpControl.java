package com.jieluo.downloadmanager.http;

import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpControl 
{

	/**
	 * Judege the url type:support range-type.
	 * @param url 
	 * @return true:support range-type.
	 */
	public static boolean JudgeUrlloadType(String url)
	{
		boolean isRange = false;
		HttpURLConnection tempURLCon = null;
		URL urlconn = null;
		try {
			urlconn = new URL(url);
			tempURLCon = (HttpURLConnection) urlconn.openConnection();
			tempURLCon.setRequestProperty("Range", "bytes=" + 0 + "-" + 1024);
			int length = tempURLCon.getContentLength();
			if(length==1025)
			{
				isRange = true;
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Log.e("network", "networkError");
		}
		if(tempURLCon!=null)
		{
			tempURLCon.disconnect();
		}
		return isRange;
	}
	/**
	 * Get the local path for saving.
	 * @param url download url String.
	 * @return localpath
	 */
	public static String getLocalPath(String url,String localPath)
	{
		String name = localPath;
		if(!name.endsWith("/"))
		{
			name += "/";
		}
		String splits[] = url.split("/");
		if(splits!=null&&splits.length>0)
		{
			name += splits[splits.length-1];
		}
		
		return name;
	}
}
