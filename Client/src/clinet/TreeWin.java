package clinet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cn.mike.util.FileType;
import cn.mike.util.myfile;
import cn.mike.util.renderer;
import cn.mike.util.treenodedata;

public class TreeWin extends JFrame implements TreeSelectionListener{	
	private static final long serialVersionUID = 1L;
	public static String downfilename;
	private String username;
	private JPopupMenu popup = null; 
	private JPopupMenu popup1 = null; 
	private JTree tree;
	TreeWin(String username){
		this.username=username; 
	}
	public JTree getFolderTree(List<myfile> files){
		//������û���Ŀ¼������,����д���
		File homeFolder = new File(username);//����һ��File����homeFolder
		if(!homeFolder.exists())
			homeFolder.mkdir();
		DefaultMutableTreeNode home = new DefaultMutableTreeNode(new treenodedata(FileType.HOME, "�ҵ��ļ�"));
		if(files.size()!=0){
			for(myfile file : files)
			{
				home.add(new DefaultMutableTreeNode(new treenodedata(file.getFileType(), file.getName())));
				
			}   //files��һ������
		}
		tree = new JTree(home);	
		tree.addTreeSelectionListener(this); 
		tree.addMouseListener(new MouseAdapter() {  	            
	            public void mouseClicked(MouseEvent e) { 
	            	DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
                    if(e.getButton() == MouseEvent.BUTTON3&&selectionNode!=null){
	                     if(selectionNode.isLeaf()&&selectionNode.toString()!="�½��ļ���"){
	                        getPopup().show(e.getComponent(),  
	                                   e.getX(), e.getY());  
	                        System.out.println("��ѡ��Ҷ����");
	                     }
	                     else if(selectionNode.isRoot()){
		                     Popup().show(e.getComponent(),  
	                                   e.getX(), e.getY());
	                     }
                     } 	                                     
                }  
            });   
		tree.setCellRenderer(new renderer());
		tree.setPreferredSize(new Dimension(180,540));
		return tree;
	}
	public JPopupMenu Popup(){
		if(popup1 == null) {
			popup1 = new JPopupMenu("Popup1"); 
			JMenuItem item  = new JMenuItem("�½��ļ���"); 
			DefaultTreeModel model=(DefaultTreeModel)tree.getModel();
			myfile folder=new myfile("�½��ļ���",FileType.FOLDER);
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("�½��ļ���");
			item.addActionListener(new ActionListener() {               
				public void actionPerformed(ActionEvent e) { 
					System.out.println("�½��ļ���");
					ConnectServer.files.add(folder);
					ConnectServer.filetype.add(FileType.FOLDER);
					model.insertNodeInto(newNode,selectedNode,ConnectServer.filename.size());
					ConnectServer.filename.add("�½��ļ���");
				}
			});
			popup1.add(item);
		}
		return popup1;		
	}
	public JPopupMenu getPopup() {  
        if(popup == null) {  
            popup = new JPopupMenu("Popup");  
            System.out.println("���Ҽ�tree�ڵ���");
            JMenuItem item1  = new JMenuItem("�������ļ�"); 
            item1.addActionListener(new ActionListener() {               
				public void actionPerformed(ActionEvent e) { 
                	DefaultMutableTreeNode nowNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                	TreePath path = tree.getSelectionPath();  
                	 if (path != null)  
                     {   
                		 String oldName=nowNode.toString();	  
                		 tree.setEditable(true); 
                		 String newNodeName=(String)JOptionPane.showInputDialog(null,"�������µĽڵ�����","������",JOptionPane.OK_CANCEL_OPTION);                		
                         nowNode.setUserObject(newNodeName);
                         for(int i=0;i<ConnectServer.filename.size();i++)
                         {
                        	 if(oldName==ConnectServer.filename.get(i)){	                        		 
                        		 ConnectServer.filename.set(i,newNodeName);
                        	 }
                         }
                         DefaultTreeModel model = (DefaultTreeModel) tree.getModel();  
                         model.nodeStructureChanged(nowNode);                          
                     } 
                	System.out.println("����������:"+tree.getSelectionPath());
                	          
                }  
            });  
            popup.add(item1);  
            DefaultTreeModel model=(DefaultTreeModel)tree.getModel(); 
            JMenuItem item2  = new JMenuItem("ɾ���ļ�");  
            item2.addActionListener(new ActionListener() {  
              		public void actionPerformed(ActionEvent e) {
              		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();	            		
                	if(selectedNode != null){  
                		if(selectedNode.isRoot()){
                			model.setRoot(null);
                		}
                		else{
                			System.out.println("����Ҫɾ�����ļ���:"+selectedNode.toString());		             		                			                		
	                		for(int i=0;i<ConnectServer.filename.size();i++){       						
	        					if(ConnectServer.filename.get(i).equals(selectedNode.toString())){
	        						ConnectServer.files.remove(i);
	        						ConnectServer.filename.remove(i);
	        						ConnectServer.filetype.remove(i);
	        						model.removeNodeFromParent(selectedNode);	        					
	        						System.out.println("ɾ�����ļ����ļ����ܸ���:"+ConnectServer.files.size()+"++"+ConnectServer.filetype.size()+"++"+ConnectServer.filename.size());
	        					}	        					
	        				}
	                		selectedNode=null;
                		}
                	}       	                
                }  
            });  
            popup.add(item2);
            JMenuItem item3  = new JMenuItem("�ƶ����ļ���"); 
            item3.addActionListener(new ActionListener() {  
          		public void actionPerformed(ActionEvent e) {
          			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();   		
          			if(selectedNode != null){  
          				for(int i=0;i<ConnectServer.filename.size();i++){
          					if(ConnectServer.filename.get(i).equals("�½��ļ���")){
          						DefaultMutableTreeNode node = (DefaultMutableTreeNode) model.getChild(model.getRoot(), i);
          						model.removeNodeFromParent(selectedNode);
          						model.insertNodeInto(selectedNode,node,0);
          					}
          				}
          				for(int i=0;i<ConnectServer.filename.size();i++){
	          				if(ConnectServer.filename.get(i).equals(selectedNode.toString())){
	      						ConnectServer.files.add(ConnectServer.files.get(i));
	      						ConnectServer.files.remove(i);
	      						ConnectServer.filename.add(ConnectServer.filename.get(i));
	    						ConnectServer.filename.remove(i);
	    						ConnectServer.filetype.add(ConnectServer.filetype.get(i));
	    						ConnectServer.filetype.remove(i);
	      					}
          				}         				
          			}
          		}
            });
            popup.add(item3);   
        }  
        return popup;  
    }  
	public void valueChanged(TreeSelectionEvent tse){
		  DefaultMutableTreeNode selectionNode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		  if(selectionNode!=null){
			  if(selectionNode.isLeaf()){ //Ҷ�ӽڵ�ļ���
				  for(int i=0;i<ConnectServer.filename.size();i++){
					  if(selectionNode.toString()==ConnectServer.filename.get(i))
						  downfilename=ConnectServer.filename.get(i)+"."+ConnectServer.filetype.get(i);
				  }
				  System.out.println("�ҵ�����ļ���:"+downfilename);
			  }
			  else{
				  selectionNode=null;
				  System.out.println("�ҵ������:��Ŀ¼");
			  }
		  }
		 }
}	
