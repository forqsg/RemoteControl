package client;

public class ClientTasklist {

	
	private String ip;
	private int port;
	private int w;
	private int h;
	private ClientMain cm;
	public ClientTasklist(String ip, int port, ClientMain cm) {
		this.ip = ip;
		this.port = port;
		this.w = 550;
		this.h = 500;		
		this.cm = cm;
		this.startFrame();
	}


	private void startFrame() {
		// TODO Auto-generated method stub
		
	}
}
