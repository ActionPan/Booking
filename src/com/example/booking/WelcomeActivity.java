package com.example.booking;

import com.example.booking.LoginActivity;
import com.example.booking.R;
import com.example.booking.WelcomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class WelcomeActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.welcome_layout);
			LayoutInflater inflater=LayoutInflater.from(WelcomeActivity.this);
			View view=inflater.inflate(R.layout.welcome_layout,null);
			setContentView(view);
			AlphaAnimation animation =new AlphaAnimation(0.1f, 1.0f);
			animation.setDuration(3000);
			view.setAnimation(animation);
			animation.startNow();
			animation.setAnimationListener(new AnimationListener() {
				
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					WelcomeActivity.this.finish();
				}
			});
		}
}
