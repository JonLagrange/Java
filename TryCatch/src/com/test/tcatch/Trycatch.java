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
		try {                                    //ִ�еĴ��룬���п������쳣��һ�������쳣������������catchִ��,���򲻻�ִ��catch���������
			return a/b;
		}catch(Exception e) {                    //����try����ִ�д��뷢�����쳣����������Ĵ��벻��ִ��
			System.out.println(e.toString());
			return 1;
		}finally {                               //����ʲô�������ִ�У�����try catch��������return ,�������ΪֻҪִ����try����catch����һ����ִ�� finally
			System.out.println("I'm finally!");
		}
	}
}
