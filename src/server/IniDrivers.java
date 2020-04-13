package server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class IniDrivers extends Thread {
	private int port = 30015;
	private Socket socket;
	private String drivers;

	public IniDrivers(Socket socket) {
		// this.serverSocket = new ServerSocket(this.port);
		// this.serverSocket.setSoTimeout(86400000);
		this.socket = socket;
	}

	public void run() {
		this.setDrivers();
		if (this.drivers == null || this.drivers.equals("")) {
			return;
		}
		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(this.drivers);
			dos.flush();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			System.out.println("Á´½Ó³¬Ê±£¡");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDrivers() {
		return drivers;
	}

	public void setDrivers() {
		File roots[] = File.listRoots();
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < roots.length; i++) {
			temp.append(roots[i].toString() + ";");
		}
		this.drivers = temp.toString();
	}

}
