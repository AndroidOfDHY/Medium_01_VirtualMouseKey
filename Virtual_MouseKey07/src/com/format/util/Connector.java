package com.format.util;

import google.project.exception.NetWorkException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {
	static Socket socket;
	public static DataInputStream dis;
	public static DataOutputStream dos;
	static boolean connected = false;
	static int port=12345;
	public static void connect(String ip) throws NetWorkException{
		try {
			System.out.println("berfore..."+ip+":"+port);
			socket = new Socket(ip, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			setConnected(true);
			System.out.println("after");
		} catch (UnknownHostException e) {
			throw new NetWorkException("未知的ip地址!");
		} catch (IOException e) {
			throw new NetWorkException("连接错误!");
		}

	}

	public static boolean isConnected() {
		return connected;
	}

	public static void setConnected(boolean connected) {
		Connector.connected = connected;
	}

	public static void writeInt(int n) throws IOException {
		dos.writeInt(n);
	}

	public static void writeFloat(float n) throws IOException {
		dos.writeFloat(n);
	}
}
