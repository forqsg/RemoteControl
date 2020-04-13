package client;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class Help extends JFrame {
	private String version = "V0.3";// 版本
	private int w;
	private int h;
	private String title;
	private String content;
	public JTextArea info;// 交互信息

	public Help(String title,String content) {
		this.w = 400;
		this.h = 500;
		this.title = title;
		this.content = content;
		this.startFrame();
	}

	public void startFrame() {
		this.setTitle(title+"帮助--" + version);
		int x = Toolkit.getDefaultToolkit().getScreenSize().width;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds((x - w) / 2, (y - h) / 2, w, h);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				Help.this.dispose();
				System.out.println("窗体关闭");
				System.gc();
			}

		});
		info = new JTextArea(content);
		info.setEditable(false);
		JScrollPane jsp2 = new JScrollPane(info);
		this.add(jsp2,BorderLayout.CENTER);
		
		this.setResizable(false);
		this.setVisible(true);
		this.validate();
	}
}
