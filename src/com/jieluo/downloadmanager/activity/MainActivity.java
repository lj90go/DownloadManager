package com.jieluo.downloadmanager.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jieluo.downloadmanager.R;
import com.jieluo.downloadmanager.R.id;
import com.jieluo.downloadmanager.R.layout;
import com.jieluo.downloadmanager.R.menu;
import com.jieluo.downloadmanager.core.AsyncTaskManager;
import com.jieluo.downloadmanager.core.DownloadManager;
import com.jieluo.downloadmanager.core.MonitorProgress;
import com.jieluo.downloadmanager.core.ObjectInfo;
import com.jieluo.downloadmanager.core.ThreadManager;
import com.jieluo.downloadmanager.core.ZyfdInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity implements MonitorProgress {

	private String path = Environment
			.getExternalStorageDirectory().getAbsolutePath()+"/"+"downloadmanager/";
	private List<String> urls;
    DownloadManager dm = null;
    private ListView listview;
    private List<String> listInfo = new ArrayList<String>();
    private HashMap<String,ZyfdInfo> map = new HashMap<String,ZyfdInfo>();
    private ListAdapter adapter;
    private Button btn;
    private Button threadBtn;
    private Button asyncBtn;
    private int downloadType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DownloadManager dm = new ThreadManager(10);
        File file0 = new File(path+"10/");
        file0.mkdir();
        File file1 = new File(path+"11/");
        file1.mkdir();
        File file2 = new File(path+"12/");
        file2.mkdir();
        File file3 = new File(path+"13/");
        file3.mkdir();
        File file4 = new File(path+"14/");
        file4.mkdir();
        File file5 = new File(path+"15/");
        file5.mkdir();
        File file6 = new File(path+"16/");
        file6.mkdir();
        File file7 = new File(path+"17/");
        file7.mkdir();
        File file8 = new File(path+"18/");
        file8.mkdir();
        File file9 = new File(path+"19/");
        file9.mkdir();
        File file10 = new File(path+"20/");
        file10.mkdir();
        File file11 = new File(path+"21/");
        file11.mkdir();
        listview = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter();
        listview.setAdapter(adapter);
        threadBtn = (Button) findViewById(R.id.threadBtn);
        asyncBtn = (Button) findViewById(R.id.asyncBtn);
        btn = (Button)findViewById(R.id.pause);
        threadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ThreadPool_download();
				downloadType = 1;
			}
		});
        asyncBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AsyncControl_download();
				downloadType = 2;
			}
		});
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String text = btn.getText().toString();
				if(dm!=null)
				{
					if(text.equals("pause"))
					{
						dm.pauseDownlaod("10");
						btn.setText("start");
					}else
					{
						urls = new ArrayList<String>();
				        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
				        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
				        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
				        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
				        dm.addDownload("10", urls, path+"10/",MainActivity.this);
				        btn.setText("pause");
					}
				}
			}
		});
    }
    private void ThreadPool_download()
    {
    	dm = new ThreadManager(20);
    	map = new HashMap<String,ZyfdInfo>();
    	listInfo = new ArrayList<String>();
    	urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("10", urls, path+"10/",this);
        
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("11", urls, path+"11/",this);
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("12", urls, path+"12/",this);
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("13", urls, path+"13/",this);
        
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("14", urls, path+"14/",this);
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("15", urls, path+"15/",this);
        listInfo.add("10");
        listInfo.add("11");
        listInfo.add("12");
        map.put("10", new ZyfdInfo());
        map.put("11", new ZyfdInfo());
        map.put("12", new ZyfdInfo());
        listInfo.add("13");
        listInfo.add("14");
        listInfo.add("15");
        map.put("13", new ZyfdInfo());
        map.put("14", new ZyfdInfo());
        map.put("15", new ZyfdInfo());
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("16", urls, path+"16/",this);
        
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("17", urls, path+"17/",this);
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("18", urls, path+"18/",this);
        listInfo.add("16");
        listInfo.add("17"); 
        listInfo.add("18");
        map.put("16", new ZyfdInfo());
        map.put("17", new ZyfdInfo());
        map.put("18", new ZyfdInfo());
        
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("19", urls, path+"19/",this);
        
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("20", urls, path+"20/",this);
        urls = new ArrayList<String>();
        urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
        urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
        urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
        dm.addDownload("21", urls, path+"21/",this);
        listInfo.add("19");
        listInfo.add("20");
        listInfo.add("21");
        map.put("19", new ZyfdInfo());
        map.put("20", new ZyfdInfo());
        map.put("21", new ZyfdInfo());
         if(adapter!=null)
         {
        	 adapter.notifyDataSetChanged();
         }
    }
    private void AsyncControl_download()
    {
    	dm = new AsyncTaskManager();
    	map = new HashMap<String,ZyfdInfo>();
    	listInfo = new ArrayList<String>();
    	 urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("10", urls, path+"10/",this);
         
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("11", urls, path+"11/",this);
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("12", urls, path+"12/",this);
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("13", urls, path+"13/",this);
         
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("14", urls, path+"14/",this);
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("15", urls, path+"15/",this);
         listInfo.add("10");
         listInfo.add("11");
         listInfo.add("12");
         map.put("10", new ZyfdInfo());
         map.put("11", new ZyfdInfo());
         map.put("12", new ZyfdInfo());
         listInfo.add("13");
         listInfo.add("14");
         listInfo.add("15");
         map.put("13", new ZyfdInfo());
         map.put("14", new ZyfdInfo());
         map.put("15", new ZyfdInfo());
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("16", urls, path+"16/",this);
         
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("17", urls, path+"17/",this);
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("18", urls, path+"18/",this);
         listInfo.add("16");
         listInfo.add("17"); 
         listInfo.add("18");
         map.put("16", new ZyfdInfo());
         map.put("17", new ZyfdInfo());
         map.put("18", new ZyfdInfo());
         
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("19", urls, path+"19/",this);
         
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56985819a8d85b0ba068b47e.gif");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("20", urls, path+"20/",this);
         urls = new ArrayList<String>();
         urls.add("http://10.10.10.28:90/course/get-mongo-data/schoolid/4/file/56973ecfb6737f1c143fc554.json");
         urls.add("http://10.10.10.28:90/homeworktutor/56973ecfb6737f1c143fc552.mp3");
         urls.add("http://10.10.10.28:90/homeworktutor/56985816a8d85b0ba068b42b.mp4");
         dm.addDownload("21", urls, path+"21/",this);
         listInfo.add("19");
         listInfo.add("20");
         listInfo.add("21");
         map.put("19", new ZyfdInfo());
         map.put("20", new ZyfdInfo());
         map.put("21", new ZyfdInfo());
         if(adapter!=null)
         {
        	 adapter.notifyDataSetChanged();
         }
    }
    class ListAdapter extends BaseAdapter
    {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return map.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listInfo.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View contentView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
//			Log.e("listview", "listview");
			if(contentView==null)
			{
				contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.adapter,null);
				holder = new ViewHolder();
				holder.idTv = (TextView) contentView.findViewById(R.id.infoid);
				holder.stateTv = (TextView) contentView.findViewById(R.id.state);
				holder.bar = (ProgressBar) contentView.findViewById(R.id.progress);
				holder.bar.setMax(100);
				contentView.setTag(holder);
			}else
			{
				holder = (ViewHolder) contentView.getTag();
			}
			String id = listInfo.get(arg0);
			ZyfdInfo info = map.get(id);
			long total = info.getAllSize();
			long downloaded = info.getDownloadsize();
			int percent = 0;
			if(total>0)
			{
				percent = (int) (downloaded*100/total);
			}
			holder.idTv.setText(id+"");
			holder.stateTv.setText(downloaded+"/"+total);
			holder.bar.setProgress(percent);
			return contentView;
		}
    	class ViewHolder
    	{
    		TextView idTv;
    		TextView stateTv;
    		ProgressBar bar;
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dm!=null)
		{
			dm.removeAll();
		}
	}
	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int what = msg.what;
			switch (what) {
			case 0:
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	public void loadingProgress(ObjectInfo info) {
		// TODO Auto-generated method stub
//		 info.toString();
//		Log.e("listview", "progress");
		 String id = info.getDownloadid();
		 if(listInfo.contains(id))
		 {
			 map.put(id, (ZyfdInfo)info);
			}
		 Message msg = handler.obtainMessage();
		 msg.what = 0;
		 msg.sendToTarget();
	}

	@Override
	public void pauseProgress(ObjectInfo info) {
		// TODO Auto-generated method stub
		Log.e("info", info.toString());
	}

	@Override
	public void removeProgress(ObjectInfo info) {
		// TODO Auto-generated method stub
		Log.e("info", info.toString());
	}
    
}
