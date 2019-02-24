package com.test.fptest;

import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Comparable<TreeNode>{

    private String name; // �ڵ�����
    private Integer count; // ����
    private TreeNode parent; // ���ڵ�
    private List<TreeNode> children; // �ӽڵ�
    private TreeNode nextHomonym; // ��һ��ͬ���ڵ�
  
    public TreeNode() {
  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	public void Sum(Integer count) {
		this.count =this.count+count;
	}
	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public TreeNode getNextHomonym() {
		return nextHomonym;
	}

	public void setNextHomonym(TreeNode nextHomonym) {
		this.nextHomonym = nextHomonym;
	}
    /**
     * ���һ���ڵ�
     * @param child
     */
    public void addChild(TreeNode child) {
        if (this.getChildren() == null) {
            List<TreeNode> list = new ArrayList<TreeNode>();
            list.add(child);
            this.setChildren(list);
        } else {
            this.getChildren().add(child);
        }
    }
    /**
    *  �Ƿ�����Ÿýڵ�,���ڷ��ظýڵ㣬�����ڷ��ؿ�
    * @param name
    * @return
    */
    public TreeNode findChild(String name) {
        List<TreeNode> children = this.getChildren();
        if (children != null) {
            for (TreeNode child : children) {
                if (child.getName().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    public int compareTo(TreeNode arg0) {
        // TODO Auto-generated method stub
        int count0 = arg0.getCount();
        // ��Ĭ�ϵıȽϴ�С�෴�����µ���Arrays.sort()ʱ�ǰ���������
        return count0 - this.count;
    }
}
