package ver1;

public class Node {
	private String id;
	private String fullName;
	
	public Node(String id, String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", fullName=" + fullName + "]";
	}
	
}
