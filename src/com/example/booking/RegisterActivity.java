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
import org.json.JSONException;
import org.json.JSONObject;

import com.booking.Urls.Urls;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	EditText et_username,et_password,et_password1;
	String username,password,password1;
	Button register,back;
	Context context;
	MyRegisterHandler myRegisterHandler;
	MyRegisterThread myRegisterThread;
	String Register_Server_answer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		init();
		btnOnclick();
	}

	private void init() {
		// TODO Auto-generated method stub
		register = (Button) this.findViewById(R.id.register);
		back = (Button) this.findViewById(R.id.back);
		et_username =(EditText)this.findViewById(R.id.username); 
		et_password =(EditText)this.findViewById(R.id.password);
		et_password1 =(EditText)this.findViewById(R.id.password1);
        
	}

	private void btnOnclick() {
		// TODO Auto-generated method stub
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String reusename=et_username.getText().toString();
				String repassword=et_password.getText().toString();
				String repassword1=et_password1.getText().toString();
				if(reusename.equals("")||repassword.equals("")||repassword1.equals("")){
					Toast.makeText(RegisterActivity.this, "请填写完整注册信息", Toast.LENGTH_LONG).show();
				}
				else{
				myRegisterHandler = new MyRegisterHandler();
				myRegisterThread = new MyRegisterThread();
				myRegisterThread.start();
				}
				}
		});
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}
	class MyRegisterThread extends Thread
    {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//super.run();
			String ServerPath=Urls.getURL()+"/addUser";
			HttpPost request=new HttpPost(ServerPath);			
			//创建参数集合
			List<NameValuePair> params=new ArrayList<NameValuePair>();		
			//把见面上用户填写的信息加入到参数集合中
			params.add(new BasicNameValuePair("username",et_username.getText().toString()));			
			params.add(new BasicNameValuePair("password",et_password.getText().toString()));			
			//把参数集放入HTTP请求包中，并设置编码方式为UTF-8
			try {				
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));				
				//线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
				HttpClient httpclient=new DefaultHttpClient();				
				//执行服务器访问 
				HttpResponse response=httpclient.execute(request);	
				//将返回值变成字符串					
				Register_Server_answer=EntityUtils.toString(response.getEntity());
				//服务器能正确响应
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					
					System.out.println("Register_Server_answer:"+Register_Server_answer);
					Message msg=new Message();
					msg.what=1;
					//发送
			        myRegisterHandler.sendMessage(msg);	
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
	//事件处理器
		public class MyRegisterHandler extends Handler{
			@Override
			public void handleMessage(Message msg){
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what == 1)//如果外面传来一个1 ,则我默认要更新界面
					{				
				    try {
						JSONObject json=new JSONObject(Register_Server_answer);
						String result=json.getString("result");
					  if(result.equals("OK")){
						  Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
					}
					if(result.equals("fail")){  
							Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
						  } 
					if(result.equals("existe")){  
						Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_LONG).show();
					  }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}   
				}

			}	
	public void clear(){
		et_username.setText("");
		et_password.setText("");
		et_password1.setText("");
		
	}
	
	}
	}
