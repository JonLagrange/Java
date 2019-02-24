package com.test.iter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Iteration {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		//for(Iterator<String> iter = list.iterator(); iter.hasNext();){
		//	String str = (String)iter.next();
		//	System.out.print(str);
		//}
		
		Iterator<String> iter = list.iterator();
		while(iter.hasNext()){
			String str = (String)iter.next();
			System.out.print(str);
		}
	}
	
	public static int longestSubstring(String x, String y) {
		int xlen = x.length(), ylen = y.length();
		if(xlen == 0 || ylen == 0) return 0;
		int max = -1;
		char[] cx = x.toCharArray();
		char[] cy = y.toCharArray();
		for(int i = 0; i < xlen; i++) {
			for(int j = 0; j < ylen; j++) {
				int m = i, n = j, len = 0;
				while(m < xlen && n < ylen) {
					if(cx[m] != cy[n]) break;
					m++;
					n++;
					len++;
				}
				if(len > max) max = len;
			}
			
		}
		return max;
	}
}
