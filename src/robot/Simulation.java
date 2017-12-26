package robot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import graphic.GraphicWindow;
import graphic.map.Map;
import graphic.map.Room;
import javafx.application.Application;
import javafx.stage.Stage;
import qLearning.QLearningAgent;
import qLearning.model.Action;
import qLearning.model.StateActionPair;
import qLearning.problem.MazeRobotReward;
import qLearning.problem.MazeRobotState;
import robot.model.Robot;
import util.Copy;
import util.Random;

public class Simulation extends Application {

	private static Map map;
	private static Robot robot;
	private static QLearningAgent qLearningAgent;
	private static int attemptsNumber = 0;
	
	private static GraphicWindow graphicWindow;
	private static MazeRobotState initState;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		/*ArrayList<Room> rooms = new ArrayList<>();
		
		rooms.add(new Room(true, false, false, true, Room.RoomType.TRAP, 0, 0));
		rooms.add(new Room(true, false, true, true, Room.RoomType.TRAP, 1, 0));
		rooms.add(new Room(true, false, true, true, Room.RoomType.TRAP, 2, 0));
		rooms.add(new Room(true, false, true, true, Room.RoomType.TRAP, 3, 0));
		rooms.add(new Room(true, false, true, false, Room.RoomType.OBJECTIVE, 4, 0));
		
		rooms.add(new Room(true, true, false, true, Room.RoomType.NEUTRAL, 0, 1));
		rooms.add(new Room(true, true, true, true, Room.RoomType.NEUTRAL, 1, 1));
		rooms.add(new Room(true, true, true, true, Room.RoomType.NEUTRAL, 2, 1));
		rooms.add(new Room(true, true, true, true, Room.RoomType.NEUTRAL, 3, 1));
		rooms.add(new Room(true, true, true, false, Room.RoomType.NEUTRAL, 4, 1));
		
		rooms.add(new Room(false, true, false, true, Room.RoomType.TRAP, 0, 2));
		rooms.add(new Room(false, true, true, true, Room.RoomType.TRAP, 1, 2));
		rooms.add(new Room(false, true, true, true, Room.RoomType.TRAP, 2, 2));
		rooms.add(new Room(false, true, true, true, Room.RoomType.TRAP, 3, 2));
		rooms.add(new Room(false, true, true, false, Room.RoomType.TRAP, 4, 2));*/

		setMap(new Map(10, 10));
		//map.addRooms(rooms);
		Room initRoom = new Room(true, true, true, true, Room.RoomType.OBJECTIVE, 2, 2);
		map.addRoom(initRoom);
		map.randomlyGenerateMaze(initRoom);
		robot = randomlyInitializedRobot();
		initState = new MazeRobotState(map.getRoom(robot.getXPos(), robot.getYPos()));
		graphicWindow = new GraphicWindow(stage, this);
		
		qLearningAgent = new QLearningAgent(initState, null, null);
	}

	public static void setMap(Map map) {
		Simulation.map = map;
	}
	
	public static Map getMap() {
		return map;
	}
	
	public static Robot getRobot() {
		return robot;
	}
	
	public void runSimulation() {
		while (true) {
			Action nextAction = qLearningAgent.getAction();
			if (nextAction == null) {
				System.out.println(LocalDateTime.now() + "  Fail! Restart simulation.");
				attemptsNumber += 1;
				//robot = new Robot(2, 1, 0.2);
				robot = randomlyInitializedRobot();
				initState = new MazeRobotState(map.getRoom(robot.getXPos(), robot.getYPos()));
				qLearningAgent = new QLearningAgent(initState, null, null);
			} else {
				nextAction.executeAction();
				System.out.println(robot.getYPos());
				MazeRobotState currentState = new MazeRobotState(map.getRoom(robot.getXPos(), robot.getYPos()));
				System.out.println(currentState);
				qLearningAgent.setCurrentState(currentState, new MazeRobotReward(new StateActionPair(currentState, nextAction)));
			}
			System.out.println("Robot XPos : " + robot.getXPos());
			System.out.println("Robot YPos : " + robot.getYPos());
			graphicWindow.updateGraphicItems();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Robot randomlyInitializedRobot() {
		ArrayList<Room> possibleRooms = Copy.roomsDeepCopy(map.getRooms().values());
		Collection<Room> roomsToRemove = new ArrayList<>();
		for(Room room : possibleRooms) {
			if(room.getRoomType() == Room.RoomType.OBJECTIVE) {
				roomsToRemove.add(room);
			}
		}
		possibleRooms.removeAll(roomsToRemove);
		Room randomRoom = Random.selectRandomFrom(possibleRooms);
		return new Robot(randomRoom.getXPos(), randomRoom.getYPos(), 0.2);
	}
	
	public static int getAttemptsNumber() {
		return attemptsNumber;
	}
}
