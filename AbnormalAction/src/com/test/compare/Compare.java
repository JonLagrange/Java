package com.test.compare;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class Compare {
	
    /*判断 array1是否包含所有的 array2*/
    private static boolean containsAll(String[] array1,String[] array2){
    	for (String str : array2){
        	if(!ArrayUtils.contains(array1,str)){
 	        	return false;
 	        }
    	}
    	return true;
	}
    
    public static void main(String[] args) throws Exception {      
		LinkedList<LinkedList<String>> records=new LinkedList<LinkedList<String>>();
		//String filePath="scripts/clustering/canopy/canopy.dat";		
		String filePath="datafile/app/WD-WCC3F5687344.csv";
		BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(filePath)));
		int count=0;
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if(line.length()==0||"".equals(line))continue;
        	String[] str=line.split(",");   
        	LinkedList<String> litm=new LinkedList<String>();
        	List<String> list=Arrays.asList(str);
    		//System.out.println(str.length);
    		//String[] son={"AUDIODG.EXE","DllHost.exe","LogonUI.exe","Mystify.scr","SGTool.exe","SSLVPNRedirector.exe","SSLVPNRedirector64.exe","SearchFilterHost.exe","SearchProtocolHost.exe","SogouCloud.exe","conhost.exe","igfxsrvc.exe","rundll32.exe","sc.exe","taskhost.exe","wmpnscfg.exe","MpCmdRun.exe","sdclt.exe","svchost.exe"};
    		//String[] son={"A","C","F"};
    		//String[] son={"DllHost.exe","conhost.exe","taskhost.exe"};
    		String[] son={"AUDIODG.EXE","DllHost.exe","LogonUI.exe","Mystify.scr","klmon.exe","nvtray.exe","taskhost.exe","360EntMisc.exe","LiveUpdate360.exe","MpCmdRun.exe","conhost.exe","rundll32.exe","sdclt.exe"};
            boolean containsAll=containsAll(str,son);              
            if(containsAll==false){
                	System.out.println(list);
                	System.out.println(count);
                	//System.out.println(containsAll);
                }
        	for(int i=0;i<str.length;i++){       		
        		litm.add(str[i].trim());
        	}
            records.add(litm);          
            count++;
        }
        br.close();
        System.out.println(count);
        System.out.println("Finish!");
    }
}
