package com.test.negative;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int cntn = 0, cntp = 0;
		float sum = 0;
		while(sc.hasNext()){
			int num = sc.nextInt();
			if(num < 0) cntn++;
			else{
				sum += num;
				cntp++;
			}
		}
		DecimalFormat fnum = new DecimalFormat("##0.0");  
		System.out.println(cntn);
		if(cntp == 0) System.out.println("0.0");
		else System.out.println(fnum.format(sum / cntp));
	}
}
