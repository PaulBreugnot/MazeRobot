package iterativeDeepeningGraphSearch.model;

public class Solution {
	private Node node;
	private boolean failure;
	private boolean cutoff;

	public Solution(Node node) {
		this.node = node;
	}
	
	public Solution(boolean failure, boolean cutoff) {
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
