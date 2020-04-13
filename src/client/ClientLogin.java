package client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



/**
* @author Administrator
*
*/
public class ClientLogin extends JFrame implements ActionListener {

	private JButton login;
	private JButton reset;
	private JButton register;
	private JButton back;
	//全局声明变量

	private JTextField textname;
	private JTextField textpassword;
	private String username = "请在此处输入用户名";
	private String userpasswrod;
	
	private String ip;
	private int port;
	private Socket socket;
	
	//private Socket socket = null;
	
	public ClientLogin(String ip, int port) {
		
		this.ip = ip;
		this.port = port;
	
		this.startFrame();
	}
	
	
	public void startFrame () {
		
		this.setTitle("登录远程控制系统");
		this.setSize(370, 220);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width / 2 - 350 / 2, height / 2 - 220 / 2 - 60);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);


		JLabel labelname = new JLabel("用户名:");
		labelname.setHorizontalAlignment(SwingConstants.RIGHT);
		labelname.setBounds(20, 13, 80, 15);
		this.getContentPane().add(labelname);

		textname = new JTextField();
		textname.setText(username);
		textname.setBounds(110, 10, 142, 21);
		this.getContentPane().add(textname);
		textname.setColumns(10);

		JLabel labelpassword = new JLabel("密    码:");
		labelpassword.setBounds(20, 48, 80, 15);
	
		labelpassword.setHorizontalAlignment(SwingConstants.RIGHT);	
		this.getContentPane().add(labelpassword);

		textpassword = new JTextField(15);
		textpassword.setColumns(10);
		textpassword.setBounds(110, 45, 142, 21);
		this.getContentPane().add(textpassword);
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				ClientLogin.this.dispose();
				System.gc();
			}

		});

		
		
		back = new JButton("返回");
		back.setBounds(25, 109, 60, 30);
		back.addActionListener(this);
		this.add(back);
		
		login = new JButton("登录");
		login.setBounds(110, 109, 60, 30);
		login.addActionListener(this);
		this.add(login);

		reset = new JButton("重置");
		reset.setBounds(195, 109, 60, 30);
		reset.addActionListener(this);
		this.add(reset);

		register = new JButton("注册");
		register.setBounds(280, 109, 60, 30);
		register.addActionListener(this);
		this.add(register);
		
		this.setResizable(false);
		this.setVisible(true);
		this.validate();
		
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		username = textname.getText();
		userpasswrod = textpassword.getText();

		if (e.getSource() == back) {
			
			ClientLogin.this.dispose();
			System.gc();
			new ClientConnect();
		}
		
		
		if (e.getSource() == login) {
			
			if (this.username.equals("")||this.userpasswrod.equals("")) {
				JOptionPane.showMessageDialog(null, "用户名或密码为空", "提示", 1);
			}
			
			else {//查询服务器的用户表是否有用户
			
					try {
						socket = new Socket(this.ip, 54321);
					} catch (IOException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
					
					if(this.login()) {					
						//进入新的窗口					
						JOptionPane.showMessageDialog(null, "用户验证通过！进入主控端", "提示", 1);				
						new ClientMain(this.username, this.ip, Integer.valueOf(port));
						ClientLogin.this.dispose();
						System.gc();
						
					}else {
						JOptionPane.showMessageDialog(null, "登陆失败", "提示", 2);
					}
				}

			
		}

		if (e.getSource() == reset) {
			this.textname.setText("");
			this.textpassword.setText("");
		}
		
		
		if (e.getSource() == register) {
			
			new ClientRegisterUI(this.ip,this.port);	
		}
		
	}


	

	public boolean login() {

		
		String usermsg = this.username + ";" + this.userpasswrod;
		SendMsg(usermsg);  //发送用户名和密码到服务器

		System.out.println("msg到这里了");
		
		InputStream gis;
		try {
			
			gis = socket.getInputStream();
			DataInputStream dis = new DataInputStream(gis);
			String info = "";
			info = dis.readUTF();   //接收服务器传来的信息
					
			if (info != null) {
				
				if (info.equals("ypwd")) {  
					JOptionPane.showMessageDialog(null, "登陆成功", "提示", 1);
					return true;
				}
				else if(info.equals("nuser")) {
					JOptionPane.showMessageDialog(null, "用户不存在", "提示", 1);
					return false;
				}
				else if(info.equals("npwd")) {
					JOptionPane.showMessageDialog(null, "密码错误", "提示", 1);
					return false;
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}	

     public void SendMsg(String usermsg) {
		
		try {
//			socket = new Socket(this.ip, 54321);
			OutputStream os;
			os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(usermsg);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("socket失败");
		}
	}
	
}