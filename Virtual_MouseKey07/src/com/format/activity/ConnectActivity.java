package com.format.activity;

import google.project.exception.NetWorkException;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.format.util.Connector;
import com.format.util.SysApplication;

public class ConnectActivity extends Activity {
	
	private EditText ipET ;
	private TextView iptv;
	private Button conButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SysApplication.getInstance().addActivity(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.conn);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		ipET = (EditText)this.findViewById(R.id.ipet);
		iptv = (TextView)this.findViewById(R.id.iptv);
		conButton=(Button)this.findViewById(R.id.connectButton);
		final String regExp = "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$";
		conButton.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				if(!Connector.isConnected()){
					System.out.println("进行连接");
				//	Connector.connect(ipET.getText().toString());
					System.out.println(ipET.getText().toString());
					boolean b = ipET.getText().toString().trim().matches(regExp);
					System.out.println(b);
					if(b == false) {
						ConnectActivity.this.showMsg("输入的ip地址格式不正确!");
						return ;
					}
					try {
						Connector.connect(ipET.getText().toString());
					} catch (NetWorkException e) {
						ConnectActivity.this.showMsg(e.getMessage());
						return ;
					}
					Connector.setConnected(true);
					ConnectActivity.this.showMsg("连接成功!");
					goOtherActivity(GridViewActivity.class);
				}else{
					System.out.println("已经连接");
				}
			}
		});
		showAnimation();
		
		
		
	}

	private void showAnimation() {
		Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
		iptv.startAnimation(alpha);
		Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
		ipET.startAnimation(scale);
	}
	
	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected void goOtherActivity(Class<? extends Activity> c) {
		Intent intent = new Intent();
		intent.setClass(this, c);
		System.out.println("start anther activity...");
		this.startActivity(intent);
	}
	
}
