package com.format.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

import com.format.util.ComUtil;
import com.format.util.Connector;

public class VirtualMouse extends Activity {
	private Button leftButton, rightButton;
	// String ip="123.152.7.168";
	private boolean leftKeyDown = false;
	private float startX, startY;
	long lastTime;
	long messageTime;
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.course);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.mouse);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		leftButton = (Button) findViewById(R.id.leftButton);
		rightButton = (Button) findViewById(R.id.rightButton);

		leftButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println("左键点击");
					try {
						Connector.writeInt(ComUtil.MOUSEKEYDOWN);
						Connector.writeInt(ComUtil.MOUSELEFTKEY);
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					leftKeyDown = true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					System.out.println("左键弹起");
					try {
						Connector.writeInt(ComUtil.MOUSEKEYUP);
						Connector.writeInt(ComUtil.MOUSELEFTKEY);
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
				}
				return true;
			}
		});
		rightButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println("右键点击");
					try {
						Connector.writeInt(ComUtil.MOUSEKEYDOWN);
						Connector.writeInt(ComUtil.MOUSERIGHTKEY);
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					leftKeyDown = true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					System.out.println("右键弹起");
					try {
						Connector.writeInt(ComUtil.MOUSEKEYUP);
						Connector.writeInt(ComUtil.MOUSERIGHTKEY);
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
				}
				return true;
			}
		});
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("keyDown");
			startX = event.getX();
			startY = event.getY();
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastTime < 400) {
				try {
					Connector.writeInt(ComUtil.MOUSEKEYDOWN);
					Connector.writeInt(ComUtil.MOUSELEFTKEY);
				} catch (IOException e) {
					showMsg("连接已断开!");
				}
				leftKeyDown = true;
			}
			lastTime = currentTime;
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			System.out.println("keyUp");
			if (leftKeyDown) {
				try {
					Connector.writeInt(ComUtil.MOUSEKEYUP);
					Connector.writeInt(ComUtil.MOUSELEFTKEY);
				} catch (IOException e) {
					showMsg("连接已断开!");
				}
				leftKeyDown = false;
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float endX = event.getX();
			float endY = event.getY();
			float moveX = endX - startX;
			float moveY = endY - startY;
			if (moveX > -1.1 && moveX < 1.1) {
				moveX *= 2;
			}
			if (moveY > -1.1 && moveY < 1.1) {
				moveY *= 2;
			}
			try {
				Connector.writeInt(ComUtil.MOUSEMOVE);
				Connector.writeFloat(moveX);
				Connector.writeFloat(moveY);
			} catch (IOException e) {
				showMsg("连接已断开!");
			}
			startX = endX;
			startY = endY;
		}
		return true;
	}

	public void showMsg(String msg) {
		long currentTime=System.currentTimeMillis();
		Connector.setConnected(false);
		if(currentTime-messageTime>=1000*3){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
		messageTime=currentTime;
	}

}