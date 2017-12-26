package graphic;

import graphic.map.Room;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import robot.Simulation;

public class GraphicWindow {
	private Stage stage;
	private Scene scene;

	private static double scale;

	private Label AttemptsLabel;

	private Simulation simulation;

	public GraphicWindow(Stage stage, Simulation simulation) {
		this.stage = stage;
		this.simulation = simulation;
		setMainView();
	}

	private void setMainView() {
		AnchorPane root = new AnchorPane();
		scene = new Scene(root);

		VBox mainVBox = new VBox();
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

		AttemptsLabel = new Label(Integer.toString(simulation.getAttemptsNumber()));
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

		Rectangle map = new Rectangle(simulation.getMap().getRealWidth() * scale,
				simulation.getMap().getRealHeight() * scale);
		map.setFill(Color.YELLOW);
		map.setOpacity(0.5);
		mapPane.getChildren().add(map);

		setRobot(mapPane);
		setRooms(mapPane);

		return mapPane;

	}

	private void setMapPaneSize(AnchorPane mapPane, int mapPaneMaxWidth, int mapPaneMaxHeight) {
		// Some tricky strange operations to optimize the mapPane size, preserving scale
		int magicPixelScale = (mapPaneMaxHeight * 1000) / (mapPaneMaxWidth);
		int magicMeterScale = ((int) (simulation.getMap().getHeight() * 1000))
				/ ((int) (simulation.getMap().getWidth() * 1));

		if (magicPixelScale == magicMeterScale) {
			scale = mapPaneMaxHeight / simulation.getMap().getHeight();
			setSize(mapPane, mapPaneMaxWidth, mapPaneMaxHeight);
		} else if (magicPixelScale > magicMeterScale) {
			scale = mapPaneMaxWidth / simulation.getMap().getWidth();
			setSize(mapPane, mapPaneMaxWidth, (int) (simulation.getMap().getHeight() * scale));
		} else if (magicPixelScale < magicMeterScale) {
			scale = mapPaneMaxHeight / simulation.getMap().getHeight();
			setSize(mapPane, (int) (simulation.getMap().getWidth() * scale), mapPaneMaxHeight);
		}
	}

	private void setSize(AnchorPane mapPane, int width, int height) {
		mapPane.setMaxWidth(width);
		mapPane.setMinWidth(width);
		mapPane.setMaxHeight(height);
		mapPane.setMinHeight(height);
	}

	private void setRobot(AnchorPane mapPane) {
	}

	private void setRooms(AnchorPane mapPane) {
		Room room = new Room(false, true, false, true, Room.RoomType.TRAP);
		mapPane.getChildren().add(room.graphicItem());
	}
	
	public static double getScale() {
		return scale;
	}
}
