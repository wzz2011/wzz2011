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
	JFrame frame=new JFrame("Ŀ¼ѡ����");
	JTabbedPane tabPane=new JTabbedPane();//ѡ�����
	Container con=new Container();//����1
	JLabel label1=new JLabel("ѡ��Ŀ¼");
	JTextField text1=new JTextField();
	JButton button1=new JButton("������");
	JButton button2=new JButton("Ok");
	JFileChooser jfc=new JFileChooser();//�ļ�ѡ����
	DownLoad(String ip,int port,String sessionID,String downfilename){
		DownLoad.sessionID=sessionID;
    	DownLoad.ip=ip;
    	DownLoad.port=port;
    	DownLoad.downfilename=downfilename;
		jfc.setCurrentDirectory(new File("d:\\"));//�ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��
        //����������ȡ����Ļ�ĸ߶ȺͿ��
        double lx=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double ly=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        frame.setLocation(new Point((int)(lx/2)-150,(int)(ly/2)-150));//�趨���ڳ���λ��
        frame.setSize(300,150);//�趨���ڴ�С
        frame.setContentPane(tabPane);//���ò���
        label1.setBounds(10,10,70,20);
        text1.setBounds(80,10,120,20);
        button1.setBounds(210,10,50,20);
        button2.setBounds(210,30,50,20);
        button1.addActionListener(this);//����¼�����
        button2.addActionListener(this);//����¼�����
        con.add(label1);
        con.add(text1);
        con.add(button1);
        con.add(button2);
        con.add(jfc);
        tabPane.add("Ŀ¼ѡ��",con);//��Ӳ���1
        frame.setVisible(true);//���ڿɼ�
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ʹ�ܹرմ��ڣ���������
       
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button1)){//�жϴ��������İ�ť���ĸ�
            jfc.setFileSelectionMode(1);//�趨ֻ��ѡ���ļ���
            int state=jfc.showOpenDialog(null);//�˾��Ǵ��ļ�ѡ��������Ĵ������
            if(state==1){
                return;//�����򷵻�
            }
            else{
            	System.out.println("Ŀ¼�Ѿ�ѡ���");
                File f1=jfc.getSelectedFile();//fΪѡ�񵽵�Ŀ¼
                text1.setText(f1.getAbsolutePath());
                filepath1=f1.getAbsolutePath();
                System.out.println("�ļ�����·��="+filepath1);
                System.out.println("�����ļ���Ϊ"+downfilename);
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
								System.out.println("��ʼ�����ļ�");
								File file = new File(filepath1+"\\"+downfilename);
								@SuppressWarnings("resource")
								FileOutputStream fos = new FileOutputStream(file);
								System.out.println("�������ļ���");
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
				                System.out.println(file.getName()+"�ļ��������");
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