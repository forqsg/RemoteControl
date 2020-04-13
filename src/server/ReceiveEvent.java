package server;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ReceiveEvent implements Runnable {

	private int port = 30012;
	private ServerSocket serverSocket;
	private Robot robot;
	private ServerMain sm;

	public ReceiveEvent(ServerMain sm) {
		try {
			this.sm = sm;
			this.serverSocket = new ServerSocket(this.port);
			this.serverSocket.setSoTimeout(86400000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();

				InputStream is = socket.getInputStream();
				int r;
				String info = "";
				while ((r = is.read()) != -1) {
					info += "" + (char) r;
				}
				//System.out.println(info);
				is.close();
				if (info != null) {
					String s[] = info.trim().split(",");
					if ("mouseClicked".equals(s[0].trim())) {
						int type = Integer.parseInt(s[s.length - 1].trim());
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							robot.mousePress(type);
							robot.mouseRelease(type);
//							System.out.println("mouseClicked:MOUSE move to " + x
//									+ "," + y + " AND execute TYPE IS click "
//									+ type);
						}
					} else if ("mousePressed".equals(s[0].trim())) {
						int type = Integer.parseInt(s[s.length - 1].trim());
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							robot.mousePress(type);
//							System.out.println("mousePressed:MOUSE move to " + x
//									+ "," + y + " AND execute TYPE IS press "
//									+ type);
						}
					} else if ("mouseReleased".equals(s[0].trim())) {
						int type = Integer.parseInt(s[s.length - 1].trim());
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
							robot.mouseRelease(type);
//							System.out.println("mouseReleased:MOUSE move to " + x
//									+ "," + y
//									+ " AND execute TYPE IS release  " + type);
						}
					} else if ("mouseDragged".equals(s[0].trim())) {
						if (s.length == 4) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
//							System.out.println("mouseDragged:MOUSE move to " + x
//									+ "," + y);
						}
					} else if ("mouseMoved".equals(s[0].trim())) {
						if (s.length == 3) {
							int x = Integer.parseInt(s[1].trim());
							int y = Integer.parseInt(s[2].trim());
							robot.mouseMove(x, y);
//							System.out.println("mouseMoved:MOUSE move to " + x
//									+ "," + y);
						}
					} else if ("keyPress".equals(s[0].trim())) {
						if (s.length == 2) {
							int keycode = Integer.parseInt(s[1]);
							robot.keyPress(keycode);
						}
					} else if ("keyRelease".equals(s[0].trim())) {
						if (s.length == 2) {
							int keycode = Integer.parseInt(s[1]);
							robot.keyRelease(keycode);
						}
					} else if ("keyTyped".equals(s[0].trim())) {
						if (s.length == 2) {
							int keycode = Integer.parseInt(s[1]);
							robot.keyPress(keycode);
							robot.keyRelease(keycode);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
