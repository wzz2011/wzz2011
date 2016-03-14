package clinet;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class DownLoad implements ActionListener{
	public String filepath1;
	private static String sessionID;
	private static String downfilename;
	private static String ip;
	private static int port;
	JFrame frame=new JFrame("目录选择器");
	JTabbedPane tabPane=new JTabbedPane();//选项卡布局
	Container con=new Container();//布局1
	JLabel label1=new JLabel("选择目录");
	JTextField text1=new JTextField();
	JButton button1=new JButton("。。。");
	JButton button2=new JButton("Ok");
	JFileChooser jfc=new JFileChooser();//文件选择器
	DownLoad(String ip,int port,String sessionID,String downfilename){
		DownLoad.sessionID=sessionID;
    	DownLoad.ip=ip;
    	DownLoad.port=port;
    	DownLoad.downfilename=downfilename;
		jfc.setCurrentDirectory(new File("d:\\"));//文件选择器的初始目录定为d盘
        //下面两行是取得屏幕的高度和宽度
        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//设定窗口出现位置
        frame.setSize(300,150);//设定窗口大小
        frame.setContentPane(tabPane);//设置布局
        label1.setBounds(10,10,70,20);
        text1.setBounds(80,10,120,20);
        button1.setBounds(210,10,50,20);
        button2.setBounds(210,30,50,20);
        button1.addActionListener(this);//添加事件处理
        button2.addActionListener(this);//添加事件处理
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(button2);
        con.add(jfc);
        tabPane.add("目录选择",con);//添加布局1
        frame.setVisible(true);//窗口可见
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使能关闭窗口，结束程序
       
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button1)){//判断触发方法的按钮是哪个
            jfc.setFileSelectionMode(1);//设定只能选择到文件夹
            int state=jfc.showOpenDialog(null);//此句是打开文件选择器界面的触发语句
            if(state==1){
                return;//撤销则返回
            }
            else{
            	System.out.println("目录已经选择好");
                File f1=jfc.getSelectedFile();//f为选择到的目录
                text1.setText(f1.getAbsolutePath());
                filepath1=f1.getAbsolutePath();
                System.out.println("文件保存路径="+filepath1);
                System.out.println("下载文件名为"+downfilename);
                button2.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e1) {
        				try{
        					@SuppressWarnings("resource")
							Socket socket = new Socket(ip,port);
							DataInputStream in=new DataInputStream(socket.getInputStream());
							DataOutputStream out=new DataOutputStream(socket.getOutputStream());
							out.writeUTF("DOWN;SESSIONID="+sessionID+"&filename="+downfilename);
							out.flush();
							if(downfilename!=null){
								System.out.println("开始下载文件");
								File file = new File(filepath1+"\\"+downfilename);
								@SuppressWarnings("resource")
								FileOutputStream fos = new FileOutputStream(file);
								System.out.println("开启了文件流");
								int bufferSize = 8192;
				                byte[] buf = new byte[bufferSize];
				                while (true) {
				                    int read = 0;
				                    if (in!= null) {
				                        read = in.read(buf);;
				                    }
	
				                    if (read == -1) {
				                        break;
				                    }
				                    fos.write(buf, 0, read);
				                }
				                out.flush();
				                System.out.println(file.getName()+"文件传输完成");
				                out.close();
							}
			                frame.dispose();
				    		 
        				}
        				 catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
        			}
                });;
            }
        }
	}	
  } 