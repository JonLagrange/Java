package com.test.fptest;

import java.io.BufferedReader;  
import java.io.FileReader;  
 
public class TestRead {  
 
    public static void main(String[] args) {  
        try {  
            BufferedReader reader = new BufferedReader(new FileReader("datafile/association/Z4YAZVXM.csv"));//��������ļ��� 
            reader.readLine();//��һ����Ϣ��Ϊ������Ϣ������,�����Ҫ��ע�͵� 
            String line = null;  
            while((line=reader.readLine())!=null){  
                String item[] = line.split(",");//CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з� 
                  
                String last = item[item.length-1];//�������Ҫ�������� 
                //int value = Integer.parseInt(last);//�������ֵ������ת��Ϊ��ֵ 
                System.out.println(last);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}