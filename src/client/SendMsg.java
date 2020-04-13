package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SendMsg {
	private int port;
	private Socket socket;
	private String ip;
	private ClientMain cm;
	
	public SendMsg(String ip,int port,ClientMain cm){
		this.ip = ip;
		this.port = port;
		this.cm = cm;
	}
	
	public boolean sendControlboolean(String operamsg){
		StringBuffer str = new StringBuffer();
		str.append(cm.info.getText());
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			str.append( "\nÁ¬½Ó¶Ë¿ÚºÅ£º" + port + "Ê§°Ü ");
			return false;
		}

		try {
			if (operamsg == null || operamsg == "") {
				socket.close();
				return false;
			}
			OutputStream os;
			os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(operamsg);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return false;
	
	} 
}
