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
				//	run �̵߳�����,�����������ʱ����δ֪��,���ܺܿ�,Ҳ���ܺ���
				// ���ʷ������Ĵ��� 			
				//������ͼȥ��������
				String ServerPath=url+"/statis1";			
				HttpPost request=new HttpPost(ServerPath);			
				//������������
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				//�Ѽ������û���д����Ϣ���뵽����������
				params.add(new BasicNameValuePair("username",username));
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
						statis1_Server_answer=EntityUtils.toString(response.getEntity());
						System.out.println("statis1_Server_answer:"+statis1_Server_answer);
						Message msg=new Message();
					    msg.what=1;				    
					    //����
					    statis1Handler.sendMessage(msg);	
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
		//Statis1Handler
	private class Statis1Handler extends Handler{
			@Override
			public void handleMessage(Message msg){
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 1)//������洫��һ��1 ,����Ĭ��Ҫ���½���
					{
				    System.out.println("Server1_answer_statis:"+statis1_Server_answer);
				   
				    
				    try {
						JSONArray array=new JSONArray(statis1_Server_answer);
						for(int i=0;i<array.length();i++)
						{
							String lobby_num= array.getJSONObject(i).getString("count(*)");
							tv1.setText("�𾴵���ҵ�û�,���!��ĿǰΪֹ,"+"\n"+"����"+lobby_num+"����ñ�ԤԼ,");  	
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
		//Statis2Thread	
				public class Statis2Thread extends Thread{
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//	run �̵߳�����,�����������ʱ����δ֪��,���ܺܿ�,Ҳ���ܺ���
						// ���ʷ������Ĵ��� 			
						//������ͼȥ��������
						String ServerPath=url+"/statis2";			
						HttpPost request=new HttpPost(ServerPath);			
						//������������
						List<NameValuePair> params=new ArrayList<NameValuePair>();
						//�Ѽ������û���д����Ϣ���뵽����������
						params.add(new BasicNameValuePair("username",username));
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
								statis2_Server_answer=EntityUtils.toString(response.getEntity());
								System.out.println("statis2_Server_answer:"+statis2_Server_answer);
								Message msg=new Message();
							    msg.what=1;				    
							    //����
							    statis2Handler.sendMessage(msg);	
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
				//Statis2Handler
				private class Statis2Handler extends Handler{
					@Override
					public void handleMessage(Message msg){
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						if(msg.what == 1)//������洫��һ��1 ,����Ĭ��Ҫ���½���
							{
						    System.out.println("Server2_answer_statis:"+statis2_Server_answer);
						    
						    try {
								JSONArray array=new JSONArray(statis2_Server_answer);
								for(int i=0;i<array.length();i++)
								{
									String room_num= array.getJSONObject(i).getString("count(*)");
									tv2.setText("����"+room_num+"�䷿�䱻ԤԼ��");
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
	//GetlistThread	
		private class GetlistThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//	run �̵߳�����,�����������ʱ����δ֪��,���ܺܿ�,Ҳ���ܺ���
			// ���ʷ������Ĵ��� 			
			//������ͼȥ��������
			String ServerPath=url+"/getlist";			
			HttpPost request=new HttpPost(ServerPath);			
			//������������
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			//�Ѽ������û���д����Ϣ���뵽����������
			params.add(new BasicNameValuePair("username",username));
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
					getlist_Server_answer=EntityUtils.toString(response.getEntity());
					System.out.println("getlist_Server_answer:"+getlist_Server_answer);
					Message msg=new Message();
				    msg.what=1;				    
				    //����
				    getlistHandler.sendMessage(msg);	
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
	//GetlistHandler
		private class GetlistHandler extends Handler{
		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1)//������洫��һ��1 ,����Ĭ��Ҫ���½���
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
			else  //��������ʧ��
			{
				  System.out.println("ʧ��");
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
					booking_type="����";
				}else{
					booking_type="����";
				}
				
				num=array.getJSONObject(i).getString("num");
				list_info=personalname+"(����/С��)ԤԼ��"+companyname+"��˾��Ԥ����"+booking_type+":"+num+"�䣬ʱ����:"+time;
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