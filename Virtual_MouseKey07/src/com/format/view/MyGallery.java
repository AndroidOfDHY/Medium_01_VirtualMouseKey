package com.format.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * �Լ���Gallery  һ�λ���ֻ��ˢһҳ
 * @author Administrator
 *
 */
public class MyGallery extends Gallery {

	public MyGallery(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    @Override  
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
            float velocityY) {  
        return false; 
    }  
	
}
