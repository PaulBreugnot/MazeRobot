package graphic;

import graphic.map.Room;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import qLearning.problem.MazeRobotState;
import robot.Simulation;

public class GraphicWindow {
	private Stage stage;
	private Scene scene;
	private StatusBar statusBar;

	private static double scale;

	private Circle robotCircle;

	private Simulation simulation;

	public GraphicWindow(Stage stage, Simulation simulation) {
		this.stage = stage;
		this.simulation = simulation;
		setMainView();
	}

	private void setMainView() {
		stage.setWidth(1000);
		stage.setHeight(800);
		BorderPane root = new BorderPane();
		scene = new Scene(root);

		//mainVBox.setSpacing(50);
		//mainVBox.setAlignment(Pos.CENTER);
		/*AnchorPane.setTopAnchor(mainVBox, 10.0);
		AnchorPane.setBottomAnchor(mainVBox, 10.0);
		AnchorPane.setLeftAnchor(mainVBox, 10.0);
		AnchorPane.setRightAnchor(mainVBox, 10.0);

		mainVBox.getChildren().addAll(TopPart(), MapView());

		root.getChildren().add(mainVBox);*/
		statusBar = new StatusBar(simulation);
		root.setTop(statusBar);
		stage.setScene(scene);
		stage.show();

	}

	private AnchorPane MapView() {

		int mapPaneMaxWidth = 900;
		int mapPaneMaxHeight = 600;

		AnchorPane mapPane = new AnchorPane(); // Every elements will be place using this pane landmark
		VBox.setVgrow(mapPane, Priority.ALWAYS);
		setMapPaneSize(mapPane, mapPaneMaxWidth, mapPaneMaxHeight);

		Rectangle map = new Rectangle(Simulation.getMap().getRealWidth() * scale,
				Simulation.getMap().getRealHeight() * scale);
		map.setFill(Color.YELLOW);
		map.setOpacity(0.5);
		mapPane.getChildren().add(map);

		setRooms(mapPane);
		setRobot(mapPane);

		return mapPane;

	}

	private void setMapPaneSize(AnchorPane mapPane, int mapPaneMaxWidth, int mapPaneMaxHeight) {
		// Some tricky strange operations to optimize the mapPane size, preserving scale
		int magicPixelScale = (mapPaneMaxHeight * 1000) / (mapPaneMaxWidth);
		int magicMeterScale = ((int) (Simulation.getMap().getHeight() * 1000))
				/ ((int) (Simulation.getMap().getWidth() * 1));

		if (magicPixelScale == magicMeterScale) {
			scale = mapPaneMaxHeight / Simulation.getMap().getHeight();
			setSize(mapPane, mapPaneMaxWidth, mapPaneMaxHeight);
		} else if (magicPixelScale > magicMeterScale) {
			scale = mapPaneMaxWidth / Simulation.getMap().getWidth();
			setSize(mapPane, mapPaneMaxWidth, (int) (Simulation.getMap().getHeight() * scale));
		} else if (magicPixelScale < magicMeterScale) {
			scale = mapPaneMaxHeight / Simulation.getMap().getHeight();
			setSize(mapPane, (int) (Simulation.getMap().getWidth() * scale), mapPaneMaxHeight);
		}
	}

	private void setSize(AnchorPane mapPane, int width, int height) {
		mapPane.setMaxWidth(width);
		mapPane.setMinWidth(width);
		mapPane.setMaxHeight(height);
		mapPane.setMinHeight(height);
	}

	private void setRobot(AnchorPane mapPane) {
		robotCircle = new Circle(
				(Room.getSize() / 2) * scale + Simulation.getRobot().getXPos() * Room.getSize() * scale,
				(Room.getSize() / 2) * scale + Simulation.getRobot().getYPos() * Room.getSize() * scale,
				Simulation.getRobot().getRadius() * scale);
		robotCircle.setFill(Color.BROWN);
		mapPane.getChildren().add(robotCircle);
	}

	private void setRooms(AnchorPane mapPane) {
		for (Room room : Simulation.getMap().getRooms().values()) {
			AnchorPane graphic = room.graphicItem();
			AnchorPane.setTopAnchor(graphic, room.getYPos() * Room.getSize() * scale);
			AnchorPane.setLeftAnchor(graphic, room.getXPos() * Room.getSize() * scale);
			mapPane.getChildren().add(graphic);
		}
	}

	public static double getScale() {
		return scale;
	}

	public void updateGraphicItems() {
		Platform.runLater(() -> statusBar.updateLabels());
		Platform.runLater(() -> updateRobotCoordinates());
		Platform.runLater(() -> updateRoomQLabels());
	}

	private void updateRobotCoordinates() {
		robotCircle.setCenterX((Room.getSize() / 2) * scale + Simulation.getRobot().getXPos() * Room.getSize() * scale);
		robotCircle.setCenterY((Room.getSize() / 2) * scale + Simulation.getRobot().getYPos() * Room.getSize() * scale);
	}

	private void updateRoomQLabels() {
		if (Simulation.getQLearningAgent().getPreviousState() != null) {
			((MazeRobotState) Simulation.getQLearningAgent().getPreviousState()).getRoom().updateQValue();
		}
	}
}
