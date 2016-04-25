package com.format.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.format.util.ComUtil;
import com.format.util.Connector;
import com.format.util.KeyGenerater;


/**
 * 模拟一个PPT助手。
 * 包括基本的上下左右  	四个键
 * @author Administrator
 *
 */
public class PPTHelper extends Activity {
	
	
	//播放按钮的切换数组
	private int imageId[] = new int[] {
		R.drawable.playppt,
		R.drawable.stopppt
	};
	
	private int index = 1;
	
	
	private ImageView[] btns = new ImageView[4] ;
	private ImageView play ;
	private Button close ;
	private Button sensor ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.ppt);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//初始化所有的按钮
		this.initBtns();
		
		//上
		btns[0].setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					btns[0].setImageResource(R.drawable.uppptclicked);
					try {
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("UP"));
					} catch (Exception e) {
						showMsg("连接已断开!");
					}
					break;
				case MotionEvent.ACTION_UP:
					btns[0].setImageResource(R.drawable.upppt);
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("UP"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				}
				return true;
			}
		});
		
		//下
		btns[1].setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					btns[1].setImageResource(R.drawable.downpptclicked);
					try {
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("DOWN"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				case MotionEvent.ACTION_UP:
					btns[1].setImageResource(R.drawable.downppt);
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("DOWN"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				}
				return true;
			}
		});
		
		//左
		btns[2].setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					btns[2].setImageResource(R.drawable.leftpptclicked);
					try {
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("LEFT"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				case MotionEvent.ACTION_UP:
					btns[2].setImageResource(R.drawable.leftppt);
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("LEFT"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				}
				return true;
			}
		});

		//右
		btns[3].setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					btns[3].setImageResource(R.drawable.rightpptclicked);
					try {
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("RIGHT"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				case MotionEvent.ACTION_UP:
					btns[3].setImageResource(R.drawable.rightppt);
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("RIGHT"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				}
				return true;
			}
		});
		
		//放大放灯片按钮 
		play.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					play.setImageResource(imageId[index]);
					index ++;
					if(index == imageId.length) {
						index = 0;
					}
					/*try {
						dos.writeInt(ComUtil.KEYDOWN);
						dos.writeInt(KeyGenerater.getKey("SHIFT"));
						dos.writeInt(ComUtil.KEYDOWN);
						dos.writeInt(KeyGenerater.getKey("F5"));
					} catch (IOException e) {
						e.printStackTrace();
					}*/
					break;
				case MotionEvent.ACTION_UP:
					/*try {
						dos.writeInt(ComUtil.KEYUP);
						dos.writeInt(KeyGenerater.getKey("F5"));
						dos.writeInt(ComUtil.KEYUP);
						dos.writeInt(KeyGenerater.getKey("SHIFT"));
					} catch (IOException e) {
						e.printStackTrace();
					}*/
					break;
				}
				return true;
			}
		});
		
		//缩小放灯片按钮
		close.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					try {
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("ESC"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				case MotionEvent.ACTION_UP:
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("ESC"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				}
				return false;
			}
		});
		
		
		sensor.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					try {
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("SHIFT"));
						Connector.writeInt(ComUtil.KEYDOWN);
						Connector.writeInt(KeyGenerater.getKey("F5"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
					break;
				case MotionEvent.ACTION_UP:
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("SHIFT"));
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey("F5"));
					} catch (IOException e) {
						showMsg("连接已断开!");
					}
				}
				return false;
			}
		});
		
		
	}

	

	//初始化本Activity的按钮
	private void initBtns() {
		btns[0] = (ImageView)this.findViewById(R.id.pptUp);
		btns[1] = (ImageView)this.findViewById(R.id.pptDown);
		btns[2] = (ImageView)this.findViewById(R.id.pptLeft);
		btns[3] = (ImageView)this.findViewById(R.id.pptRight);
		play = (ImageView)this.findViewById(R.id.pptCenter);
		close = (Button)this.findViewById(R.id.pptClose);
		sensor = (Button)this.findViewById(R.id.pptSensor);
	}
	
	
	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	
}
