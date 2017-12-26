package qLearning.problem;

import java.util.ArrayList;

import graphic.map.Room;
import qLearning.model.Action;
import qLearning.model.State;

public class MazeRobotState implements State {

	private Room room;

	public MazeRobotState(Room room) {
		this.room = room;
	}

	@Override
	public ArrayList<Action> getAvailableActions() {
		ArrayList<Action> availableActions = new ArrayList<>();
		if(room.topDoor()) {
			availableActions.add(new MazeRobotAction(MazeRobotAction.Actions.GO_UP));
		}
		if(room.bottomDoor()) {
			availableActions.add(new MazeRobotAction(MazeRobotAction.Actions.GO_DOWN));
		}
		if(room.leftDoor()) {
			availableActions.add(new MazeRobotAction(MazeRobotAction.Actions.GO_LEFT));
		}
		if(room.rightDoor()) {
			availableActions.add(new MazeRobotAction(MazeRobotAction.Actions.GO_RIGHT));
		}
		return availableActions;
	}

	public Room getRoom() {
		return room;
	}
	@Override
	public boolean isTerminal() {
		Room.RoomType roomType = room.getRoomType();
		switch (roomType) {
		case OBJECTIVE:
			return true;
		case TRAP:
			return true;
		default:
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((room == null) ? 0 : room.hashCode());
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
		MazeRobotState other = (MazeRobotState) obj;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MazeRobotState [room=" + room + "]";
	}
	
	

}
