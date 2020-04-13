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

	public JTextArea info;// ��Ϣ��ʾ

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
		info = new JTextArea("������Զ�̷���˼������IP��" + this.ip + "  Port��"+ this.port);
		info.setEditable(false);
		
		this.startFrame();
	}

	public void startFrame() {
		this.setTitle("Զ�̿���" + "  �û���"+ username +"  ���ض�ip��" + ip + "  �˿ڣ�"+ port);


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
		remotecontrol.setToolTipText("Զ�̼��");
		remotecontrol.setBounds(50,10, 64, 64);
		remotecontrol.setRequestFocusEnabled(false); // ���ò���Ҫ����
		remotecontrol.setBorderPainted(false);// �����Ʊ߿�
		remotecontrol.addActionListener(this);
		this.add(remotecontrol);
		
		this.fileoperate = new JButton(new ImageIcon(ClientMain.class.getResource("/image/fileOperator.png")));
		fileoperate.setToolTipText("�ļ�����");
		fileoperate.setBounds(170, 10, 64, 64);
		fileoperate.setRequestFocusEnabled(false); // ���ò���Ҫ����
		fileoperate.setBorderPainted(false);// �����Ʊ߿�
		fileoperate.addActionListener(this);
		this.add(fileoperate);		

		this.remotecmd = new JButton(new ImageIcon(ClientMain.class.getResource("/image/remoteCommand.png")));
		remotecmd.setToolTipText("CMD����̨");
		remotecmd.setBounds(290, 10, 64, 64);
		remotecmd.setRequestFocusEnabled(false); // ���ò���Ҫ����
		remotecmd.setBorderPainted(false);// �����Ʊ߿�
		remotecmd.addActionListener(this);
		this.add(remotecmd);

		this.taskmanage = new JButton(new ImageIcon(ClientMain.class.getResource("/image/taskmanager.png")));
		taskmanage.setToolTipText("���������");
		taskmanage.setBounds(410,10, 64, 64);
		taskmanage.setRequestFocusEnabled(false); // ���ò���Ҫ����
		taskmanage.setBorderPainted(false);// �����Ʊ߿�
		taskmanage.addActionListener(this);
		this.add(taskmanage);

	
		
		this.remoteshutdown = new JButton(new ImageIcon(ClientMain.class.getResource("/image/shutdown.png")));
		remoteshutdown.setToolTipText("Զ�̹ػ�");
		remoteshutdown.setBounds(50, 110, 64, 64);
		remoteshutdown.setRequestFocusEnabled(false); // ���ò���Ҫ����
		remoteshutdown.setBorderPainted(false);// �����Ʊ߿�
		remoteshutdown.addActionListener(this);
		this.add(remoteshutdown);

		this.reboot = new JButton(new ImageIcon(ClientMain.class.getResource("/image/remotereboot.png")));
		reboot.setToolTipText("ִ��Զ������");
		reboot.setBounds(170, 110, 64, 64);
		reboot.setRequestFocusEnabled(false); // ���ò���Ҫ����
		reboot.setBorderPainted(false);// �����Ʊ߿�
		reboot.addActionListener(this);
		this.add(reboot);
		
		this.usermanage = new JButton(new ImageIcon(ClientMain.class.getResource("/image/user.png")));
		usermanage.setToolTipText("�������");
		usermanage.setBounds(290, 110, 64, 64);
		usermanage.setRequestFocusEnabled(false); // ���ò���Ҫ����
		usermanage.setBorderPainted(false);// �����Ʊ߿�
		usermanage.addActionListener(this);
		this.add(usermanage);
	
		
		this.exit = new JButton(new ImageIcon(ClientMain.class.getResource("/image/out.png")));
		exit.setToolTipText("�������");
		exit.setBounds(410, 110, 64, 64);
		exit.setRequestFocusEnabled(false); // ���ò���Ҫ����
		exit.setBorderPainted(false);// �����Ʊ߿�
		exit.addActionListener(this);
		this.add(exit);
		
		
		JLabel label = new JLabel("Զ�̼��");
		label.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(50, 80, 64, 15);
		this.getContentPane().add(label);

		JLabel label_1 = new JLabel("�ļ�����");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_1.setBounds(170, 80, 64, 15);
		this.getContentPane().add(label_1);

		JLabel label_6 = new JLabel("CMD����̨");
		label_6.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(290, 80, 64, 15);
		this.getContentPane().add(label_6);

		JLabel label_5 = new JLabel("���������");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_5.setBounds(410, 80, 64, 15);
		this.getContentPane().add(label_5);

		JLabel label_2 = new JLabel("Զ�̹ػ�");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_2.setBounds(50, 180, 64, 15);
		this.getContentPane().add(label_2);

		JLabel label_3 = new JLabel("Զ������");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_3.setBounds(170, 180, 64, 15);
		this.getContentPane().add(label_3);

		JLabel label_4 = new JLabel("�������");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		label_4.setBounds(290, 180, 64, 15);
		this.getContentPane().add(label_4);
		
		JLabel label_7 = new JLabel("�������");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("΢���ź�", Font.PLAIN, 12));
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

		// ��Ϣ�б�p33
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
		if (e.getSource() == remotecontrol) {// ����Զ�������¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\rԶ�̲���");
			new Thread(new ReceiveScreen(new ClientEvent(this.ip, this.port))).start();
			
		} 
		 else if (e.getSource() == fileoperate) {// �����ļ��¼�
				String temp = this.info.getText();
				this.info.setText(temp + "\n\r�ļ�����");
				
				new Thread(new FilesControl(this.ip, this.port, this)).start();
				
		}else if (e.getSource() == remotecmd) {// ����CMD�¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\rCMD����");
			new ClientCMD(this.ip, this.port);
			
		}else if (e.getSource() == taskmanage) {// ��������������¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r���������");
			new ClientTasklist(this.ip, this.port, this);
			
		}
		else if (e.getSource() == remoteshutdown) {// �����ػ��¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\rԶ�̹ػ�");
			if (JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�ر�Զ�̿��ƶ˼������", "��ʾ",
					JOptionPane.YES_NO_OPTION) == 0) {
				sengmsg.sendControlboolean("cmd;Shutdown/s");
			}
			
		} 
		else if (e.getSource() == reboot) {// �����ػ��¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\rԶ������");
			if (JOptionPane.showConfirmDialog(null, "ȷ��Ҫ����Զ�̿��ƶ˼������", "��ʾ",
					JOptionPane.YES_NO_OPTION) == 0) {
				sengmsg.sendControlboolean("cmd;Shutdown/r");
			}
		}
		
		
		
		else if (e.getSource() == exit) {// �����˳��¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r�˳���¼");
			if (JOptionPane.showConfirmDialog(null, "��������", "��ʾ",JOptionPane.YES_NO_OPTION) == 0) {
				sengmsg.sendControlboolean("ini");
				ClientMain.this.dispose();
				System.gc();
				new ClientConnect();
			} else {
				return;
				
				
			}
		} else if (e.getSource() == usermanage) {// ���������¼�
			String temp = this.info.getText();
			this.info.setText(temp +"\n\r�������");
			new ChangePwd(this.username);

		}

	}


}
