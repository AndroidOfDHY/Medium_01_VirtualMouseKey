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
 * 自定义键盘操作视图
 * @author Administrator
 *
 */
public class KeyBoardView extends View {
	
	private ButtonPoint[] lockBtns = new ButtonPoint[30];

	private static final int TEXT_SIZE = 25;
	
	//判断该View是否处于锁定状态
	private boolean isLocked = false;
	

	//被拖动的按钮的索引
	private int selectedIndex = -1;
	
	//按钮集合
	private ArrayList<ButtonPoint> points ;
	

	private Paint mPaint ;
	
	//该View的高度
	private int viewHeight;
	
	//该View的宽度
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
	 * 获取这个View的宽和高
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
		
		//如果锁定了，可以多点触碰，并且按钮不可移动
		if(this.isLocked) {
			this.doInTheLockStatus(event);
		}
		//如果没有锁定，不能进行多点触碰，按钮可以移动定位
		else {
			this.doInTheUnLockStatus(event);
		}
		
		/**
		 * 如果去呼叫super.onTouchEvent()則很有可能super里面并没做任何事，并且回传false回来
		 * 一旦回传false回來，很可能后面的event (例如：Action_Move、Action_Up) 都会收不到了
		 * 所以为了确保保后面event能順利收到，要注意是否要直接呼super.TouchEvent()
		 * 必须返回true
		 */
		return true;
	}
	
	
	//未锁定屏幕操作
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
	
	

	//锁定屏幕操作
	private void doInTheLockStatus(MotionEvent event) {
		int command = event.getAction() & MotionEvent.ACTION_MASK;
		ButtonPoint btn = null;
		//获取选取的那个点的Index索引
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
	
	//锁定状态下按钮按下处理函数
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
					showMsg("连接已断开!");
				}
				this.postInvalidate();
			}
		}
		
		
		//锁定状态下按钮弹起处理函数
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
						showMsg("连接已断开!");
					}
					this.postInvalidate();	
				}
		}


	
	//根据x和y坐标得到相应的按钮，不存在返回null
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
