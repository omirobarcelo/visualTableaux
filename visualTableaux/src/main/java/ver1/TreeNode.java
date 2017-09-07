package ver1;
// https://stackoverflow.com/questions/19330731/tree-implementation-in-java-root-parents-and-children/22419453

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode {
    private Node data = null;
    private List<TreeNode> children = new ArrayList<TreeNode>();

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
