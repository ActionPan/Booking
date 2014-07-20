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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReserveActivity extends Activity{
	//int i = Integer.valueOf(my_str).intValue();
	int count;
	Urls urls;
	String url,username,companyname;
    TextView reservesum,reservedsum;
    Button reservelobby,reserveroom,check;
    GetnumThread getnumThread;
    GetnumHandler getnumHandler;
    BookinglobbyThread bookinglobbyThread;
    BookinglobbyHandler bookinglobbyHandler;
    BookingroomThread bookingroomThread;
    BookingroomHandler bookingroomHandler;
    String getnum_Server_answer,bookinglobby_Server_answer,bookingroom_Server_answer,num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_layout);
		initreserveview();
		initreserveoncilck();
		getnumnetstart();
	}
	//
    private void initreserveview(){
    	url = urls.getURL();
    	username = urls.getUserName();
    	companyname = urls.getCompanyName();
    	reservesum=(TextView)this.findViewById(R.id.reserve_sum);
    	reservedsum=(TextView)this.findViewById(R.id.reserved_sum);
    	reservelobby=(Button)this.findViewById(R.id.reserve_lobby);
    	reserveroom=(Button)this.findViewById(R.id.reserve_room);
    	check=(Button)this.findViewById(R.id.recheck);
    }
    //
    private void initreserveoncilck(){
    	reservelobby.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				bookinglobbyetstart();
				getnumnetstart();
			}
		});
    	
    	reserveroom.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				bookingroomnetstart();
				getnumnetstart();
			}
		});
    	
    	check.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ReserveActivity.this,
						BookingSocketActivity.class);
				startActivity(intent);
			}
		});
    }
    //
    private void getnumnetstart(){
    	getnumHandler = new GetnumHandler();
		getnumThread = new GetnumThread();
		getnumThread.start();
    }
    //
    private void bookinglobbyetstart(){
    	bookinglobbyHandler = new BookinglobbyHandler();
    	bookinglobbyThread = new BookinglobbyThread();
    	bookinglobbyThread.start();
    }
    //
    private void bookingroomnetstart(){
    	bookingroomHandler = new BookingroomHandler();
    	bookingroomThread = new BookingroomThread();
    	bookingroomThread.start();
    }
    //GetnumThread
    public class GetnumThread extends Thread{

		@Override
		public void run() {
			String ServerPath=url+"/getnum";
			HttpPost request=new HttpPost(ServerPath);
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username",companyname));
			try {
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpClient httpclient=new DefaultHttpClient();
				HttpResponse response=httpclient.execute(request);
				getnum_Server_answer=EntityUtils.toString(response.getEntity());
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					System.out.println("getnum_Server_answer:"+getnum_Server_answer);
					Message msg=new Message();
						msg.what=1;
						getnumHandler.sendMessage(msg);	
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
	//GetnumHandler
	public class GetnumHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1)
				{				
				 System.out.println("getnum_Server_answer:"+getnum_Server_answer);
				  try {
						JSONArray array=new JSONArray(getnum_Server_answer);
						for(int i=0;i<array.length();i++)
						{
							String lobby_num= array.getJSONObject(i).getString("count(*)");
							reservedsum.setText(lobby_num);
							urls.lobby_num=lobby_num;
							//Toast.makeText(ReserveActivity.this, "reservedsum:"+lobby_num,Toast.LENGTH_LONG).show();
							count=Integer.valueOf(lobby_num).intValue();
							
							count=count+1;
							num=""+count;
							//Toast.makeText(ReserveActivity.this, "num:"+num,Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			}
		}	
	}
	//BookinglobbyThread
    public class BookinglobbyThread extends Thread{

		@Override
		public void run() {
			String ServerPath=url+"/reserve1";
			HttpPost request=new HttpPost(ServerPath);
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("personalname",username));
			params.add(new BasicNameValuePair("companyname",companyname));
			params.add(new BasicNameValuePair("num",num));
			try {
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpClient httpclient=new DefaultHttpClient();
				HttpResponse response=httpclient.execute(request);
				bookinglobby_Server_answer=EntityUtils.toString(response.getEntity());
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					System.out.println("bookinglobby_Server_answer:"+bookinglobby_Server_answer);
					Message msg=new Message();
						msg.what=1;
						bookinglobbyHandler.sendMessage(msg);	
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
	//BookinglobbyHandler
	public class BookinglobbyHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1)
				{				
				 System.out.println("bookinglobby_Server_answer:"+bookinglobby_Server_answer);
				  try {
					  JSONObject json=new JSONObject(bookinglobby_Server_answer);
					  String result=json.getString("result");
							System.out.print(result);
							if(result.equals("OK")){
								Toast.makeText(ReserveActivity.this, "预约成功",Toast.LENGTH_LONG).show();
								reservedsum.setText(num);
							}else if(result.equals("fail")){
								Toast.makeText(ReserveActivity.this, "预约失败",Toast.LENGTH_LONG).show();
							}else if(result.equals("existe")){
								Toast.makeText(ReserveActivity.this, "已有预约",Toast.LENGTH_LONG).show();
							}	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			}
		}	
	}
	//BookingroomThread
    public class BookingroomThread extends Thread{

		@Override
		public void run() {
			String ServerPath=url+"/reserve2";
			HttpPost request=new HttpPost(ServerPath);
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("personalname",username));
			params.add(new BasicNameValuePair("companyname",companyname));
			params.add(new BasicNameValuePair("num",num));
			try {
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				HttpClient httpclient=new DefaultHttpClient();
				HttpResponse response=httpclient.execute(request);
				bookingroom_Server_answer=EntityUtils.toString(response.getEntity());
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					System.out.println("bookingroom_Server_answer:"+bookingroom_Server_answer);
					Message msg=new Message();
						msg.what=1;
						bookingroomHandler.sendMessage(msg);	
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
	//BookingroomHandler
	public class BookingroomHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1)
				{				
				 System.out.println("bookingroom_Server_answer:"+bookingroom_Server_answer);
				  try {
					  JSONObject json=new JSONObject(bookingroom_Server_answer);
					  String result=json.getString("result");
							System.out.print(result);
							if(result.equals("OK")){
								Toast.makeText(ReserveActivity.this, "预约成功",Toast.LENGTH_LONG).show();	
							}else if(result.equals("fail")){
								Toast.makeText(ReserveActivity.this, "预约失败",Toast.LENGTH_LONG).show();
							}else if(result.equals("existe")){
								Toast.makeText(ReserveActivity.this, "已有预约",Toast.LENGTH_LONG).show();
							}	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			}
		}	
	}
}
