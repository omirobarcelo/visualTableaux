package ver1;
// src.: https://stackoverflow.com/questions/19330731/tree-implementation-in-java-root-parents-and-children/22419453
// disposed of parent since it provoked stack overflow errors when checking for equality or copying

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode {
    private Node data = null;
    private List<TreeNode> children = new ArrayList<TreeNode>();

    /**
     * Not null arguments assumed
     * @param data
     */
    public TreeNode(Node data) {
        this.data = data;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    public void addChild(Node data) {
        TreeNode newChild = new TreeNode(data);
        children.add(newChild);
    }

    public void addChildren(List<TreeNode> children) {
        this.children.addAll(children);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public Node getData() {
        return data;
    }

    public void setData(Node data) {
        this.data = data;
    }
    
    /**
     * Deep clone of a tree node
     * src.: https://stackoverflow.com/questions/16098362/how-to-deep-copy-a-tree
     * @return
     */
    public TreeNode copy() {
    	List<TreeNode> copyChildren = new ArrayList<TreeNode>();
    	if (!this.children.isEmpty()) {
    		for (TreeNode child : this.children) {
    			copyChildren.add(child.copy());
    		}
    	}
    	TreeNode copy = new TreeNode(this.data);
    	copy.addChildren(copyChildren);
    	return copy;
    }
    
    /**
     * Obtains the TreeNode object contained in root from Node n data
     * @param root
     * @param n
     * @return
     */
    public static TreeNode getTreeNode(TreeNode root, Node n) {
    	Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(root);
		while (!s.isEmpty()) {
			TreeNode tn = s.pop();
			if (n.equals(tn.data))
				return tn;
			if (!tn.getChildren().isEmpty()) {
				for (TreeNode succ : tn.getChildren()) {
					s.push(succ);
				}
			}
		}
		return null;
    }
    
    @Override
    public int hashCode() {
    	return data.hashCode() ^ children.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other instanceof TreeNode) {
    		TreeNode tn = (TreeNode)other;
    		boolean sameNode = data.equals(tn.data);
    		boolean sameChildren = children.equals(tn.children);    		
    		return sameNode && sameChildren;
    	}
    	return false;
    }
}
