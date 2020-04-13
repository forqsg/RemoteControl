package Message;

public class Users {

	
	private String userName;
	private String passWord;
	
	
	public String getUserName() {//获取用户名
	    return userName;
	}
	public void setUserName(String userName) {//设定用户名
	    this.userName = userName;
	}
	public String getPassWord() {//获取密码
	    return passWord;
	}
	public void setPassWord(String passWord) {//设定密码
	    this.passWord = passWord;
	}
	public Users(String userName, String passWord) {//构造器
	    super();
	    this.userName = userName;
	    this.passWord = passWord;
	}
	public Users() {
	    super();
	}

	
}
