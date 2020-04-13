package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;


public class ClientEvent extends JFrame {
	JLabel jlabel;
	JScrollPane scroll;
	private String ip;
	private int port;
	private boolean flag = false;
	
	/*默认构造方法
	 * */
	public ClientEvent(){
		
	}
	
	/*
	 * 构造方法，参数为IP,端口号和主题
	 * */
	public ClientEvent(String IP, int port) {
		this.flag = true;
		this.ip = IP;
		this.port = port;
		this.setTitle("IP:" + ip+"  Port:"+port );
		this.jlabel = new JLabel();
		this.scroll = new JScrollPane();
		this.scroll.add(this.jlabel);
		
		scroll.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				int x = (int) e.getX() + (int) ClientEvent.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ClientEvent.this.scroll.getVerticalScrollBar().getValue();
				String operateStr ="mousePressed,"+ x + "," + y + "," + e.getModifiers();
				
				SendControl sender=new SendControl(ClientEvent.this.ip, (operateStr));
				new Thread(sender).start();
			}

			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				int x = (int) e.getX() + (int) ClientEvent.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ClientEvent.this.scroll.getVerticalScrollBar().getValue();
				String operateStr ="mouseReleased,"+ x + "," + y + "," + e.getModifiers();
				
				SendControl sender=new SendControl(ClientEvent.this.ip, (operateStr));
				new Thread(sender).start();
			}

		});
		
		
		scroll.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {//拖拽
				super.mouseDragged(e);
				int x = (int) e.getX() + (int) ClientEvent.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ClientEvent.this.scroll.getVerticalScrollBar().getValue();
				String operateStr ="mouseDragged,"+ x + "," + y + "," + e.getModifiers();//返回此事件的修饰符掩码
				
				SendControl sender=new SendControl(ClientEvent.this.ip, operateStr);
				new Thread(sender).start();
			}
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				int x = (int) e.getX() + (int) ClientEvent.this.scroll.getHorizontalScrollBar().getValue();
				int y = (int) e.getY() + (int) ClientEvent.this.scroll.getVerticalScrollBar().getValue();
				String operateStr ="mouseMoved,"+ x + "," + y;
				
				SendControl sender=new SendControl(ClientEvent.this.ip, (operateStr));
				new Thread(sender).start();
			}
		});
		
		
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				String operateStr ="keyPress,"+ e.getKeyCode();
				
				SendControl sender=new SendControl(ClientEvent.this.ip, (operateStr));
				new Thread(sender).start();
			}
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				String operateStr ="keyReleas,"+ e.getKeyCode();
				
				SendControl sender=new SendControl(ClientEvent.this.ip, (operateStr));
				new Thread(sender).start();
			}

		});
		
		this.add(scroll);
		this.setAlwaysOnTop(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(100, 75, (int) screenSize.getWidth() - 200, (int) screenSize.getHeight() - 150);
		// this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// 关闭窗体不做任何事
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				flag = false;
				ClientEvent.this.dispose();
				System.out.println("窗体关闭");
				System.gc();
			}
		});
		this.setVisible(true);
		this.validate();

	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
