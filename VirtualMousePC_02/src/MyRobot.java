import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class MyRobot {
	Robot robot;
	MyTextArea myTextArea=null;
	public MyRobot(MyTextArea myTextArea) {
		this.myTextArea=myTextArea;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void mouseKeyUp(int key) {
		//System.out.println("mouseKeyUp" + key);
		//myTextArea.setTextArea("mouseKeyUp" + key);
		switch (key) {
		case ComUtil.MOUSELEFTKEY:
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		case ComUtil.MOUSERIGHTKEY:
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
			break;
		}
	}

	public void mouseKeyDown(int key) {
		//System.out.println("mouseKeyDown" + key);
	//	myTextArea.setTextArea("mouseKeyDown" + key);
		switch (key) {
		case ComUtil.MOUSELEFTKEY:
			robot.mousePress(InputEvent.BUTTON1_MASK);
			break;
		case ComUtil.MOUSERIGHTKEY:
			robot.mousePress(InputEvent.BUTTON3_MASK);
			break;
		}
	}
	
	private void Angle(float argX, float currentX, float argY,
			float currentY, int k) {
		double addX = argX/k;
		double addY = argY/k;
		for (int i = 0; i < k; i++) {
			robot.mouseMove((int) (currentX + addX * i ),
					(int) (currentY + addY * i ));
		}
		//
	}

	public void mouseMove(float argX, float argY) {
		// TODO Òå×óÐÖÐ´µÄ
		/*
		 * XÖáÒÆ¶¯¾àÀë argX YÖáÒÆ¶¯¾àÀë argY ÊµÏÖÊó±êÆ½»¬
		 */
		// int moveX = (int) (argX);
		// int moveY = (int) (argY);
		float currentX = (float) MouseInfo.getPointerInfo().getLocation()
				.getX();
		float currentY = (float) MouseInfo.getPointerInfo().getLocation()
				.getY();
		//System.out.println(argX+" "+argY+" "+currentX+" "+currentY+" ");
		Angle(argX, currentX, argY, currentY, 400);
		//robot.mouseMove((int) (argX + currentX), (int) (argY + currentY));
	}

	
	public void keyDown(int key){
		//System.out.println("Key Down " + key);
	//	myTextArea.setTextArea("Key Down " + key);
		robot.keyPress(key);
	}
	
	public void keyUp(int key){
	//	myTextArea.setTextArea("Key Up " + key);
		robot.keyRelease(key);
	}
}
