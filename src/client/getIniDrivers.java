package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class getIniDrivers {
	private int port;
	private Socket socket;
	private String ip;
	private String[] drivers;
	private ClientMain cm;   //�������ص���Ϣ��ʾ��

	public getIniDrivers(String ip,int port,ClientMain cm) {
		this.cm = cm;
		this.ip = ip;
		this.port = port;
	}

	public String[] getDrivers() {
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp+"\n\r���ӳ�ʼ���˿ڣ�"+port+" ʧ��");
			return null;
		}

		InputStream is;
		try {
			is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			String info = "";
			info = dis.readUTF();
			is.close();
			if (info != null) {
				this.drivers = info.trim().split(";");
				return drivers;
			}
		} catch (IOException e) {
			String temp = cm.info.getText();
			cm.info.setText(temp+"\n\r����������Ϣ��port��"+port+"����������ʧ�ܣ�");
		}
		return null;
	}
	

}
