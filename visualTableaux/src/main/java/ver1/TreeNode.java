package ver1;
// https://stackoverflow.com/questions/19330731/tree-implementation-in-java-root-parents-and-children/22419453

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private Node data = null;
    private List<TreeNode> children = new ArrayList<TreeNode>();
    private TreeNode parent = null;

    public TreeNode(Node data) {
        this.data = data;
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(Node data) {
        TreeNode newChild = new TreeNode(data);
        newChild.setParent(this);
        children.add(newChild);
    }

    public void addChildren(List<TreeNode> children) {
        for(TreeNode t : children) {
            t.setParent(this);
        }
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

    private void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public TreeNode getParent() {
        return parent;
    }
    
    // TODO probably implement copy function for Backtracker
    
    // TODO maybe getTreeNode(firstNode, node)
    
    @Override
    public int hashCode() {
    	int baseHashCode = data.hashCode() ^ children.hashCode();
    	int hashCode = parent == null ? baseHashCode : baseHashCode ^ parent.hashCode();
    	return hashCode;
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other instanceof TreeNode) {
    		TreeNode tn = (TreeNode)other;
    		boolean sameNode = data.equals(tn.data);
    		boolean sameChildren = children.equals(tn.children);
    		// Just check parent data to avoid stack overflow
    		boolean sameParent = (parent==null && tn.parent==null) || 
					(parent != null && tn.parent != null && parent.data.equals(tn.parent.data));
    		return sameNode && sameChildren && sameParent;
    	}
    	return false;
    }
}
