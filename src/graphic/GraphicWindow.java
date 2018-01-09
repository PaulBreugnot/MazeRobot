package graphic;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import qLearning.problem.MazeRobotState;
import robot.Simulation;

public class GraphicWindow {
	private Stage stage;
	private Scene scene;
	private StatusBar statusBar;
	private MapDisplay mapDisplay;

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
		mapDisplay = new MapDisplay();
		root.setTop(statusBar);
		root.setCenter(mapDisplay);
		stage.setScene(scene);
		stage.show();

	}

	/*public void updateGraphicItems() {
		Platform.runLater(() -> statusBar.updateLabels());
		Platform.runLater(() -> mapDisplay.updateRobotCoordinates());
		Platform.runLater(() -> updateRoomQLabels());
	}*/

	private void updateRoomQLabels() {
		if (Simulation.getQLearningAgent().getPreviousState() != null) {
			((MazeRobotState) Simulation.getQLearningAgent().getPreviousState()).getRoom().updateQValue();
		}
	}
}
