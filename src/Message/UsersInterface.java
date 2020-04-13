package Message;

public interface UsersInterface {

	//这个接口保证两个功能 
	//注册
	public abstract void regist(Users user); 
	//登录
	public abstract boolean isLogin(String userName,String passWord);

}
