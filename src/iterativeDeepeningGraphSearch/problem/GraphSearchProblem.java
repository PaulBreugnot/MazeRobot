package iterativeDeepeningGraphSearch.problem;

import java.util.Collection;

import iterativeDeepeningGraphSearch.model.Problem;
import qLearning.model.Action;
import qLearning.model.State;
import qLearning.problem.MazeRobotState;
import robot.Simulation;

public class GraphSearchProblem implements Problem {

	private State initialState;
		
	public GraphSearchProblem(State initialState) {
		this.initialState = initialState;
	}
	@Override
	public State getInitialState() {
		return initialState;
	}

	@Override
	public boolean isGoal(State state) {
		return state.isTerminal();
	}

	@Override
	public Collection<Action> getActions(State state) {
		return state.getAvailableActions();
	}

	@Override
	public State getNextState(State state, Action action) {
		return action.executeAction();
	}

	@Override
	public double getStepCost(Object start, Object action, Object dest) {
		return 0;
	}
	@Override
	public void checkState(State state) {
		System.out.println("Relevant state?");
		System.out.println(state.equals(new MazeRobotState(Simulation.getMap().getRoom(Simulation.getRobot().getXPos(), Simulation.getRobot().getYPos()))));
		if(!state.equals(new MazeRobotState(Simulation.getMap().getRoom(Simulation.getRobot().getXPos(), Simulation.getRobot().getYPos())))) {
			Simulation.getRobot().setXPos(((MazeRobotState) state).getRoom().getXPos());
			Simulation.getRobot().setYPos(((MazeRobotState) state).getRoom().getYPos());
			Simulation.getGraphicWindow().updateGraphicItems();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("And now?");
		System.out.println(state.equals(new MazeRobotState(Simulation.getMap().getRoom(Simulation.getRobot().getXPos(), Simulation.getRobot().getYPos()))));
	}

}
