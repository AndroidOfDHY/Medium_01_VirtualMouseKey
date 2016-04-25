import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Interface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 界面
	 */
	private final int X = 20;
	private final int Y = 30;
	public JScrollPane scrollPane1;
	private final int WIDTH = 300;
	private final int HEIGHT = 49;
	public final int WIDTH2 = 500;
	public final int HEIGHT2 = 400;
	private final int BUTTONWIDTH = 102;
	private final int BUTTONHEIGHT = 24;
	private JButton buttonStart = null,buttonStop=null;
	private JTable form;
	private JPanel southP;
	private MyServer myServer = null;
	private MyTextArea text = null;
	private Object name1[] = {"编号","网卡名称", "IP地址"};
	private Object[][] infm = null;
	Interface() {
		
		
		this.addWindowListener(new Colose());
		this.setLayout(new BorderLayout());
		buttonStart = new JButton("开始");
		buttonStop = new JButton("停止");
		southP = new JPanel();
		text = new MyTextArea();
		this.text.setLineWrap(true);
	    this.text.setEditable(false);
		text.setFont(new Font("宋体",20,20));
		text.setEditable(false);
		this.add(southP, BorderLayout.SOUTH);
		this.setResizable(false);
		southP.setLayout(new FlowLayout());
		southP.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
		southP.add(buttonStart);
		southP.add(buttonStop);
		buttonStart.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
		buttonStop.setPreferredSize(new java.awt.Dimension(BUTTONWIDTH, BUTTONHEIGHT));
		buttonStart.addActionListener(new ButtonStartActionListener());
		buttonStop.addActionListener(new ButtonStaopActionListener());
		this.scrollPane1 = new JScrollPane(this.text);
		this.scrollPane1.setHorizontalScrollBarPolicy(31);
		this.scrollPane1.setVerticalScrollBarPolicy(22);
		this.add(new JScrollPane(scrollPane1), BorderLayout.CENTER);
		infm = ShowIp();
		form = new JTable(infm, name1);
		form.setEnabled(false);
		JScrollPane jScrollPane = new JScrollPane(form);
		jScrollPane.setPreferredSize(new java.awt.Dimension(102,24+18*infm.length));
		this.add(jScrollPane, BorderLayout.NORTH);
		this.pack();
		setBounds(X, Y, WIDTH2, HEIGHT2);
		this.setVisible(true);
		this.setTitle("欢迎使用本软件");
		myServer =new MyServer(text);
	}
	class Colose extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}

	private Object[][] ShowIp() {

		SystemHelper systemHelper = new SystemHelper();
		@SuppressWarnings("static-access")
		List<String> res = systemHelper.getSystemLocalIp();
		Object[][] infm = new Object[res.size()/2][name1.length];
		if (res != null && res.size() != 0) {
			for (int i = 0,j=0; i < res.size(); i=i+2,j++) {
				infm[j][0]="第"+(j+1)+"个ip";
				infm[j][1]=res.get(i);
				infm[j][2]=res.get(i+1);
			}
		} else {
			infm = new Object[0][0];
		}
		return infm;
	}

	class ButtonStartActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myServer.StartSS();
		}

	}
	class ButtonStaopActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			myServer.closeSS();
		}
		
	}
}
