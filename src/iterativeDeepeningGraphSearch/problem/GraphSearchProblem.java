package iterativeDeepeningGraphSearch.problem;

import java.util.Collection;

import iterativeDeepeningGraphSearch.model.Node;
import iterativeDeepeningGraphSearch.model.Problem;
import qLearning.model.Action;
import qLearning.model.State;
import qLearning.model.StateActionPair;
import qLearning.problem.MazeRobotAction;
import qLearning.problem.MazeRobotReward;
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
		//return action.executeAction();
		switch (((MazeRobotAction) action).getValue()) {
		case GO_UP :{
			return new MazeRobotState(Simulation.getMap().getRoom(((MazeRobotState)state).getRoom().getXPos(), ((MazeRobotState)state).getRoom().getYPos()+1));
		}
		case GO_DOWN :{
			return new MazeRobotState(Simulation.getMap().getRoom(((MazeRobotState)state).getRoom().getXPos(), ((MazeRobotState)state).getRoom().getYPos()-1));
		}
		case GO_RIGHT :{
			return new MazeRobotState(Simulation.getMap().getRoom(((MazeRobotState)state).getRoom().getXPos()+1, ((MazeRobotState)state).getRoom().getYPos()));
		}
		case GO_LEFT :{
			return new MazeRobotState(Simulation.getMap().getRoom(((MazeRobotState)state).getRoom().getXPos()-1, ((MazeRobotState)state).getRoom().getYPos()));
		}
		default :
			return null;
		}
	}

	@Override
	public double getStepCost(Object start, Object action, Object dest) {
		return 0;
	}

	@Override
	public void checkState(Node lastCutoff, Node currentNode) {
		MazeRobotState robotRealState = new MazeRobotState(
				Simulation.getMap().getRoom(Simulation.getRobot().getXPos(), Simulation.getRobot().getYPos()));
		/*System.out.println("Relevant state?");
		System.out.println(currentNode.getState().equals(robotRealState));*/
		if (!currentNode.getState().equals(robotRealState)) {
			goBackwardToNode(currentNode, lastCutoff);
		}
		/*System.out.println("And now?");
		robotRealState = new MazeRobotState(
				Simulation.getMap().getRoom(Simulation.getRobot().getXPos(), Simulation.getRobot().getYPos()));
		System.out.println(currentNode.getState().equals(robotRealState));*/
	}

	@Override
	public void goBackwardToNode(Node currentAlgoNode, Node currentRobotNode) {
		/*System.out.println("Good State? " + currentRobotNode.getState().equals(currentAlgoNode.getState()));
		System.out.println("Parent available? " + !(currentRobotNode.getParent()==null));
		System.out.println("Keep going? " + (!currentRobotNode.getState().equals(currentAlgoNode.getState()) && !(currentRobotNode.getParent()==null)));*/
		while (!currentRobotNode.getState().equals(currentAlgoNode.getState()) && !(currentRobotNode.getParent()==null)) {
			/*System.out.println("Objective State : " + currentAlgoNode.getState());
			System.out.println("Current State : " + currentRobotNode.getState());*/
			MazeRobotAction previousAction = (MazeRobotAction) currentRobotNode.getPreviousAction();
			System.out.println("Go backward.");
			switch ((MazeRobotAction.Actions) previousAction.getValue()) {
			case GO_UP: {
				Action action = new MazeRobotAction(MazeRobotAction.Actions.GO_DOWN);
				Simulation.getQLearningAgent().setNextAction(action);
				State currentState = action.executeAction();
				Simulation.getQLearningAgent().setCurrentState(currentState,
						new MazeRobotReward(new StateActionPair(currentState, action)));
				Simulation.getQLearningAgent().Learn();
				currentRobotNode = currentRobotNode.getParent();
				System.out.println("salut!");
				break;
			}
			case GO_DOWN: {
				Action action = new MazeRobotAction(MazeRobotAction.Actions.GO_UP);
				Simulation.getQLearningAgent().setNextAction(action);
				State currentState = action.executeAction();
				Simulation.getQLearningAgent().setCurrentState(currentState,
						new MazeRobotReward(new StateActionPair(currentState, action)));
				Simulation.getQLearningAgent().Learn();
				currentRobotNode = currentRobotNode.getParent();
				break;
			}
			case GO_LEFT: {
				Action action = new MazeRobotAction(MazeRobotAction.Actions.GO_RIGHT);
				Simulation.getQLearningAgent().setNextAction(action);
				State currentState = action.executeAction();
				Simulation.getQLearningAgent().setCurrentState(currentState,
						new MazeRobotReward(new StateActionPair(currentState, action)));
				Simulation.getQLearningAgent().Learn();
				currentRobotNode = currentRobotNode.getParent();
				break;
			}
			case GO_RIGHT: {
				Action action = new MazeRobotAction(MazeRobotAction.Actions.GO_LEFT);
				Simulation.getQLearningAgent().setNextAction(action);
				State currentState = action.executeAction();
				Simulation.getQLearningAgent().setCurrentState(currentState,
						new MazeRobotReward(new StateActionPair(currentState, action)));
				Simulation.getQLearningAgent().Learn();
				currentRobotNode = currentRobotNode.getParent();
				break;
			}
			}
		}
	}

}
