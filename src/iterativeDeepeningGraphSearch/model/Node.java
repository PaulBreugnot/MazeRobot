package iterativeDeepeningGraphSearch.model;

import qLearning.model.Action;
import qLearning.model.State;

public class Node {
	private State state;
	private Node parent;
	private Action previousAction;
	
	public Node(State state) {
		this.state = state;
	}
	
	public Node(State state, Node parent, Action previousAction) {
		this.state = state;
		this.parent = parent;
		this.previousAction = previousAction;
	}
	
	public State getState() {
		return state;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Action getPreviousAction() {
		return previousAction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	
}
