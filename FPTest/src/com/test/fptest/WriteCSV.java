package com.test.fptest;

import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileNotFoundException;  
import java.io.FileWriter;  
import java.io.IOException;  
 
public class WriteCSV {  
 
  public static void main(String[] args) {  
    try {  
      File csv = new File("F:/writers.csv"); // CSV�����ļ� 
 
      BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // ���� 
      // ����µ������� 
      bw.write("\"����\"" + "," + "\"1988\"" + "," + "\"1992\"");  
      bw.newLine();  
      bw.close();  
 
    } catch (FileNotFoundException e) {  
      // File����Ĵ��������е��쳣���� 
      e.printStackTrace();  
    } catch (IOException e) {  
      // BufferedWriter�ڹرն���׽�쳣 
      e.printStackTrace();  
    }  
  }  
}