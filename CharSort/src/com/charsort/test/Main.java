package com.charsort.test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String str = sc.nextLine();
			char[] ch = str.toCharArray();
			int[] cnt = new int[128];
			Map<Character, Integer> map = new HashMap<Character, Integer>();
			List<Map.Entry<Character, Integer>> list = new ArrayList<>();
			
			for(int i = 0; i < ch.length; i++) cnt[ch[i]]++; 		
			for(int i = 0; i < ch.length; i++) map.put(ch[i], cnt[ch[i]]);			
			list.addAll(map.entrySet());
			
			list.sort(new Comparator<Map.Entry<Character, Integer>>(){
				@Override
			    public int compare(Map.Entry<Character, Integer> m1, Map.Entry<Character, Integer> m2){
			        return m1.getKey() - m2.getKey();
				}		
			});
			list.sort(new Comparator<Map.Entry<Character, Integer>>(){
				@Override
			    public int compare(Map.Entry<Character, Integer> m1, Map.Entry<Character, Integer> m2){
			        return m2.getValue() - m1.getValue();
				}		
			});
			
	        for(Map.Entry<Character, Integer> entry : list){
	            System.out.print(entry.getKey());
	        }
	        System.out.println();
		}
	}
}
