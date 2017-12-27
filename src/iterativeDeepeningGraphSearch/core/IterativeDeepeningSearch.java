package iterativeDeepeningGraphSearch.core;

import java.util.Collection;

import iterativeDeepeningGraphSearch.model.Node;
import iterativeDeepeningGraphSearch.model.Problem;
import iterativeDeepeningGraphSearch.model.Solution;

public class IterativeDeepeningSearch implements Search {

	@Override
	public Solution solve(Problem problem, int limit) {
		for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
			DepthLimitedSearch solver = new DepthLimitedSearch();
			Solution sol = solver.solve(problem, depth);
			if (!sol.cutoff()) {
				return sol;
			}
		}
		return null;
	}

	@Override
	public Collection<Node> expand(Node node, Problem problem) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
