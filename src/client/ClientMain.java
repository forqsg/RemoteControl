package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;



public class ClientMain extends JFrame implements ActionListener {

	private String ip;
	private int port;

	private JButton fileoperate, remotecontrol, remotecmd,remoteshutdown,exit, usermanage, reboot,taskmanage;// 

	public JTextArea info;// 信息显示

	private SendMsg sengmsg = null;
	private String username;
	public ClientMain() {

	}

	public ClientMain (String username, String ip, int port) {
		this.username = username;
		this.ip = ip;
		this.port = port;
		sengmsg = new SendMsg(ip, port, this);
//		this.iniSelectFile();
		info = new JTextArea("已连接远程服务端计算机，IP：" + this.ip + "  Port："+ this.port);
		info.setEditable(false);
		
		this.startFrame();
	}

	public void startFrame() {
		this.setTitle("远程控制" + "  用户："+ username +"  被控端ip：" + ip + "  端口："+ port);


		this.setBounds(400, 100, 530, 400);
		
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				
				sengmsg.sendControlboolean("ini");
				ClientMain.this.dispose();
				System.gc();
				System.exit(0);
				
			}

		});

		this.setLayout(new BorderLayout());
		
		this.remotecontrol = new JButton(new ImageIcon(ClientMain.class.getResource("/image/remoteControl.png")));
		remotecontrol.setToolTipText("远程监控");
		remotecontrol.setBounds(50,10, 64, 64);
		remotecontrol.setRequestFocusEnabled(false); // 设置不需要焦点
		remotecontrol.setBorderPainted(false);// 不绘制边框
		remotecontrol.addActionListener(this);
		this.add(remotecontrol);
		
		this.fileoperate = new JButton(new ImageIcon(ClientMain.class.getResource("/image/fileOperator.png")));
		fileoperate.setToolTipText("文件操作");
		fileoperate.setBounds(170, 10, 64, 64);
		fileoperate.setRequestFocusEnabled(false); // 设置不需要焦点
		fileoperate.setBorderPainted(false);// 不绘制边框
		fileoperate.addActionListener(this);
		this.add(fileoperate);		

		this.remotecmd = new JButton(new ImageIcon(ClientMain.class.getResource("/image/remoteCommand.png")));
		remotecmd.setToolTipText("CMD控制台");
		remotecmd.setBounds(290, 10, 64, 64);
		remotecmd.setRequestFocusEnabled(false); // 设置不需要焦点
		remotecmd.setBorderPainted(false);// 不绘制边框
		remotecmd.addActionListener(this);
		this.add(remotecmd);

		this.taskmanage = new JButton(new ImageIcon(ClientMain.class.getResource("/image/taskmanager.png")));
		taskmanage.setToolTipText("任务管理器");
		taskmanage.setBounds(410,10, 64, 64);
		taskmanage.setRequestFocusEnabled(false); // 设置不需要焦点
		taskmanage.setBorderPainted(false);// 不绘制边框
		taskmanage.addActionListener(this);
		this.add(taskmanage);

	
		
		this.remoteshutdown = new JButton(new ImageIcon(ClientMain.class.getResource("/image/shutdown.png")));
		remoteshutdown.setToolTipText("远程关机");
		remoteshutdown.setBounds(50, 110, 64, 64);
		remoteshutdown.setRequestFocusEnabled(false); // 设置不需要焦点
		remoteshutdown.setBorderPainted(false);// 不绘制边框
		remoteshutdown.addActionListener(this);
		this.add(remoteshutdown);

		this.reboot = new JButton(new ImageIcon(ClientMain.class.getResource("/image/remotereboot.png")));
		reboot.setToolTipText("执行远程重启");
		reboot.setBounds(170, 110, 64, 64);
		reboot.setRequestFocusEnabled(false); // 设置不需要焦点
		reboot.setBorderPainted(false);// 不绘制边框
		reboot.addActionListener(this);
		this.add(reboot);
		
		this.usermanage = new JButton(new ImageIcon(ClientMain.class.getResource("/image/user.png")));
		usermanage.setToolTipText("口令管理");
		usermanage.setBounds(290, 110, 64, 64);
		usermanage.setRequestFocusEnabled(false); // 设置不需要焦点
		usermanage.setBorderPainted(false);// 不绘制边框
		usermanage.addActionListener(this);
		this.add(usermanage);
	
		
		this.exit = new JButton(new ImageIcon(ClientMain.class.getResource("/image/out.png")));
		exit.setToolTipText("结束监控");
		exit.setBounds(410, 110, 64, 64);
		exit.setRequestFocusEnabled(false); // 设置不需要焦点
		exit.setBorderPainted(false);// 不绘制边框
		exit.addActionListener(this);
		this.add(exit);
		
		
		JLabel label = new JLabel("远程监控");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(50, 80, 64, 15);
		this.getContentPane().add(label);

		JLabel label_1 = new JLabel("文件操作");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(170, 80, 64, 15);
		this.getContentPane().add(label_1);

		JLabel label_6 = new JLabel("CMD控制台");
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(290, 80, 64, 15);
		this.getContentPane().add(label_6);

		JLabel label_5 = new JLabel("任务管理器");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_5.setBounds(410, 80, 64, 15);
		this.getContentPane().add(label_5);

		JLabel label_2 = new JLabel("远程关机");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_2.setBounds(50, 180, 64, 15);
		this.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("远程重启");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_3.setBounds(170, 180, 64, 15);
		this.getContentPane().add(label_3);

		JLabel label_4 = new JLabel("口令管理");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_4.setBounds(290, 180, 64, 15);
		this.getContentPane().add(label_4);
		
		JLabel label_7 = new JLabel("结束监控");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_7.setBounds(410, 180, 64, 15);
		this.getContentPane().add(label_7);
		
		
		JPanel pp = new JPanel();
		pp.setLayout(new BorderLayout());
		this.add(pp, BorderLayout.CENTER);


		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setPreferredSize(new Dimension(520, 400));
		p.setBorder(BorderFactory.createEtchedBorder());
		pp.add(p, BorderLayout.CENTER);

		// 信息列表p33
		JPanel pinfo = new JPanel();
		pinfo.setLayout(new BorderLayout());
		pinfo.setPreferredSize(new Dimension(520, 100));
		pinfo.setBorder(BorderFactory.createEtchedBorder());
		p.add(pinfo, BorderLayout.SOUTH);

		JScrollPane jsp2 = new JScrollPane(info);
		pinfo.add(jsp2, BorderLayout.CENTER);

		//getContentPane().add(toolBar, BorderLayout.NORTH);

		this.setResizable(false);
		this.setVisible(true);
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == remotecontrol) {// 单击远程连接事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r远程操作");
			new Thread(new ReceiveScreen(new ClientEvent(this.ip, this.port))).start();
			
		} 
		 else if (e.getSource() == fileoperate) {// 单击文件事件
				String temp = this.info.getText();
				this.info.setText(temp + "\n\r文件操作");
				
				new Thread(new FilesControl(this.ip, this.port, this)).start();
				
		}else if (e.getSource() == remotecmd) {// 单击CMD事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\rCMD控制");
			new ClientCMD(this.ip, this.port);
			
		}else if (e.getSource() == taskmanage) {// 单击任务管理器事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r任务管理器");
			new ClientTasklist(this.ip, this.port, this);
			
		}
		else if (e.getSource() == remoteshutdown) {// 单击关机事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r远程关机");
			if (JOptionPane.showConfirmDialog(null, "确定要关闭远程控制端计算机？", "提示",
					JOptionPane.YES_NO_OPTION) == 0) {
				sengmsg.sendControlboolean("cmd;Shutdown/s");
			}
			
		} 
		else if (e.getSource() == reboot) {// 单击关机事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r远程重启");
			if (JOptionPane.showConfirmDialog(null, "确定要重启远程控制端计算机？", "提示",
					JOptionPane.YES_NO_OPTION) == 0) {
				sengmsg.sendControlboolean("cmd;Shutdown/r");
			}
		}
		
		
		
		else if (e.getSource() == exit) {// 单击退出事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r退出登录");
			if (JOptionPane.showConfirmDialog(null, "重新连接", "提示",JOptionPane.YES_NO_OPTION) == 0) {
				sengmsg.sendControlboolean("ini");
				ClientMain.this.dispose();
				System.gc();
				new ClientConnect();
			} else {
				return;
				
				
			}
		} else if (e.getSource() == usermanage) {// 单击帮助事件
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r口令管理");
			new ChangePwd(this.username);

		}

	}


}
