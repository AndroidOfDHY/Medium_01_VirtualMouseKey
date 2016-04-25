package com.format.view;

import java.io.Serializable;


/**
 * 在自己定义的View上面要画的按钮
 * @author Administrator
 *
 */
public class ButtonPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1869444568989602914L;

	private int X;
	
	private int Y;
	
	private int fileName;
	
	private String letter;
	
	private int radius;
	
	private boolean isClicked;
	
	public ButtonPoint(int x, int y, int radius) {
		super();
		X = x;
		Y = y;
		this.radius = radius;
		this.isClicked = false;
	}
	
	public ButtonPoint(int x, int y, int radius,String letter) {
		super();
		X = x;
		Y = y;
		this.radius = radius;
		this.letter = letter;
		this.isClicked = false;
	}
	
	public ButtonPoint(int x, int y, int radius,String letter,int fileName) {
		super();
		X = x;
		Y = y;
		this.radius = radius;
		this.letter = letter;
		this.isClicked = false;
		this.fileName = fileName;
	}
	
	public int getFileName() {
		return fileName;
	}

	public void setFileName(int fileName) {
		this.fileName = fileName;
	}


	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	
	public int getX() {
		return X;
	}

	public void setX(int x,int width) {
		if(x < 0) {
			X = 0 ;
		} else if(x + radius >= width) {
			X = width - radius;
		} else {
			X = x;
		}
	}

	public int getY() {
		return Y;
	}

	public void setY(int y,int height) {
		if(y < 0) {
			Y = 0 ;
		} else if(y + radius >= height) {
			Y = height - radius;
		} else {
			Y = y;
		}
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
	@Override
	public String toString() {
		return "ButtonPoint [X=" + X + ", Y=" + Y + ", letter=" + letter
				+ ", radius=" + radius + ", isClicked=" + isClicked + "]";
	}
	
}
