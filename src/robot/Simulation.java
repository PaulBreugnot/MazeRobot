package robot;

import graphic.GraphicWindow;
import graphic.map.Map;
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
		setMap(new Map(5, 5));
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
