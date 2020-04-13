package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ChangePwd extends JFrame implements ActionListener {

	
	private JButton bmodifier;
	private JButton reset;
	private JButton back;
	
	private String name;
	private JTextField username;
	
	private JPasswordField oldpwd;
	private JPasswordField newpwd;
	private JPasswordField newpwdconfirm;
	
	private String opwd;
	private String npwd;
	private String npwdcf;
	
	public JTextArea info;// ������Ϣ
	
	public ChangePwd(String name) {
		this.name = name;
		init();
	}
	
	
	public void init()
	{
		
		this.setTitle("�������");
		this.setSize(350, 220);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(width / 2 - 350 / 2, height / 2 - 220 / 2 - 60);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel labeluser = new JLabel("�û���");
		labeluser.setHorizontalAlignment(SwingConstants.RIGHT);
		labeluser.setBounds(50, 11, 80, 15);
		this.getContentPane().add(labeluser);
		
		username = new JTextField();
		username.setText(this.name);
		username.setEditable(false);
		username.setBounds(130, 10, 80, 20);
		this.getContentPane().add(username);
		username.setColumns(10);
		
		
		JLabel label_1 = new JLabel("ԭ���룺");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(20, 38, 80, 15);
		this.getContentPane().add(label_1);

		oldpwd = new JPasswordField();
		oldpwd.setEchoChar('*');
		oldpwd.setBounds(110, 35, 142, 21);
		this.getContentPane().add(oldpwd);
		oldpwd.setColumns(10);

		JLabel label_2 = new JLabel("�޸����룺");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(20, 73, 80, 15);
		this.getContentPane().add(label_2);

		newpwd = new JPasswordField();
		newpwd.setEchoChar('*');
		newpwd.setColumns(10);
		newpwd.setBounds(110, 70, 142, 21);
		this.getContentPane().add(newpwd);

		
		JLabel label_3 = new JLabel("�޸����룺");		
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(20, 108, 80, 15);
		this.getContentPane().add(label_3);

		newpwdconfirm = new JPasswordField();
		newpwdconfirm.setEchoChar('*');
		newpwdconfirm.setColumns(10);
		newpwdconfirm.setBounds(110, 105, 142, 21);
		this.getContentPane().add(newpwdconfirm);
		
		
		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				ChangePwd.this.dispose();
				System.gc();
			}

		});


		back = new JButton("����");
		back.setToolTipText("���ص�����");
		back.setBounds(60, 144, 60, 30);
		back.addActionListener(this);
		this.add(back);

		bmodifier = new JButton("�޸�");
		bmodifier.setToolTipText("�޸�����");
		bmodifier.setBounds(145, 144, 60, 30);
		bmodifier.addActionListener(this);
		this.add(bmodifier);

		reset = new JButton("���");
		reset.setToolTipText("����ı�");
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
		
		opwd = oldpwd.getText();
		npwd = newpwd.getText();
		npwdcf = newpwdconfirm.getText();
		if (e.getSource() == bmodifier) {
			if (this.opwd.equals("")||this.npwd.equals("")||this.npwdcf.equals("")) {
				JOptionPane.showMessageDialog(null, "����������", "��ʾ", 1);
			} else {

				
				JOptionPane.showMessageDialog(null, "�����޸ĳɹ�", "��ʾ", 1);
			}
		}

		if (e.getSource() == reset) {
			this.oldpwd.setText("");
			this.newpwd.setText("");
			this.newpwdconfirm.setText("");
		}
		
		if (e.getSource() == back) {
			
			ChangePwd.this.dispose();
			System.gc();
			new ClientMain();
			
		}
	}
	
//	
//	public static void main(String[] args) {
//		new Usermanage();
//	}
		
}
