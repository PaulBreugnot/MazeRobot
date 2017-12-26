package robot;

import java.util.ArrayList;

import graphic.GraphicWindow;
import graphic.map.Map;
import graphic.map.Room;
import javafx.application.Application;
import javafx.stage.Stage;

public class Simulation extends Application {

	private Map map;
	
	private int attemptsNumber;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		setMap(new Map(5, 1));
		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(new Room(false, false, false, true, Room.RoomType.TRAP, 0, 0));
		rooms.add(new Room(false, false, true, true, Room.RoomType.NEUTRAL, 1, 0));
		rooms.add(new Room(false, false, true, true, Room.RoomType.NEUTRAL, 2, 0));
		rooms.add(new Room(false, false, true, true, Room.RoomType.NEUTRAL, 3, 0));
		rooms.add(new Room(false, false, true, false, Room.RoomType.OBJECTIVE, 4, 0));
		map.setRooms(rooms);
		GraphicWindow graphicWindow = new GraphicWindow(stage, this);
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void runSimulation() {
		
	}
	
	public int getAttemptsNumber() {
		return attemptsNumber;
	}
}
