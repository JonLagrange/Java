package com.test.negpos;
import java.text.DecimalFormat;
import java.util.Scanner;

public class NegPos {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			int cnt1 = 0, cnt2 = 0, sum = 0, n = sc.nextInt();
			int[] num = new int[n];
			for(int i = 0; i < n; i++) {
				num[i] = sc.nextInt();
				if(num[i] < 0) cnt1++;
				else if(num[i] > 0) {
					cnt2++;
					sum += num[i];
				}			
			}
			double result = (double)sum / cnt2;
			DecimalFormat df = new DecimalFormat("#.0");
			System.out.print(cnt1 + " " + df.format(result) + "\n");
		}
	} 
}