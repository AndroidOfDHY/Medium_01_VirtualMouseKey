package com.format.util;

import google.project.exception.SDCardException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.os.Environment;

import com.format.view.ButtonPoint;
import com.format.view.KeyBoardView;

/**
 * 封装一些SD卡的操作,保存虚拟键盘中键盘的位置
 * @author Administrator
 *
 */
public class SDCardHelper {
	
	private String path ;
	//存储的目录名
	private String dirName ;
	
	private static SDCardHelper instance = null;
	
	private SDCardHelper() {
		path = Environment.getExternalStorageDirectory() + "/";
		dirName = "VirtualDevice";
	}
	
	public static SDCardHelper getInstance() {
		if(instance == null) {
			instance = new SDCardHelper();
		}
		return instance;
	}
	
	
	
	/**
	 * 判断SD是否存在
	 * @return
	 */
	public boolean isSDCardExist() {
		try {
			return Environment.getExternalStorageState().
					equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断dir目录是否存在
	 * @param dir
	 * @return
	 */
	public boolean isDirExist(String dir) {
		String str = path + dir;
		File file = new File(str);
		if(file.isDirectory()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		String str = path + dirName + "/" + fileName;
		File file = new File(str);
		if(file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 *  创建新目录
	 * @param dir
	 * @return
	 */
	public void creatNewDir(String dir) {
		String str = path + dir;
		File file = new File(str);
		file.mkdir();
	}
	
	
	/**
	 * 创建新文件
	 * @param fileName
	 */
	public File creatNewFile(String fileName) throws SDCardException{
		if(!this.isDirExist(dirName)) {
			this.creatNewDir(dirName);
		}
		String url = path + dirName + "/" + fileName;
		File file = new File(url + ".bak");
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			throw new SDCardException("创建文件失败!");
		}
		return file;
	}
	
	public File getDir(String dirName) {
		String url = path + dirName ;
		File file = new File(url);
		return file;
	}

	
	/**
	 * 布局写入文件操作
	 * @param points 要保存的点的位置
	 * @param file
	 */
	public void writeToFile(ArrayList<ButtonPoint> points, File file) throws SDCardException {
		if(!this.isDirExist(dirName)) {
			this.creatNewDir(dirName);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new SDCardException("文件不存在!");
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(fos);
			for(int i = 0 ;i < points.size(); i ++) {
				ButtonPoint point = points.get(i);
				oos.writeObject(point);
			}
		} catch (IOException e) {
			throw new SDCardException("文件写入失败!");
		} finally {
			try {
				oos.flush();
				oos.close();
				fos.close();
			} catch (IOException e) {
				throw new SDCardException("文件写入失败!");
			}
		}
	}
	
	
	/**
	 * 将按钮信息保存到文件中, 以左右上下键值的顺序保存
	 * @param btns
	 * @param file
	 */
	/*public void writeToFile(List<MyButton> btns, File file) throws SDCardException{
		if(!this.isDirExist(dirName)) {
			this.creatNewDir(dirName);
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new SDCardException("文件不存在!");
		}
		DataOutputStream dos = new DataOutputStream(fos);
		System.out.println(btns.size());
		try {
			for(int i = 0; i < btns.size(); i ++) {
				MyButton btn = btns.get(i);
				int left = btn.getLeft();
				int right = btn.getRight();
				int top = btn.getTop();
				int bottom = btn.getBottom();
				int key = btn.getKey();
				dos.writeInt(left);
				dos.writeInt(right);
				dos.writeInt(top);
				dos.writeInt(bottom);
				dos.writeInt(key);
				System.out.println(left + "," + right + "," + top + "," + bottom + "," + key);
			}
		} catch (IOException e) {
			throw new SDCardException("文件写入失败!");
		} finally {
			try {
				dos.flush();
				dos.close();
			} catch (IOException e) {
				throw new SDCardException("文件写入失败!");
			}
		}
	}*/
	
	public void readInfoFromFile(String fileName, KeyBoardView view) throws SDCardException {
		String url = path + dirName + "/" + fileName + ".bak";
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(url)));
		} catch (StreamCorruptedException e1) {
			throw new SDCardException("载入失败!");
		} catch (FileNotFoundException e1) {
			throw new SDCardException("文件不存在!");
		} catch (IOException e1) {
			throw new SDCardException("载入失败!");
		}
		Object obj = null;
			try {
				while( (obj = ois.readObject()) != null ) {
					ButtonPoint btn = (ButtonPoint)obj;
					System.out.println(btn);
					view.getPoints().add(btn);
				}
				view.postInvalidate();
			} catch (OptionalDataException e) {
				throw new SDCardException("载入文件失败!");
			} catch (ClassNotFoundException e) {
				throw new SDCardException("载入文件失败!");
			} catch (IOException e) {
				view.postInvalidate();
				throw new SDCardException("载入文件成功!");
			} finally {
				
			}
			System.out.println(2);
			
	}
	
	
}
