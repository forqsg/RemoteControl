package Message;

public class Users {

	
	private String userName;
	private String passWord;
	
	
	public String getUserName() {//��ȡ�û���
	    return userName;
	}
	public void setUserName(String userName) {//�趨�û���
	    this.userName = userName;
	}
	public String getPassWord() {//��ȡ����
	    return passWord;
	}
	public void setPassWord(String passWord) {//�趨����
	    this.passWord = passWord;
	}
	public Users(String userName, String passWord) {//������
	    super();
	    this.userName = userName;
	    this.passWord = passWord;
	}
	public Users() {
	    super();
	}

	
}
