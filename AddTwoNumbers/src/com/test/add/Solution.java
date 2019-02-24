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
		System.out.println("请输入链表数据，以0结尾：");
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
			System.out.println("链表为空！");
			return;
		}
		System.out.println("链表数据为：");
		while(p != null) {
			System.out.print(p.data+" ");
			p = p.next;
		}
		System.out.println();
	}
	
	/*
	给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。
	示例：
	输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
	输出：7 -> 0 -> 8
	原因：342 + 465 = 807
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
	 问题：如何将两个排序链表合并成一个长排序链表
	分析：有两个链表l1，l2，构建一个新的链表 dummy，遍历 l1 和l2，比较l1 和l2，谁小移动谁，用curr来记录需要移动的位置。
	循环结束，l1 和l2 有一个尾指针没有指向，需要在把尾指针指向 dummy的结尾的，最后返回 dummy的下一个节点即可。
	*/
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode p = l1, q = l2, curr = dummyHead;
		while(p != null && q != null) {
			if(p.data <= q.data) {
				curr.next = p;
				curr = curr.next;  //curr指针后移一位
				p = p.next;  //l1指针后移一位
				
			}
			else {
				curr.next = q;
				curr = curr.next;
				q = q.next;
			}
		}
		/*循环结束，l1 和 l2 有一个尾指针没有指向，需要在把尾指针指向 dummy的结尾的，最后返回 dummy的下一个节点即可。*/
		if(p != null) curr.next = p;
		if(q != null) curr.next = q;
		return dummyHead.next;
	}
}
