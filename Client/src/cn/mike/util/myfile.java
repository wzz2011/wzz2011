package cn.mike.util;

import java.io.DataInputStream;

public class myfile {
	private String name;//�ļ���
	private String fileType;//�ļ�����
	private String fileSize;//�ļ���С
	private String modifyDate;//�޸�ʱ��
	public myfile(String name,String fileType)
	{
		this.fileType=fileType;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public DataInputStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
