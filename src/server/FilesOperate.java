package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;


public class FilesOperate extends DoFile implements Runnable {
	private Socket socket;
	private String path;
	private String info;
	private ServerMain sm;

	public FilesOperate(Socket socket, String info, ServerMain sm) {
		this.socket = socket;
		this.info = info;
		this.sm = sm;
	}

	public void run() {
		StringBuffer str = new StringBuffer();
		str.append(sm.info.getText());
		try {
			if (info != null) {
				String s[] = info.trim().split(",");
				if (s.length >= 2) {
					if ("tj".equals(s[0]) && s[1] != "") {
						path = s[1];
						try {
							if (this.readfile(path)) {
								OutputStream os = socket.getOutputStream();
								DataOutputStream dos = new DataOutputStream(os);
								dos.writeUTF("tj;" + this.getFilePath());
								dos.flush();
								dos.close();
								os.close();
							}
						} catch (UnknownHostException e) {
							System.out.println("文件未找到！");
						} catch (ConnectException e) {
							System.out.println("链接超时！");
						} catch (IOException e) {
							System.out.println("不可打开文件！");
						} finally {
							socket.close();
						}
					} else if ("sc".equals(s[0]) && s[1] != "") {
						path = s[1];
						try {
							if (this.deletefile(path, new StringBuffer(""))) {
								OutputStream os = socket.getOutputStream();
								DataOutputStream dos = new DataOutputStream(os);
								dos.writeUTF("sc;" + this.getMsg());
								str.append("\n" + this.getMsg());
								this.setMsg(new StringBuffer());
								dos.flush();
							}
						} catch (UnknownHostException e) {
							e.printStackTrace();
						} catch (ConnectException e) {
							System.out.println("链接超时！");
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							socket.close();
						}
					} else if ("xz".equals(s[0]) && s[1] != "") {
						path = s[1];
						new SendFile(socket, path, this.sm).start();
					} else if ("up".equals(s[0]) && s[1] != "") {
						path = s[1];
						new DownFile(socket, path, this.sm).start();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.sm.info.setText(str.toString().trim());
		}

	}
}
