package client;

import java.awt.Image;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

;

public class ReceiveScreen implements Runnable {

	private int port;
	private ClientEvent ce;
	private byte[] imageBytes;
	public ReceiveScreen() {

	}

	public ReceiveScreen(ClientEvent ce) {
		this.ce = ce;
		this.port = 55556;
	}

	@Override
	public void run() {
		while (ce.isFlag()) {
			Socket socket = null;
			try {
				socket = new Socket(ce.getIp(), this.port);
				DataInputStream dis = new DataInputStream(socket.getInputStream());

				Image image = null;
				try {
					
					//
					while(true){
				          imageBytes = new byte[dis.readInt()];   //���õ������������鳤��
				          dis.readFully(imageBytes);  //���е����ݴ�ŵ�byte��
				          ce.jlabel.setIcon(new ImageIcon(imageBytes));
				          ce.scroll.setViewportView(ce.jlabel);
				          ce.validate();
				      }

					
				} catch (IOException ioe) {
					//System.out.print("1");
				}
				try {
					dis.close();
					//zis.close();
				} catch (Exception e) {
				}
				try {
					TimeUnit.MILLISECONDS.sleep(50);// ����ͼƬ���ʱ��
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			} catch (IOException ioe) {
			} finally {
				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
				}
			}
		}
	}
}
