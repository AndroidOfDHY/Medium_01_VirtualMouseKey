package com.format.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.format.util.Connector;
import com.format.util.SysApplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class GridViewActivity extends Activity {

	private int mainImageId[] = { R.drawable.app_help, R.drawable.connect,
			R.drawable.keyboard, R.drawable.mouse, R.drawable.ppt,
			R.drawable.logout, };

	private String mainImageText[] = { "���ʹ��", "����PC", "��������", "�������", "PPT����",
			"�˳�" };

	private String[] strs = { "��ӭʹ�����߼���", };

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				updateText();
				break;
			}
		};

	};

	private int index = 0;

	private GridView gv;

	private TextSwitcher ts;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		gv = (GridView) this.findViewById(R.id.gridView);
		ts = (TextSwitcher) this.findViewById(R.id.textSwitcher);

		initGridView();
		initTextSwitcher();
		initTimer();

		// �����Ϣ����
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int itemId,
					long arg3) {
				if (itemId == 5) {
					SysApplication.getInstance().exit();
				} else if (!Connector.isConnected() && itemId != 1 && itemId != 0) {
					showWrongAnimation();
				} else if (itemId == 0) {
					goOtherActivity(CourseActivity.class);
				} else if (itemId == 1) {
					goOtherActivity(ConnectActivity.class);
				} else if (itemId == 2) {
					goOtherActivity(VirtualKeyBoard.class);
				} else if (itemId == 3) {
					goOtherActivity(VirtualMouse.class);
				} else if (itemId == 4) {
					goOtherActivity(PPTHelper.class);
				}  
			}
		});

	}

	protected void goOtherActivity(Class<? extends Activity> c) {
		Intent intent = new Intent();
		intent.setClass(this, c);
		System.out.println("start anther activity...");
		this.startActivity(intent);
	}

	protected void showWrongAnimation() {
		gv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
		this.showMsg("��������PC");
	}

	private void initTimer() {
		Timer timer = new Timer();
		timer.schedule(new MyTask(), 0, 2000);
	}

	private void initTextSwitcher() {
		ts.setFactory(new MyViewFactory());
		ts.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left));
		ts.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right));
	}

	private void initGridView() {
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < mainImageId.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", mainImageId[i]);// ���ͼ����Դ��ID
			map.put("ItemText", mainImageText[i]);// �������ItemText
			lstImageItem.add(map);
		}
		// ������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ
		SimpleAdapter saImageItems = new SimpleAdapter(this, // ûʲô����
				lstImageItem,// ������Դ
				R.layout.item,// night_item��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				new String[] { "ItemImage", "ItemText" },
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// ��Ӳ�����ʾ
		gv.setAdapter(saImageItems);
	}

	private void updateText() {
		ts.setText(strs[index]);
		index++;
		if (index >= strs.length) {
			index = 0;
		}
	}

	private class MyViewFactory implements ViewFactory {

		@Override
		public View makeView() {
			TextView tv = new TextView(GridViewActivity.this);
			tv.setTextColor(Color.RED);
			tv.setTextSize(30);
			tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
			return tv;
		}

	}

	private class MyTask extends TimerTask {

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}

	}

	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}