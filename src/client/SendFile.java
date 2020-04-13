package client;

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
	  private ClientMain cm ;

	  public SendFile(Socket socket, String filePath, ClientMain cm) {
	    this.socket = socket;
	    this.filePath = filePath;
	    this.cm = cm;
	  }

	  public void run()
	  {
	    File fi = new File(this.filePath);
	    if(!fi.isFile()){
	    	return;
	    }
	    StringBuffer str = new StringBuffer();
	    str.append(cm.info.getText());
	    str.append("\n文件长度:" + (int)fi.length() + "字节\n");
	    
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
	      str.append("传输完毕\n");
	      this.cm.info.setText(str.toString());
	      fis.close();
	      this.socket.close();
	    } catch (Exception e) {
	      System.out.println("服务器接收文件出错！");
	      e.printStackTrace();
	    }
	  }
	  	
}
