package com.test.add;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		ListNode L1 = new ListNode(0);
		ListNode L2 = new ListNode(0);
		L1 = creatLinkList();
		L2 = creatLinkList();

		printLinkList(addTwoNum(L1, L2));
		//printLinkList(mergeTwoLists(L1, L2));
		//printLinkList(L1);
		//printLinkList(L2);
	}
	
	public static ListNode creatLinkList() {
		Scanner sc = new Scanner(System.in);
		ListNode head, p, node;
		head = new ListNode(0);
		p = head;
		int x = 0, flag = 1;
		System.out.println("�������������ݣ���0��β��");
		while(flag == 1) {
			x=sc.nextInt();
			if(x != 0) {
				node = new ListNode(x);
				p.next = node;
				p = node;
			}
			else flag = 0;
		}
		p.next = null;
		return head;
	}
	
	public static void printLinkList(ListNode head) {
		ListNode p = head.next;
		if(p == null)
		{
			System.out.println("����Ϊ�գ�");
			return;
		}
		System.out.println("��������Ϊ��");
		while(p != null) {
			System.out.print(p.data+" ");
			p = p.next;
		}
		System.out.println();
	}
	
	/*
	���������ǿ���������ʾ�����Ǹ�������λ����������ʽ�洢�����ǵ�ÿ���ڵ�ֻ�洢�������֡���������ӷ���һ���µ�����
	ʾ����
	���룺(2 -> 4 -> 3) + (5 -> 6 -> 4)
	�����7 -> 0 -> 8
	ԭ��342 + 465 = 807
	*/
	public static ListNode addTwoNum(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode p = l1, q = l2, curr = dummyHead;
		int carry = 0;
		while(p != null || q != null) {
			int x = (p != null) ? p.data : 0;
			int y = (q != null) ? q.data : 0;
			int sum = x + y + carry;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			if(p != null) p = p.next;
			if(q != null) q = q.next;
		}
		if(carry > 0) {
			curr.next = new ListNode(carry);
		}
		return dummyHead.next;
	}

	/*
	 ���⣺��ν�������������ϲ���һ������������
	����������������l1��l2������һ���µ����� dummy������ l1 ��l2���Ƚ�l1 ��l2��˭С�ƶ�˭����curr����¼��Ҫ�ƶ���λ�á�
	ѭ��������l1 ��l2 ��һ��βָ��û��ָ����Ҫ�ڰ�βָ��ָ�� dummy�Ľ�β�ģ���󷵻� dummy����һ���ڵ㼴�ɡ�
	*/
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode p = l1, q = l2, curr = dummyHead;
		while(p != null && q != null) {
			if(p.data <= q.data) {
				curr.next = p;
				curr = curr.next;  //currָ�����һλ
				p = p.next;  //l1ָ�����һλ
				
			}
			else {
				curr.next = q;
				curr = curr.next;
				q = q.next;
			}
		}
		/*ѭ��������l1 �� l2 ��һ��βָ��û��ָ����Ҫ�ڰ�βָ��ָ�� dummy�Ľ�β�ģ���󷵻� dummy����һ���ڵ㼴�ɡ�*/
		if(p != null) curr.next = p;
		if(q != null) curr.next = q;
		return dummyHead.next;
	}
}
