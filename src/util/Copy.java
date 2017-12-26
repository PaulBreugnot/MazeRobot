package util;

import java.util.ArrayList;
import java.util.Collection;

import graphic.map.Room;

public abstract class Copy {

	public static ArrayList<Room> roomsDeepCopy(Collection<Room> rooms){
		ArrayList<Room> roomsDeepCopy = new ArrayList<>();
		for(Room room : rooms) {
			roomsDeepCopy.add(room);
		}
		return roomsDeepCopy;
	}
}
