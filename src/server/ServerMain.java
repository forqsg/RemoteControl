package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;


public class ServerMain extends JFrame implements Runnable {
	private int port = 55555;
	private ServerSocket serverSocket = null;
	private boolean iniDriverFlg = true;
	private Socket socket = null;
	public boolean ycFlag = false;// 单链接
	public JTextArea info;// 交互信息
	private StringBuffer str;

	private JTable table;
	private UserTableModel tableModel;
	UserInfo[] users;
	
	public ServerMain() {
		try {
			
			new Thread(new UsersManage(54321)).start();		
			new Thread(new SendScreen(this)).start();   //发送截图线程
			new Thread(new ReceiveEvent(this)).start();   //接收事件线程，并处理
			this.serverSocket = new ServerSocket(this.port);
			this.serverSocket.setSoTimeout(86400000);
			this.startFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	
	public void startFrame() {
		
		
		this.setTitle("远程控制软件");
		this.setBounds(300, 100, 800, 430);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//this.getContentPane().setLayout(null);
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 2, 785, 195);
		scrollPane.setViewportView(table);
		this.getContentPane().add(scrollPane);

		tableModel = new UserTableModel();
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(20);
		
		

		// 信息列表
		JPanel pp = new JPanel();
		pp.setLayout(new BorderLayout());
		this.add(pp, BorderLayout.CENTER);


		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setPreferredSize(new Dimension(520, 400));
		p.setBorder(BorderFactory.createEtchedBorder());
		pp.add(p, BorderLayout.CENTER);

		// 信息列表
		JPanel pinfo = new JPanel();
		pinfo.setLayout(new BorderLayout());
		pinfo.setPreferredSize(new Dimension(520, 200));
		pinfo.setBorder(BorderFactory.createEtchedBorder());
		p.add(pinfo, BorderLayout.SOUTH);

		info = new JTextArea();
		info = new JTextArea("等待客户端连接\n");
		info.setEditable(false);
		JScrollPane jsp = new JScrollPane(info);
		pinfo.add(jsp, BorderLayout.CENTER);
		

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
				System.gc();
			}

		});

		this.setResizable(false);
		this.setVisible(true);
		this.validate();
	}

	@Override
	public void run() {
		while (true) {
			try {
				socket = serverSocket.accept();
				
				if (iniDriverFlg) { 
					
					new IniDrivers(socket).start();   // 初始化磁盘数
					iniDriverFlg = false;
					str = new StringBuffer();
					str.append(this.info.getText());
					str.append("控制端: "+socket.getRemoteSocketAddress() +"  连接成功");
					this.info.setText(str.toString().trim()+"\n");
					
				} else {
					
					InputStream gis = socket.getInputStream();
					DataInputStream dis = new DataInputStream(gis);
					String info = "";
					info = dis.readUTF();
					
					if (info != null) {
						
						String s[] = info.split(";");//匹配来拆分此字符串
						
						if (info.equals("ini")) {   //如果控制端发来信息“ini”命令，则结束监控
							iniDriverFlg = true;
							str = new StringBuffer();
							str.append(this.info.getText());
							str.append("控制端: "+socket.getRemoteSocketAddress() +"结束远程监控");
							this.info.setText(str.toString().trim()+"\n");
							
						} else if (s[0].equals("cmd")) {  //如果发来数组中有cmd命令，则执行cmd控制线程
							
							new ServerCMD(socket,s[1]).start();
							
						} else {
							// 文件操作
							new Thread(new FilesOperate(socket, info,this)).start();
						}
	
						
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		
		new Thread(new ServerMain()).start();
		
	}


	
	
	@SuppressWarnings("serial")
	class UserTableModel extends AbstractTableModel {
		private String[] columns = { ".", "名称", "IP", "端口", "主机名", "MAC地址",
				"操作系统", "上线时间", "状态" };

		UserTableModel(UserInfo[] users) {
			ServerMain.this.users = users;
		}

		UserTableModel() {
			this(new UserInfo[0]);
		}

		@Override
		public int getRowCount() {
			return users.length;
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			UserInfo user = users[row];
			switch (column) {
			case 0:
				return user.isChecked();
			case 1:
				return user.getName();
			case 2:
				return user.getIP();
			case 3:
				return user.getPort();
			case 4:
				return user.getHostName();
			case 5:
				return user.getMAC();
			case 6:
				return user.getOS();
			case 7:
				return user.getTime();
			case 8:
				return user.getState();
			default:
				System.err.println("Logic Error");
			}
			return "";
		}

		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return Boolean.class;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			}
			return String.class;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0)
				return true;
			else
				return false;
		}

		public UserInfo getUserInfo(int row) {
			return users[row];
		}

		public void setUserInfo(UserInfo[] users) {
			ServerMain.this.users = users;
			fireTableDataChanged();

		}

		public String getColumnName(int column) {
			return columns[column];
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
