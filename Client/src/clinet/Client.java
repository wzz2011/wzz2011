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
		new LoginFrame("�Ϸʳ�����");
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
	
	//��ȡ��Ļ��С,��λ����
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//��½��ť��
	public LoginFrame(String frameTitle){
		this.setTitle(frameTitle);
		this.setSize(400, 325);
		this.setLocation((int)((screenSize.getWidth()-this.getWidth())/2),(int)(screenSize.getHeight()-this.getHeight())/2);	
		//���
		JPanel btnPanel = new JPanel();
		JPanel BoxPanel = new JPanel();	
		//����
		Box box = Box.createVerticalBox();	
		//LOGOͼƬ
		ImageIcon background = new ImageIcon("images/clientLoginLogo.jpg");		
		//��Ϣ�����
		JLabel nameLabel = new JLabel("�û���:");
		JLabel passwordLabel = new JLabel("��    ��:");
		JLabel ipLabel = new JLabel("������IP:");
		JLabel portLabel = new JLabel("�������˿�:");	
		name = new JTextField(12);
		password = new JPasswordField(12);
		ip = new JTextField(8);
		port = new JTextField(8);
		
		//��ѡ��
		autocb=new JCheckBox("�Զ���¼");
		memorycb=new JCheckBox("��ס����");
		BoxPanel.add(autocb);
		BoxPanel.add(memorycb);

		//��ť
		loginBtn = new JButton("��    ½");
		cancelBtn = new JButton("ע     ��");
		btnPanel.add(loginBtn);
		btnPanel.add(cancelBtn);
		
		//��ӵ�����	
		box.add(new InputPanel(nameLabel,name));
		box.add(new InputPanel(passwordLabel,password));
		box.add(BoxPanel);
		box.add(new InputPanel(ipLabel,ip));
		box.add(new InputPanel(ipLabel,ip,portLabel,port));
		box.add(btnPanel);
			
		//�������ӵ����
		this.add(new JLabel(background),BorderLayout.NORTH);
		this.add(box);		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
		/* ��ť�¼�*/	
		//��½��ť
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//��½��ť��Ӧ���Զ��庯��
					login(name.getText(),MD5.getMD5(String.valueOf(password.getPassword())),
							ip.getText(),Integer.valueOf(port.getText()));			
			}
		});		
		//ȡ����ť
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}	
	/*
	 * ��½����
	 */
	public void login(String name,String password,String ip,int port){
		final JFrame frame = this;
		/*
		 * ������ʾ��
		 */
		final JDialog dialog = new JDialog(this,"��������",false);
		dialog.setModal(false);
		JButton cancel = new JButton("ȡ��");
		//��ʾ��Ϣ
		JPanel panel = new JPanel();
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,30,0));
		p1.add(new JLabel("���Ժ�..."));
		p1.add(cancel);
		panel.setLayout(new GridLayout(4,1));
		panel.setLocation(100, 100);
		panel.add(new JLabel("�������ӵ� :"));
		panel.add(new JLabel(ip+" : "+port));
		panel.add(p1);
		
		JLabel icon = new JLabel(new ImageIcon("images/connect.png"));
		icon.setPreferredSize(new Dimension(50,50));
		
		dialog.setSize(290, 150);
		dialog.setLocation((int)((screenSize.getWidth()-dialog.getWidth())/2),(int)(screenSize.getHeight()-dialog.getHeight())/2);
		dialog.add(icon,BorderLayout.WEST);
		dialog.add(panel);
		dialog.setVisible(true);
		//������½���
		this.setEnabled(false);	
		final ConnectServer connectServer = new ConnectServer(this,ip,port,dialog,name,password);//�Զ�����
		final Thread connectThread = new Thread(connectServer);
		connectThread.start();
		//ȡ����ť
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectServer.distroy();
				frame.setEnabled(true);
				dialog.dispose();
			}
		});
	}
}
//��Ϣ������壬���� ���ֵ�
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


