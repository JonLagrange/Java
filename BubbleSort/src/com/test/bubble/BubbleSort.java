package com.test.bubble;
import java.util.Scanner;

public class BubbleSort {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			int n = sc.nextInt();
			int[] pInt = new int[n];
			for(int i = 0; i < n; i++) pInt[i] = sc.nextInt();
			int flag = sc.nextInt();
			sortIntegerArray(pInt, flag);
			for(int i = 0; i < n; i++) System.out.print(pInt[i]+" ");
			System.out.println();
		}
	}
	
	public static void sortIntegerArray(int[] pIntegerArray, int iSortFlag) {
		for(int i = 0; i < pIntegerArray.length - 1; i++) {
			for(int j = pIntegerArray.length - 1; j > i; j--) {
				if(iSortFlag == 0) {
					if(pIntegerArray[j] < pIntegerArray[j - 1]) 
						Swap(pIntegerArray, j, j - 1);					
				}
				else if(iSortFlag == 1) {
					if(pIntegerArray[j] > pIntegerArray[j - 1]) 
						Swap(pIntegerArray, j, j - 1);					
				}
			}
		}
	}
	
	public static void Swap(int[] A, int i, int j) {
		int temp;
		temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	} 
}
