import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	ServerSocket ss;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	private ServerThread serverThread = null;
	private Thread t1 = null;
	MyRobot robot;
	MyTextArea myTextArea = null;
	public boolean Serverflag = false;
	boolean flag;

	public MyServer(MyTextArea myTextArea) {
		this.myTextArea = myTextArea;
		robot = new MyRobot(myTextArea);
		serverThread = new ServerThread();

	}

	class ServerThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (Serverflag) {
				try {
					socket = ss.accept();
					flag = true;
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					MyReadRunnable runnable = new MyReadRunnable();
					new Thread(runnable).start();
					myTextArea.setTextArea("�ֻ����ӽ�����");
				} catch (IOException e) {
					try {
						if (null != ss) {
							ss.close();
						}
						if (null != dis) {
							dis.close();
						}
						if (null != dos) {
							dos.close();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dos=null;
					dis=null;
					ss = null;
					myTextArea.setTextArea("�������ر���");
				}
			}

		}
	}

	void StartSS() {
		if (Serverflag == true) {
			myTextArea.setTextArea("�������Ѿ�����");
			return;
		}
		myTextArea.setTextArea("������������");
		Serverflag = true;
		try {
			ss = new ServerSocket(12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
		t1 = new Thread(serverThread);
		t1.start();
	}

	void closeSS() {
		if (Serverflag == false) {
			myTextArea.setTextArea("��������û����");
			return;
		}
		try {
			Serverflag = false;
			flag = false;
			if (null != ss) {
				ss.close();
			}
			if (null != dis) {
				dis.close();
			}
			if (null != dos) {
				dos.close();
			}
			dos=null;
			dis=null;
			ss = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("close server 1...");
			myTextArea.setTextArea("close server 1...");
		}
	}

	class MyReadRunnable implements Runnable {

		public void run() {
			while (flag) {
				int type = 0;
				int key;
				try {
					if(dis==null)
						continue;
					type = dis.readInt();
					switch (type) {
					case ComUtil.MOUSEMOVE:
						float moveX = dis.readFloat();
						float moveY = dis.readFloat();
						robot.mouseMove(moveX, moveY);
						break;
					case ComUtil.MOUSEKEYDOWN:
						key = dis.readInt();
						robot.mouseKeyDown(key);
						break;
					case ComUtil.MOUSEKEYUP:
						key = dis.readInt();
						robot.mouseKeyUp(key);
						break;
					case ComUtil.KEYDOWN:
						key = dis.readInt();
						robot.keyDown(key);
						break;
					case ComUtil.KEYUP:
						key = dis.readInt();
						robot.keyUp(key);
						break;
					// �����Ǽ��̲���
					case ComUtil.KEY_DOWN:
						key = dis.readInt();
						robot.keyDown(key);
						break;
					case ComUtil.KEY_UP:
						key = dis.readInt();
						robot.keyUp(key);
						break;
					default:
						myTextArea.setTextArea("����Ĳ���");
						// System.out.println("����ʧ��:" + type);
					}
				} catch (IOException e) {
					flag = false;
					myTextArea.setTextArea("�ֻ��뿪��");
					//e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new Interface();
		// new MyServer();

	}

}
