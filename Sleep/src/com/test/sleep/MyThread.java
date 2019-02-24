package com.test.sleep;

public class MyThread extends Thread {
	public static void main(String[] args) { 
		new MyThread().start(); 
	}
	
	public void run() { 
		for (int i = 0; i < 100; i++) { 
			if ((i) % 10 == 0) {
				System.out.println("����-" + i); 
			} 
			System.out.print(i); 
			try { 
				Thread.sleep(1000); 
				System.out.print("�߳�˯��1�룡\n"); 
			} 
			catch (InterruptedException e) { 
				e.printStackTrace(); 
			} 
		} 
	} 
}
