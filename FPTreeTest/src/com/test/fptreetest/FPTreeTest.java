package com.test.fptree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FPTree {
	public static final String DBDRIVER="oracle.jdbc.driver.OracleDriver";  
    public static final String DBURL="jdbc:oracle:thin:@localhost:1521:orcl";//���ݿ����ӵ�ַ��orclΪȫ�����ݿ���  
    public static final String DBUSER="c##Jon";//�û���  
    public static final String DBPASSWORD="123456";//����
	public static final int  support = 7; // �趨��С֧������ֵ
	//�����һ�εĴ���
	public Map<String,Integer> ordermap=new HashMap<String,Integer>();
	
	/*�������ݿ�*/
    static Connection ConnectDatabase() 
		{
			Connection conn = null;       
			try{
					Class.forName(DBDRIVER);
					System.out.println("Success loading Oracle Driver!");
					
			}catch (Exception e) {
			   System.out.println("Error loading Driver!");
			   e.printStackTrace();
			}
			try{
				conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
				if(conn!=null)
					 System.out.println("Success connect database!");
				
			}catch(SQLException e) {
			    System.out.println("Error connect database!");
			    e.printStackTrace();
			}
			return conn;  
		}
    
    /*ִ��sql���*/
    static ResultSet GetdataFromDatabase(Connection conn,String sql) throws SQLException
    {
    	 System.out.print(sql+"\n");
    	 java.sql.Statement statement = null;  
    	 statement  = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
    	 ResultSet rs = statement.executeQuery(sql);
         return rs;        	
    }
	
	@SuppressWarnings("unused")
	public LinkedList<LinkedList<String>> readF1(Connection conn) throws IOException, SQLException {      
		LinkedList<LinkedList<String>> records=new LinkedList<LinkedList<String>>();
		//String filePath="scripts/clustering/canopy/canopy.dat";
		int i,j,rowCount;
    	String sql="select distinct term_agent_id from cp_terminal_info";
    	ResultSet rs=GetdataFromDatabase(conn,sql);
    	
       	rs.last();
       	rowCount=rs.getRow();
   		String termagentid[]=new String[rowCount];
   		rs.beforeFirst();
   		i=0;
   		while(rs.next())
   			//list.add(rs.getString(1));
   			termagentid[i++]=rs.getString(1);
        rs.close();
		for(i=0;i<termagentid.length;i++){
			FileReader fr=new FileReader("F:/test/"+termagentid[i]+".csv");
			BufferedReader br = new BufferedReader(fr);
	        for (String line = br.readLine(); line != null; line = br.readLine()) {
	            if(line.length()==0||"".equals(line))continue;
	        	String[] str=line.split(",");   
	        	LinkedList<String> litm=new LinkedList<String>();
	        	for(j=0;j<str.length;j++){
	        		litm.add(str[j].trim());
	        	}
	            records.add(litm);             
	        }
	        br.close();
	        return records;
		}
		return records;
		
		/*String filePath="datafile/association/useritems.csv";
		BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if(line.length()==0||"".equals(line))continue;
        	String[] str=line.split(",");   
        	LinkedList<String> litm=new LinkedList<String>();
        	for(int i=0;i<str.length;i++){
        		litm.add(str[i].trim());
        	}
            records.add(litm);             
        }
        br.close();
        return records;*/
    }
	//������ͷ��
	public LinkedList<TreeNode> buildHeaderLink(LinkedList<LinkedList<String>> records){
		LinkedList<TreeNode> header=null;
		if(records.size()>0){
			header=new LinkedList<TreeNode>();
		}else{
			return null;
		}
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		for(LinkedList<String> items:records){
			
			for(String item:items){
				//�������������1��������������
				if(map.containsKey(item)){
					map.get(item).Sum(1);
				}else{
					TreeNode node=new TreeNode();
					node.setName(item);
					node.setCount(1);
					map.put(item, node);
				}
             }
		}
		 // ��֧�ֶȴ��ڣ�����ڣ�minSup������뵽F1��
        Set<String> names = map.keySet();
        for (String name : names) {
            TreeNode tnode = map.get(name);
            if (tnode.getCount() >= support) {
            	header.add(tnode);
            }
        }
        sort(header);
		
        String test="ddd";
		return header;
	}
	//ѡ������,���������ȣ�����������,�ֵ�˳��,��Сд���д
	public List<TreeNode> sort(List<TreeNode> list){
		int len=list.size();
		for(int i=0;i<len;i++){
			
			for(int j=i+1;j<len;j++){
				TreeNode node1=list.get(i);
				TreeNode node2=list.get(j);
				if(node1.getCount()<node2.getCount()){
					TreeNode tmp=new TreeNode();
					tmp=node2;
					list.remove(j);
					//listָ��λ�ò��룬ԭ����>=jԪ�ض��������ƣ�����ɾ��,���Բ���ǰҪɾ����ԭ����Ԫ��
					list.add(j,node1);
					list.remove(i);
					list.add(i,tmp);
				}
				//���������ȣ�����������,�ֵ�˳��,��Сд���д
				if(node1.getCount()==node2.getCount()){
					String name1=node1.getName();
					String name2=node2.getName();
					int flag=name1.compareTo(name2);
					if(flag>0){
						TreeNode tmp=new TreeNode();
						tmp=node2;
						list.remove(j);
						//listָ��λ�ò��룬ԭ����>=jԪ�ض��������ƣ�����ɾ��,���Բ���ǰҪɾ����ԭ����Ԫ��
						list.add(j,node1);
						list.remove(i);
						list.add(i,tmp);
					}
					

				}
			}
		}
		
		return list;
	}
	//ѡ�����򣬽���,���ͬ����L �еĴ�������
	public   List<String> itemsort(LinkedList<String> lis,List<TreeNode> header){
		//List<String> list=new ArrayList<String>();
		//ѡ������
		int len=lis.size();
		for(int i=0;i<len;i++){
			for(int j=i+1;j<len;j++){
				String key1=lis.get(i);
				String key2=lis.get(j);
				Integer value1=findcountByname(key1,header);
				if(value1==-1)continue;
				Integer value2=findcountByname(key2,header);
				if(value2==-1)continue;
				if(value1<value2){
					String tmp=key2;
					lis.remove(j);
					lis.add(j,key1);
					lis.remove(i);
					lis.add(i,tmp);
				}
				if(value1==value2){
					int v1=ordermap.get(key1);
					int v2=ordermap.get(key2);
					if(v1>v2){
						String tmp=key2;
						lis.remove(j);
						lis.add(j,key1);
						lis.remove(i);
						lis.add(i,tmp);
					}
				}
		     }
		}
		return lis;
	}
	public Integer findcountByname(String itemname,List<TreeNode> header){
		Integer count=-1;
		for(TreeNode node:header){
			if(node.getName().equals(itemname)){
				count= node.getCount();
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @param records �������ļ�¼,��I1,I2,I3
	 * @param header �����н��ܵı�ͷ
	 * @return ���ع����õ���
	 */
	public TreeNode builderFpTree(LinkedList<LinkedList<String>> records,List<TreeNode> header){
		
		   TreeNode root;
		   if(records.size()<=0){
			   return null;
		   }
		   root=new TreeNode();
		   for(LinkedList<String> items:records){
			   itemsort(items,header);
			  addNode(root,items,header);
			}
		String dd="dd";	
		String test=dd;
		return root;
	}
	//���Ѿ��з�֦���ڵ�ʱ���ж������Ľڵ��Ƿ����ڸ÷�֦��ĳ���ڵ㣬��ȫ���غϣ��ݹ�
	public  TreeNode addNode(TreeNode root,LinkedList<String> items,List<TreeNode> header){
		if(items.size()<=0)return null;
		String item=items.poll();
		//��ǰ�ڵ�ĺ��ӽڵ㲻�����ýڵ㣬��ô���ⴴ��һ֧��֧��
		TreeNode node=root.findChild(item);
		if(node==null){
            node=new TreeNode();
			node.setName(item);
			node.setCount(1);
			node.setParent(root);
			root.addChild(node);
			
			//�ӽ������ڵ�ӵ���ͷ�� 
			for(TreeNode head:header){
				if(head.getName().equals(item)){
					while(head.getNextHomonym()!=null){
						head=head.getNextHomonym();
					}
					head.setNextHomonym(node);
					break;
				}
			}
			//�ӽ������ڵ�ӵ���ͷ��
		}else{
			node.setCount(node.getCount()+1);
		}
 
		addNode(node,items,header);
		return root;
	}
	//��Ҷ���ҵ����ڵ㣬�ݹ�֮
	public void toroot(TreeNode node,LinkedList<String> newrecord){
		if(node.getParent()==null)return;
		String name=node.getName();
		newrecord.add(name);
		toroot(node.getParent(),newrecord);
	}
	//������FP-tree��������ϣ������Ƶ���
	public void combineItem(TreeNode node,LinkedList<String> newrecord,String Item){
		if(node.getParent()==null)return;
		String name=node.getName();
		newrecord.add(name);
		toroot(node.getParent(),newrecord);
	}
	//fp-growth
	public void fpgrowth(LinkedList<LinkedList<String>> records,String item,int c){
		//�����µ�����ģʽ���ĸ�����¼�������¹���FP-tree
		LinkedList<LinkedList<String>> newrecords=new LinkedList<LinkedList<String>>();
		//������ͷ
		LinkedList<TreeNode> header=buildHeaderLink(records);
		//����FP-Tree
		TreeNode fptree= builderFpTree(records,header);
		//�����ݹ������
		if(header.size()<=0||fptree==null){
			//System.out.println("-----------------");
			return;
		}
		//��ӡ���,���Ƶ���
		if(item!=null){
			//Ѱ������ģʽ��,����β��ʼ
			for(int i=header.size()-1;i>=0;i--){
				TreeNode head=header.get(i);
				String itemname=head.getName();
				Integer count=0;
				while(head.getNextHomonym()!=null){
					head=head.getNextHomonym();
					//Ҷ��count���ڶ��٣������������¼
					count=count+head.getCount();
					
				}
				//��ӡƵ���
				System.out.println(head.getName()+","+item+"\t"+count);
				//if(c>5)
    				//System.out.println(item+"\t"+c);
			}
		}
		//Ѱ������ģʽ��,����β��ʼ
		for(int i=header.size()-1;i>=0;i--){
			TreeNode head=header.get(i);
			String itemname;
			int c1=c;
			//�����
			if(item==null){
				itemname=head.getName();
			}else{
				itemname=head.getName()+","+item;
			}
			c1++;
			while(head.getNextHomonym()!=null){
				head=head.getNextHomonym();
				//Ҷ��count���ڶ��٣������������¼
				Integer count=head.getCount();
				for(int n=0;n<count;n++){
				   LinkedList<String> record=new LinkedList<String>();
				   toroot(head.getParent(),record);
				   newrecords.add(record);
				}
			}
			//System.out.println("-----------------");
			//�ݹ�֮,������FP-Tree
			fpgrowth(newrecords,itemname,c1);
		}
    }
	//������򣬴˲�Ҳ����ʡ�ԣ�Ϊ�˼����ټӹ�������鷳����
	public void orderF1(LinkedList<TreeNode> orderheader){
		for(int i=0;i<orderheader.size();i++){
			TreeNode node=orderheader.get(i);
			ordermap.put(node.getName(), i);
		}
		
	}
	public static void main(String[] args) throws IOException, SQLException {
		Connection conn=ConnectDatabase();
		// TODO Auto-generated method stub
		/*String s1="i1";
		int flag=s1.compareTo("I1");
		System.out.println(flag);*/
		//��ȡ����
		FPTree fpg=new FPTree();
		LinkedList<LinkedList<String>> records=fpg.readF1(conn);
		LinkedList<TreeNode> orderheader=fpg.buildHeaderLink(records);
		//fpg.orderF1(orderheader);
        fpg.fpgrowth(records,null,0);
        System.out.println("Finish!");
	}

}
