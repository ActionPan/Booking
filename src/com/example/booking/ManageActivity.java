package com.example.booking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ���е�Fragment��Ƕ�������
 */
public class ManageActivity extends Activity implements OnClickListener {

	private CustomFragment customFragment; // ����չʾ���Ƶ�Fragment
	private WorkFragment workFragment; // ����չʾ��ҵ���Fragment
	private PersonFragment personFragment; // ����չʾ��Ա��Fragment
	private CountFragment countFragment; // ����չʾͳ�Ƶ�Fragment
	private View backimgLayout;
	private View customLayout; // ���ƽ��沼��
	private View workLayout; // ��ҵ����沼��
	private View personLayout; // ��Ա���沼��
	private View countLayout; // ͳ�ƽ��沼��
	private ImageView customImage; // ��Tab��������ʾ����ͼ��Ŀؼ�
	private ImageView workImage; // ��Tab��������ʾ��ҵ��ͼ��Ŀؼ�
	private ImageView personImage; // ��Tab��������ʾ��Աͼ��Ŀؼ�
	private ImageView countImage; // ��Tab��������ʾͳ��ͼ��Ŀؼ�
	private TextView titleText;
	private TextView customText; // ��Tab��������ʾ���Ʊ���Ŀؼ�
	private TextView workText; // ��Tab��������ʾ��ҵ�����Ŀؼ�
	private TextView personText; // ��Tab��������ʾ��Ա����Ŀؼ�
	private TextView countText; // ��Tab��������ʾͳ�Ʊ���Ŀؼ�

	CountFragment countfragment;

	private FragmentManager fragmentManager; // ���ڶ�Fragment���й���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_layout);
		// ��ʼ������Ԫ��
		initViews();
		fragmentManager = getFragmentManager();
		// ��һ������ʱѡ�е�0��tab
		setTabSelection(0);
	}

	// �������ȡ��ÿ����Ҫ�õ��Ŀؼ���ʵ���������������úñ�Ҫ�ĵ���¼���
	private void initViews() {
		backimgLayout = findViewById(R.id.backimg_layout);
		customLayout = findViewById(R.id.custom_layout);
		workLayout = findViewById(R.id.work_layout);
		personLayout = findViewById(R.id.person_layout);
		countLayout = findViewById(R.id.count_layout);
		customImage = (ImageView) findViewById(R.id.custom_image);
		workImage = (ImageView) findViewById(R.id.work_image);
		personImage = (ImageView) findViewById(R.id.person_image);
		countImage = (ImageView) findViewById(R.id.count_image);
		titleText = (TextView) findViewById(R.id.title_text);
		customText = (TextView) findViewById(R.id.custom_text);
		workText = (TextView) findViewById(R.id.work_text);
		personText = (TextView) findViewById(R.id.person_text);
		countText = (TextView) findViewById(R.id.count_text);
		customLayout.setOnClickListener(this);
		workLayout.setOnClickListener(this);
		personLayout.setOnClickListener(this);
		countLayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_layout:
			// ������˶���tabʱ��ѡ�е�1��tab
			setTabSelection(0);
			titleText.setText("ҵ����");
			break;
		case R.id.work_layout:
			// ���������ҵ��tabʱ��ѡ�е�2��tab
			setTabSelection(1);
			titleText.setText("ͳ�Ʋ�ѯ");
			break;
		case R.id.person_layout:
			// ���������Աtabʱ��ѡ�е�3��tab
			setTabSelection(2);
			titleText.setText("��Ա����");
			break;
		case R.id.count_layout:
			// �������ͳ��tabʱ��ѡ�е�4��tab
			setTabSelection(3);
			titleText.setText("��ҵ����");
			break;
		default:
			break;
		}
	}

	/**
	 * ���ݴ����index����������ѡ�е�tabҳ��
	 * 
	 * @param index
	 *            ÿ��tabҳ��Ӧ���±ꡣ0��ʾ���ƣ�1��ʾ��ҵ��2��ʾ��Ա��3��ʾͳ�ơ�
	 */
	private void setTabSelection(int index) {
		// ÿ��ѡ��֮ǰ��������ϴε�ѡ��״̬
		clearSelection();
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			// ������˶���tabʱ���ı�ؼ���ͼƬ��������ɫ
			customImage.setImageResource(R.drawable.message_selected);
			customText.setTextColor(Color.BLUE);
			if (customFragment == null) {
				// ���WorkFragmentΪ�գ��򴴽�һ������ӵ�������
				customFragment = new CustomFragment();
				transaction.add(R.id.content, customFragment);
			} else {
				// ���WorkFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(customFragment);
			}
			break;
		case 1:
			// ���������ҵ��tabʱ���ı�ؼ���ͼƬ��������ɫ
			workImage.setImageResource(R.drawable.contacts_selected);
			workText.setTextColor(Color.BLUE);
			if (workFragment == null) {
				// ���FunctionFragmentΪ�գ��򴴽�һ������ӵ�������
				workFragment = new WorkFragment();
				transaction.add(R.id.content, workFragment);
			} else {
				// ���FunctionFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(workFragment);
			}
			break;
		case 2:
			// ���������Աtabʱ���ı�ؼ���ͼƬ��������ɫ
			personImage.setImageResource(R.drawable.news_selected);
			personText.setTextColor(Color.BLUE);
			if (personFragment == null) {
				// ���PersonFragmentΪ�գ��򴴽�һ������ӵ�������
				personFragment = new PersonFragment();
				transaction.add(R.id.content, personFragment);
			} else {
				// ���PersonFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(personFragment);
			}
			break;
		case 3:
		default:
			// �������ͳ��tabʱ���ı�ؼ���ͼƬ��������ɫ
			countImage.setImageResource(R.drawable.setting_selected);
			countText.setTextColor(Color.BLUE);
			if (countFragment == null) {
				// ���QueryFragmentΪ�գ��򴴽�һ������ӵ�������
				countFragment = new CountFragment();
				transaction.add(R.id.content, countFragment);
			} else {
				// ���QueryFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(countFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * ��������е�ѡ��״̬��
	 */
	private void clearSelection() {
		customImage.setImageResource(R.drawable.message_unselected);
		customText.setTextColor(Color.parseColor("#FFFFFF"));
		workImage.setImageResource(R.drawable.contacts_unselected);
		workText.setTextColor(Color.parseColor("#FFFFFF"));
		personImage.setImageResource(R.drawable.news_unselected);
		personText.setTextColor(Color.parseColor("#FFFFFF"));
		countImage.setImageResource(R.drawable.setting_unselected);
		countText.setTextColor(Color.parseColor("#FFFFFF"));
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (customFragment != null) {
			transaction.hide(customFragment);
		}
		if (workFragment != null) {
			transaction.hide(workFragment);
		}
		if (personFragment != null) {
			transaction.hide(personFragment);
		}
		if (countFragment != null) {
			transaction.hide(countFragment);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.Exit_login) {
			Exit_login();
		} else if (item.getItemId() == R.id.Exit_app) {
			Exit_app();
		}
		return super.onOptionsItemSelected(item);
	}

	public void Exit_login() {
		AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
		alertbBuilder.setTitle("�˳���¼").setMessage("�˳���ǰ�˺�");
		alertbBuilder.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
//						Intent intent = new Intent(ManageActivity.this,
//								LoginActivity.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						ManageActivity.this.startActivity(intent);
						ManageActivity.this.finish();
					}
				});
		alertbBuilder.setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		alertbBuilder.show();
	}

	public void Exit_app() {
		AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
		alertbBuilder.setTitle("�˳�Booking").setMessage("��ȷ��Ҫ�뿪�ͻ�����");
		alertbBuilder.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent();   
						intent.setClass(ManageActivity.this, LoginActivity.class);  
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //ע�Ȿ�е�FLAG����  
						startActivity(intent);  
						finish();//�ص��Լ�  
					}
				});
		alertbBuilder.setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		alertbBuilder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage, menu);
		return true;
	}
}
