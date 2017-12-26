package qLearning.problem;

import graphic.map.Room;
import qLearning.model.AbstractReward;
import qLearning.model.Reward;
import qLearning.model.StateActionPair;

public class MazeRobotReward extends AbstractReward implements Reward {

	public MazeRobotReward(StateActionPair stateActionPair) {
		this.stateActionPair = stateActionPair;
	}

	@Override
	public int getReward(StateActionPair stateActionPair) {
		Room room = ((MazeRobotState) stateActionPair.getState()).getRoom();
		if(stateActionPair.getState().getAvailableActions().size() == 1) {
			//Dead End
			return -1;
		}
		switch (room.getRoomType()) {
		case OBJECTIVE:
			return 1;
		case TRAP:
			return -1;
		case NEUTRAL:
			return 0;
		default:
			return 0;
		}
	}

}
