package com.example.booking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import com.booking.Urls.Urls;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CommonActivity extends ListActivity {
	Urls urls;
	String url,username;
	TextView commonmsg;
	EditText keyword;
	Button search;
	List<MyRecord> exampleRecords;
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();
	GetbookingThread getbookingThread;
	GetbookingHandler getbookingHandler;
	
	SeekThread seekThread;
	SeekHandler seekHandler;
	String getbooking_Server_answer,seek_Server_answer,booking_Server_answer;
	MyAdapter myAdapter;
	String result;
	ImageView serviceimg;
	JSONArray getbooking_Server_answer_array;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_layout);
		initview();
		initoncilck();
		startnet();
		
        
	}
	/*
	@Override   
    protected void onListItemClick(ListView l, View v, int position, long id) { 
		super.onListItemClick(l, v, position, id);
		TextView tv2=(TextView)v.findViewById(R.id.lblLabel);
        Toast.makeText(CommonActivity.this, "You click: " + position+"content:"+tv2.getText().toString(), Toast.LENGTH_SHORT).show();      
    }  
 */
	private void initview() {
		url=Urls.getURL();
		username=Urls.getUserName();
		commonmsg = (TextView) this.findViewById(R.id.msg);
		serviceimg = (ImageView) this.findViewById(R.id.serviceimg);
		keyword = (EditText) this.findViewById(R.id.keyword);
		search = (Button) this.findViewById(R.id.search);
		
	}
	private void startnet() {
		getbookingHandler = new GetbookingHandler();
		getbookingThread = new GetbookingThread();
		getbookingThread.start();
	}
	private void startsearchnet() {
		seekHandler = new SeekHandler();
		seekThread = new SeekThread();
		seekThread.start();
	}
	private void initoncilck() {
		serviceimg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startnet();
			}
		});
		
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keywordstring=keyword.getText().toString();
				startsearchnet();
			}
		});
	}

	//
	class MyAdapter extends ArrayAdapter<MyRecord> {

		public MyAdapter(Context context) {
			super(context, R.layout.record_row, R.id.lblLabel, exampleRecords);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			MyRecord record = getItem(position);
			TextView lblLabel = (TextView) view.findViewById(R.id.lblLabel);
			ImageView imageView = (ImageView) view.findViewById(R.id.img);
			lblLabel.setText(record.getLabel());
			lazyImageHelper.loadImage(imageView, record.getImageUrl(), false);
			return view;
		}
	}

	// GetbookingThread
	public class GetbookingThread extends Thread {
		@Override
		public void run() {
			String ServerPath = url + "/list";
			HttpPost request = new HttpPost(ServerPath);
			try {
				// request.setEntity(new
				// UrlEncodedFormEntity(params,HTTP.UTF_8));
				// 线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
				HttpClient httpclient = new DefaultHttpClient();
				// 执行服务器访问
				HttpResponse response = httpclient.execute(request);
				// 服务器能正确响应
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 将返回值变成字符串
					result = EntityUtils.toString(response.getEntity());
					getbooking_Server_answer = new String(
							result.getBytes("ISO-8859-1"), "utf-8");
					System.out.println("getbooking_Server_answer:"
							+ getbooking_Server_answer);
					Message msg = new Message();
					msg.what = 1;
					// 发送
					getbookingHandler.sendMessage(msg);
					
				} else {
					System.out.println("访问失败");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// GetbookingHandler
	public class GetbookingHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1)// 如果外面传来一个1 ,则我默认要更新界面
			{
				try {
					System.out.println("getbooking_Server_answer:"
							+ getbooking_Server_answer);
					getbooking_Server_answer_array = new JSONArray(getbooking_Server_answer);
					exampleRecords = new ArrayList<MyRecord>();
					for (int i = 0; i < getbooking_Server_answer_array.length(); i++) {
						exampleRecords.add(new MyRecord(i, "餐厅名:"
								+ getbooking_Server_answer_array.getJSONObject(i).getString("name")
								+ "\n" + "地址:"
								+ getbooking_Server_answer_array.getJSONObject(i).getString("address"),
								url + getbooking_Server_answer_array.getJSONObject(i).getString("img")));
					}
					setListAdapter(new MyAdapter(CommonActivity.this));
					commonmsg.setText("尊敬的客户，欢迎进入本系统，以下是可预约的餐厅，长按可进行预约，如需刷新，请点击我....");
					//设置列表长按的监听函数来监听长按操作  
				      getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){  
				      public boolean onItemLongClick(AdapterView parent, View view, int position,  
				           long id) {
							try {
								String company=getbooking_Server_answer_array.getJSONObject(position).getString("username");
								urls.CompanyName=company;
								 AlertDialog.Builder dialog = new AlertDialog.Builder(CommonActivity.this);
					    			dialog.setTitle("预约");
					    			dialog.setMessage("你将要跳转到该餐厅的预约界面");
					    			dialog.setPositiveButton("确定",
					    			new DialogInterface.OnClickListener() {
					    				public void onClick(DialogInterface dialog, int which) {
					    					Intent intent = new Intent(CommonActivity.this,
													ReserveActivity.class);
											startActivity(intent);
					    				}
					    			});
					    			dialog.setNegativeButton("取消",
					    			new DialogInterface.OnClickListener() {
					    				public void onClick(DialogInterface dialog, int which) {
					    					dialog.cancel();
					    				}
					    			});
					    			dialog.create().show();
				
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
				        return true;  
				       }  
				      });  
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else // 访问网络失败
			{
				System.out.println("失败");
			}
		}
	}
	
	// SeekThread
		public class SeekThread extends Thread {
			@Override
			public void run() {
				String ServerPath = url + "/seek";
				HttpPost request = new HttpPost(ServerPath);
				//创建参数集合
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				//把见面上用户填写的信息加入到参数集合中
				params.add(new BasicNameValuePair("name",keyword.getText().toString()));
				try {
					request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					// request.setEntity(new
					// UrlEncodedFormEntity(params,HTTP.UTF_8));
					// 线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
					HttpClient httpclient = new DefaultHttpClient();
					// 执行服务器访问
					HttpResponse response = httpclient.execute(request);
					// 服务器能正确响应
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						// 将返回值变成字符串
						result = EntityUtils.toString(response.getEntity());
						seek_Server_answer = new String(
								result.getBytes("ISO-8859-1"), "utf-8");
						System.out.println("seek_Server_answer:"
								+ seek_Server_answer);
						Message msg = new Message();
						msg.what = 1;
						// 发送
						seekHandler.sendMessage(msg);
					} else {
						System.out.println("访问失败");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// SeekHandler
		public class SeekHandler extends Handler {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 1)// 如果外面传来一个1 ,则我默认要更新界面
				{
					try {
						System.out.println("seek_Server_answer:"
								+ seek_Server_answer);
						JSONArray array = new JSONArray(seek_Server_answer);
						exampleRecords = new ArrayList<MyRecord>();
						for (int i = 0; i < array.length(); i++) {
							exampleRecords.add(new MyRecord(i, "餐厅名:"
									+ array.getJSONObject(i).getString("name")
									+ "\n" + "地址:"
									+ array.getJSONObject(i).getString("address"),
									url + array.getJSONObject(i).getString("img")));
						}
						setListAdapter(new MyAdapter(CommonActivity.this));
						commonmsg.setText("尊敬的客户，欢迎进入本系统，以下是可预约的餐厅，如需预约，请点击我....");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else // 访问网络失败
				{
					System.out.println("失败");
				}
			}
		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.Exit_login) {
			Exit_login();
		} else if (item.getItemId() == R.id.Exit_app) {
			Exit_app();
		}
		return super.onOptionsItemSelected(item);
	}

	public void Exit_login() {
		AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
		alertbBuilder.setTitle("退出登录").setMessage("退出当前账号");
		alertbBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Intent intent = new Intent(ManageActivity.this,
						// LoginActivity.class);
						// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						// ManageActivity.this.startActivity(intent);
						CommonActivity.this.finish();
					}
				});
		alertbBuilder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		alertbBuilder.show();
	}

	public void Exit_app() {
		AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
		alertbBuilder.setTitle("退出Booking").setMessage("你确定要离开客户端吗？");
		alertbBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent();
						intent.setClass(CommonActivity.this,
								LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
						startActivity(intent);
						finish();// 关掉自己
					}
				});
		alertbBuilder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		alertbBuilder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage, menu);
		return true;
	}

}
