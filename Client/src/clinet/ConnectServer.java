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
	private int flag = 0;//0Ϊ���Ӳ��ɹ�,1Ϊ�û�ȡ��,2Ϊ����������Ӧ
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
			socket.setSoTimeout(1000000);//��ʱʱ��
			//��ʼ����
			System.out.println("start"); 
			socket.connect(new InetSocketAddress(ip,port), 100000);//ֻҪû���쳣�����ӳɹ�
			System.out.println(ip+" "+port); 
			dialog.dispose();//���ӳɹ������ٶԻ���
			flag = 2;
			checkUser(socket);//�Զ��庯�����û������֤����������
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
				JOptionPane.showMessageDialog(frame, "�޷����ӵ�������,�����������ӣ�");
			else if(flag ==2)
				JOptionPane.showMessageDialog(frame, "������δ��Ӧ����");
		}
	}
	
	public boolean checkUser(Socket socket) throws Exception{
		DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		//������������û�����
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
	
	//��������
	public void distroy(){
		try {
			flag = 1;
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
