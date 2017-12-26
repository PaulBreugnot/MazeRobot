package graphic;

import graphic.map.Room;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import robot.Simulation;

public class GraphicWindow {
	private Stage stage;
	private Scene scene;

	private static double scale;

	private Label AttemptsLabel;
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
		AnchorPane root = new AnchorPane();
		scene = new Scene(root);

		VBox mainVBox = new VBox();
		mainVBox.setSpacing(50);
		mainVBox.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(mainVBox, 10.0);
		AnchorPane.setBottomAnchor(mainVBox, 10.0);
		AnchorPane.setLeftAnchor(mainVBox, 10.0);
		AnchorPane.setRightAnchor(mainVBox, 10.0);

		mainVBox.getChildren().addAll(TopPart(), MapView());

		root.getChildren().add(mainVBox);
		stage.setScene(scene);
		stage.show();

	}

	private HBox TopPart() {
		HBox top = new HBox();
		VBox.setVgrow(top, Priority.NEVER);
		top.setMaxHeight(100);

		AttemptsLabel = new Label(Integer.toString(Simulation.getAttemptsNumber()));
		top.setAlignment(Pos.CENTER);
		top.setSpacing(30);

		top.getChildren().addAll(new Label("Attempts : "), AttemptsLabel, setRunButton());

		return top;
	}

	private Button setRunButton() {
		Button runButton = new Button("Run Simulation");

		runButton.setOnAction(event -> {
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws Exception {
					simulation.runSimulation();
					return null;
				}
			};
			new Thread(task).start();
		});

		return runButton;
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
		Platform.runLater(() -> updateLabels());
		Platform.runLater(() -> updateRobotCoordinates());
	}

	public void updateLabels() {
		AttemptsLabel.setText(Integer.toString(Simulation.getAttemptsNumber()));
	}

	public void updateRobotCoordinates() {
		robotCircle.setCenterX((Room.getSize() / 2) * scale + Simulation.getRobot().getXPos() * Room.getSize() * scale);
		robotCircle.setCenterY((Room.getSize() / 2) * scale + Simulation.getRobot().getYPos() * Room.getSize() * scale);
	}
}
