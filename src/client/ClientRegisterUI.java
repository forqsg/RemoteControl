package client;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Message.Users;


public class ClientRegisterUI extends JFrame implements ActionListener {
	
	private String ip;
	private int port;
	
	private JButton bregister;
	private JButton reset;
	private JButton back;
	
	private String name = "输入用户名";
	private JTextField username;
	
	private JPasswordField newpwd;
	private JPasswordField newpwdconfirm;

	private String npwd;
	private String npwdcf;
	
	
	
	public ClientRegisterUI(String ip,int port) {
		this.ip = ip;
		this.port = port;
		
		this.startFrame();
	}

	private void startFrame() {
		
		this.setTitle("用户注册");
		this.setSize(350, 220);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width / 2 - 350 / 2, height / 2 - 220 / 2 - 60);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel labeluser = new JLabel("用户名：");
		labeluser.setHorizontalAlignment(SwingConstants.RIGHT);
		labeluser.setBounds(20, 38, 80, 15);
		this.getContentPane().add(labeluser);
		
		username = new JTextField();
		username.setText(this.name);
		username.setBounds(110, 35, 142, 21);
		this.getContentPane().add(username);
		username.setColumns(10);

		JLabel labelnewpwd = new JLabel("修改密码：");
		labelnewpwd.setHorizontalAlignment(SwingConstants.RIGHT);
		labelnewpwd.setBounds(20, 73, 80, 15);
		this.getContentPane().add(labelnewpwd);

		newpwd = new JPasswordField();
		newpwd.setEchoChar('*');
		newpwd.setColumns(10);
		newpwd.setBounds(110, 70, 142, 21);
		this.getContentPane().add(newpwd);

		
		JLabel labelnewpwdconfirm = new JLabel("修改密码：");		
		labelnewpwdconfirm.setHorizontalAlignment(SwingConstants.RIGHT);
		labelnewpwdconfirm.setBounds(20, 108, 80, 15);
		this.getContentPane().add(labelnewpwdconfirm);

		newpwdconfirm = new JPasswordField();
		newpwdconfirm.setEchoChar('*');
		newpwdconfirm.setColumns(10);
		newpwdconfirm.setBounds(110, 105, 142, 21);
		this.getContentPane().add(newpwdconfirm);
		
		
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				ClientRegisterUI.this.dispose();
				System.gc();
			}

		});


		back = new JButton("返回");
		back.setToolTipText("返回到主控");
		back.setBounds(60, 144, 60, 30);
		back.addActionListener(this);
		this.add(back);

		bregister = new JButton("注册");
		bregister.setToolTipText("注册用户");
		bregister.setBounds(145, 144, 60, 30);
		bregister.addActionListener(this);
		this.add(bregister);

		reset = new JButton("清除");
		reset.setToolTipText("清除文本");
		reset.setBounds(230, 144, 60, 30);
		reset.addActionListener(this);
		this.add(reset);
		
		
		
		
		
		
		this.setResizable(false);
		this.setVisible(true);
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		name  = username.getText();
		npwd = newpwd.getText();
		npwdcf = newpwdconfirm.getText();
		if (e.getSource() == bregister) {
			if (this.name.equals("")||this.npwd.equals("")||this.npwdcf.equals("")) {
				JOptionPane.showMessageDialog(null, "请把信息填完整", "提示", 1);
			} else {

				//启动线程进行用户注册
				
				
				
				
				
				
				
				
				JOptionPane.showMessageDialog(null, "用户注册成功!", "提示", 1);
			}
		}

		if (e.getSource() == reset) {
			this.username.setText("");
			this.newpwd.setText("");
			this.newpwdconfirm.setText("");
		}
		
		if (e.getSource() == back) {
			
			ClientRegisterUI.this.dispose();
			System.gc();
			new ClientLogin(this.ip,this.port);
			
		}
	}
	
	
	
	
	
	public boolean register() {
		/* 获取MAC地址 */
		String MAC = PCInfoUtils.getMAC();
		/* 获取操作系统 */
		String OSName = PCInfoUtils.getOSName();
		String info = name + "//" + MAC + "//" + OSName;
		Users register = new Users();

		return false;
	}


	
	public void SendMsg() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
