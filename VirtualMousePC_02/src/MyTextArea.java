import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

class MyTextArea extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void setTextArea(String s) {
		if(!s.equalsIgnoreCase("服务器已经开启")&&!s.equalsIgnoreCase("服务器还没开启"))
		{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
			String info = s + "\t" + sdf.format(date) + "\n";
			this.append(info);
			this.setCaretPosition(this.getText().length());
		}else
		{
			JOptionPane.showMessageDialog(this, s, "警告 ",JOptionPane.WARNING_MESSAGE);
		}
		
	}
}
