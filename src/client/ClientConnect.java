package client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ClientConnect extends JFrame implements ActionListener {


	private JButton chick;
	private JButton reset;
	//全局声明变量

	private JTextField serverIP;
	private JTextField serverPort;
	
	private String ip = "127.0.0.1";
	private String port = "55555";
	
	
	public ClientConnect() {
		this.startFrame();
	}

	public void startFrame() {
	
		this.setTitle("连接到服务器");
		this.setSize(350, 220);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width / 2 - 350 / 2, height / 2 - 220 / 2 - 60);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		
		JLabel label_1 = new JLabel("服务器地址：");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(20, 38, 80, 15);
		this.getContentPane().add(label_1);

		serverIP = new JTextField();
		serverIP.setText(ip);
		serverIP.setBounds(110, 35, 142, 21);
		this.getContentPane().add(serverIP);
		serverIP.setColumns(10);

		JLabel label_3 = new JLabel("服务器端口：");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(20, 73, 80, 15);
		this.getContentPane().add(label_3);

		serverPort = new JTextField();
		serverPort.setText(port);
		serverPort.setColumns(10);
		serverPort.setBounds(110, 70, 142, 21);
		this.getContentPane().add(serverPort);

		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				ClientConnect.this.dispose();
				System.gc();
				System.exit(0);
				
			}

		});


		chick = new JButton("连接");
		chick.setToolTipText("进入控制端");
		chick.setBounds(100, 109, 60, 30);
		chick.addActionListener(this);
		this.add(chick);

		reset = new JButton("清除");
		reset.setToolTipText("清除文本");
		reset.setBounds(200, 109, 60, 30);
		reset.addActionListener(this);
		this.add(reset);
		
		
		
		this.setResizable(false);
		this.setVisible(true);
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ip = serverIP.getText();
		port = serverPort.getText();
		if (e.getSource() == chick) {
			if (this.ip.equals("")||this.port.equals("")) {
				JOptionPane.showMessageDialog(null, "IP或端口为空", "提示", 1);
			} else {
				new ClientLogin(this.ip,Integer.valueOf(this.port));
				ClientConnect.this.dispose();
				System.gc();
			}
		}

		if (e.getSource() == reset) {
			this.serverIP.setText("");
			this.serverPort.setText("");
		}
		
	}

	public static void main(String[] args) {
		new ClientConnect();
	}

}
