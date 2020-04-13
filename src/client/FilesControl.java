package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;


public class FilesControl extends Thread implements ActionListener{
	private int port;
	private Socket socket;
	private String ip;
	private String[][] files;
	private ClientMain cm;

	
	/////////////////
	private JButton fh;// ������һ���˵�
	private JButton tj;// �ύ
	private JButton sc;// ɾ��
	private JButton up;// �ϴ�
	private JButton xz;// ����

	private JTextField path;// ·��
	private JTextArea list;// �ļ��б�
	public JTextArea info;// ������Ϣ

	private JPanel cc;// �����߿�
	private JButton[] cc1;
	private JLabel[] cc2;
	private int countc;
	private String[] drivers;
	private getIniDrivers rd;
	private String[][] file;// �ļ�����
	private String listStr;
	private JFileChooser fc = new JFileChooser();
	private String downloadPath = "";
	private String uploadPath = "";
	private String[] selectFile = new String[3];
	private String driver = "";


	
	
	public FilesControl(String ip, int port, ClientMain cm) {
		this.ip = ip;
		this.cm = cm;
		this.port = port;
				
		rd = new getIniDrivers(this.ip, this.port, this.cm);
		this.drivers = rd.getDrivers();
		if (drivers != null) {
			countc = drivers.length;
		} else {
			countc = 0;
		}
		
		
		
		this.startFrame();
	}

	public void startFrame() {
		
		JFrame jffile = new JFrame();
		jffile.setTitle("�ļ�������");
		jffile.setBounds(400, 200, 500, 400);
		
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		jffile.add(p1, BorderLayout.CENTER);

		// ��߲˵�Ŀ¼��
		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());
		p2.setPreferredSize(new Dimension(80, 400));
		p2.setBorder(BorderFactory.createEtchedBorder());
		p1.add(p2, BorderLayout.WEST);

		p2.add(new JLabel(new ImageIcon(FilesControl.class.getResource("/image/cidiannao.png")), JLabel.LEFT),
				BorderLayout.NORTH);

		JPanel p22 = new JPanel();
		p22.setLayout(new BorderLayout());
		p2.add(p22, BorderLayout.CENTER);

		cc = new JPanel();
		p22.add(cc, BorderLayout.NORTH);
		cc.setLayout(new GridLayout(countc, 1));
		cc1 = new JButton[countc];
		cc2 = new JLabel[countc];
		JPanel[] pci = new JPanel[countc];
		for (int i = 0; i < countc; i++) {
			pci[i] = new JPanel();
			cc.add(pci[i]);

			cc1[i] = new JButton(new ImageIcon(FilesControl.class.getResource("/image/cipan.png")));
			cc1[i].setPreferredSize(new Dimension(25, 28));
			cc1[i].setRequestFocusEnabled(false); // ���ò���Ҫ����
			cc1[i].setBorderPainted(false);// �����Ʊ߿�
			cc1[i].addActionListener(this);
			pci[i].add(cc1[i]);
			cc2[i] = new JLabel(drivers[i]);
			pci[i].add(cc2[i]);
		}

		// �ұ���ʾ�б�p3{p31,p32,p33}
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.setPreferredSize(new Dimension(520, 400));
		p3.setBorder(BorderFactory.createEtchedBorder());
		p1.add(p3, BorderLayout.CENTER);

		// ·��p31
		JPanel p31 = new JPanel();
		p31.setLayout(new BorderLayout());
		p31.setPreferredSize(new Dimension(520, 30));
		p31.setBorder(BorderFactory.createEtchedBorder());
		p3.add(p31, BorderLayout.NORTH);

		this.tj = new JButton(new ImageIcon(FilesControl.class.getResource("/image/openfile.png")));
		tj.addActionListener(this);
		tj.setPreferredSize(new Dimension(28, 28));
		tj.setRequestFocusEnabled(false); // ���ò���Ҫ����
		tj.setBorderPainted(false);// �����Ʊ߿�
		p31.add(tj, BorderLayout.WEST);

		path = new JTextField("");
		p31.add(path, BorderLayout.CENTER);

		JPanel p311 = new JPanel();
		p311.setLayout(new BorderLayout());
		p31.add(p311, BorderLayout.EAST);
		this.fh = new JButton(new ImageIcon(FilesControl.class.getResource("/image/fileback.png")));
		fh.addActionListener(this);
		fh.setPreferredSize(new Dimension(28, 28));
		fh.setRequestFocusEnabled(false); // ���ò���Ҫ����
		fh.setBorderPainted(false);// �����Ʊ߿�

		p311.add(fh, BorderLayout.WEST);

		JPanel p3111 = new JPanel();
		p3111.setLayout(new BorderLayout());
		p311.add(p3111, BorderLayout.CENTER);

		this.sc = new JButton(new ImageIcon(FilesControl.class.getResource("/image/fileclose.png")));
		sc.addActionListener(this);
		sc.setPreferredSize(new Dimension(28, 28));
		sc.setRequestFocusEnabled(false); // ���ò���Ҫ����
		sc.setBorderPainted(false);// �����Ʊ߿�
		p3111.add(sc, BorderLayout.WEST);

		this.up = new JButton(new ImageIcon(FilesControl.class.getResource("/image/upfile.png")));
		up.addActionListener(this);
		up.setPreferredSize(new Dimension(28, 28));
		up.setRequestFocusEnabled(false); // ���ò���Ҫ����
		up.setBorderPainted(false);// �����Ʊ߿�
		p3111.add(up, BorderLayout.CENTER);

		this.xz = new JButton(new ImageIcon(FilesControl.class.getResource("/image/downfile.png")));
		xz.addActionListener(this);
		xz.setPreferredSize(new Dimension(28, 28));
		xz.setRequestFocusEnabled(false); // ���ò���Ҫ����
		xz.setBorderPainted(false);// �����Ʊ߿�
		p311.add(xz, BorderLayout.EAST);

		// ��ʾ�б�p32
		JPanel p32 = new JPanel();
		p32.setLayout(new BorderLayout());
		p32.setPreferredSize(new Dimension(520, 400));
		p32.setBorder(BorderFactory.createEtchedBorder());
		p3.add(p32, BorderLayout.CENTER);
		list = new JTextArea("");
		list.setEditable(false);
		JScrollPane jsp = new JScrollPane(list);
		/**
		 * 
		 * **/
		list.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				try {
					int count = list.getLineOfOffset(list.getCaretPosition());// ����к�
					if (file != null) {
						if (count < file.length) {
							path.setText("");
							selectFile = file[count];
							path.setText(file[count][2]);
						}
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}

		});

		p32.add(jsp, BorderLayout.CENTER);

		// ��Ϣ�б�p33
		JPanel p33 = new JPanel();
		p33.setLayout(new BorderLayout());
		p33.setPreferredSize(new Dimension(520, 80));
		p33.setBorder(BorderFactory.createEtchedBorder());
		p3.add(p33, BorderLayout.SOUTH);

		JScrollPane jsp2 = new JScrollPane(info);
		p33.add(jsp2, BorderLayout.CENTER);

		jffile.setResizable(false);
		jffile.setVisible(true);
		jffile.validate();
		
	}
	

	
	public String[][] getFiles(String operamsg) {
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp + "\n���ӷ������˿ڣ�" + port + "ʧ�� ");
			return null;
		}

		try {
			if (operamsg == null || operamsg == "") {
				socket.close();
				return null;
			}
			OutputStream os;
			os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(operamsg);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String info = "";
			info = dis.readUTF();
			is.close();
			socket.close();
			if (info != null && info != "") {
				String s[] = info.trim().split(";");

				if (s[0].equals("tj")) {
					files = new String[s.length - 1][3];
					for (int i = 0; i < s.length - 1; i++) {
						String[] temp = s[i + 1].trim().split(",");
					
						if (temp.length >= 3) {
							files[i][0] = temp[0];
							files[i][1] = temp[1];
							files[i][2] = temp[2];
						} else {
							String t = cm.info.getText();
							cm.info.setText(t + "\n�ļ���ȡʧ��");
						}
					}
					return files;
				}
			}
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp + "\n�ļ���ȡʧ��!");
		}
		return null;

	}

	public boolean deleteFile(String opera) {
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp + "\n�ļ���Ϣ��port��" + port + "������ʧ�� ");
			return false;
		}

		try {
			if (opera == null || opera == "") {
				socket.close();
				return false;
			}
			OutputStream os;
			os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(opera);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String info = "";
			info = dis.readUTF();
			is.close();
			socket.close();
			if (info != null && info != "") {
				String s[] = info.split(";");
				if (s[0].equals("sc")) {
					s[1] = s[1].replace("[scWhiteSpase]", "  ");
					String[] temps = s[1].split(",");
					StringBuffer str = new StringBuffer();
					for (int i = 0; i < temps.length; i++) {
						str.append(temps[i] + "\n");
					}
					String temp = cm.info.getText();
					cm.info.setText(temp + "\n�ļ�ɾ����"
							+ str.toString());
					return true;
				}

			}
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp + "\n�ļ�ɾ��ʧ��!");
		}
		return false;
	}

	public boolean upFile(String opera, String upload) {
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp + "\n���ӷ������˿ڣ�" + port + "ʧ�� ");
			return false;
		}

		try {
			if (opera == null || opera == "") {
				socket.close();
				return false;
			}
			OutputStream os;
			os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(opera);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		new SendFile(socket, upload, this.cm).start();

		return false;
	}

	public boolean downFile(String opera, String download) {
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp + "\n���ӷ������˿ڣ�" + port + "ʧ�� ");
			return false;
		}

		try {
			if (opera == null || opera == "") {
				socket.close();
				return false;
			}
			OutputStream os;
			os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(opera);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		new DownFile(socket, download, this.cm).start();

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == fh) {// �����¼�
			this.listParentFlies();
			return;
		} else if (e.getSource() == tj) {// �ύ�¼�
			if ("".equals(this.path.getText())) {
				JOptionPane.showMessageDialog(null, "��ַ������Ϊ��", "��ʾ��Ϣ", 1);
				return;
			}
			this.listFlies();
			return;
		} else if (e.getSource() == sc) {// ɾ���¼�
			if (this.path.getText().equals(this.driver)) {
				JOptionPane.showMessageDialog(null, "����ɾ�����̣�������ѡ��", "��ʾ��Ϣ", 1);
				return;
			}
			if (this.path.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "ɾ����ַ����������ѡ��", "��ʾ��Ϣ", 1);
				return;
			}
			if (JOptionPane.showConfirmDialog(null,
					"�Ƿ�ɾ����" + this.path.getText(), "ɾ����Ϣ",
					JOptionPane.YES_NO_OPTION) == 0) {
				this.deleteFile("sc," + this.path.getText());
				this.iniSelectFile();
				this.listParentFlies();
			}
			return;
		} else if (e.getSource() == up) {// �ϴ��¼�
			String serverPath = "";

			if (this.selectFile[2].equals("") && driver.equals("")) {
				JOptionPane.showMessageDialog(null, "Ŀ���ַΪ�գ�������ѡ��", "��ʾ��Ϣ", 1);
				return;
			}
			if ("��    ��".equals(this.selectFile[0])) {
				JOptionPane.showMessageDialog(null, "����ѡ���ļ�Ϊ�����ַ!������ѡ�񡭡�",
						"��ʾ��Ϣ", 1);
				return;
			}
			if (!this.selectFile[2].equals("")) {
				serverPath = this.selectFile[2];
			} else {
				serverPath = driver;
			}

			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int intRetVal = fc.showOpenDialog(cm);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				uploadPath = fc.getSelectedFile().getPath();
			}
			if (uploadPath.equals("")) {
				return;
			}
			if (new File(uploadPath).isDirectory()) {
				JOptionPane.showMessageDialog(null, "��ѡ���ļ���", "��ʾ��Ϣ", 1);
				return;
			}
			if (JOptionPane.showConfirmDialog(null, "�ϴ��ļ���ַ��" + serverPath
					+ "\n�����������ַ��" + uploadPath, "�ϴ���Ϣ",
					JOptionPane.YES_NO_OPTION) == 0) {
				this.upFile("up," + serverPath, this.uploadPath);
				uploadPath = "";
				this.iniSelectFile();
			}
			return;
		} else if (e.getSource() == xz) {// �����¼�
			if (this.selectFile[2].equals("")) {
				JOptionPane.showMessageDialog(null, "���ص�ַ����������ѡ��", "��ʾ��Ϣ", 1);
				return;
			}
			if ("�ļ���".equals(this.selectFile[0])) {
				JOptionPane.showMessageDialog(null, "���������ļ��У���ѡ���ļ����ء���",
						"��ʾ��Ϣ", 1);
				return;
			}
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int intRetVal = fc.showOpenDialog(cm);
			if (intRetVal == JFileChooser.APPROVE_OPTION) {
				downloadPath = fc.getSelectedFile().getPath();
			}
			if (downloadPath.equals("")) {
				return;
			}
			if (JOptionPane.showConfirmDialog(null, "���ص�ַ��"
					+ downloadPath + "\n���������ַ��" + this.selectFile[2], "������Ϣ",
					JOptionPane.YES_NO_OPTION) == 0) {
				this.downFile("xz," + this.selectFile[2], this.downloadPath);
				downloadPath = "";
				this.iniSelectFile();
			}
			return;
		}

		for (int i = 0; i < this.countc; i++) {// ���������¼�
			if (e.getSource() == cc1[i]) {
				this.list.setText("");
				this.path.setText("");
				this.driver = "";
				this.file = this.getFiles("tj," + this.drivers[i]);
				if (this.file != null && !file.equals("")) {
					this.setListStr(file);
					this.list.setText(this.getListStr());
				}
				this.path.setText(drivers[i]);
				this.driver = drivers[i];
				return;
			}
		}
	}
	
	
	public int getCountc() {
		return countc;
	}

	public void setCountc(int countc) {
		this.countc = countc;
	}

	public String getListStr() {
		return listStr;
	}

	public void setListStr(String[][] str) {
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			temp.append("[" + str[i][0] + "]    ");
			temp.append(str[i][1]);
			temp.append("    <·����" + str[i][2] + ">");
			temp.append("\n\r");
		}
		this.listStr = temp.toString();
	}

	public void iniSelectFile() {
		for (int i = 0; i < this.selectFile.length; i++) {
			selectFile[i] = "";
		}
	}

	public void listFlies() {
		this.list.setText("");
		this.file = this.getFiles("tj," + this.path.getText());
		if (this.file != null && !file.equals("")) {
			this.setListStr(file);
			this.list.setText(this.getListStr());
		}
	}

	public void listParentFlies() {
		this.list.setText("");

		this.file = this.getFiles("tj,"
				+ new File(this.path.getText()).getParent());
		if (this.file != null && !file.equals("")) {
			this.setListStr(file);
			this.list.setText(this.getListStr());
			this.path.setText(new File(this.path.getText()).getParent());
		}
	}
	
	
	
	
	
	
	
	
	
	
}
