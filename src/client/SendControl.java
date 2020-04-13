package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendControl implements Runnable {
	private String ip;
	private int port = 30012;
	private String operateStr;

	public SendControl(String ip, String operateStr) {
		this.ip = ip;
		this.operateStr = operateStr;
	}

	@Override
	public void run() {
		if (this.operateStr == null || this.operateStr.equals("")) {
			return;
		}
		try {
			Socket socket = new Socket(this.ip, this.port);
			OutputStream os = socket.getOutputStream();
			os.write((this.operateStr).getBytes());
			os.flush();
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			System.out.println("Á¬½Ó³¬Ê±£¡");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
