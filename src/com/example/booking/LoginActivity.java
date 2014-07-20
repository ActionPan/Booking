package com.example.booking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginActivity extends Activity {
	Urls urls;
	String url = urls.getURL();
	private Button login, register;
	private EditText login_username,login_password;
	private CheckBox cb_remeber;
	private RadioGroup bg;
	private RadioButton radioButton1,radioButton2;
	String result;
	SharedPreferences sp;
	String username,password,user,manage,bb;
	
	MyLoginThread myLoginThread;
	MyLoginHandler myLoginHandler;
	String Server_answer;
	String type;
	String type_num;
	Message msg=new Message();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		init();
		myLoginHandler=new MyLoginHandler();
		btnOnclik();
	}

	private void init() {
		// TODO Auto-generated method stub
		login = (Button) this.findViewById(R.id.login_btn);
		register = (Button) this.findViewById(R.id.register_btn);
		login_username=(EditText)findViewById(R.id.login_username);
		login_password=(EditText)findViewById(R.id.login_password);
		bg = (RadioGroup)this.findViewById(R.id.radioGroup);
		radioButton1 = (RadioButton)this.findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton)this.findViewById(R.id.radioButton2);
		cb_remeber = (CheckBox)this.findViewById(R.id.checkBox1);
		//
		bg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==radioButton1.getId())  
                {  
                	Toast.makeText(LoginActivity.this, "企业用户", Toast.LENGTH_SHORT).show();
                	type=radioButton1.getText().toString();
                	type_num="2";
                }else  
                {  
                	Toast.makeText(LoginActivity.this, "一般用户", Toast.LENGTH_SHORT).show();
                	type=radioButton2.getText().toString();
                	type_num="1";
                }
				System.out.println("type:"+type);
				System.out.println("type_num:"+type_num);
			}
		});
		sp=getSharedPreferences("spFile", Context.MODE_PRIVATE);

	}

	private void btnOnclik() {
		// TODO Auto-generated method stub
		cb_remeber.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					sp.edit().putBoolean("isChecked", true).commit();
				} else {
					sp.edit().putBoolean("isChecked", false).commit();
				}
			}
		});
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				username=login_username.getText().toString();
				password=login_password.getText().toString();
				
				if(cb_remeber.isChecked()){
					Editor editor=sp.edit();
					editor.putBoolean("isChecked", true);
					editor.putString("username", username);
					editor.putString("password", password);
					editor.commit();
				}
				urls.UserName=username;
				if(username.equals("")||password.equals("")){
					Toast.makeText(LoginActivity.this, "请输入完整的登录信息", Toast.LENGTH_SHORT).show();	
				}else{
				new MyLoginThread().start();
				}
			}
		});

		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);

			}
		});

	}
	public class MyLoginThread extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//	run 线程的任务,这个任务的完成时间是未知的,可能很快,也可能很慢
			// 访问服务器的代码 
			//我们试图去访问网络
			String ServerPath=url+"/login";
			HttpPost request=new HttpPost(ServerPath);
			//创建参数集合
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//把见面上用户填写的信息加入到参数集合中
			params.add(new BasicNameValuePair("username",login_username.getText().toString()));
			params.add(new BasicNameValuePair("password",login_password.getText().toString()));
			
			params.add(new BasicNameValuePair("type",type_num));
			//把参数集放入HTTP请求包中，并设置编码方式为UTF-8
			try {
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				//线程的主体主要用以将用户输入的信息通过Post的形式发送到服务器
				HttpClient httpclient=new DefaultHttpClient();
				//执行服务器访问 
				HttpResponse response=httpclient.execute(request);
				Server_answer=EntityUtils.toString(response.getEntity());
				//服务器能正确响应
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					//将返回值变成字符串
					
					System.out.println("Server_answer:"+Server_answer);
					
						msg.what=1;
						//发送
				    myLoginHandler.sendMessage(msg);	
					
    
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
	public class MyLoginHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(msg.what == 1)//如果外面传来一个1 ,则我默认要更新界面
				{				
			    try {
					JSONObject json=new JSONObject(Server_answer);
					String result=json.getString("result");
				  if(result.equals("OK")){
					  String type=json.getString("type");
					if(type.equals("2")){  
					  Toast.makeText(LoginActivity.this, "登录成功,企业用户", Toast.LENGTH_SHORT).show();
					  Intent intent = new Intent(LoginActivity.this,
								ManageActivity.class);
						startActivity(intent);
					}else if(type.equals("1")){
					  Toast.makeText(LoginActivity.this, "登录成功,一般用户", Toast.LENGTH_SHORT).show();
					  Intent intent = new Intent(LoginActivity.this,
								CommonActivity.class);
						startActivity(intent);
					}
				}
				if(result.equals("none")){  
						System.out.println("登录失败");
						Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
					  } 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			}

		}	
	}
	
	
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			if(sp.getBoolean("isChecked", false)){
				cb_remeber.setChecked(true);
				login_username.setText(sp.getString("username", null));
				login_password.setText(sp.getString("password", null));
			}
			super.onResume();
		}

	// 两次点击才退出
	boolean isExit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit) {
				finish();
			} else {
				Toast.makeText(this, "继续点击退出", Toast.LENGTH_SHORT).show();
				isExit = true;
				// 定时器，3秒钟点击，这上次点击失效。
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isExit = false;
					}
				}, 3000);
			}
		}
		return false;// 防止一次点击后，调用方法，退出
	}
	
	protected void onNewIntent(Intent intent) {  
		// TODO Auto-generated method stub  
		super.onNewIntent(intent);  
		//退出  
		        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {  
		               finish();  
		        }  
		}  
}
