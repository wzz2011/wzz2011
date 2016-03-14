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
    JFrame frame=new JFrame("文件选择器");
    JTabbedPane tabPane=new JTabbedPane();//选项卡布局
    Container con1=new Container();//布局2
    JLabel label2=new JLabel("选择文件");
    JTextField text2=new JTextField();
    JButton button2=new JButton("。。。");
    JButton button3=new JButton("ok");
    JFileChooser jfc=new JFileChooser();//文件选择器
    UpLoad(){
    	return;
    }
    UpLoad(String ip,int port,String sessionID){
    	this.sessionID=sessionID;
    	this.ip=ip;
    	this.port=port;
        jfc.setCurrentDirectory(new File("d:\\"));//文件选择器的初始目录定为d盘
        //下面两行是取得屏幕的高度和宽度
        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//设定窗口出现位置
        frame.setSize(300,150);//设定窗口大小
        frame.setContentPane(tabPane);//设置布局
       //下面设定标签等的出现位置和高宽
        label2.setBounds(10,10,100,20);
        text2.setBounds(80,10,120,20);
        button2.setBounds(210,10,50,20);
        button3.setBounds(210,30,50,20);
        button2.addActionListener(this);//添加事件处理
        button3.addActionListener(this);
        con1.add(label2);
        con1.add(text2);
        con1.add(button2);
        con1.add(button3);
        con1.add(jfc);
        tabPane.add("文件选择",con1);//添加布局2
        frame.setVisible(true);//窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
    }
    public void actionPerformed(ActionEvent e){//事件处理
        if(e.getSource().equals(button2)){
        	System.out.println("选择文件中");
            jfc.setFileSelectionMode(0);//设定只能选择到文件
            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            System.out.println(state);
            if(state==1){
                return;//撤销则返回
            }
            else{
            	System.out.println("文件已经选择好");
                File f=jfc.getSelectedFile();//f为选择到的文件
                text2.setText(f.getAbsolutePath());
                filepath2=f.getAbsolutePath();
                filename=f.getName();   
                String[] file=filename.split("[.]");
                System.out.println(filepath2);   
                button3.addActionListener(new ActionListener() {
        			@SuppressWarnings({ "resource" })
					public void actionPerformed(ActionEvent e1) {	
        				try {
        					System.out.println("准备上传"); 
							Socket socket = new Socket(ip,port);
							DataInputStream in=new DataInputStream(socket.getInputStream());
							DataOutputStream out=new DataOutputStream(socket.getOutputStream());
							out.writeUTF("UP;SESSIONID="+sessionID+"&filename="+filename);
							out.flush();
							String getin=in.readUTF();
							System.out.println(getin); 
							if(getin.equals("READY")){
								try {
									 System.out.println("开始上传文件");
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
						    		 System.out.println("上传完毕");
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
