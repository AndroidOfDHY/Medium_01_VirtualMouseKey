package com.format.activity;

import google.project.exception.SDCardException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.format.util.SDCardHelper;
import com.format.view.ButtonPoint;
import com.format.view.KeyBoardView;

public class VirtualKeyBoard extends Activity {
	
	private int[] imageIds = new int[] {
			R.drawable.key_a,
			R.drawable.key_b,
			R.drawable.key_c,
			R.drawable.key_d,
			R.drawable.key_e,
			R.drawable.key_f,
			R.drawable.key_g,
			R.drawable.key_h,
			R.drawable.key_i,
			R.drawable.key_j,
			R.drawable.key_k,
			R.drawable.key_l,
			R.drawable.key_m,
			R.drawable.key_n,
			R.drawable.key_o,
			R.drawable.key_p,
			R.drawable.key_q,
			R.drawable.key_r,
			R.drawable.key_s,
			R.drawable.key_t,
			R.drawable.key_u,
			R.drawable.key_v,
			R.drawable.key_w,
			R.drawable.key_x,
			R.drawable.key_y,
			R.drawable.key_z,
			R.drawable.key_1,
			R.drawable.key_2,
			R.drawable.key_3,
			R.drawable.key_4,
			R.drawable.key_5,
			R.drawable.key_6,
			R.drawable.key_7,
			R.drawable.key_8,
			R.drawable.key_9,
			R.drawable.key_0,
			R.drawable.key_up,
			R.drawable.key_down,
			R.drawable.key_left,
			R.drawable.key_right,
			R.drawable.key_backspace,
			R.drawable.key_esc,
			R.drawable.key_return,
	};
	

	private String[] letters = {
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1"
			,"2","3","4","5","6","7","8","9","0","��","��","��","��","��","ESC","RT"
	};
	
	private String[] EN_LETTERS = {
			"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"
	};
	
	//Ĭ�ϵĴ洢�ļ���
	private String dirName = "VirtualDevice";
	
	final int SINGLE_DIALOG = 7788;
	
	//��ť��Ĭ�ϰ뾶
	private int radius;
	
	//��ť���ֵ���ʼλ��
	private int startX;
	private int startY;
	
	private TextView tip = null;
	
	private KeyBoardView view = null;
	
	private ListView listView = null;
	
	
	public int getRadius() {
		return radius;
	}


	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.startX = 0;
		this.startY = 0;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.key);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		tip = (TextView)this.findViewById(R.id.tip);
		listView = (ListView)this.findViewById(R.id.listView);
		view = (KeyBoardView)this.findViewById(R.id.keyBoard);
		
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // ��Ļ��ȣ����أ�
        int height = metric.heightPixels;  // ��Ļ�߶ȣ����أ�
        this.setRadius(this.adjustFontSize(width, height));
	
		//�Զ�����3������ļ���
		init3StandardKeyBoards();

		this.initListView();
		
	}
	
	//�Զ�����3������ļ��̺���,Activityһ��ʼ��������ļ���
	private void init3StandardKeyBoards() {
		
		try {
			
			if(!SDCardHelper.getInstance().isSDCardExist()) {
				VirtualKeyBoard.this.showMsg("SD��������!");
				return ;
			}
			
			if(!SDCardHelper.getInstance().isFileExist("��ĸ���ֱ�׼����")) {
				ArrayList<ButtonPoint> all = this.generateStandardKeyboard();
				File file = SDCardHelper.getInstance().creatNewFile("��ĸ���ֱ�׼����");
				SDCardHelper.getInstance().writeToFile(all, file);
			}
			
			if(!SDCardHelper.getInstance().isFileExist("���ֱ�׼����")) {
				ArrayList<ButtonPoint> num = this.generateStandardNums();
				File file = SDCardHelper.getInstance().creatNewFile("���ֱ�׼����");
				SDCardHelper.getInstance().writeToFile(num, file);
			}
			
			if(!SDCardHelper.getInstance().isFileExist("��ĸ��׼����")) {
				ArrayList<ButtonPoint> letter = this.generateStandardLetters();
				File file = SDCardHelper.getInstance().creatNewFile("��ĸ��׼����");
				SDCardHelper.getInstance().writeToFile(letter, file);
			}
		} catch (SDCardException e) {
			VirtualKeyBoard.this.showMsg(e.getMessage());
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, "���浱ǰ����");
		menu.add(1, 2, 2, "�����ѱ����ļ�");
		menu.add(1, 3, 3, "ɾ�����һ����ť");
		menu.add(2, 2, 2, "���²���");
		menu.add(2, 1, 1, "������ǰ����");
		menu.add(2, 3, 3, "�˳�");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch(id)
		{
		case SINGLE_DIALOG:
			
			File dir = SDCardHelper.getInstance().getDir(this.dirName);
			File[] files = dir.listFiles();
			final String[] fileNames = new String[files.length];
			
			for(int i = 0; i < fileNames.length; i ++) {
				fileNames[i] = files[i].getName().subSequence(0, files[i].getName().length() - 4).toString();
			}
			
			Builder b = new AlertDialog.Builder(this);
			b.setTitle("��ѡ���ļ�");
			b.setSingleChoiceItems(fileNames, -1, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						System.out.println(fileNames[which]);
						view.getPoints().clear();
						SDCardHelper.getInstance().readInfoFromFile(fileNames[which], view);
					} catch (SDCardException e) {
						VirtualKeyBoard.this.showMsg(e.getMessage());
					}
				}
			});
			b.setNegativeButton("ȷ��", null);
			return b.create();
		}
		return null;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getGroupId()) 
		{
		case 1:
			switch(item.getItemId())
			{
			case 1:
				//���沼��
				item.setEnabled(false);
				this.savedInTheFile();
				break;
			case 2:
				//�����ѱ����ļ�
				if(!SDCardHelper.getInstance().isDirExist(dirName)) {
					this.showMsg("���ޱ����ļ�,���ȱ��沼��!");
					return super.onOptionsItemSelected(item);
				}
				if(SDCardHelper.getInstance().getDir(dirName).list().length == 0) {
					this.showMsg("���ޱ����ļ�,���ȱ��沼��!");
					return super.onOptionsItemSelected(item);
				}
				this.showDialog(SINGLE_DIALOG);
				break;
			case 3:
				//����
				if(view.isLocked()) {
					this.showMsg("����״̬���޷�����!");
					return super.onOptionsItemSelected(item);
				}
				if(view.getPoints().size() == 0) {
					this.showMsg("Ŀǰû�м���,����ʧ��!");
					return super.onOptionsItemSelected(item);
				}
				view.getPoints().remove(view.getPoints().size()-1);
				if(view.getPoints().size() >= 20) {
					this.startX = 0;
					this.startY = 0;
				}
				view.postInvalidate();
				break;
			}
			break;

		case 2:
			switch(item.getItemId())
			{
			case 1:
				//-----����-----
				if(view.isLocked() == true) {
					this.showMsg("���Ѿ�������!");
					return super.onOptionsItemSelected(item);
				}
				view.setLocked(true);
				this.tip.setVisibility(View.INVISIBLE);
				//����֮��������ӷ�����
				break;
			case 2:
				//���²���
				view.getPoints().clear();
				this.startX = radius;
				this.startY = radius;
				view.postInvalidate();
				this.tip.setVisibility(View.VISIBLE);
				view.setLocked(false);
				this.startX = 0;
				this.startY = 0;
				break;
			case 3:
				//�˳�
				view.setLocked(false);
				goOtherActivity(GridViewActivity.class);
				break;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * ���ֱ��浽�ļ���
	 */
	private void savedInTheFile() {
		final EditText et = new EditText(this);
		new AlertDialog.Builder(this).setTitle("�������ļ���").setView(
			     et).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							if(!SDCardHelper.getInstance().isSDCardExist()) {
								VirtualKeyBoard.this.showMsg("SD��������,�޷�����!");
								return ;
							}
							File file = SDCardHelper.getInstance().creatNewFile(et.getText().toString().trim());
							SDCardHelper.getInstance().writeToFile(view.getPoints(), file);
							VirtualKeyBoard.this.showMsg("�����ļ��ɹ�!");
						} catch (SDCardException e) {
							VirtualKeyBoard.this.showMsg(e.getMessage());
						}
					}
				})
			     .setNegativeButton("ȡ��", null).show();
	}

	private void initListView() {
		List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
		for(int i = 0 ; i < imageIds.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("myImage", imageIds[i]);
			listItems.add(listItem);
		}
		
		SimpleAdapter adapter = new SimpleAdapter(this,listItems,R.layout.adapter,
														new String[] {"myImage"},
														new int[] {R.id.myImage});
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int start,
					long arg3) {
				if(view.isLocked()) {
					VirtualKeyBoard.this.showMsg("��������ס,�޷����.����������!");
					return ;
				}
				if(view.getPoints().size() >= 44) {
					VirtualKeyBoard.this.showMsg("��������Ӹ���ļ�����!");
					return ;
				}
				view.getPoints().add(new ButtonPoint(startX, startY, VirtualKeyBoard.this.getRadius(), letters[start]));
				if(startX + radius >= view.getWidth()) {
					startX = 0;
					startY += radius;
				} else {
					startX += radius;
				}
				view.postInvalidate();					
			}
		});
	}
	
	/**
	 * ����һ����׼�ļ��̣�ֻ�������ֺ���ĸ
	 */
	public ArrayList<ButtonPoint> generateStandardKeyboard() {
		ArrayList<ButtonPoint> letterNum = new ArrayList<ButtonPoint>();
		for(int i = 0; i < 10; i ++) {
			letterNum.add(new ButtonPoint( VirtualKeyBoard.this.getRadius() * (i + 1),VirtualKeyBoard.this.getRadius(), VirtualKeyBoard.this.getRadius(), letters[i + 26]));
		}
		
		for(int i = 0; i < 10; i ++) {
			letterNum.add(new ButtonPoint(VirtualKeyBoard.this.getRadius() * (i + 1),VirtualKeyBoard.this.getRadius() * 3,VirtualKeyBoard.this.getRadius(), EN_LETTERS[i]));
		}
		for(int i = 0; i < 9; i ++) {
			letterNum.add(new ButtonPoint(VirtualKeyBoard.this.getRadius() * (i + 1) + VirtualKeyBoard.this.getRadius() / 2,VirtualKeyBoard.this.getRadius() * 4,VirtualKeyBoard.this.getRadius(), EN_LETTERS[i + 10]));
		}
		
		for(int i = 0; i < 7; i ++) {
			letterNum.add(new ButtonPoint(VirtualKeyBoard.this.getRadius() * (i + 1) + VirtualKeyBoard.this.getRadius(),VirtualKeyBoard.this.getRadius() * 5,VirtualKeyBoard.this.getRadius(), EN_LETTERS[19 + i]));
		}
		return letterNum;
	}
	
	/**
	 * ����һ����׼�ļ��̣�ֻ��������
	 */
	public ArrayList<ButtonPoint> generateStandardNums() {
		ArrayList<ButtonPoint> num = new ArrayList<ButtonPoint>();
		for(int i = 0; i < 10; i ++) {
			num.add(new ButtonPoint( VirtualKeyBoard.this.getRadius() * (i + 1),VirtualKeyBoard.this.getRadius(), VirtualKeyBoard.this.getRadius(), letters[i + 26]));
		}
		return num;
	}
	
	/**
	 * ����һ����׼�ļ��̣�ֻ������ĸ
	 */
	public ArrayList<ButtonPoint> generateStandardLetters() {
		ArrayList<ButtonPoint> letter = new ArrayList<ButtonPoint>();
		for(int i = 0; i < 10; i ++) {
			letter.add(new ButtonPoint(VirtualKeyBoard.this.getRadius() * (i + 1),VirtualKeyBoard.this.getRadius() * 3,VirtualKeyBoard.this.getRadius(), EN_LETTERS[i]));
		}
		for(int i = 0; i < 9; i ++) {
			letter.add(new ButtonPoint(VirtualKeyBoard.this.getRadius() * (i + 1) + VirtualKeyBoard.this.getRadius() / 2,VirtualKeyBoard.this.getRadius() * 4,VirtualKeyBoard.this.getRadius(), EN_LETTERS[i + 10]));
		}
		
		for(int i = 0; i < 7; i ++) {
			letter.add(new ButtonPoint(VirtualKeyBoard.this.getRadius() * (i + 1) + VirtualKeyBoard.this.getRadius(),VirtualKeyBoard.this.getRadius() * 5,VirtualKeyBoard.this.getRadius(), EN_LETTERS[19 + i]));
		}
		return letter;
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
	
	public int adjustFontSize(int screenWidth, int screenHeight){
	    if (screenWidth <= 240) {        // 240X320 ��Ļ
	        return 20;
	    }else if (screenWidth <= 320){   // 320X480 ��Ļ
	        return 30;
	    }else if (screenWidth <= 480){   // 480X800 �� 480X854 ��Ļ
	        return 60;
	    }else if (screenWidth <= 540){   // 540X960 ��Ļ
	        return 80;
	    }else if(screenWidth <= 800){    // 800X1280 ��Ļ
	        return 80;
	    }else{                          // ���� 800X1280
	        return 80;
	    }
	}


}
