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
 * 所有的Fragment都嵌入在这里。
 */
public class ManageActivity extends Activity implements OnClickListener {

	private CustomFragment customFragment; // 用于展示定制的Fragment
	private WorkFragment workFragment; // 用于展示主业务的Fragment
	private PersonFragment personFragment; // 用于展示人员的Fragment
	private CountFragment countFragment; // 用于展示统计的Fragment
	private View backimgLayout;
	private View customLayout; // 定制界面布局
	private View workLayout; // 主业务界面布局
	private View personLayout; // 人员界面布局
	private View countLayout; // 统计界面布局
	private ImageView customImage; // 在Tab布局上显示定制图标的控件
	private ImageView workImage; // 在Tab布局上显示主业务图标的控件
	private ImageView personImage; // 在Tab布局上显示人员图标的控件
	private ImageView countImage; // 在Tab布局上显示统计图标的控件
	private TextView titleText;
	private TextView customText; // 在Tab布局上显示定制标题的控件
	private TextView workText; // 在Tab布局上显示主业务标题的控件
	private TextView personText; // 在Tab布局上显示人员标题的控件
	private TextView countText; // 在Tab布局上显示统计标题的控件

	CountFragment countfragment;

	private FragmentManager fragmentManager; // 用于对Fragment进行管理

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manage_layout);
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	// 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
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
			// 当点击了定制tab时，选中第1个tab
			setTabSelection(0);
			titleText.setText("业务定制");
			break;
		case R.id.work_layout:
			// 当点击了主业务tab时，选中第2个tab
			setTabSelection(1);
			titleText.setText("统计查询");
			break;
		case R.id.person_layout:
			// 当点击了人员tab时，选中第3个tab
			setTabSelection(2);
			titleText.setText("人员定制");
			break;
		case R.id.count_layout:
			// 当点击了统计tab时，选中第4个tab
			setTabSelection(3);
			titleText.setText("主业务功能");
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示定制，1表示主业务，2表示人员，3表示统计。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了定制tab时，改变控件的图片和文字颜色
			customImage.setImageResource(R.drawable.message_selected);
			customText.setTextColor(Color.BLUE);
			if (customFragment == null) {
				// 如果WorkFragment为空，则创建一个并添加到界面上
				customFragment = new CustomFragment();
				transaction.add(R.id.content, customFragment);
			} else {
				// 如果WorkFragment不为空，则直接将它显示出来
				transaction.show(customFragment);
			}
			break;
		case 1:
			// 当点击了主业务tab时，改变控件的图片和文字颜色
			workImage.setImageResource(R.drawable.contacts_selected);
			workText.setTextColor(Color.BLUE);
			if (workFragment == null) {
				// 如果FunctionFragment为空，则创建一个并添加到界面上
				workFragment = new WorkFragment();
				transaction.add(R.id.content, workFragment);
			} else {
				// 如果FunctionFragment不为空，则直接将它显示出来
				transaction.show(workFragment);
			}
			break;
		case 2:
			// 当点击了人员tab时，改变控件的图片和文字颜色
			personImage.setImageResource(R.drawable.news_selected);
			personText.setTextColor(Color.BLUE);
			if (personFragment == null) {
				// 如果PersonFragment为空，则创建一个并添加到界面上
				personFragment = new PersonFragment();
				transaction.add(R.id.content, personFragment);
			} else {
				// 如果PersonFragment不为空，则直接将它显示出来
				transaction.show(personFragment);
			}
			break;
		case 3:
		default:
			// 当点击了统计tab时，改变控件的图片和文字颜色
			countImage.setImageResource(R.drawable.setting_selected);
			countText.setTextColor(Color.BLUE);
			if (countFragment == null) {
				// 如果QueryFragment为空，则创建一个并添加到界面上
				countFragment = new CountFragment();
				transaction.add(R.id.content, countFragment);
			} else {
				// 如果QueryFragment不为空，则直接将它显示出来
				transaction.show(countFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
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
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
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
		alertbBuilder.setTitle("退出登录").setMessage("退出当前账号");
		alertbBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
//						Intent intent = new Intent(ManageActivity.this,
//								LoginActivity.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						ManageActivity.this.startActivity(intent);
						ManageActivity.this.finish();
					}
				});
		alertbBuilder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		alertbBuilder.show();
	}

	public void Exit_app() {
		AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(this);
		alertbBuilder.setTitle("退出Booking").setMessage("你确定要离开客户端吗？");
		alertbBuilder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent();   
						intent.setClass(ManageActivity.this, LoginActivity.class);  
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置  
						startActivity(intent);  
						finish();//关掉自己  
					}
				});
		alertbBuilder.setNegativeButton("取消",
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
