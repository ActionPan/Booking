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

import com.booking.Urls.Urls;
import com.example.booking.ReserveActivity.GetnumHandler;
import com.example.booking.ReserveActivity.GetnumThread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookingSocketActivity extends Activity {
	Urls urls;
	String url,lobby_num;
	Button btnConnect;
	MySocketIO socketIO;
	TextView tv_companyName, tv_No,yournum;
	MyHandler handler = new MyHandler();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_shou);
		url = urls.getURL();
		lobby_num = urls.getlobby_num();
		btnConnect = (Button) this.findViewById(R.id.button1);
		tv_companyName = (TextView) this.findViewById(R.id.textView2);
		tv_No = (TextView) this.findViewById(R.id.textView3);
		yournum= (TextView) this.findViewById(R.id.yournum);
		yournum.setText(lobby_num);
		btnConnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				socketIO = new MySocketIO(handler);
			}
		});
		
	}
	
	public class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			switch (msg.what) {
			case 1: {
				BookingSocketActivity.this.setTitle("连接成功");
				break;
			}
			case 2: {

				Bundle bundle = msg.getData();

				tv_companyName.setText(bundle.getString("company"));
				tv_No.setText(bundle.getString("no"));

				break;
			}
			default:
				break;

			}

		}

	}
	
}
