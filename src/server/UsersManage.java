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
				System.out.println("socket�ɹ�");
				InputStream gis = socket.getInputStream();
				DataInputStream dis = new DataInputStream(gis);
				String info = "";
				info = dis.readUTF();
				String s[] = info.split(";");//ƥ������ִ��ַ���
				//�ӱ��ص������ļ���ȡ�ļ���Ϣ���û���������  ������������  username��userpwd
				System.out.println("���ڶ�ȡ�ļ�");
				File file = new File("C:/abc/users.txt");		
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));//����һ��BufferedReader������ȡ�ļ�
	            
				this.username = br.readLine();
				this.userpwd = br.readLine();
				System.out.println("�û�����"+username+"  ���룺"+userpwd);
				
//				if(s[0].equals(this.username) && s[1].equals(this.userpwd)) {//ƥ��ɹ��������棬����ȡ�û�������ʾ��ҳ��
//					SendMsg("y");		
//					System.out.println("msg���ͳɹ�");
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
		
		if(s[0].equals(username)) {//ƥ��ɹ��������棬����ȡ�û�������ʾ��ҳ��	
			
			if(md5(s[1]).equals(this.userpwd)) {//ƥ��ɹ��������棬����ȡ�û�������ʾ��ҳ��
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
	
	//�������
	  private static String md5(String password) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("md5");
	            
	            //����������� ������
	            byte[] bytes = md.digest(password.getBytes());
	            
	            String str = Base64.getEncoder().encodeToString(bytes);
	            
	            return str;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	  
	  
	  
	  /**�ж��û������Ƿ���ȷ
	   */
	  public boolean checkpassword(String newpasswd,String oldpasswd){
	      if(md5(newpasswd).equals(oldpasswd))
	          return true;
	      else
	          return false;
	  }
	  
	
	
	
	
	
	
	
	
//	  /**����MD5���м���
//	   * @param str  �����ܵ��ַ���
//	   * @return  ���ܺ���ַ���
//	   * @throws NoSuchAlgorithmException  û�����ֲ�����ϢժҪ���㷨
//	   * @throws UnsupportedEncodingException  
//	   */
//	  public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
//	      //ȷ�����㷽��
//	      MessageDigest md5=MessageDigest.getInstance("MD5");
//	      BASE64Encoder base64en = new BASE64Encoder();
//	      //���ܺ���ַ���
//	      String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
//	      return newstr;
//	  }
//	  
//	  
//	  /**�ж��û������Ƿ���ȷ
//	   * @param newpasswd  �û����������
//	   * @param oldpasswd  ���ݿ��д洢�����룭���û������ժҪ
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
	  
	
	
	//������Ϣ
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
