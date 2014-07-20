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

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CountFragment extends Fragment {
	Urls urls;
	String url = urls.getURL();
	String username=urls.getUserName();
	TextView tv1,tv2;
	ListView lv;
	SimpleAdapter adapter;
	GetlistThread getlistThread;
	GetlistHandler getlistHandler;
	Statis1Thread statis1Thread;
	Statis1Handler statis1Handler;
	Statis2Thread statis2Thread;
	Statis2Handler statis2Handler;
	String getlist_Server_answer;
	String statis1_Server_answer;
	String statis2_Server_answer;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		countinitview();
		statis1init();
		statis2init();
		getlistinit();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View countLayout = inflater.inflate(R.layout.count_layout,
				container, false);
		return countLayout;
	}
	private void countinitview(){
		lv=(ListView)getActivity().findViewById(R.id.countlistView1);
		tv1=(TextView)getActivity().findViewById(R.id.Lobby);
		tv2=(TextView)getActivity().findViewById(R.id.Room);
	}
	private void statis1init(){
		statis1Handler= new Statis1Handler();
		statis1Thread= new Statis1Thread();
		statis1Thread.start();
	}
	private void statis2init(){
		statis2Handler= new Statis2Handler();
		statis2Thread= new Statis2Thread();
		statis2Thread.start();
	}
	private void getlistinit(){
    	getlistHandler=new GetlistHandler();
    	getlistThread=new GetlistThread();
    	getlistThread.start();
	}
	//Statis1Thread	
	private class Statis1Thread extends Thread{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//	run 线程的任务,这个任务的完成时间是未知的,可能很快,也可能很慢
				// 访问服务器的代码 			
				//我们试图去访问网络
				String ServerPath=url+"/statis1";			
				HttpPost request=new HttpPost(ServerPath);			
				//创建参数集合
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				//把见面上用户填写的信息加入到参数集合中
				params.add(new BasicNameValuePair("username",username));
				//把参数集放入HTTP请求包中，并设置编码方式为UTF-8
				try {				
					request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));				
					//线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
					HttpClient httpclient=new DefaultHttpClient();				
					//执行服务器访问 
					HttpResponse response=httpclient.execute(request);				
					//服务器能正确响应
					if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
					{
						//将返回值变成字符串
						statis1_Server_answer=EntityUtils.toString(response.getEntity());
						System.out.println("statis1_Server_answer:"+statis1_Server_answer);
						Message msg=new Message();
					    msg.what=1;				    
					    //发送
					    statis1Handler.sendMessage(msg);	
					}
					else
					{
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
		//Statis1Handler
	private class Statis1Handler extends Handler{
			@Override
			public void handleMessage(Message msg){
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 1)//如果外面传来一个1 ,则我默认要更新界面
					{
				    System.out.println("Server1_answer_statis:"+statis1_Server_answer);
				   
				    
				    try {
						JSONArray array=new JSONArray(statis1_Server_answer);
						for(int i=0;i<array.length();i++)
						{
							String lobby_num= array.getJSONObject(i).getString("count(*)");
							tv1.setText("尊敬的企业用户,你好!截目前为止,"+"\n"+"已有"+lobby_num+"间大堂被预约,");  	
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		    
				}
				else  //访问网络失败
				{
					  System.out.println("失败");
				}	
			}	
		}
		//Statis2Thread	
				public class Statis2Thread extends Thread{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//	run 线程的任务,这个任务的完成时间是未知的,可能很快,也可能很慢
						// 访问服务器的代码 			
						//我们试图去访问网络
						String ServerPath=url+"/statis2";			
						HttpPost request=new HttpPost(ServerPath);			
						//创建参数集合
						List<NameValuePair> params=new ArrayList<NameValuePair>();
						//把见面上用户填写的信息加入到参数集合中
						params.add(new BasicNameValuePair("username",username));
						//把参数集放入HTTP请求包中，并设置编码方式为UTF-8
						try {				
							request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));				
							//线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
							HttpClient httpclient=new DefaultHttpClient();				
							//执行服务器访问 
							HttpResponse response=httpclient.execute(request);				
							//服务器能正确响应
							if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
							{
								//将返回值变成字符串
								statis2_Server_answer=EntityUtils.toString(response.getEntity());
								System.out.println("statis2_Server_answer:"+statis2_Server_answer);
								Message msg=new Message();
							    msg.what=1;				    
							    //发送
							    statis2Handler.sendMessage(msg);	
							}
							else
							{
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
				//Statis2Handler
				private class Statis2Handler extends Handler{
					@Override
					public void handleMessage(Message msg){
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						if(msg.what == 1)//如果外面传来一个1 ,则我默认要更新界面
							{
						    System.out.println("Server2_answer_statis:"+statis2_Server_answer);
						    
						    try {
								JSONArray array=new JSONArray(statis2_Server_answer);
								for(int i=0;i<array.length();i++)
								{
									String room_num= array.getJSONObject(i).getString("count(*)");
									tv2.setText("已有"+room_num+"间房间被预约。");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}		    
						}
						else  //访问网络失败
						{
							  System.out.println("失败");
						}	
					}	
				}
	//GetlistThread	
		private class GetlistThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//	run 线程的任务,这个任务的完成时间是未知的,可能很快,也可能很慢
			// 访问服务器的代码 			
			//我们试图去访问网络
			String ServerPath=url+"/getlist";			
			HttpPost request=new HttpPost(ServerPath);			
			//创建参数集合
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//把见面上用户填写的信息加入到参数集合中
			params.add(new BasicNameValuePair("username",username));
			//把参数集放入HTTP请求包中，并设置编码方式为UTF-8
			try {				
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));				
				//线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
				HttpClient httpclient=new DefaultHttpClient();				
				//执行服务器访问 
				HttpResponse response=httpclient.execute(request);				
				//服务器能正确响应
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					//将返回值变成字符串
					getlist_Server_answer=EntityUtils.toString(response.getEntity());
					System.out.println("getlist_Server_answer:"+getlist_Server_answer);
					Message msg=new Message();
				    msg.what=1;				    
				    //发送
				    getlistHandler.sendMessage(msg);	
				}
				else
				{
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
	//GetlistHandler
		private class GetlistHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1)//如果外面传来一个1 ,则我默认要更新界面
				{
			    System.out.println("Server_answer_getlist:"+getlist_Server_answer);
			    SimpleAdapter adapter=new SimpleAdapter(
			    		getActivity(),
			    		getList(getlist_Server_answer),
			    		R.layout.count_listlayout,
			    		new String[]{"booking_info"},
			    		new int[]{R.id.booking_list}
			    		); 
			    lv.setAdapter(adapter);			    
			}
			else  //访问网络失败
			{
				  System.out.println("失败");
			}	
		}	
	}
	public List<Map<String,String>> getList(String ServerString)
	{
		String personalname;
		String companyname;
		String time;
		String type;
		String booking_type;
		String num;
		String list_info;
		List<Map<String,String>> list=new ArrayList<Map<String,String>> ();
		try {
			JSONArray array=new JSONArray(ServerString);
			for(int i=0;i<array.length();i++)
			{
				personalname=array.getJSONObject(i).getString("personalname");
				companyname=array.getJSONObject(i).getString("companyname");
				time=array.getJSONObject(i).getString("time");
				type=array.getJSONObject(i).getString("type");
				if(type.equals("1")){
					booking_type="大堂";
				}else{
					booking_type="房间";
				}
				
				num=array.getJSONObject(i).getString("num");
				list_info=personalname+"(先生/小姐)预约了"+companyname+"公司所预订的"+booking_type+":"+num+"间，时间是:"+time;
					Map<String,String> map=new HashMap<String,String>();
					map.put("booking_info",list_info);									
					list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		countinitview();
		statis1init();
		statis2init();
		getlistinit();
	}
	
}