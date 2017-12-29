package iterativeDeepeningGraphSearch.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import qLearning.model.Action;
import util.Random;
import iterativeDeepeningGraphSearch.model.Node;
import iterativeDeepeningGraphSearch.model.Problem;
import iterativeDeepeningGraphSearch.model.Solution;

public class DepthLimitedSearch implements Search {

	HashSet<Node> exploredNodes = new HashSet<>();
	Node lastCutoff;

	@Override
	public Solution solve(Problem problem, int limit) {
		return RecursiveDLS(new Node(problem.getInitialState()), problem, limit);
	}

	public Solution RecursiveDLS(Node node, Problem problem, int limit) {
		exploredNodes.add(node);
		if (problem.isGoal(node.getState())) {
			return new Solution(node);
		} else {
			if (limit == 0) {
				return new Solution(node, false, true);
			} else {
				boolean cutoffOccurred = false;
				ArrayList<Action> availableActions = node.getState().getAvailableActions();
				while(availableActions.size()>0) {
					Action action = Random.selectRandomActionFrom(availableActions);
					availableActions.remove(action);
					if(lastCutoff!=null) {
					problem.checkState(lastCutoff, node);
					}
					Node child = new Node(problem.getNextState(node.getState(), action), node, action);
					if(exploredNodes.contains(child)) {
						System.out.println("AlreadyExplored!");
						cutoffOccurred = true;
						lastCutoff = node;
					}
					else {
						action.executeAction();
						Solution sol = RecursiveDLS(child, problem, limit - 1);
						if (sol.cutoff()) {
							cutoffOccurred = true;
							lastCutoff = sol.getNode();
						} else if (!sol.failure()) {
							return new Solution(child);
						}
					}
				}
				if (cutoffOccurred) {
					return new Solution(lastCutoff, false, true);
				} else {
					return new Solution(node, true, false);
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
