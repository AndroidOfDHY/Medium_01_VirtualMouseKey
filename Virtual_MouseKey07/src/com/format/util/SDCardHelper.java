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
 * ��װһЩSD���Ĳ���,������������м��̵�λ��
 * @author Administrator
 *
 */
public class SDCardHelper {
	
	private String path ;
	//�洢��Ŀ¼��
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
	 * �ж�SD�Ƿ����
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
	 * �ж�dirĿ¼�Ƿ����
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
	 * �ж��ļ��Ƿ����
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
	 *  ������Ŀ¼
	 * @param dir
	 * @return
	 */
	public void creatNewDir(String dir) {
		String str = path + dir;
		File file = new File(str);
		file.mkdir();
	}
	
	
	/**
	 * �������ļ�
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
			throw new SDCardException("�����ļ�ʧ��!");
		}
		return file;
	}
	
	public File getDir(String dirName) {
		String url = path + dirName ;
		File file = new File(url);
		return file;
	}

	
	/**
	 * ����д���ļ�����
	 * @param points Ҫ����ĵ��λ��
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
			throw new SDCardException("�ļ�������!");
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(fos);
			for(int i = 0 ;i < points.size(); i ++) {
				ButtonPoint point = points.get(i);
				oos.writeObject(point);
			}
		} catch (IOException e) {
			throw new SDCardException("�ļ�д��ʧ��!");
		} finally {
			try {
				oos.flush();
				oos.close();
				fos.close();
			} catch (IOException e) {
				throw new SDCardException("�ļ�д��ʧ��!");
			}
		}
	}
	
	
	/**
	 * ����ť��Ϣ���浽�ļ���, ���������¼�ֵ��˳�򱣴�
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
			throw new SDCardException("�ļ�������!");
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
			throw new SDCardException("�ļ�д��ʧ��!");
		} finally {
			try {
				dos.flush();
				dos.close();
			} catch (IOException e) {
				throw new SDCardException("�ļ�д��ʧ��!");
			}
		}
	}*/
	
	public void readInfoFromFile(String fileName, KeyBoardView view) throws SDCardException {
		String url = path + dirName + "/" + fileName + ".bak";
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File(url)));
		} catch (StreamCorruptedException e1) {
			throw new SDCardException("����ʧ��!");
		} catch (FileNotFoundException e1) {
			throw new SDCardException("�ļ�������!");
		} catch (IOException e1) {
			throw new SDCardException("����ʧ��!");
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
				throw new SDCardException("�����ļ�ʧ��!");
			} catch (ClassNotFoundException e) {
				throw new SDCardException("�����ļ�ʧ��!");
			} catch (IOException e) {
				view.postInvalidate();
				throw new SDCardException("�����ļ��ɹ�!");
			} finally {
				
			}
			System.out.println(2);
			
	}
	
	
}
