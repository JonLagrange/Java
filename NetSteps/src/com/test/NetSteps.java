package com.test;
import java.util.Scanner;

public class NetSteps {
	
	public static int func(int n){
		if(n == 0 || n == 1) return 1;
		else return n * func(n - 1);
	}
	
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		int x = sc.nextInt();
		int y = sc.nextInt();
		System.out.println(func(x + y) / (func(x) * func(y)));
	}
}
