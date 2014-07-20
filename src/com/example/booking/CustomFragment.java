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
	EditText lobby;//����
	EditText room;//����
	EditText starttime;
	EditText overtime;
	Button action;//ִ��
	Button clean;//���
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
					Toast.makeText(getActivity(), "����д������ԤԼ��Ϣ", Toast.LENGTH_LONG).show();
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
					//	run �̵߳�����,�����������ʱ����δ֪��,���ܺܿ�,Ҳ���ܺ���
					// ���ʷ������Ĵ��� 			
					//������ͼȥ��������
					String ServerPath=url+"/busin";			
					HttpPost request=new HttpPost(ServerPath);			
					//������������
					List<NameValuePair> params=new ArrayList<NameValuePair>();
					//�Ѽ������û���д����Ϣ���뵽����������
					params.add(new BasicNameValuePair("username",username));
					params.add(new BasicNameValuePair("room1",lobby.getText().toString()));
					params.add(new BasicNameValuePair("room2",room.getText().toString()));
					params.add(new BasicNameValuePair("start_time",starttime.getText().toString()));
					params.add(new BasicNameValuePair("over_time",overtime.getText().toString()));
					//�Ѳ���������HTTP������У������ñ��뷽ʽΪUTF-8
					try {				
						request.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));				
						//�̵߳�������Ҫ���Խ��û��������Ϣͨ��Post����ʽ���͵�������
						HttpClient httpclient=new DefaultHttpClient();				
						//ִ�з��������� 
						HttpResponse response=httpclient.execute(request);				
						//����������ȷ��Ӧ
						if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
						{
							//������ֵ����ַ���
							busin_Server_answer=EntityUtils.toString(response.getEntity());
							System.out.println("busin_Server_answer:"+busin_Server_answer);
							Message msg=new Message();
						    msg.what=1;				    
						    //����
						    businHandler.sendMessage(msg);	
						}
						else
						{
							System.out.println("����ʧ��");
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
					if(msg.what == 1)//������洫��һ��1 ,����Ĭ��Ҫ���½���
						{
					    System.out.println("busin_Server_answer:"+busin_Server_answer);
					    try {
					    	JSONObject json=new JSONObject(busin_Server_answer);
							String result=json.getString("result");
							if(result.equals("OK")){
								  Toast.makeText(getActivity(), "��ӳɹ�", Toast.LENGTH_SHORT).show();
							}
							  else if(result.equals("fail")){
									Toast.makeText(getActivity(), "���ʧ��", Toast.LENGTH_LONG).show();
								  } 
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		    
					}
					else  //��������ʧ��
					{
						  System.out.println("ʧ��");
					}	
				}	
			}
}