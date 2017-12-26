package graphic.map;

import graphic.GraphicWindow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Room {

	public enum RoomType {
		NEUTRAL, OBJECTIVE, TRAP
	};
	
	private RoomType roomType;
	private static double RoomSize = 1.0;
	private boolean topDoor;
	private boolean bottomDoor;
	private boolean leftDoor;
	private boolean rightDoor;
	private int XPos;
	private int YPos;
	
	
	public Room(boolean topDoor, boolean bottomDoor, boolean leftDoor, boolean rightDoor, RoomType roomType, int XPos, int YPos) {
		this.topDoor = topDoor;
		this.bottomDoor = bottomDoor;
		this.leftDoor = leftDoor;
		this.rightDoor = rightDoor;
		this.roomType = roomType;
		this.XPos = XPos;
		this.YPos = YPos;
	}

	public static void setSize(double RoomSize) {
		Room.RoomSize = RoomSize;
	}

	public static double getSize() {
		return RoomSize;
	}
	
	public int getXPos() {
		return XPos;
	}
	
	public int getYPos() {
		return YPos;
	}

	public AnchorPane graphicItem() {
		AnchorPane roomPane = new AnchorPane();
		Rectangle rectangle = new Rectangle(0, 0, RoomSize * GraphicWindow.getScale(),
				RoomSize * GraphicWindow.getScale());
		switch (roomType) {
		case NEUTRAL: {
			rectangle.setFill(Color.YELLOW);
			break;
		}
		case OBJECTIVE: {
			rectangle.setFill(Color.GREEN);
			break;
		}
		case TRAP: {
			rectangle.setFill(Color.RED);
			break;
		}
		}

		roomPane.getChildren().add(rectangle);

		if (topDoor) {
			Line line1 = new Line(0, 0, RoomSize * GraphicWindow.getScale() / 10, 0);
			line1.setStrokeWidth(5);

			Line line4 = new Line(RoomSize * GraphicWindow.getScale() * 9 / 10, 0, RoomSize * GraphicWindow.getScale(),
					0);
			line4.setStrokeWidth(5);
			roomPane.getChildren().addAll(line1, line4);
		} else {
			Line line = new Line(0, 0, RoomSize * GraphicWindow.getScale(), 0);
			line.setStrokeWidth(5);
			roomPane.getChildren().add(line);
		}

		if (bottomDoor) {
			Line line1 = new Line(0, RoomSize * GraphicWindow.getScale(), RoomSize * GraphicWindow.getScale() / 10,
					RoomSize * GraphicWindow.getScale());
			line1.setStrokeWidth(5);

			Line line4 = new Line(RoomSize * GraphicWindow.getScale() * 9 / 10, RoomSize * GraphicWindow.getScale(),
					RoomSize * GraphicWindow.getScale(), RoomSize * GraphicWindow.getScale());
			line4.setStrokeWidth(5);
			roomPane.getChildren().addAll(line1, line4);
		} else {
			Line line = new Line(0, RoomSize * GraphicWindow.getScale(), RoomSize * GraphicWindow.getScale(),
					RoomSize * GraphicWindow.getScale());
			line.setStrokeWidth(5);
			roomPane.getChildren().add(line);
		}

		if (leftDoor) {
			Line line1 = new Line(0, 0, 0, RoomSize * GraphicWindow.getScale() / 10);
			line1.setStrokeWidth(5);

			Line line4 = new Line(0, RoomSize * GraphicWindow.getScale() * 9 / 10, 0,
					RoomSize * GraphicWindow.getScale());
			line4.setStrokeWidth(5);
			roomPane.getChildren().addAll(line1, line4);
		} else {
			Line line = new Line(0, 0, 0, RoomSize * GraphicWindow.getScale());
			line.setStrokeWidth(5);
			roomPane.getChildren().add(line);
		}

		if (rightDoor) {
			Line line1 = new Line(RoomSize * GraphicWindow.getScale(), 0, RoomSize * GraphicWindow.getScale(),
					RoomSize * GraphicWindow.getScale() / 10);
			line1.setStrokeWidth(5);

			Line line4 = new Line(RoomSize * GraphicWindow.getScale(), RoomSize * GraphicWindow.getScale() * 9 / 10,
					RoomSize * GraphicWindow.getScale(), RoomSize * GraphicWindow.getScale());
			line4.setStrokeWidth(5);
			roomPane.getChildren().addAll(line1, line4);
		} else {
			Line line = new Line(RoomSize * GraphicWindow.getScale(), 0, RoomSize * GraphicWindow.getScale(),
					RoomSize * GraphicWindow.getScale());
			line.setStrokeWidth(5);
			roomPane.getChildren().add(line);
		}

		return roomPane;
	}

}
