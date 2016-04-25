package com.format.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.format.adapter.GalleryAdapter;
import com.format.util.SysApplication;
import com.format.view.MyGallery;

public class CourseActivity extends Activity {
	
	private int imagesId[] = {
			R.drawable.lession1,
			R.drawable.lession2,
			R.drawable.lession3,
			R.drawable.lession4,
			R.drawable.lession5,
			R.drawable.lession6,
			R.drawable.lession7
	};
	
	private String imagesStr[] = {
		"连接之前请确定手机上的热点已经打开",
		"手机上的热点打开之后在PC端上找到这个热点并且连接",
		"这时候可以通过连接PC连接电脑了",
		"无线鼠标界面",
		"无线键盘界面",
		"无线键盘界面",
		"PPT界面,注意,使用前请先打开PC端上的PPT文件"
	};
	
	private TextSwitcher ts;
	private MyGallery gl;
	private ImageSwitcher is;
	
	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {    
            switch (msg.what) {    
            case 1:    
            	Bundle b = msg.getData();
            	int index = b.getInt("index");
            	is.setImageResource(imagesId[index]);
                break;    
            case 2:
            	Bundle b2 = msg.getData();
            	int index2 = b2.getInt("index");
            	ts.setText(imagesStr[index2]);
            	break;
            }    
        };   
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SysApplication.getInstance().addActivity(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.course);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		ts = (TextSwitcher)this.findViewById(R.id.courseTS);
		gl = (MyGallery)this.findViewById(R.id.gallery);
		is = (ImageSwitcher)this.findViewById(R.id.imageSwitcher);
		
		initTextSwitcher();
		initGallery();
		initImageSwitcher();
	}
	
	//菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.course_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	//菜单选中发生事件处理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.course_exit:
			SysApplication.getInstance().exit();
			break;
		case R.id.course_main:
			this.goOtherActivity(GridViewActivity.class);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initImageSwitcher() {
		is.setFactory(new MyViewFactory("ImageSwitcher"));
		is.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));  
		is.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
	}

	private void initGallery() {
		GalleryAdapter adapter = new GalleryAdapter(this);
		
		gl.setSpacing(160);
		
		gl.setAdapter(adapter);
		
		gl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				
				for(int i = 1 ;i <= 2; i ++ ) {
					Message msg = new Message();
					msg.what = i;
					Bundle b = new Bundle();
					b.putInt("index", index);
					msg.setData(b);
					handler.sendMessage(msg);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}

	private void initTextSwitcher() {
		ts.setFactory(new MyViewFactory("TextSwitcher"));
		ts.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));  
		ts.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
	}
	
	protected void goOtherActivity(Class<? extends Activity> c) {
		Intent intent = new Intent();
		intent.setClass(this, c);
		this.startActivity(intent);
	}
	
	private class MyViewFactory implements ViewFactory {
		
		String myType;
		
		public MyViewFactory(String type) {
			this.myType = type;
		}

		@Override
		public View makeView() {
			if(myType.equals("ImageSwitcher")) {
				ImageView iv = new ImageView(CourseActivity.this);
				return iv;
			} else if(myType.equals("TextSwitcher")) {
				TextView tv = new TextView(CourseActivity.this);
				tv.setTextColor(Color.RED);
				tv.setTextSize(20);
				tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);    
				return tv;
			} else {
				return null;
			}
		} 
		
	}

}
