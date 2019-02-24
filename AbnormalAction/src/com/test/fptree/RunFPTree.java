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
    public static final String DBURL="jdbc:oracle:thin:@localhost:1521:orcl";//数据库连接地址，orcl为全局数据库名  
    public static final String DBUSER="c##Jon";//用户名  
    public static final String DBPASSWORD="123456";//密码

	//保存第一次的次序
	public Map<String,Integer> ordermap=new HashMap<String,Integer>();
	
	/*连接数据库*/
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
    
    /*执行sql语句*/
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
	        //读取数据
	        if(rowNum==0){
	        	System.out.println("用户"+termagentid[i]+"在这59天里未执行任何程序！");        	
	        }
	        else{
	        	System.out.println("用户"+termagentid[i]+"在这59天里执行程序的天数为"+rowNum+"天，使用最频繁的程序为：");        
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
