package com.test.fptree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FPTree {
	public int support; // �趨��С֧������ֵ
	public int resultcount=0;
	public int maxc=0;
	//�����һ�εĴ���
	public Map<String,Integer> ordermap=new HashMap<String,Integer>();
	
	//������ͷ��
	public LinkedList<TreeNode> buildHeaderLink(LinkedList<LinkedList<String>> records){
		LinkedList<TreeNode> header=null;
		if(records.size()>0){
			header=new LinkedList<TreeNode>();
		}else{
			return null;
		}
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		for(LinkedList<String> items:records){
			
			for(String item:items){
				//�������������1��������������
				if(map.containsKey(item)){
					map.get(item).Sum(1);
				}else{
					TreeNode node=new TreeNode();
					node.setName(item);
					node.setCount(1);
					map.put(item, node);
				}
             }
		}
		 // ��֧�ֶȴ��ڣ�����ڣ�minSup������뵽F1��
        Set<String> names = map.keySet();
        for (String name : names) {
            TreeNode tnode = map.get(name);
            if (tnode.getCount() >= support) {
            	header.add(tnode);
            }
        }
        sort(header);		
        return header;
	}
	//ѡ������,���������ȣ�����������,�ֵ�˳��,��Сд���д
	public List<TreeNode> sort(List<TreeNode> list){
		int len=list.size();
		for(int i=0;i<len;i++){
			
			for(int j=i+1;j<len;j++){
				TreeNode node1=list.get(i);
				TreeNode node2=list.get(j);
				if(node1.getCount()<node2.getCount()){
					TreeNode tmp=new TreeNode();
					tmp=node2;
					list.remove(j);
					//listָ��λ�ò��룬ԭ����>=jԪ�ض��������ƣ�����ɾ��,���Բ���ǰҪɾ����ԭ����Ԫ��
					list.add(j,node1);
					list.remove(i);
					list.add(i,tmp);
				}
				//���������ȣ�����������,�ֵ�˳��,��Сд���д
				if(node1.getCount()==node2.getCount()){
					String name1=node1.getName();
					String name2=node2.getName();
					int flag=name1.compareTo(name2);
					if(flag>0){
						TreeNode tmp=new TreeNode();
						tmp=node2;
						list.remove(j);
						//listָ��λ�ò��룬ԭ����>=jԪ�ض��������ƣ�����ɾ��,���Բ���ǰҪɾ����ԭ����Ԫ��
						list.add(j,node1);
						list.remove(i);
						list.add(i,tmp);
					}
				}
			}
		}
		return list;
	}
	//ѡ�����򣬽���,���ͬ����L �еĴ�������
	public List<String> itemsort(LinkedList<String> lis,List<TreeNode> header){
		//List<String> list=new ArrayList<String>();
		//ѡ������
		int len=lis.size();
		for(int i=0;i<len;i++){
			for(int j=i+1;j<len;j++){
				String key1=lis.get(i);
				String key2=lis.get(j);
				Integer value1=findcountByname(key1,header);
				if(value1==-1)continue;
				Integer value2=findcountByname(key2,header);
				if(value2==-1)continue;
				if(value1<value2){
					String tmp=key2;
					lis.remove(j);
					lis.add(j,key1);
					lis.remove(i);
					lis.add(i,tmp);
				}
				if(value1==value2){
					int v1=ordermap.get(key1);
					int v2=ordermap.get(key2);
					if(v1>v2){
						String tmp=key2;
						lis.remove(j);
						lis.add(j,key1);
						lis.remove(i);
						lis.add(i,tmp);
					}
				}
		     }
		}
		return lis;
	}
	public Integer findcountByname(String itemname,List<TreeNode> header){
		Integer count=-1;
		for(TreeNode node:header){
			if(node.getName().equals(itemname)){
				count= node.getCount();
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @param records �������ļ�¼,��I1,I2,I3
	 * @param header �����н��ܵı�ͷ
	 * @return ���ع����õ���
	 */
	public TreeNode builderFpTree(LinkedList<LinkedList<String>> records,List<TreeNode> header){
		
		   TreeNode root;
		   if(records.size()<=0){
			   return null;
		   }
		   root=new TreeNode();
		   for(LinkedList<String> items:records){
			   itemsort(items,header);   
			   addNode(root,items,header);
			}
		return root;
	}
	//���Ѿ��з�֦���ڵ�ʱ���ж������Ľڵ��Ƿ����ڸ÷�֦��ĳ���ڵ㣬��ȫ���غϣ��ݹ�
	public  TreeNode addNode(TreeNode root,LinkedList<String> items,List<TreeNode> header){
		if(items.size()<=0)return null;
		String item=items.poll();
		//��ǰ�ڵ�ĺ��ӽڵ㲻�����ýڵ㣬��ô���ⴴ��һ֧��֧��
		TreeNode node=root.findChild(item);
		if(node==null){
            node=new TreeNode();
			node.setName(item);
			node.setCount(1);
			node.setParent(root);
			root.addChild(node);
			
			//�ӽ������ڵ�ӵ���ͷ�� 
			for(TreeNode head:header){
				if(head.getName().equals(item)){
					while(head.getNextHomonym()!=null){
						head=head.getNextHomonym();
					}
					head.setNextHomonym(node);
					break;
				}
			}
			//�ӽ������ڵ�ӵ���ͷ��
		}else{
			node.setCount(node.getCount()+1);
		}
 
		addNode(node,items,header);
		return root;
	}
	//��Ҷ���ҵ����ڵ㣬�ݹ�֮
	public void toroot(TreeNode node,LinkedList<String> newrecord){
		if(node.getParent()==null)return;
		String name=node.getName();
		newrecord.add(name);
		toroot(node.getParent(),newrecord);
	}
	//������FP-tree��������ϣ������Ƶ���
	public void combineItem(TreeNode node,LinkedList<String> newrecord,String Item){
		if(node.getParent()==null)return;
		String name=node.getName();
		newrecord.add(name);
		toroot(node.getParent(),newrecord);
	}
	//fp-growth
	public void fpgrowth(LinkedList<LinkedList<String>> records,String item,int c){
		//�����µ�����ģʽ���ĸ�����¼�������¹���FP-tree
		LinkedList<LinkedList<String>> newrecords=new LinkedList<LinkedList<String>>();
		//������ͷ
		LinkedList<TreeNode> header=buildHeaderLink(records);
		//����FP-Tree
		TreeNode fptree= builderFpTree(records,header);
		
		//�����ݹ������
		if(header.size()<=0||fptree==null){
			//System.out.println("-----------------");
			return;
		}
		//��ӡ���,���Ƶ���
		if(item!=null){
			//Ѱ������ģʽ��,����β��ʼ
			for(int i=header.size()-1;i>=0;i--){
				TreeNode head=header.get(i);
				Integer count=0;
				while(head.getNextHomonym()!=null){
					head=head.getNextHomonym();
					//Ҷ��count���ڶ��٣������������¼
					count=count+head.getCount();
					
				}
				//��ӡƵ���
				//System.out.println(head.getName()+","+item+"\t"+count);
				if(c==maxc){
					System.out.println(head.getName()+","+item+"\t"+count);
					if(head.getName().isEmpty()){
						resultcount=1;
						System.out.println(resultcount);
					}
				}
				//System.out.println(resultcount);
			}
		}
		
		//Ѱ������ģʽ��,����β��ʼ
		for(int i=header.size()-1;i>=0;i--){
			TreeNode head=header.get(i);
			String itemname;
			int c1=c;
			//�����
			if(item==null){
				itemname=head.getName();
			}else{
				itemname=head.getName()+","+item;
			}
			c1++;

			while(head.getNextHomonym()!=null){
				head=head.getNextHomonym();
				//Ҷ��count���ڶ��٣������������¼
				Integer count=head.getCount();
				for(int n=0;n<count;n++){
				   LinkedList<String> record=new LinkedList<String>();
				   toroot(head.getParent(),record);
				   newrecords.add(record);
				}
			}
			//System.out.println("-----------------");
			//�ݹ�֮,������FP-Tree
			fpgrowth(newrecords,itemname,c1);
			if(c1>maxc)
				maxc=c1;
		}
    }
	//������򣬴˲�Ҳ����ʡ�ԣ�Ϊ�˼����ټӹ�������鷳����
	public void orderF1(LinkedList<TreeNode> orderheader){
		for(int i=0;i<orderheader.size();i++){
			TreeNode node=orderheader.get(i);
			ordermap.put(node.getName(), i);
		}		
	}
}

	