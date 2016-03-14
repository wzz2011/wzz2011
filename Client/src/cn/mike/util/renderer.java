package cn.mike.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import clinet.ConnectServer;

public class renderer extends DefaultTreeCellRenderer{
	private static final long serialVersionUID = 1L;
	ImageIcon home = new ImageIcon("images/folder_image/home.gif");
	ImageIcon folder = new ImageIcon("images/folder_image/folder.gif");
	ImageIcon txt = new ImageIcon("images/folder_image/txt.gif");
	ImageIcon excel = new ImageIcon("images/folder_image/excel.gif");
	ImageIcon word = new ImageIcon("images/folder_image/word.gif");
	ImageIcon pdf = new ImageIcon("images/folder_image/pdf.jpg");
	ImageIcon ppt = new ImageIcon("images/folder_image/ppt.jpg");
	public int i=0;
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {		
				System.out.println("调用renderer次数"+row);
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,row, hasFocus);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
				ImageIcon icon = null;
				if(node.toString()!=null){
			if(row==0){				
				icon = home;
				JButton jb=new JButton(icon);
				this.add(jb);
				this.setIcon(icon);
			}
			else if(row>0&&row<=ConnectServer.files.size()){
				System.out.println("文件和文件夹总个数:"+ConnectServer.files.size()+"+"+ConnectServer.filetype.size()+"+"+ConnectServer.filename.size());
				treenodedata nodeData=new treenodedata(ConnectServer.filetype.get(row-1), ConnectServer.filename.get(row-1));				
				if(nodeData.getNodeType().equals(FileType.HOME))
					icon = home;
				else if(nodeData.getNodeType().equals(FileType.FOLDER))
					icon = folder;
				else if(nodeData.getNodeType().equals(FileType.TXT))
					icon = txt;
				else if(nodeData.getNodeType().equals(FileType.EXCEL))
					icon = excel;
				else if(nodeData.getNodeType().equals(FileType.WORD2003))
					icon = word;
				else if(nodeData.getNodeType().equals(FileType.PDF))
					icon = pdf;
				else if(nodeData.getNodeType().equals(FileType.PPT))
					icon = ppt;
					JButton jb=new JButton(icon);
					this.add(jb);
					this.setIcon(icon);
			}
		}		
		return this;
	}
}
