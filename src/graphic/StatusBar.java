package graphic;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import robot.Simulation;

public class StatusBar extends HBox {
	private Simulation simulation;
	private Label AttemptsLabel;
	private Button RunButton;

	public StatusBar(Simulation simulation) {
		this.simulation = simulation;
		
		BorderPane.setMargin(this, new Insets(12,12,12,12));
		this.setMaxHeight(100);

		this.setAlignment(Pos.CENTER);
		this.setSpacing(30);

		setRunButton();
		setAttemptsLabel();
		
		this.getChildren().addAll(new Label("Attempts : "), AttemptsLabel, RunButton);
	}
	
	private void setAttemptsLabel() {
		AttemptsLabel = new Label(Integer.toString(Simulation.getAttemptsNumber()));
	}
	
	private void setRunButton() {
		RunButton = new Button("Run Simulation");

		RunButton.setOnAction(event -> {
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws Exception {
					simulation.runSimulation();
					return null;
				}
			};
			new Thread(task).start();
		});
	}
	


	public void updateLabels() {
		AttemptsLabel.setText(Integer.toString(Simulation.getAttemptsNumber()));
	}
}
