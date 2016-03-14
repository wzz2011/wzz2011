package clinet;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import cn.mike.util.myfile;

public class UpLoad implements ActionListener{
	public String filepath2;
	private String sessionID;
	private String ip;
	private String filename;
	private int port;
    JFrame frame=new JFrame("�ļ�ѡ����");
    JTabbedPane tabPane=new JTabbedPane();//ѡ�����
    Container con1=new Container();//����2
    JLabel label2=new JLabel("ѡ���ļ�");
    JTextField text2=new JTextField();
    JButton button2=new JButton("������");
    JButton button3=new JButton("ok");
    JFileChooser jfc=new JFileChooser();//�ļ�ѡ����
    UpLoad(){
    	return;
    }
    UpLoad(String ip,int port,String sessionID){
    	this.sessionID=sessionID;
    	this.ip=ip;
    	this.port=port;
        jfc.setCurrentDirectory(new File("d:\\"));//�ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��
        //����������ȡ����Ļ�ĸ߶ȺͿ��
        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//�趨���ڳ���λ��
        frame.setSize(300,150);//�趨���ڴ�С
        frame.setContentPane(tabPane);//���ò���
       //�����趨��ǩ�ȵĳ���λ�ú͸߿�
        label2.setBounds(10,10,100,20);
        text2.setBounds(80,10,120,20);
        button2.setBounds(210,10,50,20);
        button3.setBounds(210,30,50,20);
        button2.addActionListener(this);//����¼�����
        button3.addActionListener(this);
        con1.add(label2);
        con1.add(text2);
        con1.add(button2);
        con1.add(button3);
        con1.add(jfc);
        tabPane.add("�ļ�ѡ��",con1);//��Ӳ���2
        frame.setVisible(true);//���ڿɼ�
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ʹ�ܹرմ��ڣ���������
    }
    public void actionPerformed(ActionEvent e){//�¼�����
        if(e.getSource().equals(button2)){
        	System.out.println("ѡ���ļ���");
            jfc.setFileSelectionMode(0);//�趨ֻ��ѡ���ļ�
            int state=jfc.showOpenDialog(null);//�˾��Ǵ��ļ�ѡ��������Ĵ������
            System.out.println(state);
            if(state==1){
                return;//�����򷵻�
            }
            else{
            	System.out.println("�ļ��Ѿ�ѡ���");
                File f=jfc.getSelectedFile();//fΪѡ�񵽵��ļ�
                text2.setText(f.getAbsolutePath());
                filepath2=f.getAbsolutePath();
                filename=f.getName();   
                String[] file=filename.split("[.]");
                System.out.println(filepath2);   
                button3.addActionListener(new ActionListener() {
        			@SuppressWarnings({ "resource" })
					public void actionPerformed(ActionEvent e1) {	
        				try {
        					System.out.println("׼���ϴ�"); 
							Socket socket = new Socket(ip,port);
							DataInputStream in=new DataInputStream(socket.getInputStream());
							DataOutputStream out=new DataOutputStream(socket.getOutputStream());
							out.writeUTF("UP;SESSIONID="+sessionID+"&filename="+filename);
							out.flush();
							String getin=in.readUTF();
							System.out.println(getin); 
							if(getin.equals("READY")){
								try {
									 System.out.println("��ʼ�ϴ��ļ�");
									 BufferedInputStream bis = null;  								    	 
							    	 bis = new BufferedInputStream(new FileInputStream(f));
							    	 byte[] buf = new byte[8192];  
							    	 System.out.println(f.length());
						    		 int len = 0;  
						    		 while(((len=bis.read(buf))!=-1)){  
						    			 out.write(buf, 0, len); 
						    		 } 
						    		 out.flush();
						    		 out.close();
						    		 System.out.println("�ϴ����");
						    		 frame.dispose();							    		
						    		 ConnectServer.files.add(new myfile(file[0], file[1]));
						    		 ConnectServer.filename.add(file[0]);
						    		 ConnectServer.filetype.add(file[1]);
						    		 ConnectServer.m.diopose();
						    		 ConnectServer.m=new MyDisk("wzz",sessionID, ConnectServer.files,ip,port);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
						} catch (IOException e2) {
							e2.printStackTrace();
						}
	        		}
	        		});;
	            }
	        }
	    }
}
