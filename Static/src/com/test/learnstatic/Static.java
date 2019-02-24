package com.test.learnstatic;

public class Static {
	private static String str1 = "I Love Lanlan!";
	private String str2 = "Lanlan loves me!";
	
	public Static() {
		
	}
	
	public void print1() {
		System.out.println(str1);
		System.out.println(str2);
		print2();
	}
	
	public static void print2() {
		System.out.println(str1);
		//System.out.println(str2);
		//print1();
	}
	
	public static void main(String[] args) {
		//Static s = new Static();
		//s.str1 = "Haha";
		//s.print1();
		//s.print2();
		
		//Static.str1 = "Love";
		Static.print2();
	}
}
