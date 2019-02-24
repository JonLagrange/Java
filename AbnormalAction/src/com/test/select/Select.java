package com.test.select;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

public class Select {
	public static final String DBDRIVER="oracle.jdbc.driver.OracleDriver";  
    public static final String DBURL="jdbc:oracle:thin:@localhost:1521:orcl";//���ݿ����ӵ�ַ��orclΪȫ�����ݿ���  
    public static final String DBUSER="c##Jon";//�û���  
    public static final String DBPASSWORD="123456";//����

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
    
     /*����tablename*/
    static String tablenameCreate(int year,int month,int day,String restr)
    {
    	String m,d,tname;
    	if(month<10)
    		m="0"+String.valueOf(month);
    	else
    		m=String.valueOf(month);
    	if(day<10)
    		d="0"+String.valueOf(day);
    	else
    		d=String.valueOf(day);
    	tname="DL_TMSAPP_"+restr+"_"+String.valueOf(year)+m+d;
		return tname;
    	
    }
    
    /*��ѯÿ���û�ִ�еĳ��򲢽����������csv�ļ��б�����һ�����ھ���*/
    @SuppressWarnings("resource")
	static void getAgentApps(Connection conn,String tablename) throws SQLException, IOException
    {
    	int i,j,k,l,rowCount,rowCountl;
    	ResultSet rs=null,rsl=null;
    	String sql=null,sqll=null;
    	sql="select distinct term_agent_id from cp_terminal_info";//��ѯ���е�termagentid
    	rs=GetdataFromDatabase(conn,sql);
    	
   		if(rs==null)
   			return;
       	rs.last();
       	rowCount=rs.getRow();
   		String termagentid[]=new String[rowCount];
   		rs.beforeFirst();
   		i=0;
   		while(rs.next())
   			//list.add(rs.getString(1));
   			termagentid[i++]=rs.getString(1);
        rs.close();
        
        for (j=0;j<termagentid.length;j++) {
        	try{ 
            	sqll="select distinct app_name from "+tablename+" where agent_id = '"+termagentid[j]+"'";  //����ÿ��termagentid��ѯ��ÿ��ִ�е����г���
            	rsl=GetdataFromDatabase(conn,sqll);
           		if(rsl==null)
           			return;
               	rsl.last();
               	rowCountl=rsl.getRow();
           		String appname[]=new String[rowCountl];
           		rsl.beforeFirst(); 
           		k=0;
           		while(rsl.next()){
           			//list.add(rs.getString(1));
           			appname[k++]=rsl.getString(1);
           		}
                rsl.close();             
            	File appfile=new File("datafile/app/",termagentid[j]+".csv");
            	//file.createNewFile();
            	OutputStream appout=new FileOutputStream(appfile,true);
                for(l=0;l<appname.length;l++){
                	appout.write(appname[l].getBytes());
                	appout.write(',');
                   	System.out.println(appname[l]);
               	}    
                appout.write('\n');
        	}catch(Exception e){
        		e.printStackTrace();}
       	}
        
        for (j=0;j<termagentid.length;j++) {
        	try{ 
            	sqll="select distinct act_time from "+tablename+" where agent_id = '"+termagentid[j]+"'";  //����ÿ��termagentid��ѯ��ÿ��ִ�е����г���
            	rsl=GetdataFromDatabase(conn,sqll);
           		if(rsl==null)
           			return;
               	rsl.last();
               	rowCountl=rsl.getRow();
           		String acttime[]=new String[rowCountl];
           		rsl.beforeFirst(); 
           		k=0;
           		while(rsl.next()){
           			//list.add(rs.getString(1));
           		    acttime[k++]=rsl.getString(1);
           		}
                rsl.close();             
            	File timefile=new File("datafile/time/",termagentid[j]+".csv");
            	//file.createNewFile();
            	OutputStream timeout=new FileOutputStream(timefile,true);
                for(l=0;l<acttime.length;l++){
                	timeout.write(acttime[l].getBytes());
                	timeout.write(',');
                   	System.out.println(acttime[l]);
               	}    
                timeout.write('\n');
        	}catch(Exception e){
        		e.printStackTrace();}
       	}
    }
     
	public static void main(String[] args) throws SQLException, ParseException, IOException {
		Connection conn=ConnectDatabase();
		String tablename=null,restr=null;
		Calendar bef = Calendar.getInstance();
		restr="C0A80006";
		//String agentid="Z4YAZWRB";
		//int daybegin=15;
    	for(int i=22;i<=24;i++)
    	{
        	tablename=tablenameCreate(bef.get(Calendar.YEAR),1,i,restr);
        	getAgentApps(conn,tablename);
        	//System.out.println("2017��1��"+i+"��");
    	}
    	for(int i=6;i<=28;i++)
    	{
        	tablename=tablenameCreate(bef.get(Calendar.YEAR),2,i,restr);
        	getAgentApps(conn,tablename);
    	}
    	for(int i=1;i<=23;i++)
    	{
        	tablename=tablenameCreate(bef.get(Calendar.YEAR),3,i,restr);
        	getAgentApps(conn,tablename);
    	}
    	for(int i=15;i<=24;i++)
    	{
        	tablename=tablenameCreate(bef.get(Calendar.YEAR),8,i,restr);
        	getAgentApps(conn,tablename);
    	}   	
        conn.close();	
        System.out.println("Finish!");	
	}
}
