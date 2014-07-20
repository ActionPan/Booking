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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WorkFragment extends Fragment {
	Urls urls;
	String url = urls.getURL();
	String username = urls.getUserName();

	GetNameHandler getnameHandler;
	GetNumHandler getnumHandler;
	List<Map<String, String>> list;
	JSONArray array,array1;
	Button bt1, bt2, bt3, bt4;
	TextView tv;
	EditText et;
	String getbooking_Server_answer, getnum_Server_answer;
	String result;
	
	String Name;
	int num = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getnameHandler = new GetNameHandler();

		getnumHandler = new GetNumHandler();
		bt1 = (Button) getActivity().findViewById(R.id.bt21);
		bt2 = (Button) getActivity().findViewById(R.id.bt22);
		bt4 = (Button) getActivity().findViewById(R.id.bt24);

		tv = (TextView) getActivity().findViewById(R.id.tv22);
		

		bt1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(num>0){
					num = num - 1;

					tv.setText(list.get(num).get("num"));
				}
				
			}
		});
		bt2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("array.length():"+array.length());
				if(num<array.length()-1){
				num = num + 1;
				System.out.println("num:"+num);
				tv.setText(list.get(num).get("num"));
				}
			}
		});
		bt4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("---------------");
				new Thread(new MyThread()).start();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View workLayout = inflater.inflate(R.layout.work_layout, container,
				false);
		return workLayout;
	}

	public class MyThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String ServerPath = url + "/Broadcast";

			HttpPost request = new HttpPost(ServerPath);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("CompanyName", Name));
			params.add(new BasicNameValuePair("CurrentNo", tv.getText().toString()));
			try {
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpClient httpclient = new DefaultHttpClient();
				// 执行服务器访问
				HttpResponse response = httpclient.execute(request);
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
		// TODO Auto-generated method stub

	}

	public class MyThread2 implements Runnable {

		@Override
		public void run() {
			String ServerPath = url + "/num";
			HttpPost request = new HttpPost(ServerPath);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("username", username));
			try {
				// request.setEntity(new
				// UrlEncodedFormEntity(params,HTTP.UTF_8));
				// 线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
				
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));	
				
				HttpClient httpclient = new DefaultHttpClient();
				// 执行服务器访问
				HttpResponse response = httpclient.execute(request);
				// 服务器能正确响应
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 将返回值变成字符串
					result = EntityUtils.toString(response.getEntity());
					getnum_Server_answer = new String(result.getBytes("ISO-8859-1"), "utf-8");
					System.out.println("getbooking_Server_answer:"+ getnum_Server_answer);
					Message msg = new Message();
					msg.what = 1;
					// 发送
					getnumHandler.sendMessage(msg);
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

	public class GetNumHandler extends Handler {
		// TODO Auto-generated method stub
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1)// 如果外面传来一个1 ,则我默认要更新界面
			{
				try {

					array = new JSONArray(getnum_Server_answer);
					list = new ArrayList<Map<String, String>>();
					for (int i = 0; i < array.length(); i++) {

						
						Map<String, String> map = new HashMap<String, String>();
						map.put("num", array.getJSONObject(i).getString("num"));
						list.add(map);
					}
					
					
					tv.setText(list.get(num).get("num"));
					System.out.println("------>"+array.getJSONObject(0).getString("num"));
					System.out.println("------>"+list.get(0).get("num"));
					

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

	public class MyThread1 implements Runnable {

		@Override
		public void run() {
			String ServerPath = url + "/getname";
			HttpPost request = new HttpPost(ServerPath);

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("username", username));
			try {
				// request.setEntity(new
				// UrlEncodedFormEntity(params,HTTP.UTF_8));
				// 线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器

				request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
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
					getnameHandler.sendMessage(msg);
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

	public class GetNameHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1)// 如果外面传来一个1 ,则我默认要更新界面
			{
				try {

					JSONArray array1 = new JSONArray(getbooking_Server_answer);

					for (int i = 0; i < array1.length(); i++) {

						Name = array1.getJSONObject(i).getString("name");
						
						System.out.println(Name);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else // 访问网络失败
			{
				System.out.println("失败");
			}
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		new Thread(new MyThread1()).start();
		new Thread(new MyThread2()).start();
		super.onResume();
	}
}