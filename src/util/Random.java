package util;

import java.util.Collection;

import graphic.map.Room;
import qLearning.model.Action;

public abstract class Random {

	public static Room selectRandomRoomFrom(Collection<Room> items) {
		int n = items.size();
		if(n>0) {
		Room rooms[] = items.toArray(new Room[0]);
		return rooms[((int) Math.floor(Math.random() * n))];
		}
		return null;
	}
	
	public static Action selectRandomActionFrom(Collection<Action> items) {
		int n = items.size();
		if(n>0) {
		Action actions[] = items.toArray(new Action[0]);
		return actions[((int) Math.floor(Math.random() * n))];
		}
		return null;
	}
}
