package graphic;

import graphic.map.Room;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import robot.Simulation;

public class MapDisplay extends AnchorPane {

	private Circle robotCircle;

	private static double scale;

	public MapDisplay() {

		int mapPaneMaxWidth = 900;
		int mapPaneMaxHeight = 600;

		setMapPaneSize(mapPaneMaxWidth, mapPaneMaxHeight);

		Rectangle map = new Rectangle(Simulation.getMap().getRealWidth() * scale,
				Simulation.getMap().getRealHeight() * scale);
		map.setFill(Color.YELLOW);
		map.setOpacity(0.5);
		getChildren().add(map);

		setRooms();
		setRobot();

		//setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.MEDIUM)));
	}

	public static double getScale() {
		return scale;
	}

	private void setMapPaneSize(int mapPaneMaxWidth, int mapPaneMaxHeight) {
		// Some tricky strange operations to optimize the mapPane size, preserving scale
		int magicPixelScale = (mapPaneMaxHeight * 1000) / (mapPaneMaxWidth);
		int magicMeterScale = ((int) (Simulation.getMap().getHeight() * 1000))
				/ ((int) (Simulation.getMap().getWidth() * 1));

		if (magicPixelScale == magicMeterScale) {
			scale = mapPaneMaxHeight / Simulation.getMap().getHeight();
			setSize(mapPaneMaxWidth, mapPaneMaxHeight);
		} else if (magicPixelScale > magicMeterScale) {
			scale = mapPaneMaxWidth / Simulation.getMap().getWidth();
			setSize(mapPaneMaxWidth, (int) (Simulation.getMap().getHeight() * scale));
		} else if (magicPixelScale < magicMeterScale) {
			scale = mapPaneMaxHeight / Simulation.getMap().getHeight();
			setSize((int) (Simulation.getMap().getWidth() * scale), mapPaneMaxHeight);
		}
	}

	private void setSize(int width, int height) {
		setMaxWidth(width);
		setMinWidth(width);
		setMaxHeight(height);
		setMinHeight(height);
	}

	private void setRobot() {
		robotCircle = new Circle(
				(Room.getSize() / 2) * scale + Simulation.getRobot().getXPos() * Room.getSize() * scale,
				(Room.getSize() / 2) * scale + Simulation.getRobot().getYPos() * Room.getSize() * scale,
				Simulation.getRobot().getRadius() * scale);
		robotCircle.setFill(Color.BROWN);
		getChildren().add(robotCircle);
	}

	private void setRooms() {
		for (Room room : Simulation.getMap().getRooms().values()) {
			AnchorPane graphic = room.graphicItem();
			AnchorPane.setTopAnchor(graphic, room.getYPos() * Room.getSize() * scale);
			AnchorPane.setLeftAnchor(graphic, room.getXPos() * Room.getSize() * scale);
			getChildren().add(graphic);
		}
	}

	public void updateRobotCoordinates() {
		robotCircle.setCenterX((Room.getSize() / 2) * scale + Simulation.getRobot().getXPos() * Room.getSize() * scale);
		robotCircle.setCenterY((Room.getSize() / 2) * scale + Simulation.getRobot().getYPos() * Room.getSize() * scale);
	}

}
