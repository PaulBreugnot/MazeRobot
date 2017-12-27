package iterativeDeepeningGraphSearch.model;

public class Solution {
	private Node node;
	private boolean failure = false;
	private boolean cutoff = false;

	public Solution(Node node) {
		this.node = node;
	}
	
	public Solution(Node node, boolean failure, boolean cutoff) {
		this.node = node;
		this.failure = failure;
		this.cutoff = cutoff;
	}
	
	public boolean failure() {
		return failure;
	}
	
	public boolean cutoff() {
		return cutoff;
	}
	
	public Node getNode() {
		return node;
	}
}
