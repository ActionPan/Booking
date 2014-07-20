package com.example.booking;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.booking.Urls.Urls;
import com.example.booking.BookingSocketActivity.MyHandler;

import android.os.Bundle;
import android.os.Message;


import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

public class MySocketIO implements IOCallback {
	
 
	private SocketIO socket;

	MyHandler mhandler;
	
	//构造函数
	public MySocketIO( MyHandler handler)
	{
		mhandler=handler;
		socket = new SocketIO();
		try {
			
			socket.connect(Urls.getURL()+"/", this);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(String data, IOAcknowledge ack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(JSONObject json, IOAcknowledge ack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on(String event, IOAcknowledge ack, Object... args) {
		// TODO Auto-generated method stub
		if(event.equals("welcome"))
		{
			Message msg=new Message();
			msg.what=1;
			mhandler.sendMessage(msg);
		}
		if(event.endsWith("serverBroadcast"))
		{
			JSONObject object=null;
			if(args.length>0)
			{
				
				 try {
					object=new JSONObject(args[0].toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			Message msg=new Message();
			msg.what=2;
			Bundle bundel=new Bundle();
			try {
				bundel.putString("company", object.getString("company"));
				bundel.putString("no", object.getString("no"));
				System.out.println("gdsgs"+object.getString("company"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//把接收到的数据，通过事件，发送给主界面
			msg.setData(bundel);
			mhandler.sendMessage(msg);
		}
		
	}

	@Override
	public void onError(SocketIOException socketIOException) {
		// TODO Auto-generated method stub
		
	}

}
