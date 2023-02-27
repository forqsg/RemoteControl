package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;


//�����߳�
public class DownFile extends Thread {
	private String savePath = null;
	private int bufferSize = 8192;
	private Socket socket = null;
	private DataOutputStream out = null;
	private ServerMain sm;
	
	public DownFile(Socket s, String savePath,ServerMain sm) {
		this.socket = s;
		this.savePath = savePath;
		this.sm = sm;
		File f = new File(this.savePath);
		if (!f.exists()) {
			f.mkdir();
		}
	}

	public void run() {
		try {
			getOutputStream();
			saveFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getOutputStream() throws Exception {
		this.out = new DataOutputStream(this.socket.getOutputStream());
		this.out.flush();
		return;

	}

	private void saveFile() throws Exception {
		StringBuffer str = new StringBuffer();
		DataInputStream inputStream = null;
		inputStream = new DataInputStream(new BufferedInputStream(
				this.socket.getInputStream()));

		byte[] buf = new byte[this.bufferSize];
		long fileLength = 0L;
		this.savePath += inputStream.readUTF();
		DataOutputStream fileOut = new DataOutputStream(
				new BufferedOutputStream(new BufferedOutputStream(
						new FileOutputStream(this.savePath))));
		fileLength = inputStream.readLong();
		str.append("\n�ļ��ĳ���Ϊ:" + fileLength + "\n");
		str.append("��ʼ�����ļ�!\n");
		while (true) {
			int read = 0;
			if (inputStream != null) {
				read = inputStream.read(buf);
			}
			if (read == -1) {
				break;
			}
			fileOut.write(buf, 0, read);
		}
		str.append("������ɣ��ļ���Ϊ" + this.savePath + "\n");
		try {
			fileOut.close();
		} catch (RuntimeException localRuntimeException) {
		}
		StringBuffer str1 = new StringBuffer();
		str1.append(sm.info.getText());
		str1.append(str.toString().toString().trim());
		this.sm.info.setText(str1.toString().trim());
		shutDownConnection();
	}

	private void shutDownConnection() {
		try {
			if (this.out != null)
				this.out.close();
			if (this.socket != null)
				this.socket.close();
		} catch (Exception localException) {
		}
	}
}
