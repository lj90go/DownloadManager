package com.jieluo.downloadmanager.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpDownload 
{
	private boolean paused = false;
	public void Httpdown_single(String url,HttpMonitor monitor,boolean isRange,String filepath)
	{
		InputStream in = null;
		HttpURLConnection conn = null;			
		URL urlconn = null;
		long downloadSize = 0;
		try {
			urlconn = new URL(url);
			conn = (HttpURLConnection) urlconn.openConnection();		
			Log.e("download", url+","+isRange+","+filepath);
			File file = new File(filepath);
			 if(isRange)
			{
				if(!file.exists())
				{
					file.createNewFile();
				}
				long size = file.length();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Referer", url);
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Accept-Encoding", "identity"); 
				conn.setRequestProperty(
								"User-Agent",
								"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Range", "bytes="
						+ size + "-");
				RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
				randomFile.seek(size);
				Log.e("download", size+","+conn.getContentLength()+","+conn.getResponseCode());
				downloadSize = size;		
				int extraLength = conn.getContentLength();
				boolean boo = false;
				//206说明该文件没接收完全，200表示已经接收完。
				if(conn.getResponseCode()!=206)
				{
					boo = true;
					extraLength = 0;
				}
				monitor.initTotal(url, downloadSize, extraLength);
				in = conn.getInputStream();
				byte[] bys = new byte[512];
				
				while(true&&!boo&&!paused)
				{
					int length = in.read(bys);
					if(length!=-1)
					{
						randomFile.write(bys, 0, length);
					}else
					{
						break;
					}
					monitor.updateDownload(url, downloadSize, length);

				}
				randomFile.close();
			}else
			{
				long downloadsize = conn.getContentLength();
				long extraSize = downloadSize;
				in = conn.getInputStream();
				if(file.exists())
				{
					if(downloadsize!=file.length())
					{
						file.delete();
						downloadsize = 0;
					}
				}
				monitor.initTotal(url, downloadSize, extraSize);
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				byte[] bys = new byte[512];
				while(true&&!paused)
				{
					int length = in.read(bys);
					if(length!=-1)
					{
						out.write(bys, 0, length);
					}else
					{
						break;
					}
					downloadSize +=length;
					monitor.updateDownload(url, downloadSize, length);

				}
				if(out!=null)
				{
					out.close();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(in!=null)
			{
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(conn!=null)
			{
				conn.disconnect();
			}
		}
	}
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
}
