package com.test.fptree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.test.fptree.FPTree;
public class RunFPTree {
	public static final String DBDRIVER="oracle.jdbc.driver.OracleDriver";  
    public static final String DBURL="jdbc:oracle:thin:@localhost:1521:orcl";//���ݿ����ӵ�ַ��orclΪȫ�����ݿ���  
    public static final String DBUSER="c##Jon";//�û���  
    public static final String DBPASSWORD="123456";//����

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

	public static void main(String[] args) throws IOException, SQLException {
		// TODO Auto-generated method stub	
		Connection conn=ConnectDatabase();	
		LinkedList<LinkedList<String>> records=new LinkedList<LinkedList<String>>();
		//String filePath="scripts/clustering/canopy/canopy.dat";
		int i,j,k,rowCount;
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
			FileReader fr=new FileReader("datafile/app/"+termagentid[i]+".csv");
			BufferedReader br = new BufferedReader(fr);
			int rowNum=0;
	        for (String line = br.readLine(); line != null; line = br.readLine()){
	            if(line.length()==0||"".equals(line))continue;
	        	String[] str=line.split(",");   
	        	LinkedList<String> litm=new LinkedList<String>();
	        	for(j=0;j<str.length;j++){
	        		litm.add(str[j].trim());
	        	}
	            rowNum++;
	            records.add(litm);             
	        }
	        br.close();
	        //��ȡ����
	        if(rowNum==0){
	        	System.out.println("�û�"+termagentid[i]+"����59����δִ���κγ���");        	
	        }
	        else{
	        	System.out.println("�û�"+termagentid[i]+"����59����ִ�г��������Ϊ"+rowNum+"�죬ʹ����Ƶ���ĳ���Ϊ��");        
				for(k=59;k>=0;k--){
			        FPTree fpg=new FPTree();
			        //LinkedList<LinkedList<String>> records=fpg.readF1(conn);				
					fpg.support=k;
					//System.out.println(fpg.support);
					if(fpg.support<rowNum){
				        LinkedList<TreeNode> orderheader=fpg.buildHeaderLink(records);		
						fpg.orderF1(orderheader);					
				        fpg.fpgrowth(records,null,0);
					}
				}
	        }
		}	
		/*String s1="i1";
		int flag=s1.compareTo("I1");
		System.out.println(flag);*/
        System.out.println("Finish!");
	}
}
