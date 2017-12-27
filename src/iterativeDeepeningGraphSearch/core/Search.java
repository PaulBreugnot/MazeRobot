package iterativeDeepeningGraphSearch.core;

import java.util.Collection;
import iterativeDeepeningGraphSearch.model.Node;
import iterativeDeepeningGraphSearch.model.Problem;
import iterativeDeepeningGraphSearch.model.Solution;

public interface Search {
    public Solution solve(Problem problem, int limit);
    public Collection<Node> expand(Node node, Problem problem);

}
