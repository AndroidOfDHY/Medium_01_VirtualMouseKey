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
		if(!s.equalsIgnoreCase("�������Ѿ�����")&&!s.equalsIgnoreCase("��������û����"))
		{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��HH��mm��ss��");
			String info = s + "\t" + sdf.format(date) + "\n";
			this.append(info);
			this.setCaretPosition(this.getText().length());
		}else
		{
			JOptionPane.showMessageDialog(this, s, "���� ",JOptionPane.WARNING_MESSAGE);
		}
		
	}
}
