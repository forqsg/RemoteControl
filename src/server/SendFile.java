package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SendFile extends Thread{
	  private Socket socket = null;
	  private String filePath = null;
	  private ServerMain sm;
	  
	  public SendFile(Socket socket, String filePath,ServerMain sm) {
	    this.socket = socket;
	    this.filePath = filePath;
	    this.sm = sm;
	  }

	  public void run()
	  {
	    File fi = new File(this.filePath);
	    if(!fi.isFile()){
	    	return;
	    }
	    StringBuffer str = new StringBuffer();
		str.append(sm.info.getText());
		str.append("\n传输文件"+this.filePath+"，文件长度:" + (int)fi.length() + "字节");
		this.sm.info.setText(str.toString().trim());
	    try {
	      DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(this.filePath)));
	      DataOutputStream ps = new DataOutputStream(this.socket.getOutputStream());

	      ps.writeUTF(fi.getName());
	      ps.flush();
	      ps.writeLong(fi.length());
	      ps.flush();

	      int bufferSize = 1024;
	      byte[] buf = new byte[bufferSize];
	      while (true)
	      {
	        int read = 0;
	        if (fis != null) {
	          read = fis.read(buf);
	        }
	        if (read == -1) {
	          break;
	        }
	        ps.write(buf, 0, read);
	      }
	      ps.flush();

	      fis.close();
	      this.socket.close();
	    } catch (Exception e) {
	      System.out.println("被控端读取文件时出错！");
	      e.printStackTrace();
	    }
	  }
	  
	
}
