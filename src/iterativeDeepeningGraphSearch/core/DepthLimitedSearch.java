package iterativeDeepeningGraphSearch.core;

import java.util.Collection;
import java.util.HashSet;

import qLearning.model.Action;
import iterativeDeepeningGraphSearch.model.Node;
import iterativeDeepeningGraphSearch.model.Problem;
import iterativeDeepeningGraphSearch.model.Solution;

public class DepthLimitedSearch implements Search {

	HashSet<Node> exploredNodes = new HashSet<>();

	@Override
	public Solution solve(Problem problem, int limit) {
		return RecursiveDLS(new Node(problem.getInitialState()), problem, limit);
	}

	public Solution RecursiveDLS(Node node, Problem problem, int limit) {
		if (problem.isGoal(node.getState())) {
			return new Solution(node);
		} else {
			if (limit == 0) {
				return new Solution(false, true);
			} else {
				boolean cutoffOccurred = false;
				for (Action action : node.getState().getAvailableActions()) {
					problem.checkState(node.getState());
					Node child = new Node(problem.getNextState(node.getState(), action));
					//if (!exploredNodes.contains(child)) {
					//	exploredNodes.add(child);
						Solution sol = RecursiveDLS(child, problem, limit - 1);
						if (sol.cutoff()) {
							cutoffOccurred = true;
						} else if (!sol.failure()) {
							return new Solution(child);
						}
					//}
				}
				if (cutoffOccurred) {
					return new Solution(false, true);
				} else {
					return new Solution(true, false);
				}
			}

		}
	}

	@Override
	public Collection<Node> expand(Node node, Problem problem) {
		// TODO Auto-generated method stub
		return null;
	}

}
