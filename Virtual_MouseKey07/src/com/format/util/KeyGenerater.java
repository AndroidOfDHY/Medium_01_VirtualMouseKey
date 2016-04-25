package com.format.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 键盘生成器
 * 然按钮上的字母对应Java中的KeyEvent类的字母序列
 * @author Administrator
 *
 */
public class KeyGenerater {
	
	
	private static Map<String, Integer> keys = new HashMap<String, Integer>();
	
	static {
		keys.put("A", ComUtil.VK_A);
		keys.put("B", ComUtil.VK_B);
		keys.put("C", ComUtil.VK_C);
		keys.put("D", ComUtil.VK_D);
		keys.put("E", ComUtil.VK_E);
		keys.put("F", ComUtil.VK_F);
		keys.put("G", ComUtil.VK_G);
		keys.put("H", ComUtil.VK_H);
		keys.put("I", ComUtil.VK_I);
		keys.put("J", ComUtil.VK_J);
		keys.put("K", ComUtil.VK_K);
		keys.put("L", ComUtil.VK_L);
		keys.put("M", ComUtil.VK_M);
		keys.put("N", ComUtil.VK_N);
		keys.put("O", ComUtil.VK_O);
		keys.put("P", ComUtil.VK_P);
		keys.put("Q", ComUtil.VK_Q);
		keys.put("R", ComUtil.VK_R);
		keys.put("S", ComUtil.VK_S);
		keys.put("T", ComUtil.VK_T);
		keys.put("U", ComUtil.VK_U);
		keys.put("V", ComUtil.VK_V);
		keys.put("W", ComUtil.VK_W);
		keys.put("X", ComUtil.VK_X);
		keys.put("Y", ComUtil.VK_Y);
		keys.put("Z", ComUtil.VK_Z);
		
		keys.put("1", ComUtil.VK_1);
		keys.put("2", ComUtil.VK_2);
		keys.put("3", ComUtil.VK_3);
		keys.put("4", ComUtil.VK_4);
		keys.put("5", ComUtil.VK_5);
		keys.put("6", ComUtil.VK_6);
		keys.put("7", ComUtil.VK_7);
		keys.put("8", ComUtil.VK_8);
		keys.put("9", ComUtil.VK_9);
		keys.put("0", ComUtil.VK_0);
		
		keys.put("UP", ComUtil.VK_UP);
		keys.put("DOWN", ComUtil.VK_DOWN);
		keys.put("LEFT", ComUtil.VK_LEFT);
		keys.put("RIGHT", ComUtil.VK_RIGHT);
		
		keys.put("↑", ComUtil.VK_UP);
		keys.put("↓", ComUtil.VK_DOWN);
		keys.put("←", ComUtil.VK_LEFT);
		keys.put("→", ComUtil.VK_RIGHT);
		
		keys.put("SHIFT", ComUtil.VK_SHIFT);
		keys.put("F5", ComUtil.VK_F5);
		keys.put("ESC", ComUtil.VK_ESCAPE);		
		
		keys.put("×", ComUtil.VK_BACK_SPACE);	
		keys.put("RT", ComUtil.VK_ENTER);	
	}
	
	public static int getKey(String letter) {
		return (Integer)keys.get(letter);
	}
	
}
