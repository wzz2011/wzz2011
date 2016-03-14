package clinet;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import cn.mike.util.myfile;

@SuppressWarnings("serial")
public class MyDisk extends JFrame{
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
	public MyDisk(String username,String sessionID,List<myfile> files,String ip,int port){
		this.setTitle("�Ϸʳ�����");
		this.setSize(1000, 700);
		this.setLocation((int)((screenSize.getWidth()-this.getWidth())/2),(int)(screenSize.getHeight()-this.getHeight())/2);
		this.setLayout(new BorderLayout(10, 10));
		try {
			//�������
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//����
		JPanel topPanel = new JPanel();
		JPanel topRightPanel = new JPanel();
		//��ӵ�½��Ϣ��
		topRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT,30, 5));
		topRightPanel.add(new JLabel("��ӭ��"+username));
		topRightPanel.add(new JLabel("�ϴε�½ʱ�䣺"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		try {
			topRightPanel.add(new JLabel("��ǰIP��"+InetAddress.getLocalHost().getHostAddress()));
		} catch (UnknownHostException e) {
			topRightPanel.add(new JLabel("��ǰIP����ȡʧ��"));
		}
		//��ť
		JButton uploadBtn = new JButton("��  ��");
		JButton downloadBtn = new JButton("��  ��");
		topRightPanel.add(uploadBtn);
		topRightPanel.add(downloadBtn);		
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10, 5));
		topPanel.add(new JLabel(new ImageIcon("images/logo.jpg")));
		topPanel.add(topRightPanel);
		//�ײ�
		JPanel buttomPanel = new JPanel();	
		TreeWin win = new TreeWin(username);//�ļ�������������¼�
		JPanel mianPanel = new JPanel();//�в����
		JPanel rightPanel = new JPanel();//���Ҳ����
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(win.getFolderTree(files)), rightPanel) ;
		splitPane.setOneTouchExpandable(true);
		mianPanel.add(splitPane);

		rightPanel.add(new JLabel("��ӭʹ�� �Ϸʳ���������"));
		buttomPanel.add(new JLabel("������� �Ϸʳ����ƹ�˾����"));
		topPanel.setPreferredSize(new Dimension(1000,40));
		rightPanel.setPreferredSize(new Dimension(800,540));
		mianPanel.setPreferredSize(new Dimension(1000,540));
		buttomPanel.setPreferredSize(new Dimension(1000,20));
		mianPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
		this.add(topPanel,BorderLayout.NORTH);
		this.add(mianPanel,BorderLayout.WEST);
		this.add(buttomPanel,BorderLayout.SOUTH);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		uploadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {		
				new UpLoad(ip,port,sessionID);			
			}
		});
		downloadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				new DownLoad(ip,port,sessionID,TreeWin.downfilename);
			}
		});
	}
	public void diopose(){
		this.dispose();
	}
}
	


