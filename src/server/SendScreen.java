package server;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class SendScreen implements Runnable {

	public static final int DEFAULT_SERVER_PORT = 55556;
	private int serverPort;
	private Robot robot;
	private ServerSocket serverSocket;
	private Rectangle rec;
	private Dimension screen;
	private BufferedImage image;
	private Socket socket;
//	private ZipOutputStream zip;
	private ServerMain sm;
	private DataOutputStream dos = null; 
	private byte imageBytes[];
	
	public SendScreen(ServerMain sm) {
		this.sm = sm;
		this.serverPort = SendScreen.DEFAULT_SERVER_PORT;

		try {
			serverSocket = new ServerSocket(this.serverPort);
			serverSocket.setSoTimeout(86400000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		rec = new Rectangle(screen);
		
	}

	public void changeServerPort(int serverPort) {
		if (this.serverPort == serverPort)
			return;
		this.serverPort = serverPort;
		try {
			this.serverSocket.close();
		} catch (Exception e) {
		}
		try {
			serverSocket = new ServerSocket(this.serverPort);
			serverSocket.setSoTimeout(86400000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public int getServerPort() {
		return this.serverPort;
	}

	  /**
	   *  压缩图片
	   * @param 需要压缩的图片
	   * @return 压缩后的byte数组
	   * @throws IOException 
	   * @throws ImageFormatException 
	   */
	  public byte[] getImageBytes(BufferedImage image) throws ImageFormatException, IOException{
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      //压缩器压缩，先拿到存放到byte输出流中
	      JPEGImageEncoder jpegd = JPEGCodec.createJPEGEncoder(baos);
	      //将iamge压缩
	      jpegd.encode(image);
	      //转换成byte数组
	      return baos.toByteArray();  
	  }
	
	
	public void run() {
		while (true) {	
			try {

				socket = serverSocket.accept();			
//				zip = new ZipOutputStream(new DataOutputStream(
//						socket.getOutputStream()));
//				zip.setLevel(9);// 为后续的 DEFLATED 条目设置压缩级别 压缩级别 (0-9)
//				try {
//					img = robot.createScreenCapture(rect);
//					zip.putNextEntry(new ZipEntry("test.jpg"));
//					ImageIO.write(img, "jpg", zip);
//					if (zip != null)
//						zip.close();
//				} catch (IOException ioe) {
//					System.out.println("错误2");
//				}
				
				
				dos = new DataOutputStream(socket.getOutputStream());
				image = robot.createScreenCapture(rec);
                imageBytes = getImageBytes(image);
                dos.writeInt(imageBytes.length);
                dos.write(imageBytes);
                dos.flush();
                Thread.sleep(50);   //线程睡眠

                
				
			} catch (IOException ioe) {
				System.out.println("错误1");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
		
	}

}
