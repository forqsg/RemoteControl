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
	//ȫ����������

	private JTextField textname;
	private JTextField textpassword;
	private String username = "���ڴ˴������û���";
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
		
		this.setTitle("��¼Զ�̿���ϵͳ");
		this.setSize(370, 220);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width / 2 - 350 / 2, height / 2 - 220 / 2 - 60);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);


		JLabel labelname = new JLabel("�û���:");
		labelname.setHorizontalAlignment(SwingConstants.RIGHT);
		labelname.setBounds(20, 13, 80, 15);
		this.getContentPane().add(labelname);

		textname = new JTextField();
		textname.setText(username);
		textname.setBounds(110, 10, 142, 21);
		this.getContentPane().add(textname);
		textname.setColumns(10);

		JLabel labelpassword = new JLabel("��    ��:");
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

		
		
		back = new JButton("����");
		back.setBounds(25, 109, 60, 30);
		back.addActionListener(this);
		this.add(back);
		
		login = new JButton("��¼");
		login.setBounds(110, 109, 60, 30);
		login.addActionListener(this);
		this.add(login);

		reset = new JButton("����");
		reset.setBounds(195, 109, 60, 30);
		reset.addActionListener(this);
		this.add(reset);

		register = new JButton("ע��");
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
				JOptionPane.showMessageDialog(null, "�û���������Ϊ��", "��ʾ", 1);
			}
			
			else {//��ѯ���������û����Ƿ����û�
			
					try {
						socket = new Socket(this.ip, 54321);
					} catch (IOException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
					
					if(this.login()) {					
						//�����µĴ���					
						JOptionPane.showMessageDialog(null, "�û���֤ͨ�����������ض�", "��ʾ", 1);				
						new ClientMain(this.username, this.ip, Integer.valueOf(port));
						ClientLogin.this.dispose();
						System.gc();
						
					}else {
						JOptionPane.showMessageDialog(null, "��½ʧ��", "��ʾ", 2);
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
		SendMsg(usermsg);  //�����û��������뵽������

		System.out.println("msg��������");
		
		InputStream gis;
		try {
			
			gis = socket.getInputStream();
			DataInputStream dis = new DataInputStream(gis);
			String info = "";
			info = dis.readUTF();   //���շ�������������Ϣ
					
			if (info != null) {
				
				if (info.equals("ypwd")) {  
					JOptionPane.showMessageDialog(null, "��½�ɹ�", "��ʾ", 1);
					return true;
				}
				else if(info.equals("nuser")) {
					JOptionPane.showMessageDialog(null, "�û�������", "��ʾ", 1);
					return false;
				}
				else if(info.equals("npwd")) {
					JOptionPane.showMessageDialog(null, "�������", "��ʾ", 1);
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
			System.out.println("socketʧ��");
		}
	}
	
}