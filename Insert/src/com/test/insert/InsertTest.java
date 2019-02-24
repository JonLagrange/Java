package com.test.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import com.mysql.jdbc.PreparedStatement;

public class InsertTest{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        final String url = "jdbc:mysql://localhost:3306/vote";    
		final String name = "com.mysql.jdbc.Driver";
		final String user = "root";
		final String password = "root";
		
		Connection conn = null;
		Class.forName(name);//指定连接类型  
		conn = DriverManager.getConnection(url, user, password);//获取连接  
		if (conn!=null) {
			System.out.println("获取连接成功");
			insert(conn);
		}else{
			System.out.println("获取连接失败");
		}
    }    
    public static void insert(Connection conn){
    	long begin = new Date().getTime();
    	String prefix = "INSERT INTO library_mission (user,type,description,imageshot,capital,cost,amount,totalcost,date_mission) VALUES ";
    	try{
    		StringBuffer suffix = new StringBuffer();
    		conn.setAutoCommit(false);
    		PreparedStatement pst = (PreparedStatement) conn.prepareStatement("");//准备执行语句
    		for (int i = 1; i <= 100; i++) {
    			suffix = new StringBuffer();
    			for (int j = 1; j <= 10000; j++) {
    				suffix.append("('"+i*j+"','微信投票'"+ ",'ZZZZZZZZZZZZ'"+",'imageshot/Screenshot_2014-10-23-08-37-55.png'"+",'59'"+",'1'"+",'"+"3"+"','1000'" +",'2018-01-25 11:35:53.643157'"+"),");
    			}
    			String sql = prefix + suffix.substring(0, suffix.length() - 1);
				pst.addBatch(sql);
				pst.executeBatch();
				conn.commit();
				suffix = new StringBuffer();
    		}
    		pst.close();
    		conn.close();
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	long end = new Date().getTime();
    	System.out.println("100万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
    	System.out.println("插入完成");
    }
}