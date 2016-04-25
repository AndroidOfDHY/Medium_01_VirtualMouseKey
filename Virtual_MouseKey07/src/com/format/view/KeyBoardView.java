package com.format.view;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.format.util.ComUtil;
import com.format.util.Connector;
import com.format.util.KeyGenerater;


/**
 * �Զ�����̲�����ͼ
 * @author Administrator
 *
 */
public class KeyBoardView extends View {
	
	private ButtonPoint[] lockBtns = new ButtonPoint[30];

	private static final int TEXT_SIZE = 25;
	
	//�жϸ�View�Ƿ�������״̬
	private boolean isLocked = false;
	

	//���϶��İ�ť������
	private int selectedIndex = -1;
	
	//��ť����
	private ArrayList<ButtonPoint> points ;
	

	private Paint mPaint ;
	
	//��View�ĸ߶�
	private int viewHeight;
	
	//��View�Ŀ��
	private int viewWidth;
	
	public KeyBoardView(Context context) {
		super(context);
		this.init();
	}
	

	public KeyBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}
	
	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public ArrayList<ButtonPoint> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<ButtonPoint> points) {
		this.points = points;
	}
	
	private void init() {
		points = new ArrayList<ButtonPoint>();
		//points.add(new ButtonPoint(60,60,RADIUS,"W"));
		//points.add(new ButtonPoint(160,160,RADIUS,"C"));
		//points.add(new ButtonPoint(360,360,RADIUS,"S"));
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(1);
		mPaint.setTextSize(TEXT_SIZE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.getViewWidthAndHeight();
		for(int i = 0 ; i < points.size();i ++) {
			ButtonPoint point = points.get(i);
			mPaint.setColor(Color.BLUE);
			if(point.isClicked()) {
				mPaint.setStyle(Style.FILL);
			} else {
				mPaint.setStyle(Paint.Style.STROKE);
			}
			canvas.drawRect(new Rect(point.getX(),point.getY(),point.getX() + point.getRadius(),point.getY() + point.getRadius()), mPaint);
			//canvas.drawCircle((int)point.getX(), (int)point.getY(), point.getRadius(), mPaint);
			mPaint.setColor(Color.RED);
			canvas.drawText(point.getLetter(), point.getX() + point.getRadius() / 2, point.getY() + point.getRadius() / 2, mPaint);
		}
	}

	
	/**
	 * ��ȡ���View�Ŀ�͸�
	 */
	private void getViewWidthAndHeight() {
		if(this.viewHeight == 0) {
			this.viewHeight = this.getHeight();
		}
		if(this.viewWidth == 0) {
			this.viewWidth = this.getWidth();
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		//��������ˣ����Զ�㴥�������Ұ�ť�����ƶ�
		if(this.isLocked) {
			this.doInTheLockStatus(event);
		}
		//���û�����������ܽ��ж�㴥������ť�����ƶ���λ
		else {
			this.doInTheUnLockStatus(event);
		}
		
		/**
		 * ���ȥ����super.onTouchEvent()�t���п���super���沢û���κ��£����һش�false����
		 * һ���ش�false�؁��ܿ��ܺ����event (���磺Action_Move��Action_Up) �����ղ�����
		 * ����Ϊ��ȷ��������event������յ���Ҫע���Ƿ�Ҫֱ�Ӻ�super.TouchEvent()
		 * ���뷵��true
		 */
		return true;
	}
	
	
	//δ������Ļ����
	private void doInTheUnLockStatus(MotionEvent event) {
		int command = event.getAction();
		int x = (int)event.getX();
		int y = (int)event.getY();
		System.out.println(x + "," + y + "...........");
		ButtonPoint btn = null;
		switch(command)
		{
		case MotionEvent.ACTION_DOWN:
			btn = this.getButtonPointFromXAndY((int)event.getX(), (int)event.getY());
			if(btn != null && selectedIndex == -1) {
				selectedIndex = points.indexOf(btn);
				System.out.println(btn.getX() + "," + btn.getY() + "!!!!!!!!!!!!!");
				btn.setClicked(true);
				this.postInvalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			if(selectedIndex != -1) {
				btn = points.get(selectedIndex);
				btn.setClicked(false);
				this.postInvalidate();
			}
			if(btn != null && selectedIndex != -1) {
				selectedIndex = -1;
				btn.setClicked(false);
				this.postInvalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(selectedIndex != -1) {
				btn = points.get(selectedIndex);
				btn.setX(x - btn.getRadius() / 3, viewHeight);
				btn.setY(y - btn.getRadius() / 3, viewWidth);
				this.postInvalidate();
			}
			break;
		}
	}
	
	

	//������Ļ����
	private void doInTheLockStatus(MotionEvent event) {
		int command = event.getAction() & MotionEvent.ACTION_MASK;
		ButtonPoint btn = null;
		//��ȡѡȡ���Ǹ����Index����
		int index = event.getActionIndex();
		switch(command) 
		{
		case MotionEvent.ACTION_DOWN:
			this.buttonDownInLockStatus(btn,index,event);
			break;
		case MotionEvent.ACTION_UP:
			this.buttonUpInLockStatus(btn,index,event);
			break;
		
		case MotionEvent.ACTION_POINTER_UP:
			this.buttonUpInLockStatus(btn,index,event);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			this.buttonDownInLockStatus(btn,index,event);
			break;
		}
		
	}
	
	//����״̬�°�ť���´�����
		private void buttonDownInLockStatus(ButtonPoint btn, int index, MotionEvent event) {
			btn = this.getButtonPointFromXAndY((int)event.getX(index), (int)event.getY(index));
			if(btn != null) {
				int pointId = event.getPointerId(index);
				btn.setClicked(true);
				this.lockBtns[pointId] = btn;
				try {
					Connector.writeInt(ComUtil.KEYDOWN);
					Connector.writeInt(KeyGenerater.getKey(btn.getLetter()));
				} catch (IOException e) {
					showMsg("�����ѶϿ�!");
				}
				this.postInvalidate();
			}
		}
		
		
		//����״̬�°�ť��������
		private void buttonUpInLockStatus(ButtonPoint btn, int index, MotionEvent event) {
			
				int pointId = event.getPointerId(index);
				btn = this.lockBtns[pointId];
				if(btn != null) {
					this.lockBtns[pointId] = null;
					btn.setClicked(false);
					try {
						Connector.writeInt(ComUtil.KEYUP);
						Connector.writeInt(KeyGenerater.getKey(btn.getLetter()));
					} catch (IOException e) {
						showMsg("�����ѶϿ�!");
					}
					this.postInvalidate();	
				}
		}


	
	//����x��y����õ���Ӧ�İ�ť�������ڷ���null
	private ButtonPoint getButtonPointFromXAndY(int x,int y) {
		ButtonPoint point = null;
		for(int i = 0 ; i < points.size();i ++) {
			ButtonPoint bp = points.get(i);
			int offsetMinX = bp.getX();
			int offsetMinY = bp.getY();
			int offsetMaxX = bp.getX() + bp.getRadius();
			int offsetMaxY = bp.getY() + bp.getRadius();
			if(x >= offsetMinX && x <= offsetMaxX && y >= offsetMinY && y <= offsetMaxY) {
				point = bp;
				break;
			}
		}
		return point;
	}

	public void showMsg(String msg) {
		Connector.setConnected(false);
		Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
	}

}
