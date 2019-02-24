package com.test;
import java.util.Scanner;

public class Niuniu {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		int job[][] = new int[n][2];
		int capacity[] = new int[m];
		int maxsalary[] =new int[m];
		for(int i=0;i<n;i++){
			for(int j=0;j<2;j++){
				job[i][j] = in.nextInt();
			}
		}
		for(int i=0;i<m;i++){			
			capacity[i]=in.nextInt();
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (capacity[i]>=job[j][0]&&job[j][1]>maxsalary[i]) {
					maxsalary[i]=job[j][1];
				}
			}
		}
		for (int i = 0; i < maxsalary.length; i++) {
			System.out.println(maxsalary[i]);
		}
	}
}