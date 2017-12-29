package graphic.map;

import graphic.GraphicWindow;
import javafx.scene.control.Label;
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
	
	public RoomType getRoomType() {
		return roomType;
	}

	public boolean topDoor() {
		return topDoor;
	}
	
	public boolean bottomDoor() {
		return bottomDoor;
	}
	
	public boolean rightDoor() {
		return rightDoor;
	}
	
	public boolean leftDoor() {
		return leftDoor;
	}
	
	public void setTopDoor(boolean topDoor) {
		this.topDoor = topDoor;
	}
	
	public void setBottomDoor(boolean bottomDoor) {
		this.bottomDoor = bottomDoor;
	}
	
	public void setLeftDoor(boolean leftDoor) {
		this.leftDoor = leftDoor;
	}
	
	public void setRightDoor(boolean rightDoor) {
		this.rightDoor = rightDoor;
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

		if (bottomDoor) {
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

		if (topDoor) {
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

		setQLabels(roomPane);
		return roomPane;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + XPos;
		result = prime * result + YPos;
		result = prime * result + (bottomDoor ? 1231 : 1237);
		result = prime * result + (leftDoor ? 1231 : 1237);
		result = prime * result + (rightDoor ? 1231 : 1237);
		result = prime * result + ((roomType == null) ? 0 : roomType.hashCode());
		result = prime * result + (topDoor ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (XPos != other.XPos)
			return false;
		if (YPos != other.YPos)
			return false;
		if (bottomDoor != other.bottomDoor)
			return false;
		if (leftDoor != other.leftDoor)
			return false;
		if (rightDoor != other.rightDoor)
			return false;
		if (roomType != other.roomType)
			return false;
		if (topDoor != other.topDoor)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Room [XPos=" + XPos + ", YPos=" + YPos + "]";
	}

	private void setQLabels(AnchorPane roomPane) {
		double maxWidth = RoomSize * GraphicWindow.getScale() * 0.5;
		double maxHeight = RoomSize * GraphicWindow.getScale() * 0.25;
		Label UP = new Label("UP : ");
		UP.setMaxWidth(maxWidth);
		UP.setMaxHeight(maxHeight);
		UP.setStyle("-fx-font-size : " + (int) Math.floor(maxWidth/4) +"px");
		AnchorPane.setLeftAnchor(UP, RoomSize * GraphicWindow.getScale() *0.05);
		Label DOWN = new Label("DOWN : ");
		DOWN.setMaxWidth(maxWidth);
		DOWN.setMaxHeight(maxHeight);
		DOWN.setStyle("-fx-font-size : " + (int) Math.floor(maxWidth/4) +"px");
		AnchorPane.setTopAnchor(DOWN, RoomSize * GraphicWindow.getScale() * 0.25);
		AnchorPane.setLeftAnchor(DOWN, RoomSize * GraphicWindow.getScale() *0.05);
		Label LEFT = new Label("LEFT : ");
		LEFT.setMaxWidth(maxWidth);
		LEFT.setMaxHeight(maxHeight);
		LEFT.setStyle("-fx-font-size : " + (int) Math.floor(maxWidth/4) +"px");
		AnchorPane.setTopAnchor(LEFT, RoomSize * GraphicWindow.getScale() * 0.5);
		AnchorPane.setLeftAnchor(LEFT, RoomSize * GraphicWindow.getScale() *0.05);
		Label RIGHT = new Label("RIGHT : ");
		RIGHT.setMaxWidth(maxWidth);
		RIGHT.setMaxHeight(maxHeight);
		RIGHT.setStyle("-fx-font-size : " + (int) Math.floor(maxWidth/4) +"px");
		AnchorPane.setTopAnchor(RIGHT, RoomSize * GraphicWindow.getScale() * 0.75);
		AnchorPane.setLeftAnchor(RIGHT, RoomSize * GraphicWindow.getScale() *0.05);
		roomPane.getChildren().addAll(UP, DOWN, LEFT, RIGHT);
	}
}
