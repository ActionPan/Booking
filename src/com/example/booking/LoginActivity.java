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
                	Toast.makeText(LoginActivity.this, "��ҵ�û�", Toast.LENGTH_SHORT).show();
                	type=radioButton1.getText().toString();
                	type_num="2";
                }else  
                {  
                	Toast.makeText(LoginActivity.this, "һ���û�", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(LoginActivity.this, "�����������ĵ�¼��Ϣ", Toast.LENGTH_SHORT).show();	
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
			//	run �̵߳�����,�����������ʱ����δ֪��,���ܺܿ�,Ҳ���ܺ���
			// ���ʷ������Ĵ��� 
			//������ͼȥ��������
			String ServerPath=url+"/login";
			HttpPost request=new HttpPost(ServerPath);
			//������������
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//�Ѽ������û���д����Ϣ���뵽����������
			params.add(new BasicNameValuePair("username",login_username.getText().toString()));
			params.add(new BasicNameValuePair("password",login_password.getText().toString()));
			
			params.add(new BasicNameValuePair("type",type_num));
			//�Ѳ���������HTTP������У������ñ��뷽ʽΪUTF-8
			try {
				request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				//�̵߳�������Ҫ���Խ��û��������Ϣͨ��Post����ʽ���͵�������
				HttpClient httpclient=new DefaultHttpClient();
				//ִ�з��������� 
				HttpResponse response=httpclient.execute(request);
				Server_answer=EntityUtils.toString(response.getEntity());
				//����������ȷ��Ӧ
				if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
				{
					//������ֵ����ַ���
					
					System.out.println("Server_answer:"+Server_answer);
					
						msg.what=1;
						//����
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
	//�¼�������
	public class MyLoginHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(msg.what == 1)//������洫��һ��1 ,����Ĭ��Ҫ���½���
				{				
			    try {
					JSONObject json=new JSONObject(Server_answer);
					String result=json.getString("result");
				  if(result.equals("OK")){
					  String type=json.getString("type");
					if(type.equals("2")){  
					  Toast.makeText(LoginActivity.this, "��¼�ɹ�,��ҵ�û�", Toast.LENGTH_SHORT).show();
					  Intent intent = new Intent(LoginActivity.this,
								ManageActivity.class);
						startActivity(intent);
					}else if(type.equals("1")){
					  Toast.makeText(LoginActivity.this, "��¼�ɹ�,һ���û�", Toast.LENGTH_SHORT).show();
					  Intent intent = new Intent(LoginActivity.this,
								CommonActivity.class);
						startActivity(intent);
					}
				}
				if(result.equals("none")){  
						System.out.println("��¼ʧ��");
						Toast.makeText(LoginActivity.this, "�û������������", Toast.LENGTH_LONG).show();
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

	// ���ε�����˳�
	boolean isExit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit) {
				finish();
			} else {
				Toast.makeText(this, "��������˳�", Toast.LENGTH_SHORT).show();
				isExit = true;
				// ��ʱ����3���ӵ�������ϴε��ʧЧ��
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isExit = false;
					}
				}, 3000);
			}
		}
		return false;// ��ֹһ�ε���󣬵��÷������˳�
	}
	
	protected void onNewIntent(Intent intent) {  
		// TODO Auto-generated method stub  
		super.onNewIntent(intent);  
		//�˳�  
		        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {  
		               finish();  
		        }  
		}  
}
