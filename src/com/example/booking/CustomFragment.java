package com.example.booking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONObject;

import com.booking.Urls.Urls;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomFragment extends Fragment {
	Urls urls;
	String url = urls.getURL();
	String username=urls.getUserName();
	BusinThread businThread;
	BusinHandler businHandler;
	String busin_Server_answer;
	EditText lobby;//大厅
	EditText room;//房间
	EditText starttime;
	EditText overtime;
	Button action;//执行
	Button clean;//清空
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		custominitview();
		custominitoncilck();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View customLayout = inflater.inflate(R.layout.custom_layout, container, false);
		return customLayout;
	}
	public void custominitview(){
		lobby=(EditText)getActivity().findViewById(R.id.culobby_sum);
		room=(EditText)getActivity().findViewById(R.id.curoom_sum);
		starttime=(EditText)getActivity().findViewById(R.id.custart_time);
		overtime=(EditText)getActivity().findViewById(R.id.cuover_time);
		action=(Button)getActivity().findViewById(R.id.cuaction);
		clean=(Button)getActivity().findViewById(R.id.cuclean);
	}
	private void cleancontent(){
		lobby.setText("");
		room.setText("");
		starttime.setText("");
		overtime.setText("");
	}
	private void custominitoncilck(){
		action.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String culobby=lobby.getText().toString();
				String curoom=room.getText().toString();
				String custarttime=starttime.getText().toString();
				String cuovertime=overtime.getText().toString();
				if(culobby.equals("")||curoom.equals("")||custarttime.equals("")||cuovertime.equals("")){
					Toast.makeText(getActivity(), "请填写完整的预约信息", Toast.LENGTH_LONG).show();
				}else{
				businHandler = new BusinHandler();
				businThread = new BusinThread();
				businThread.start();
				}
			}
		});
        clean.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cleancontent();
			}
		});
	}
	//BusinThread	
			public class BusinThread extends Thread{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//	run 线程的任务,这个任务的完成时间是未知的,可能很快,也可能很慢
					// 访问服务器的代码 			
					//我们试图去访问网络
					String ServerPath=url+"/busin";			
					HttpPost request=new HttpPost(ServerPath);			
					//创建参数集合
					List<NameValuePair> params=new ArrayList<NameValuePair>();
					//把见面上用户填写的信息加入到参数集合中
					params.add(new BasicNameValuePair("username",username));
					params.add(new BasicNameValuePair("room1",lobby.getText().toString()));
					params.add(new BasicNameValuePair("room2",room.getText().toString()));
					params.add(new BasicNameValuePair("start_time",starttime.getText().toString()));
					params.add(new BasicNameValuePair("over_time",overtime.getText().toString()));
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
							busin_Server_answer=EntityUtils.toString(response.getEntity());
							System.out.println("busin_Server_answer:"+busin_Server_answer);
							Message msg=new Message();
						    msg.what=1;				    
						    //发送
						    businHandler.sendMessage(msg);	
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
			//BusinHandler
			public class BusinHandler extends Handler{
				@Override
				public void handleMessage(Message msg){
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					if(msg.what == 1)//如果外面传来一个1 ,则我默认要更新界面
						{
					    System.out.println("busin_Server_answer:"+busin_Server_answer);
					    try {
					    	JSONObject json=new JSONObject(busin_Server_answer);
							String result=json.getString("result");
							if(result.equals("OK")){
								  Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
							}
							  else if(result.equals("fail")){
									Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_LONG).show();
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
}