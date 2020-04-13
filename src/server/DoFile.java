package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.net.Socket;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DoFile {

	private String filesPath;

	private StringBuffer msg;

	public DoFile() {
		this.msg = new StringBuffer();
	}

	/**
	 * ɾ��ĳ���ļ����µ������ļ��к��ļ�
	 * 
	 * @param delpath
	 *            String
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public boolean deletefile(String delpath, StringBuffer sb) {
		StringBuffer temp = sb;
		File file = new File(delpath);
		if (!file.exists()) {
			msg.append("δ�ҵ��ļ�!,");
			return false;
		}
		try {
			if (!file.isDirectory()) {
				file.delete();
				msg.append(temp.toString() + "" + delpath + ",ɾ���ɹ�!,");
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				msg.append("," + temp.toString() + "��ʼɾ����," + temp.toString()
						+ delpath + ",");
				temp.append("[scWhiteSpase]");
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						msg.append(temp.toString() + "-"
								+ delfile.getAbsolutePath() + "ɾ���ɹ���,");
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "\\" + filelist[i], temp);
					}
				}
				temp.delete(0, 14);
				file.delete();
				msg.append(temp.toString() + delpath + "," + temp.toString()
						+ "ɾ����ɣ�" + ",");

			}
		} catch (Exception e) {
			temp.delete(0, 14);
			msg.append(temp.toString() + "�ļ�ɾ��ʧ�ܣ�,");
		}
		return true;
	}

	/***
	 * 
	 * ��ȡ�ļ����ļ���Ŀ¼
	 * */
	public boolean readfile(String filepath) {
		File file = new File(filepath);
		if (!file.exists()) {
			msg.append("δ�ҵ��ļ�\n\r");
			return false;
		}
		if (file.exists() && !file.isDirectory()) {
			StringBuffer files = new StringBuffer();// type name path
			files.append("�ļ�,");
			files.append(file.getName() + ",");
			files.append(file.getAbsolutePath() + ";");
			this.setFilePath(files.toString());
			return true;

		} else if (file.exists() && file.isDirectory()) {
			String[] filelist = file.list();
			StringBuffer files = new StringBuffer();// type name path
			if(filelist==null){
				msg.append("δ�ҵ��ļ���");
				return false;
			}
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(filepath + "\\" + filelist[i]);

				if (readfile.isDirectory()) {
					files.append("�ļ���,");
					files.append(readfile.getName() + ",");
					files.append(readfile.getAbsolutePath() + ";");
				} else if (!readfile.isDirectory()) {
					files.append("��    ��,");
					files.append(readfile.getName() + ",");
					files.append(readfile.getAbsolutePath() + ";");
				}

			}
			this.setFilePath(files.toString());
			return true;
		}

		return false;
	}
	

	public String getFilePath() {
		return filesPath;
	}

	public void setFilePath(String filePath) {
		this.filesPath = filePath;
	}

	public String getMsg() {
		return msg.toString();
	}

	public void setMsg(StringBuffer msg) {
		this.msg = msg;
	}

//	public static void main(String[] args) {
//	
//
//	}
}