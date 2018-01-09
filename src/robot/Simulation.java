package robot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import graphic.GraphicWindow;
import graphic.map.Map;
import graphic.map.Room;
import iterativeDeepeningGraphSearch.core.DepthLimitedSearch;
import iterativeDeepeningGraphSearch.problem.GraphSearchProblem;
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
	public static int delay = 50;
	public static final int explorationsCycles = 300;
	public static boolean displayON;

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

		ArrayList<Room> rooms = new ArrayList<>();

		rooms.add(new Room(true, false, false, true, Room.RoomType.NEUTRAL, 0, 0));
		rooms.add(new Room(true, false, true, true, Room.RoomType.NEUTRAL, 1, 0));
		rooms.add(new Room(true, false, true, true, Room.RoomType.NEUTRAL, 2, 0));
		rooms.add(new Room(true, false, true, true, Room.RoomType.NEUTRAL, 3, 0));
		rooms.add(new Room(true, false, true, false, Room.RoomType.NEUTRAL, 4, 0));

		rooms.add(new Room(true, true, false, true, Room.RoomType.NEUTRAL, 0, 1));
		rooms.add(new Room(true, true, true, true, Room.RoomType.NEUTRAL, 1, 1));
		rooms.add(new Room(true, true, true, true, Room.RoomType.NEUTRAL, 2, 1));
		rooms.add(new Room(true, true, true, true, Room.RoomType.NEUTRAL, 3, 1));
		rooms.add(new Room(true, true, true, false, Room.RoomType.NEUTRAL, 4, 1));

		rooms.add(new Room(false, true, false, true, Room.RoomType.NEUTRAL, 0, 2));
		rooms.add(new Room(false, true, true, true, Room.RoomType.NEUTRAL, 1, 2));
		rooms.add(new Room(false, true, true, true, Room.RoomType.NEUTRAL, 2, 2));
		rooms.add(new Room(false, true, true, true, Room.RoomType.NEUTRAL, 3, 2));
		rooms.add(new Room(false, true, true, false, Room.RoomType.OBJECTIVE, 4, 2));

		setMap(new Map(60, 60));
		// map.addRooms(rooms);
		Room initRoom = new Room(true, true, true, true, Room.RoomType.OBJECTIVE, 30, 30);
		map.addRoom(initRoom);
		map.randomlyGenerateMaze(initRoom);

		robot = randomlyInitializedRobot();
		// robot = new Robot(0, 0, 0.2);
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

	public static GraphicWindow getGraphicWindow() {
		return graphicWindow;
	}

	public static QLearningAgent getQLearningAgent() {
		return qLearningAgent;
	}

	public void runSimulation() {
		displayON = false;
		for (int i = 0; i < explorationsCycles; i++) {
			// robot = new Robot(0, 0, 0.2);
			if (displayON) {
				graphicWindow.updateGraphicItems();
			try {
				Thread.sleep(2 * delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
			initState = new MazeRobotState(map.getRoom(robot.getXPos(), robot.getYPos()));
			exploreWithIDGS(initState);

			System.out.println(LocalDateTime.now() + "  End of try. Reinitialize...");
			attemptsNumber += 1;
			robot = randomlyInitializedRobot();
			initState = new MazeRobotState(map.getRoom(robot.getXPos(), robot.getYPos()));
			System.out.println("Init State : " + initState);
			qLearningAgent = new QLearningAgent(initState, null, null);
		}
		QLearningAgent.refreshEpsilon(0.4);
		displayON = true;
		attemptsNumber = 0;
		while (true) {
			Action nextAction = qLearningAgent.getAction();
			if (nextAction == null) {
				graphicWindow.updateGraphicItems();
				System.out.println(LocalDateTime.now() + "  End of try. Reinitialize...");
				attemptsNumber += 1;
				// robot = new Robot(2, 1, 0.2);
				robot = randomlyInitializedRobot();
				initState = new MazeRobotState(map.getRoom(robot.getXPos(), robot.getYPos()));
				qLearningAgent = new QLearningAgent(initState, null, null);
			} else {
				MazeRobotState currentState = (MazeRobotState) nextAction.executeAction();
				// System.out.println(currentState);
				qLearningAgent.setCurrentState(currentState,
						new MazeRobotReward(new StateActionPair(currentState, nextAction)));
			}
			// System.out.println("Robot XPos : " + robot.getXPos());
			// System.out.println("Robot YPos : " + robot.getYPos());
		}
	}

	private Robot randomlyInitializedRobot() {
		ArrayList<Room> possibleRooms = Copy.roomsDeepCopy(map.getRooms().values());
		Collection<Room> roomsToRemove = new ArrayList<>();
		for (Room room : possibleRooms) {
			if (room.getRoomType() == Room.RoomType.OBJECTIVE) {
				roomsToRemove.add(room);
			}
		}
		possibleRooms.removeAll(roomsToRemove);
		Room randomRoom = Random.selectRandomRoomFrom(possibleRooms);
		return new Robot(randomRoom.getXPos(), randomRoom.getYPos(), 0.2);
	}

	public void exploreWithIDGS(MazeRobotState initState) {
		// IterativeDeepeningSearch explorer = new IterativeDeepeningSearch();
		// explorer.solve(new GraphSearchProblem(initState), 0);
		DepthLimitedSearch explorer = new DepthLimitedSearch();
		explorer.solve(new GraphSearchProblem(initState), Integer.MAX_VALUE);
	}

	public static int getAttemptsNumber() {
		return attemptsNumber;
	}
}
