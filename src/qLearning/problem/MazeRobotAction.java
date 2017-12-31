package qLearning.problem;

import qLearning.model.Action;
import qLearning.model.State;
import robot.Simulation;

public class MazeRobotAction implements Action {

	public enum Actions {
		GO_UP, GO_DOWN, GO_LEFT, GO_RIGHT
	}

	public Actions action;

	public MazeRobotAction(Actions action) {
		this.action = action;
	}

	@Override
	public Actions getValue() {
		return action;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
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
		MazeRobotAction other = (MazeRobotAction) obj;
		if (action != other.action)
			return false;
		return true;
	}

	@Override
	public State executeAction() {
		System.out.println("Executed Action : " + action);
		switch (action) {
		case GO_UP :{
			Simulation.getRobot().incrementYPos(1);
			break;
		}
		case GO_DOWN :{
			Simulation.getRobot().incrementYPos(-1);
			break;
		}
		case GO_RIGHT :{
			Simulation.getRobot().incrementXPos(1);
			break;
		}
		case GO_LEFT :{
			Simulation.getRobot().incrementXPos(-1);
			break;
		}
		}

		Simulation.getGraphicWindow().updateGraphicItems();
		try {
			Thread.sleep(Simulation.delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new MazeRobotState(Simulation.getMap().getRoom(Simulation.getRobot().getXPos(), Simulation.getRobot().getYPos()));
	}

	@Override
	public String toString() {
		return "MazeRobotAction [action=" + action + "]";
	}

}
