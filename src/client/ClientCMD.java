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
	public JTextArea info;// ������Ϣ
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
		this.setTitle("CMD����̨  ");
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
		
		info = new JTextArea("Э��Զ�̼������ַ��" + this.ip + "��"+ this.port);
		info.setEditable(false);
		JScrollPane jsp2 = new JScrollPane(info);
		this.add(jsp2,BorderLayout.CENTER);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		p1.add(new JLabel("��������:"));
		p1.setBorder(BorderFactory.createEtchedBorder());

		cmd = new JTextField("", 20);
		p1.add(cmd);

		chick = new JButton("ִ��");
		chick.addActionListener(this);
		p1.add(chick);
		
		reset = new JButton("���");
		reset.addActionListener(this);
		p1.add(reset);
		
		bHelp = new JButton("������Ϣ");
		bHelp.addActionListener(this);
		p1.add(bHelp);
		
		this.add(p1,BorderLayout.SOUTH);
		
		
		
		cmd.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() ==KeyEvent.VK_ENTER){

					if (cmd.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "������ִ�����", "������Ϣ��", 1);
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
				JOptionPane.showMessageDialog(null, "����Ϊ�գ�����������", "��ʾ", 1);
			} else {
				String temp = this.info.getText();
				this.info.setText(temp+"\n"+cmd.getText());
				this.cmdOpera(cmd.getText());
			}
		}else if(e.getSource() == bHelp){
			new Help("CMD�����ȫ",this.getHelp());
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
			str.append( "\nCMD��Ϣ��port��" + port + "������ʧ�� ");
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
				str.append("\n--������Ϣ--\n"+info);
				this.info.setText(str.toString());
					return true;
			}
		} catch (IOException e) {
			str.append("\nCMD��Ϣ��port��" + port + "����ȡ������Ϣʧ��!");
		}
		return false;
	
	} 
	
	public String getHelp() {
		return help;
	}

	public void setHelp() {
		this.help = "�й�ĳ���������ϸ��Ϣ������� HELP ������\n"+
					"ASSOC ��ʾ���޸��ļ���չ��������\n"+
					"AT �ƻ��ڼ���������е�����ͳ���\n"+
					"ATTRIB ��ʾ������ļ����ԡ�\n"+
					"BREAK ���û������չʽ CTRL+C ��顣\n"+
					"CACLS ��ʾ���޸��ļ��ķ��ʿ����б�(ACLs)��\n"+
					"CALL ����һ����������������һ����\n"+
					"CD ��ʾ��ǰĿ¼�����ƻ�����ġ�\n"+
					"CHCP ��ʾ�����û����ҳ����\n"+
					"CHDIR ��ʾ��ǰĿ¼�����ƻ�����ġ�\n"+
					"CHKDSK �����̲���ʾ״̬���档\n"+
					"CHKNTFS ��ʾ���޸�����ʱ����̼�顣\n"+
					"CLS �����Ļ��\n"+
					"CMD ����һ�� Windows ������ͳ��򴰿ڡ�\n"+
					"COLOR ����Ĭ�Ͽ���̨ǰ���ͱ�����ɫ��\n"+
					"COMP �Ƚ������������ļ������ݡ�\n"+
					"COMPACT ��ʾ����� NTFS �������ļ���ѹ����\n"+
					"CONVERT �� FAT ��ת���� NTFS��������ת����ǰ��������\n"+
					"COPY ������һ���ļ����Ƶ���һ��λ�á�\n"+
					"DATE ��ʾ���������ڡ�\n"+
					"DEL ɾ������һ���ļ���\n"+
					"DIR ��ʾһ��Ŀ¼�е��ļ�����Ŀ¼��\n"+
					"DISKCOMP �Ƚ��������̵����ݡ�\n"+
					"DISKCOPY ��һ�����̵����ݸ��Ƶ���һ�����̡�\n"+
					"DOSKEY �༭�����С����� Windows ��������ꡣ\n"+
					"ECHO ��ʾ��Ϣ����������Դ򿪻���ϡ�\n"+
					"ENDLOCAL �������ļ��л������ĵı��ػ���\n"+
					"ERASE ɾ������һ���ļ���\n"+
					"EXIT �˳� CMD.EXE ����(������ͳ���)��\n"+
					"FC �Ƚ������������ļ�������ʾ��ͬ����\n"+
					"FIND ���ļ������������ַ�����\n"+
					"FINDSTR ���ļ��������ַ�����\n"+
					"FOR Ϊһ���ļ��е�ÿ���ļ�����һ��ָ�������\n"+
					"FORMAT ��ʽ�����̣��Ա�� Windows ʹ�á�\n"+
					"FTYPE ��ʾ���޸������ļ���չ���������ļ����͡�\n"+
					"GOTO �� Windows ������ͳ���ָ�������������ĳ���������С�\n"+
					"GRAFTABL ���� Windows ����ͼ��ģʽ��ʾ\n"+
					"��չ�ַ�����\n"+
					"HELP �ṩ Windows ����İ�����Ϣ��\n"+
					"IF ִ������������е������Դ���\n"+
					"LABEL ���������Ļ�ɾ�����̵ľ�ꡣ\n"+
					"MD ����Ŀ¼��\n"+
					"MKDIR ����Ŀ¼��\n"+
					"MODE ����ϵͳ�豸��\n"+
					"MORE һ����ʾһ�������Ļ��\n"+
					"MOVE ���ļ���һ��Ŀ¼�Ƶ���һ��Ŀ¼��\n"+
					"PATH ��ʾ�����ÿ�ִ���ļ�������·����\n"+
					"PAUSE ��ͣ���ļ��Ĵ�����ʾ��Ϣ��\n"+
					"POPD ��ԭ PUSHD ����ĵ�ǰĿ¼����һ��ֵ��\n"+
					"PRINT ��ӡ�ı��ļ���\n"+
					"PROMPT ���� Windows ������ʾ����\n"+
					"PUSHD ���浱ǰĿ¼��Ȼ�������и��ġ�\n"+
					"RD ɾ��Ŀ¼��\n"+
					"RECOVER ��������Ĵ��ָ̻��ɶ���Ϣ��\n"+
					"REM ��¼���ļ��� CONFIG.SYS �е�ע�͡�\n"+
					"REN �������ļ���\n"+
					"RENAME �������ļ���\n"+
					"REPLACE �滻�ļ���\n"+
					"RMDIR ɾ��Ŀ¼��\n"+
					"SET ��ʾ�����û�ɾ�� Windows ����������\n"+
					"SETLOCAL ��ʼ���ļ��л������ĵı��ػ���\n"+
					"SHIFT �������ļ��п��滻������λ�á�\n"+
					"SORT ��������з��ࡣ\n"+
					"START ������һ������������ָ���ĳ�������\n"+
					"SUBST ��·����һ���������Ź�����\n"+
					"TIME ��ʾ������ϵͳʱ�䡣\n"+
					"TITLE ���� CMD.EXE �Ự�Ĵ��ڱ��⡣\n"+
					"TREE ��ͼ��ģʽ��ʾ��������·����Ŀ¼�ṹ��\n"+
					"TYPE ��ʾ�ı��ļ������ݡ�\n"+
					"VER ��ʾ Windows �汾��\n"+
					"VERIFY ���� Windows �Ƿ���֤�ļ��Ƿ�����ȷ\n"+
					"д����̡�\n"+
					"VOL ��ʾ���̾������кš�\n"+
					"XCOPY �����ļ���Ŀ¼����\n"+
					
					
					"appwiz.cpl------------���ɾ������\n"+
					
					"control userpasswords2--------�û��ʻ�����\n"+
					
					"cleanmgr-------��������\n"+
					
					"CMD--------------������ʾ�����Ե����� Windows ��һ��������Ping��Convert ��Щ������ͼ�λ����� ʹ�õĹ���Ҫ����������ɡ�\n"+
					
					"cmd------jview�쿴Java������汾��\n"+
					
					
					"command.com------���õ�����ϵͳ���õ� NTVDM��һ�� DOS�����������ȫ��һ������ Virtual PC �� ���⻷������ϵͳ������ϵ���󡣵�������������ʾ�������� DOS ����ʱ��ʵ����Ҳ ���Զ�ת�Ƶ� NTVDM������£��� CMD ����ûʲô��ϵ��\n"+
					
					"calc-----------����������\n"+
					
					"chkdsk.exe-----Chkdsk���̼��\n"+
					
					"compmgmt.msc---���������\n"+
					
					"conf-----------���� netmeeting\n"+
					
					"control userpasswords2-----User Account Ȩ������\n"+
					
					"devmgmt.msc--- �豸������\n"+
					
					"diskmgmt.msc---���̹���ʵ�ó���\n"+
					
					"dfrg.msc-------������Ƭ�������\n"+
					
					"drwtsn32------ ϵͳҽ��\n"+
					
					"dvdplay--------����Media Player\n"+
					
					"dxdiag-----------DirectX Diagnostic Tool\n"+
					
					"gpedit.msc-------����Ա༭��\n"+
					
					"gpupdate /target:computer /force ǿ��ˢ�������\n"+
					
					"eventvwr.exe-----�¼��鿴��\n"+
					
					"explorer-------����Դ������\n"+
					
					"logoff---------ע������\n"+
					
					"lusrmgr.msc----�����û�����\n"+
					
					"msinfo32---------ϵͳ��Ϣ\n"+
					
					"msconfig---------ϵͳ����ʵ�ó���\n"+
					
					"net start (servicename)----�����÷���\n"+
					
					"net stop (servicename)-----ֹͣ�÷���\n"+
					
					"notepad--------�򿪼��±�\n"+
					
					"nusrmgr.cpl-------ͬcontrol userpasswords�����û��ʻ��������\n"+
					
					"Nslookup-------IP��ַ�����\n"+
					
					"oobe/msoobe /a----���XP�Ƿ񼤻�\n"+
					
					"perfmon.msc----��������ܼ�����\n"+
					
					"progman--------���������\n"+
					
					"regedit----------ע���༭��\n"+
					
					"regedt32-------ע���༭��\n"+
					
					"regsvr32 /u *.dll----ֹͣdll�ļ�����\n"+
					
					"route print------�鿴·�ɱ� \n"+
					
					"rononce -p ----15��ػ�\n"+
					
					"rsop.msc-------����Խ����\n"+
					
					"rundll32.exe rundll32.exe %Systemroot%System32shimgvw.dll,ImageView_Fullscreen----����һ���հ׵�Windows ͼƬ�ʹ���鿴��\n"+
					
					"secpol.msc--------���ذ�ȫ����\n"+
					
					"services.msc---���ط�������\n"+
					
					"sfc /scannow-----����ϵͳ�ļ������\n"+
					
					"sndrec32-------¼����\n"+
					
					"taskmgr-----�����������������2000��xp��2003��\n"+
					
					"tsshutdn-------60�뵹��ʱ�ػ�����\n"+
					
					"winchat--------XP�Դ�����������\n"+
					
					"winmsd---------ϵͳ��Ϣ\n"+
					
					"winver-----��ʾAbout Windows ����\n"+
					
					"wupdmgr-----------Windows Update";
	}

}
