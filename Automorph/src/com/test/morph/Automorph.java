package com.test.morph;
import java.util.Scanner;

public class Automorph {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			int cnt = 0, n = sc.nextInt();
			for(int i = 0; i <= n; i++) {
				if(CalAutomorphicNumbers(i)) cnt++;
			}
			System.out.println(cnt);
		}	
	}
	
	public static boolean CalAutomorphicNumbers(int n) {
		int flag = 1, digit = 0, temp = n, square = n * n;
		while(temp > 0) {
			temp /= 10;
			digit++;
		}
		while(digit > 0) {
			if(square % 10 != n % 10) flag = 0;
			square /= 10;
			n /= 10;
			digit--;
		}		
		if(flag == 1) return true;		
		return false;
	} 
}
