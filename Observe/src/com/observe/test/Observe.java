package com.observe.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Observe{
	public static final String DBDRIVER="oracle.jdbc.driver.OracleDriver";
	public static final String DBURL="jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String DBUSER="c##Jon";
	public static final String DBPASSWORD="123456";
    static String onesysmanyaccount="DL_DXMANU_MORE_JH";
    static String oneaccountmanysys="DL_DXMANU_MORE_HJ";
	
	static Connection ConnectDatabase(){
		Connection conn=null;
		try{
			Class.forName(DBDRIVER);
			System.out.println("Success loading Oracle Driver!");
		}catch(Exception e){
			System.out.println("Error loading Driver!");
			e.printStackTrace();
		}
		try{
			conn=DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			if(conn!=null)
			    System.out.println("Success connect database!");
		}catch(SQLException e){
			System.out.println("Error connect database!");
			e.printStackTrace();
		}
		return conn;
	}
	
    /*构造tablename*/
   static String tablenameCreate(int year,int month,String restr){
    	String m,tname;
    	if(month<10)
   	    	m="0"+String.valueOf(month);
    	else
   	    	m=String.valueOf(month);
    	tname="DL_DXMANU_"+restr+"_"+String.valueOf(year)+m;
	    return tname;
   	
   }
   
   /*执行sql语句*/
   static ResultSet GetdataFromDatabase(Connection conn,String sql) throws SQLException{
	   
   	   System.out.print(sql+"\n");
       java.sql.Statement statement = null;  
   	   statement  = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
   	   ResultSet rs = statement.executeQuery(sql);
       return rs;        	
   }
   
   //寻找异常操作序列
   static String Findmorehj(String usr) throws SQLException, ParseException
   {   
	   Connection conn=ConnectDatabase();   
	   String sql="select sysid,username,occurtime from "+oneaccountmanysys+" where username='"+usr+"'";
       ResultSet rs=GetdataFromDatabase(conn,sql);
	   //System.out.println(user);
       String str="";
	   if(rs==null)
			return null;
	    	       	
	   rs.last();
	   int rowCount=rs.getRow();
	   rs.beforeFirst();  
	   ArrayList<String> list1=new ArrayList<String>();//创建取结果的列表
	   ArrayList<String> list2=new ArrayList<String>();//创建取结果的列表
	   while(rs.next()){	   
    	   list1.add(rs.getString(1));
    	   list2.add(rs.getString(2));
    	   //user[i++]=rs.getString(2);
    	   //event[i++]=rs.getInt(3);
       }
	   rs.close();
	   if(list1!= null && list2!=null && list1.size()>0 && list2.size()>0){//如果list中存入了数据，转化为数组
		   
           String[] sys=new String[list1.size()];//创建一个和list长度一样的数组
           String[] user=new String[list2.size()];
           for(int i=0;i<list1.size();i++){
        	   sys[i]=list1.get(i);//数组赋值了。
           }
           for(int i=0;i<list2.size();i++){
        	   user[i]=list2.get(i);//数组赋值了。
           }
           
           for(int i=0;i<user.length;i++){
        	   str=str+sys[i]+" ";
           }
	   }
	   return str;
   }
   
   //寻找异常操作序列
   static String Findmorejh(String sid) throws SQLException, ParseException
   {   
	   Connection conn=ConnectDatabase();   
	   String sql="select sysid,username,occurtime from "+onesysmanyaccount+" where sysid='"+sid+"'";
       ResultSet rs=GetdataFromDatabase(conn,sql);
	   //System.out.println(user);
       String str="";
	   if(rs==null)
			return null;
	    	       	
	   rs.last();
	   int rowCount=rs.getRow();
	   rs.beforeFirst();  
	   ArrayList<String> list1=new ArrayList<String>();//创建取结果的列表
	   ArrayList<String> list2=new ArrayList<String>();//创建取结果的列表
	   while(rs.next()){	   
    	   list1.add(rs.getString(1));
    	   list2.add(rs.getString(2));
    	   //user[i++]=rs.getString(2);
    	   //event[i++]=rs.getInt(3);
       }
	   rs.close();
	   if(list1!= null && list2!=null && list1.size()>0 && list2.size()>0){//如果list中存入了数据，转化为数组
		   
           String[] sys=new String[list1.size()];//创建一个和list长度一样的数组
           String[] user=new String[list2.size()];
           for(int i=0;i<list1.size();i++){
        	   sys[i]=list1.get(i);//数组赋值了。
           }
           for(int i=0;i<list2.size();i++){
        	   user[i]=list2.get(i);//数组赋值了。
           }
           
           for(int i=0;i<sys.length;i++){
        	   str=str+user[i]+" ";
           }
	   }
	   return str;
   }
	      
   static void Insert(String tablename,String datenowstr,String restr) throws SQLException, ParseException{
	   
	   Connection conn=ConnectDatabase();
       Statement statement=conn.createStatement();
       String sql1=null,sql2=null;

       sql1="insert into "+onesysmanyaccount+" select sysid,username,occurtime from "+tablename+" where sysid in (select sysid from "+tablename+" where to_char(OCCURTIME,'YYYY-MM-DD')='"+datenowstr+"' group by sysid having count(sysid)>1) order by sysid";
       sql2="insert into "+oneaccountmanysys+" select sysid,username,occurtime from "+tablename+" where username in (select username from "+tablename+" where to_char(OCCURTIME,'YYYY-MM-DD')='"+datenowstr+"' group by username having count(username)>1) order by username";
       //System.out.println(sql1);
       //System.out.println(sql2);
       
       statement.executeQuery(sql1);	
       statement.executeQuery(sql2);

	   
	   String sql3="select sysid,username,occurtime from "+onesysmanyaccount+"";
	   String sqll="select sysid,username,occurtime from "+oneaccountmanysys+"";
	   ResultSet rs=GetdataFromDatabase(conn,sql3);
	   ResultSet rsl=GetdataFromDatabase(conn,sqll);
		if(rsl==null)
			return;
 
    	rsl.last();
    	int rowCount=rsl.getRow();
		String user[]=new String[rowCount];
		rsl.beforeFirst();  
		int i=0;
		while(rsl.next()) 
			user[i++]=rsl.getString(2);
        rsl.close();        
        
      //寻找异常数据
      //while(i<sys.length)
      for(i=0;i<user.length;i++)
      {
          String sql4="insert into DL_DXMANU_ABNORMALHJ(SYSID,USERNAME,OCCURTIME) values ('"+Findmorehj(user[i])+"','"+user[i]+"','"+datenowstr+"')";
       	  statement.executeQuery(sql4); 
      }
      
		if(rs==null)
			return;
 
    	rs.last();
    	int rCount=rs.getRow();
		String sys[]=new String[rCount];
		rs.beforeFirst();  
		i=0;
		while(rs.next()) 
			sys[i++]=rs.getString(1);
        rs.close();        
        
      //寻找异常数据
      //while(i<sys.length)
      for(i=0;i<sys.length;i++)
      {
          String sql5="insert into DL_DXMANU_ABNORMALJH(SYSID,USERNAME,OCCURTIME) values ('"+sys[i]+"','"+Findmorejh(sys[i])+"','"+datenowstr+"')";
       	  statement.executeQuery(sql5); 
      }
      statement.close();
   }
   
   public static void main(String[] args)throws Exception{
		//OneSysManyAccount();
        Connection conn=ConnectDatabase();
        Statement statement=conn.createStatement();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar bef = Calendar.getInstance();//此时打印它获取的是系统当前时间
        
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datenowstr= sdf.format(bef.getTime());       
		String restr=null,tablename=null,sqlhj=null,sqljh=null;
		restr="44010001";
		tablename=tablenameCreate(bef.get(Calendar.YEAR),bef.get(Calendar.MONTH)+1,restr);
    	Insert(tablename,datenowstr,restr);    
    	sqlhj="insert into DL_DXMANU_HJ select distinct * from DL_DXMANU_ABNORMALHJ";
    	sqljh="insert into DL_DXMANU_JH select distinct * from DL_DXMANU_ABNORMALJH";
    	statement.executeQuery(sqlhj);
    	statement.executeQuery(sqljh);
    	statement.close();
	}
}
