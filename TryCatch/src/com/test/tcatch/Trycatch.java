package com.test.tcatch;

public class Trycatch {
	public static void main(String[] args) {
		int a = 10, b = 0;
		try {
			System.out.println(cal(a,b));
		}catch(Exception e) {
			System.out.println(e.toString());
		}finally {
			System.out.println("main finally!");
		}
	}
	
	public static int cal(int a, int b) {
		try {                                    //执行的代码，其中可能有异常。一旦发现异常，则立即跳到catch执行,否则不会执行catch里面的内容
			return a/b;
		}catch(Exception e) {                    //除非try里面执行代码发生了异常，否则这里的代码不会执行
			System.out.println(e.toString());
			return 1;
		}finally {                               //不管什么情况都会执行，包括try catch里面用了return ,可以理解为只要执行了try或者catch，就一定会执行 finally
			System.out.println("I'm finally!");
		}
	}
}
