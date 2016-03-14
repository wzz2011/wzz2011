package clinet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cn.mike.util.myfile;

public class ConnectServer implements Runnable{
	private JFrame frame;
	private String ip;
	private int port;
	private String username;
	private String password;
	private JDialog dialog;
	private Socket socket;
	private String id;
	
	private String[] filetmp;
	public static ArrayList<String> filename;
	public static ArrayList<String> filetype;
	public static ArrayList<myfile> files;
	private String gain;
	private int flag = 0;//0为连接不成功,1为用户取消,2为服务器不响应
	public static MyDisk m;

	public ConnectServer(JFrame frame,String ip,int port,JDialog dialog,String username,String password){
		this.frame = frame;
		this.ip = ip;
		this.port = port;
		this.dialog = dialog;
		this.username = username;
		this.password = password;
	}
	public ConnectServer(){				
	}
	public void run(){
		socket = new Socket();
		try {
			socket.setSoTimeout(1000000);//超时时间
			//开始连接
			System.out.println("start"); 
			socket.connect(new InetSocketAddress(ip,port), 100000);//只要没有异常就连接成功
			System.out.println(ip+" "+port); 
			dialog.dispose();//连接成功后销毁对话框
			flag = 2;
			checkUser(socket);//自定义函数，用户身份验证，发送数据
			files=new ArrayList<myfile>();
			if(filename.size()!=0){
				for(int i=0;i<filename.size();i++)
				{
					files.add(new myfile(filename.get(i), filetype.get(i)));
					System.out.println(filename.size());
				}
			}
			m=new MyDisk("wzz",id,files,ip,port);

		} 
		catch (Exception e) {
			e.printStackTrace();
			frame.setEnabled(true);
			dialog.dispose();
			if(flag == 0)
				JOptionPane.showMessageDialog(frame, "无法连接到服务器,请检查网络连接！");
			else if(flag ==2)
				JOptionPane.showMessageDialog(frame, "服务器未响应请求！");
		}
	}
	
	public boolean checkUser(Socket socket) throws Exception{
		DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		//向服务器发送用户数据
		ps.writeUTF("LOGIN;"+"username="+username+"&"+"password="+password);
		ps.flush();
		String readin = dis.readUTF();
		System.out.println(readin); 
		String[] read = readin.split("[;]");
		String[] tmp = read[1].split("[=]");
		id=tmp[1];
		System.out.println(id); 
		if(read[0].equals("SUCCESS")){
		try {
			filename = new ArrayList<String>();
			filetype = new ArrayList<String>();
			ps.writeUTF("LIST");
			ps.flush();
			gain = dis.readUTF();
			if(!gain.equals("")){
				System.out.println(gain);
				String[] file = gain.split("[?]");
				for(int i = 0;i < file.length;i++){
					filetmp=file[i].split("[.]");
					filename.add(filetmp[0]);
					filetype.add(filetmp[1]);
					System.out.println(filename.get(i));
					System.out.println(filetype.get(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	  }
		else
		return false;
	}
	
	//销毁连接
	public void distroy(){
		try {
			flag = 1;
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
