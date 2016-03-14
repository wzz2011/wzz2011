package cn.mike.util;

public class treenodedata{
	/*
	 * ÍøÅÌÄ¿Â¼½Úµã
	 */
	private String nodeType;
	private String nodeName;
	
	public treenodedata(String nodeType,String nodeName){
		this.nodeType = nodeType;
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}

	public String getNodeName() {
		return nodeName;
	}

	public String toString(){
		return nodeName;
	}
	
}
