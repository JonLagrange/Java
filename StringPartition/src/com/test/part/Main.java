package com.test.part;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			int n = sc.nextInt();
			String[] str = new String[n];
			for(int i = 0; i < n; i++) str[i] = sc.next();					
			for(int i = 0; i < n; i++){
				string_partition(str[i]);
			}
		}
	}
	public static void string_partition(String s){
		char[] ch = s.toCharArray();
		int len;
	    if(ch.length % 8 == 0) len = ch.length;
	    else len = ch.length + (8 - ch.length % 8);
	    
		for(int j = 0; j < ch.length; j++){
			System.out.print(ch[j]);
			if((j + 1) % 8 == 0 && (j + 1) < ch.length) System.out.print("\n");
		}
		for(int j = ch.length; j < len; j++) System.out.print("0");
		System.out.print("\n");
	}
}
