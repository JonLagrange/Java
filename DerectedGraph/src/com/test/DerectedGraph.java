package com.test;

import java.util.Stack;

/**
 * Java: �ڽӾ���ͼ
 *
 * @author skywang
 * @date 2014/04/19
 */

import java.io.IOException;
import java.util.Scanner;

public class DerectedGraph {
    // �ڽӱ��б��Ӧ������Ķ���
    private class ENode {
        int ivex;       // �ñ���ָ��Ķ����λ��
        ENode nextEdge; // ָ����һ������ָ��
    }

    // �ڽӱ��б�Ķ���
    private class VNode {
        char data;          // ������Ϣ
        ENode firstEdge;    // ָ���һ�������ö���Ļ�
    };

    private VNode[] mVexs;  // ��������


    /* 
     * ����ͼ(�Լ���������)
     */
    public DerectedGraph() {

        // ����"������"��"����"
        System.out.printf("input vertex number: ");
        int vlen = readInt();
        System.out.printf("input edge number: ");
        int elen = readInt();
        if ( vlen < 1 || elen < 1 || (elen > (vlen*(vlen - 1)))) {
            System.out.printf("input error: invalid parameters!\n");
            return ;
        }

        // ��ʼ��"����"
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = readChar();
            mVexs[i].firstEdge = null;
        }

        // ��ʼ��"��"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // ��ȡ�ߵ���ʼ����ͽ�������
            System.out.printf("edge(%d):", i);
            char c1 = readChar();
            char c2 = readChar();
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);
            // ��ʼ��node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // ��node1���ӵ�"p1���������ĩβ"
            if(mVexs[p1].firstEdge == null)
              mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
        }
    }

    /*
     * ����ͼ(�����ṩ�ľ���)
     *
     * ����˵����
     *     vexs  -- ��������
     *     edges -- ������
     */
    public DerectedGraph(char[] vexs, char[][] edges) {

        // ��ʼ��"������"��"����"
        int vlen = vexs.length;
        int elen = edges.length;

        // ��ʼ��"����"
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            mVexs[i] = new VNode();
            mVexs[i].data = vexs[i];
            mVexs[i].firstEdge = null;
        }

        // ��ʼ��"��"
        for (int i = 0; i < elen; i++) {
            // ��ȡ�ߵ���ʼ����ͽ�������
            char c1 = edges[i][0];
            char c2 = edges[i][1];
            // ��ȡ�ߵ���ʼ����ͽ�������
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);

            // ��ʼ��node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // ��node1���ӵ�"p1���������ĩβ"
            if(mVexs[p1].firstEdge == null)
              mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
        }
    }

    /*
     * ��node�ڵ����ӵ�list�����
     */
    private void linkLast(ENode list, ENode node) {
        ENode p = list;

        while(p.nextEdge!=null)
            p = p.nextEdge;
        p.nextEdge = node;
    }

    /*
     * ����chλ��
     */
    private int getPosition(char ch) {
        for(int i=0; i<mVexs.length; i++)
            if(mVexs[i].data==ch)
                return i;
        return -1;
    }

    /*
     * ��ȡһ�������ַ�
     */
    private char readChar() {
        char ch='0';

        do {
            try {
                ch = (char)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!((ch>='a'&&ch<='z') || (ch>='A'&&ch<='Z')));

        return ch;
    }

    /*
     * ��ȡһ�������ַ�
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /*
     * ��ӡ�������ͼ
     */
    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("%d(%c): ", i, mVexs[i].data);
            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
                node = node.nextEdge;
            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	String temp = sc.nextLine(); 
    	String[] ss = temp.trim().split(" ");
    	
    	int n = Integer.parseInt(ss[0]);
    	int m = Integer.parseInt(ss[1]);
    	
        //char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[] vexs = new int[n];
        int[][] edges = new int[m][2];
        		
        for(int i = 0; i < m; i++)
        {
        	String temp2 = sc.nextLine(); 
        	String[] ss2 = temp2.trim().split(",");
        	
        	edges[i][0] = Integer.parseInt(ss2[0]);
        	edges[i][1] = Integer.parseInt(ss2[1]);

        }
        for(int i = 0; i < m; i++)
        {
        	for(int j = 0; j < 2; j++)
        	{
        		System.out.println(edges[i][j]);
        	}
        }

        //DerectedGraph pG;

        // �Զ���"ͼ"(����������)
        //pG = new ListDG();
        // �������е�"ͼ"
        //pG = new DerectedGraph(vexs, edges);

        //pG.print();   // ��ӡͼ
    }
}
	