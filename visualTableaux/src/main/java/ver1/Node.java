package ver1;

public class Node {
	private String id;
	
	public Node(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + "]";
	}
	
}
