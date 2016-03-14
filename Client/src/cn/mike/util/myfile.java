package cn.mike.util;

import java.io.DataInputStream;

public class myfile {
	private String name;//文件名
	private String fileType;//文件类型
	private String fileSize;//文件大小
	private String modifyDate;//修改时间
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
