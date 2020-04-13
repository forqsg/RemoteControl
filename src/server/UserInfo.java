package server;

public class UserInfo {
	private String name;
	private String MAC;
	private String IP;
	private String hostName;
	private String port;
	private String state;
	private String time;
	private String OS;
	private boolean isChecked=false;
	public String getOS() {
		return OS;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public void setOS(String oS) {
		OS = oS;
	}


	public String getName() {
		return name;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getPort() {
		return port;
	}

	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", MAC=" + MAC + ", IP=" + IP
				+ ", hostName=" + hostName + ", port=" + port + ", state="
				+ state + ", time=" + time + ", OS=" + OS + "isChecked,"+isChecked+"]";
	}

	public void setPort(String port) {
		this.port = port;
	}

	public UserInfo(String name, String mAC, String iP, String hostName,
			String oS) {
		super();
		this.name = name;
		MAC = mAC;
		IP = iP;
		this.hostName = hostName;
		OS = oS;
		this.time="";
		this.port="";
		this.state="¿Îœﬂ";
	}

	public UserInfo(String name, String mAC, String iP, String hostName,
			String port, String state, String time, String oS) {
		super();
		this.name = name;
		MAC = mAC;
		IP = iP;
		this.hostName = hostName;
		this.port = port;
		this.state = state;
		this.time = time;
		OS = oS;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserInfo() {
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}
}
