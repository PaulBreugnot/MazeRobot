package util;

import java.util.Collection;

import graphic.map.Room;

public abstract class Random {

	public static Room selectRandomFrom(Collection<Room> items) {
		int n = items.size();
		if(n>0) {
		Room rooms[] = items.toArray(new Room[0]);
		return rooms[((int) Math.floor(Math.random() * n))];
		}
		return null;
	}
}
