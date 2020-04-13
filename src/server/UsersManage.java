package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import sun.misc.BASE64Encoder;

public class UsersManage extends Thread {

	private ServerSocket serverSocket;
	private int serverPort;
	private Socket socket;
	
	String username = null;
	String userpwd = null;
	
    public UsersManage(int serverPort) {
    	this.serverPort = serverPort;
    	
    	try {
			this.serverSocket = new ServerSocket(this.serverPort);	
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }
	
	
	public void run() {
		
		while(true) {
		
			try {

				socket = serverSocket.accept();
				System.out.println("socket成功");
				InputStream gis = socket.getInputStream();
				DataInputStream dis = new DataInputStream(gis);
				String info = "";
				info = dis.readUTF();
				String s[] = info.split(";");//匹配来拆分此字符串
				//从本地的配置文件读取文件信息：用户名和密码  ，将参数传给  username，userpwd
				System.out.println("正在读取文件");
				File file = new File("C:/abc/users.txt");		
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));//构造一个BufferedReader类来读取文件
	            
				this.username = br.readLine();
				this.userpwd = br.readLine();
				System.out.println("用户名："+username+"  密码："+userpwd);
				
//				if(s[0].equals(this.username) && s[1].equals(this.userpwd)) {//匹配成功，返回真，并调取用户名，显示到页面
//					SendMsg("y");		
//					System.out.println("msg发送成功");
//				}
//				else {
//					SendMsg("n");
//				}
		
				isExist(s,this.username);
					
				
				br.close();
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}	
		}
	}
	
	public void isExist(String s[],String username) {
		
		if(s[0].equals(username)) {//匹配成功，返回真，并调取用户名，显示到页面	
			
			if(md5(s[1]).equals(this.userpwd)) {//匹配成功，返回真，并调取用户名，显示到页面
				SendMsg("ypwd");	
			
			}
			else {
				SendMsg("npwd");
			}
			
		}
		else {
			SendMsg("nuser");
		}
		
		
	}
	
	//密码加密
	  private static String md5(String password) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("md5");
	            
	            //生成随机东东 不可逆
	            byte[] bytes = md.digest(password.getBytes());
	            
	            String str = Base64.getEncoder().encodeToString(bytes);
	            
	            return str;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	  
	  
	  
	  /**判断用户密码是否正确
	   */
	  public boolean checkpassword(String newpasswd,String oldpasswd){
	      if(md5(newpasswd).equals(oldpasswd))
	          return true;
	      else
	          return false;
	  }
	  
	
	
	
	
	
	
	
	
//	  /**利用MD5进行加密
//	   * @param str  待加密的字符串
//	   * @return  加密后的字符串
//	   * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
//	   * @throws UnsupportedEncodingException  
//	   */
//	  public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
//	      //确定计算方法
//	      MessageDigest md5=MessageDigest.getInstance("MD5");
//	      BASE64Encoder base64en = new BASE64Encoder();
//	      //加密后的字符串
//	      String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
//	      return newstr;
//	  }
//	  
//	  
//	  /**判断用户密码是否正确
//	   * @param newpasswd  用户输入的密码
//	   * @param oldpasswd  数据库中存储的密码－－用户密码的摘要
//	   * @return
//	   * @throws NoSuchAlgorithmException
//	   * @throws UnsupportedEncodingException
//	   */
//	  public boolean checkpassword(String newpasswd,String oldpasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
//	      if(EncoderByMd5(newpasswd).equals(oldpasswd))
//	          return true;
//	      else
//	          return false;
//	  }
	  
	
	
	//发送信息
	  public void SendMsg(String usermsg) {

			try {
				OutputStream os;
				os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.writeUTF(usermsg);
				dos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	
	
	

}
