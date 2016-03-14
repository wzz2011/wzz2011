package clinet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import cn.mike.util.MD5;

public class Client {
	public static void main(String[] args) {
		new LoginFrame("合肥城市云");
	}
}
@SuppressWarnings("serial")
class LoginFrame extends JFrame{
	private JTextField name;
	private JPasswordField password;
	private JTextField ip;
	private JTextField port;
	private JButton loginBtn;
	private JButton cancelBtn;
	private JCheckBox autocb;
	private JCheckBox memorycb;
	
	//获取屏幕大小,定位窗体
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//登陆按钮锁
	public LoginFrame(String frameTitle){
		this.setTitle(frameTitle);
		this.setSize(400, 325);
		this.setLocation((int)((screenSize.getWidth()-this.getWidth())/2),(int)(screenSize.getHeight()-this.getHeight())/2);	
		//面板
		JPanel btnPanel = new JPanel();
		JPanel BoxPanel = new JPanel();	
		//盒子
		Box box = Box.createVerticalBox();	
		//LOGO图片
		ImageIcon background = new ImageIcon("images/clientLoginLogo.jpg");		
		//信息输入框
		JLabel nameLabel = new JLabel("用户名:");
		JLabel passwordLabel = new JLabel("密    码:");
		JLabel ipLabel = new JLabel("服务器IP:");
		JLabel portLabel = new JLabel("服务器端口:");	
		name = new JTextField(12);
		password = new JPasswordField(12);
		ip = new JTextField(8);
		port = new JTextField(8);
		
		//复选框
		autocb=new JCheckBox("自动登录");
		memorycb=new JCheckBox("记住密码");
		BoxPanel.add(autocb);
		BoxPanel.add(memorycb);

		//按钮
		loginBtn = new JButton("登    陆");
		cancelBtn = new JButton("注     册");
		btnPanel.add(loginBtn);
		btnPanel.add(cancelBtn);
		
		//添加到盒子	
		box.add(new InputPanel(nameLabel,name));
		box.add(new InputPanel(passwordLabel,password));
		box.add(BoxPanel);
		box.add(new InputPanel(ipLabel,ip));
		box.add(new InputPanel(ipLabel,ip,portLabel,port));
		box.add(btnPanel);
			
		//把面板添加到组件
		this.add(new JLabel(background),BorderLayout.NORTH);
		this.add(box);		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
		/* 按钮事件*/	
		//登陆按钮
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//登陆按钮响应，自定义函数
					login(name.getText(),MD5.getMD5(String.valueOf(password.getPassword())),
							ip.getText(),Integer.valueOf(port.getText()));			
			}
		});		
		//取消按钮
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}	
	/*
	 * 登陆函数
	 */
	public void login(String name,String password,String ip,int port){
		final JFrame frame = this;
		/*
		 * 连接提示框
		 */
		final JDialog dialog = new JDialog(this,"正在连接",false);
		dialog.setModal(false);
		JButton cancel = new JButton("取消");
		//提示信息
		JPanel panel = new JPanel();
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,30,0));
		p1.add(new JLabel("请稍候..."));
		p1.add(cancel);
		panel.setLayout(new GridLayout(4,1));
		panel.setLocation(100, 100);
		panel.add(new JLabel("正在连接到 :"));
		panel.add(new JLabel(ip+" : "+port));
		panel.add(p1);
		
		JLabel icon = new JLabel(new ImageIcon("images/connect.png"));
		icon.setPreferredSize(new Dimension(50,50));
		
		dialog.setSize(290, 150);
		dialog.setLocation((int)((screenSize.getWidth()-dialog.getWidth())/2),(int)(screenSize.getHeight()-dialog.getHeight())/2);
		dialog.add(icon,BorderLayout.WEST);
		dialog.add(panel);
		dialog.setVisible(true);
		//锁定登陆面板
		this.setEnabled(false);	
		final ConnectServer connectServer = new ConnectServer(this,ip,port,dialog,name,password);//自定义类
		final Thread connectThread = new Thread(connectServer);
		connectThread.start();
		//取消按钮
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectServer.distroy();
				frame.setEnabled(true);
				dialog.dispose();
			}
		});
	}
}
//信息输入面板，用于 布局的
@SuppressWarnings("serial")
class InputPanel extends JPanel{
	public InputPanel(JLabel label,JTextField textField){
		this.add(label);
		this.add(textField);
	}
	public InputPanel(JLabel label1,JTextField textField1,JLabel label2,JTextField textField2){
		this.setPreferredSize(new Dimension(390,100));
		this.add(label1);
		this.add(textField1);
		this.add(label2);
		this.add(textField2);
	}
}


