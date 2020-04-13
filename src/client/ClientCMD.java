package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientCMD extends JFrame implements ActionListener{

	private int w;
	private int h;
	private JTextField cmd;
	private JButton chick;
	private JButton reset;
	private JButton bHelp;
	private int port;
	private Socket socket;
	private String ip;
	public JTextArea info;// 交互信息
	private String help;

	public ClientCMD(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.w = 550;
		this.h = 500;
		this.startFrame();
		this.setHelp();
	}

	public void startFrame() {
		this.setTitle("CMD控制台  ");
		int x = Toolkit.getDefaultToolkit().getScreenSize().width;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds((x - w) / 2, (y - h) / 2, w, h);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				ClientCMD.this.dispose();
				System.gc();
			}

		});

		this.setLayout(new BorderLayout());
		
		info = new JTextArea("协助远程计算机地址：" + this.ip + "："+ this.port);
		info.setEditable(false);
		JScrollPane jsp2 = new JScrollPane(info);
		this.add(jsp2,BorderLayout.CENTER);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("命令输入:"));
		p1.setBorder(BorderFactory.createEtchedBorder());

		cmd = new JTextField("", 20);
		p1.add(cmd);

		chick = new JButton("执行");
		chick.addActionListener(this);
		p1.add(chick);
		
		reset = new JButton("清空");
		reset.addActionListener(this);
		p1.add(reset);
		
		bHelp = new JButton("帮助信息");
		bHelp.addActionListener(this);
		p1.add(bHelp);
		
		this.add(p1,BorderLayout.SOUTH);
		
		
		
		cmd.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() ==KeyEvent.VK_ENTER){

					if (cmd.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "请输入执行命令！", "链接信息框", 1);
					} else {
						String temp = info.getText();
						info.setText(temp+"\n"+cmd.getText());
						cmdOpera(cmd.getText());
					}
				}		
			}

		});
		
		
		this.setResizable(false);
		this.setVisible(true);
		this.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == chick) {
			if (this.cmd.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "输入为空，请重新输入", "提示", 1);
			} else {
				String temp = this.info.getText();
				this.info.setText(temp+"\n"+cmd.getText());
				this.cmdOpera(cmd.getText());
			}
		}else if(e.getSource() == bHelp){
			new Help("CMD命令大全",this.getHelp());
		}else if (e.getSource() == reset) {
			this.cmd.setText("");
		}

	}
	
	public boolean cmdOpera(String opera){
		StringBuffer str = new StringBuffer();
		str.append(this.info.getText());
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			str.append( "\nCMD信息：port（" + port + "）链接失败 ");
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
			dos.writeUTF("cmd;"+opera);
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
				str.append("\n--返回信息--\n"+info);
				this.info.setText(str.toString());
					return true;
			}
		} catch (IOException e) {
			str.append("\nCMD信息：port（" + port + "）读取返回信息失败!");
		}
		return false;
	
	} 
	
	public String getHelp() {
		return help;
	}

	public void setHelp() {
		this.help = "有关某个命令的详细信息，请键入 HELP 命令名\n"+
					"ASSOC 显示或修改文件扩展名关联。\n"+
					"AT 计划在计算机上运行的命令和程序。\n"+
					"ATTRIB 显示或更改文件属性。\n"+
					"BREAK 设置或清除扩展式 CTRL+C 检查。\n"+
					"CACLS 显示或修改文件的访问控制列表(ACLs)。\n"+
					"CALL 从另一个批处理程序调用这一个。\n"+
					"CD 显示当前目录的名称或将其更改。\n"+
					"CHCP 显示或设置活动代码页数。\n"+
					"CHDIR 显示当前目录的名称或将其更改。\n"+
					"CHKDSK 检查磁盘并显示状态报告。\n"+
					"CHKNTFS 显示或修改启动时间磁盘检查。\n"+
					"CLS 清除屏幕。\n"+
					"CMD 打开另一个 Windows 命令解释程序窗口。\n"+
					"COLOR 设置默认控制台前景和背景颜色。\n"+
					"COMP 比较两个或两套文件的内容。\n"+
					"COMPACT 显示或更改 NTFS 分区上文件的压缩。\n"+
					"CONVERT 将 FAT 卷转换成 NTFS。您不能转换当前驱动器。\n"+
					"COPY 将至少一个文件复制到另一个位置。\n"+
					"DATE 显示或设置日期。\n"+
					"DEL 删除至少一个文件。\n"+
					"DIR 显示一个目录中的文件和子目录。\n"+
					"DISKCOMP 比较两个软盘的内容。\n"+
					"DISKCOPY 将一个软盘的内容复制到另一个软盘。\n"+
					"DOSKEY 编辑命令行、调用 Windows 命令并创建宏。\n"+
					"ECHO 显示消息，或将命令回显打开或关上。\n"+
					"ENDLOCAL 结束批文件中环境更改的本地化。\n"+
					"ERASE 删除至少一个文件。\n"+
					"EXIT 退出 CMD.EXE 程序(命令解释程序)。\n"+
					"FC 比较两个或两套文件，并显示不同处。\n"+
					"FIND 在文件中搜索文字字符串。\n"+
					"FINDSTR 在文件中搜索字符串。\n"+
					"FOR 为一套文件中的每个文件运行一个指定的命令。\n"+
					"FORMAT 格式化磁盘，以便跟 Windows 使用。\n"+
					"FTYPE 显示或修改用于文件扩展名关联的文件类型。\n"+
					"GOTO 将 Windows 命令解释程序指向批处理程序中某个标明的行。\n"+
					"GRAFTABL 启用 Windows 来以图像模式显示\n"+
					"扩展字符集。\n"+
					"HELP 提供 Windows 命令的帮助信息。\n"+
					"IF 执行批处理程序中的条件性处理。\n"+
					"LABEL 创建、更改或删除磁盘的卷标。\n"+
					"MD 创建目录。\n"+
					"MKDIR 创建目录。\n"+
					"MODE 配置系统设备。\n"+
					"MORE 一次显示一个结果屏幕。\n"+
					"MOVE 将文件从一个目录移到另一个目录。\n"+
					"PATH 显示或设置可执行文件的搜索路径。\n"+
					"PAUSE 暂停批文件的处理并显示消息。\n"+
					"POPD 还原 PUSHD 保存的当前目录的上一个值。\n"+
					"PRINT 打印文本文件。\n"+
					"PROMPT 更改 Windows 命令提示符。\n"+
					"PUSHD 保存当前目录，然后对其进行更改。\n"+
					"RD 删除目录。\n"+
					"RECOVER 从有问题的磁盘恢复可读信息。\n"+
					"REM 记录批文件或 CONFIG.SYS 中的注释。\n"+
					"REN 重命名文件。\n"+
					"RENAME 重命名文件。\n"+
					"REPLACE 替换文件。\n"+
					"RMDIR 删除目录。\n"+
					"SET 显示、设置或删除 Windows 环境变量。\n"+
					"SETLOCAL 开始批文件中环境更改的本地化。\n"+
					"SHIFT 更换批文件中可替换参数的位置。\n"+
					"SORT 对输入进行分类。\n"+
					"START 启动另一个窗口来运行指定的程序或命令。\n"+
					"SUBST 将路径跟一个驱动器号关联。\n"+
					"TIME 显示或设置系统时间。\n"+
					"TITLE 设置 CMD.EXE 会话的窗口标题。\n"+
					"TREE 以图形模式显示驱动器或路径的目录结构。\n"+
					"TYPE 显示文本文件的内容。\n"+
					"VER 显示 Windows 版本。\n"+
					"VERIFY 告诉 Windows 是否验证文件是否已正确\n"+
					"写入磁盘。\n"+
					"VOL 显示磁盘卷标和序列号。\n"+
					"XCOPY 复制文件和目录树。\n"+
					
					
					"appwiz.cpl------------添加删除程序\n"+
					
					"control userpasswords2--------用户帐户设置\n"+
					
					"cleanmgr-------垃圾整理\n"+
					
					"CMD--------------命令提示符可以当作是 Windows 的一个附件，Ping，Convert 这些不能在图形环境下 使用的功能要借助它来完成。\n"+
					
					"cmd------jview察看Java虚拟机版本。\n"+
					
					
					"command.com------调用的则是系统内置的 NTVDM，一个 DOS虚拟机。它完全是一个类似 Virtual PC 的 虚拟环境，和系统本身联系不大。当我们在命令提示符下运行 DOS 程序时，实际上也 是自动转移到 NTVDM虚拟机下，和 CMD 本身没什么关系。\n"+
					
					"calc-----------启动计算器\n"+
					
					"chkdsk.exe-----Chkdsk磁盘检查\n"+
					
					"compmgmt.msc---计算机管理\n"+
					
					"conf-----------启动 netmeeting\n"+
					
					"control userpasswords2-----User Account 权限设置\n"+
					
					"devmgmt.msc--- 设备管理器\n"+
					
					"diskmgmt.msc---磁盘管理实用程序\n"+
					
					"dfrg.msc-------磁盘碎片整理程序\n"+
					
					"drwtsn32------ 系统医生\n"+
					
					"dvdplay--------启动Media Player\n"+
					
					"dxdiag-----------DirectX Diagnostic Tool\n"+
					
					"gpedit.msc-------组策略编辑器\n"+
					
					"gpupdate /target:computer /force 强制刷新组策略\n"+
					
					"eventvwr.exe-----事件查看器\n"+
					
					"explorer-------打开资源管理器\n"+
					
					"logoff---------注销命令\n"+
					
					"lusrmgr.msc----本机用户和组\n"+
					
					"msinfo32---------系统信息\n"+
					
					"msconfig---------系统配置实用程序\n"+
					
					"net start (servicename)----启动该服务\n"+
					
					"net stop (servicename)-----停止该服务\n"+
					
					"notepad--------打开记事本\n"+
					
					"nusrmgr.cpl-------同control userpasswords，打开用户帐户控制面板\n"+
					
					"Nslookup-------IP地址侦测器\n"+
					
					"oobe/msoobe /a----检查XP是否激活\n"+
					
					"perfmon.msc----计算机性能监测程序\n"+
					
					"progman--------程序管理器\n"+
					
					"regedit----------注册表编辑器\n"+
					
					"regedt32-------注册表编辑器\n"+
					
					"regsvr32 /u *.dll----停止dll文件运行\n"+
					
					"route print------查看路由表 \n"+
					
					"rononce -p ----15秒关机\n"+
					
					"rsop.msc-------组策略结果集\n"+
					
					"rundll32.exe rundll32.exe %Systemroot%System32shimgvw.dll,ImageView_Fullscreen----启动一个空白的Windows 图片和传真查看器\n"+
					
					"secpol.msc--------本地安全策略\n"+
					
					"services.msc---本地服务设置\n"+
					
					"sfc /scannow-----启动系统文件检查器\n"+
					
					"sndrec32-------录音机\n"+
					
					"taskmgr-----任务管理器（适用于2000／xp／2003）\n"+
					
					"tsshutdn-------60秒倒计时关机命令\n"+
					
					"winchat--------XP自带局域网聊天\n"+
					
					"winmsd---------系统信息\n"+
					
					"winver-----显示About Windows 窗口\n"+
					
					"wupdmgr-----------Windows Update";
	}

}
