package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ServerCMD extends Thread {
	private Socket socket = null;
	private DataOutputStream dos = null;
	private OutputStream os = null;
	private String msg;

	public ServerCMD(Socket socket, String msg) {
		this.socket = socket;
		this.msg = msg;
	}

	public void run() {
		Process pro = null;
		try {
			pro = Runtime.getRuntime().exec("cmd /c " + this.msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamReader ipsr = new InputStreamReader(pro.getInputStream());
		BufferedReader br = new BufferedReader(ipsr);
		String cmdResult = "";
		String msgs = null;
		try {
			while ((msgs = br.readLine()) != null)
				cmdResult = cmdResult + msgs + "\n";
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sendmsg = cmdResult + "over";
		try {

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeUTF(sendmsg);
			dos.flush();
			dos.close();
			os.close();

		} catch (UnknownHostException e) {
			System.out.println("�ļ�δ�ҵ���");
		} catch (ConnectException e) {
			System.out.println("���ӳ�ʱ��");
		} catch (IOException e) {
			System.out.println("���ɴ��ļ���");
		} finally {
			try {
				if (this.dos != null)
					this.dos.close();
				if (this.os != null)
					this.os.close();
				if (this.socket != null)
					this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
